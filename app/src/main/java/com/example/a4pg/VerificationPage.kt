package com.example.a4pg

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.regex.Pattern



class VerificationPage : AppCompatActivity() {
    private lateinit var cameraPreviewView: PreviewView
    private lateinit var captureLicenseButton: Button
    private lateinit var captureFaceButton: Button
    private lateinit var licenseImagePreview: ImageView
    private lateinit var faceImagePreview: ImageView
    private lateinit var resultTextView: TextView
    private lateinit var licenseNumberInput: EditText
    private lateinit var cameraExecutor: ExecutorService

    data class LicenseData(
        var licenseNumber: String? = null,
        var fullName: String? = null,
        var dateOfBirth: String? = null,
        var address: String? = null,
        var state: String? = null,
        var zipCode: String? = null
    )

    private var imageCapture: ImageCapture? = null
    private var capturedLicenseBitmap: Bitmap? = null
    private var capturedFaceBitmap: Bitmap? = null

    companion object {
        private const val TAG = "VerificationPage"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        // Ontario-specific patterns
        private val LICENSE_REGEX = Pattern.compile("\\b[A-Z]{2}\\d{3}-\\d{5}-\\d{5}\\b") // Matches "DE101-40706-60905"
        private val NAME_REGEX = Pattern.compile("(?i)DONNE NOM\\s+([A-Z]+)\\s+([A-Z]+)") // Captures "DOE JOHN"
        private val DOB_REGEX = Pattern.compile("\\b\\d{4}/\\d{2}/\\d{2}\\b") // Matches "1966/09/05"
        private val ADDRESS_REGEX = Pattern.compile("(\\d+[A-Z]+)\\s+([A-Z]+),([A-Z]{2}),([A-Z0-9\\s]+)") // Matches "123ANYSTREET TORONTO,ON,M0M 0M0"
        private val LICENSE_EXPIRY_REGEX = Pattern.compile("EXP\\.EXP\\s+(\\d{4}/\\d{2}/\\d{2})") // Matches "2014/04/23"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verificationpage)

        cameraPreviewView = findViewById(R.id.view_finder)
        captureLicenseButton = findViewById(R.id.capture_license_button)
        captureFaceButton = findViewById(R.id.capture_face_button)
        licenseImagePreview = findViewById(R.id.license_image_preview)
        faceImagePreview = findViewById(R.id.face_image_preview)
        resultTextView = findViewById(R.id.text_result)
        licenseNumberInput = findViewById(R.id.license_number_input)

        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        captureLicenseButton.setOnClickListener { takePhoto(isLicense = true) }
        captureFaceButton.setOnClickListener { takePhoto(isLicense = false) }

        cameraExecutor = Executors.newSingleThreadExecutor()
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    private fun startCamera() {
        val cameraProviderFuture: ListenableFuture<ProcessCameraProvider> = ProcessCameraProvider.getInstance(this)
        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder().build().also {
                it.setSurfaceProvider(cameraPreviewView.surfaceProvider)
            }

            imageCapture = ImageCapture.Builder().build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(this, cameraSelector, preview, imageCapture)
            } catch (exc: Exception) {
                Log.e(TAG, "Use case binding failed", exc)
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto(isLicense: Boolean) {
        val imageCapture = imageCapture ?: return

        imageCapture.takePicture(ContextCompat.getMainExecutor(this), object : ImageCapture.OnImageCapturedCallback() {
            override fun onCaptureSuccess(image: ImageProxy) {
                val bitmap = image.toBitmap()
                image.close()
                if (isLicense) {
                    capturedLicenseBitmap = bitmap
                    licenseImagePreview.setImageBitmap(bitmap)
                    licenseImagePreview.visibility = ImageView.VISIBLE
                    extractLicenseNumber(bitmap)
                } else {
                    capturedFaceBitmap = bitmap
                    faceImagePreview.setImageBitmap(bitmap)
                    faceImagePreview.visibility = ImageView.VISIBLE
                    compareFaces()
                }
            }

            override fun onError(exception: ImageCaptureException) {
                Log.e(TAG, "Photo capture failed: ", exception)
            }
        })
    }

    private fun extractLicenseNumber(bitmap: Bitmap) {
        val image = InputImage.fromBitmap(bitmap, 0)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(image)
            .addOnSuccessListener { visionText ->
                val fullText = visionText.textBlocks.joinToString("\n") { it.text }
                Log.d(TAG, "Raw OCR Text:\n$fullText") // For debugging

                val licenseData = LicenseData().apply {
                    // License Number (e.g., DE101-40706-60905)
                    licenseNumber = LICENSE_REGEX.matcher(fullText).let { matcher ->
                        if (matcher.find()) matcher.group().trim() else null
                    }

                    // Full Name (e.g., DOE JOHN)
                    fullName = NAME_REGEX.matcher(fullText).let { matcher ->
                        if (matcher.find()) "${matcher.group(1)} ${matcher.group(2)}" else null
                    }

                    // Date of Birth (e.g., 1966/09/05)
                    dateOfBirth = DOB_REGEX.matcher(fullText).let { matcher ->
                        if (matcher.find()) matcher.group() else null
                    }

                    // Address (e.g., 123ANYSTREET TORONTO,ON,M0M 0M0)
                    address = ADDRESS_REGEX.matcher(fullText).let { matcher ->
                        if (matcher.find()) {
                            "${matcher.group(1)}, ${matcher.group(2)}, ${matcher.group(4)}"
                        } else null
                    }
                }

                updateUIWithExtractedData(licenseData)
            }
            .addOnFailureListener { e ->
                Log.e(TAG, "Text recognition failed", e)
            }
    }

    private fun formatLicenseNumber(rawNumber: String): String {
        return rawNumber.replace(" ", "")
            .replace("-", "")
            .let { if (it.length == 13) "${it.substring(0, 5)}-${it.substring(5)}" else it }
    }

    private fun updateUIWithExtractedData(data: LicenseData) {
        licenseNumberInput.setText(data.licenseNumber ?: "")

        val resultText = buildString {
            append("License: ${data.licenseNumber ?: "Not found"}\n")
            append("Name: ${data.fullName ?: "Not found"}\n")
            append("DOB: ${data.dateOfBirth ?: "Not found"}\n")
            append("Address: ${data.address ?: "Not found"}")
        }

        Log.d(TAG, "Extracted Data:\n$resultText") // Debug log
        resultTextView.text = resultText
    }

    private fun compareFaces() {
        if (capturedLicenseBitmap != null && capturedFaceBitmap != null) {
            Toast.makeText(this, "Face comparison feature coming soon!", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(this, "Please capture both images first.", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cameraExecutor.shutdown()
    }
}