package com.example.a4pg

import android.Manifest
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.ImageCapture
import androidx.camera.view.PreviewView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class VerificationPage : AppCompatActivity() {
    private lateinit var cameraPreviewView: PreviewView
    private lateinit var captureButton: Button
    private lateinit var resultTextView: TextView

    private var imageCapture: ImageCapture? = null

    companion object {
        private const val TAG = "VerificationPage"
        private const val REQUEST_CODE_PERMISSIONS = 10
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.verificationpage)

        // Initialize UI components
        cameraPreviewView = findViewById(R.id.viewFinder)
        captureButton = findViewById(R.id.capture_button)
        resultTextView = findViewById(R.id.text_result)

        // Request camera permissions
        if (allPermissionsGranted()) {
            startCamera()
        } else {
            ActivityCompat.requestPermissions(
                this, REQUIRED_PERMISSIONS, REQUEST_CODE_PERMISSIONS)
        }

        // Set up capture button listener
        captureButton.setOnClickListener {
            takePhoto()
        }
    }

    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (allPermissionsGranted()) {
                startCamera()
            } else {
                // Handle permission denial
                Log.d(TAG, "Permissions not granted by the user.")
                finish()
            }
        }
    }

    private fun startCamera() {
        // TODO: Implement camera startup
    }

    private fun takePhoto() {
        // TODO: Implement photo capturing
    }

    private fun recognizeTextFromImage(imageUri: Uri) {
        // TODO: Implement text recognition
    }
}
