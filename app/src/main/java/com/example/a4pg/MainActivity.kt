package com.example.a4pg

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Box
import android.os.Bundle
import android.widget.Button
import com.example.a4pg.CustomTypography
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHost
import androidx.navigation.NavHostController
import com.example.a4pg.ui.theme._4PGTheme


class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            _4PGTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("Election App") }
                        )
                    }
                ) { innerPadding ->
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(innerPadding)
                    ) {
                        MainContent(onLoginClick = {
                            val intent = Intent(this@MainActivity, VerificationPage::class.java)
                            startActivity(intent)
                        })

                    }
                }
            }
        }
    }
}


@Composable
fun MainContent(modifier: Modifier = Modifier, onLoginClick: () -> Unit) {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = modifier.fillMaxSize()) {
        // Tab Row
        TabRow(selectedTabIndex = selectedTabIndex) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = { Text("Home") }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = {
                    selectedTabIndex = 1
                    onLoginClick()
                },
                text = { Text("Verify Me") }
            )
            Tab(
                selected = selectedTabIndex == 2,
                onClick = { selectedTabIndex = 2 },
                text = { Text("Candidates") }
            )
            Tab(
                selected = selectedTabIndex == 3,
                onClick = { selectedTabIndex = 3 },
                text = { Text("Voting") }
            )
        }

        // Tab Content
        when (selectedTabIndex) {
            0 -> HomeScreen()
            1 -> VerificationScreen()
            2 -> CandidatesScreen()
            3 -> VotingScreen()
        }
    }
}

@Composable
fun Greeting(name: String) {
}

// Landing Page with news and X updates
@Composable
fun HomeScreen() {
    var selectedTabIndex by remember { mutableStateOf(0) }

    Column(modifier = Modifier.fillMaxSize()) {
        // Tab Row
        TabRow(selectedTabIndex = selectedTabIndex) {
            Tab(
                selected = selectedTabIndex == 0,
                onClick = { selectedTabIndex = 0 },
                text = { Text("X Updates") }
            )
            Tab(
                selected = selectedTabIndex == 1,
                onClick = { selectedTabIndex = 1 },
                text = { Text("News") }
            )
        }

        // Tab Content
        when (selectedTabIndex) {
            0 -> HottestUpdatesTab()
            1 -> NewsTab()
        }
    }
}

// X Updates from Candidates
@Composable
fun HottestUpdatesTab() {
    Column(modifier = Modifier.fillMaxSize()) {
        PostItem(
            avatarResId = R.drawable.ford,
            name = "Doug Ford",
            date = "Feb 28",
            content = "Thank you, Ontario!\n\nI will never take your support for granted. I will wake up everyday to fight like I’ve never fought before.\n\nTogether, we’ll protect Ontario."
        )
        PostItem(
            avatarResId = R.drawable.mike,
            name = "Mike Schreiner",
            date = "Feb 27",
            content = "Today folks from across Ontario are heading to the polls.\n\nI know folks from across the province are ready for change. They're ready for a government that puts fairness first. Fairness for our housing market, fairness for our healthcare system, and fairness for our environment.\n\nNo matter who you're voting for, make sure you VOTE. Polls close at 9PM."
        )
        PostItem(
            avatarResId = R.drawable.bonnie,
            name = "Bonnie Crombie",
            date = "Mar 1",
            content = "Rebuilding takes time.\n\nOntario’s Liberals fought hard in this tough snap election to win official party status for the first time in seven years. And we aren't going anywhere. Last night, we earned 30% of the vote and we will continue building. Thank you to everyone who voted for change, who knocked on doors and showed up every day to help our campaign.\n\nI’m so proud to be your Ontario Liberal Leader!"
        )
        PostItem(
            avatarResId = R.drawable.ford,
            name = "Doug Ford",
            date = "Feb 27",
            content = "Thank you, Ontario!\n\nI will never take your support for granted. I will wake up everyday to fight like I’ve never fought before.\n\nTogether, we’ll protect Ontario."
        )
        PostItem(
            avatarResId = R.drawable.mike,
            name = "Mike Schreiner",
            date = "Feb 26",
            content = "Today folks from across Ontario are heading to the polls.\n\nI know folks from across the province are ready for change. They're ready for a government that puts fairness first. Fairness for our housing market, fairness for our healthcare system, and fairness for our environment.\n\nNo matter who you're voting for, make sure you VOTE. Polls close at 9PM."
        )
    }
}

// Politic News
@Composable
fun NewsTab() {
    val newsItems = listOf(
        News(
            imageResId = R.drawable.news_image1,
            author = "Nadine Yousif",
            source = "BBC News, Toronto",
            date = "Feb 28, 2025",
            content = "'Trump thinks he can break us' - Ontario's Doug Ford makes bullish victory speech"
        ),
        News(
            imageResId = R.drawable.news_image2,
            date = "Feb 26, 2025",
            author = "Nadine Yousif",
            source = "BBC News, Toronto",
            content = "Five takeaways from Canada's Liberal leadership debates"
        ),
        News(
            imageResId = R.drawable.news_image3,
            date = "Feb 25, 2025",
            author = "Jessica Murphy & Nadine Yousif",
            source = "BBC News, Toronto",
            content = "Who might replace Trudeau as Liberal Party leader?"
        ),
        News(
            imageResId = R.drawable.news_image4,
            date = "Jan 28, 2025",
            author = "Nadine Yousif",
            source = "BBC News, Toronto",
            content = "Ontario's 'Captain Canada' calls snap election as Trump tariff threat looms"
        )
    )

    Column(modifier = Modifier.fillMaxSize()) {
        newsItems.forEach { news ->
            NewsItem(
                imageResId = news.imageResId,
                date = news.date,
                author = news.author,
                source = news.source,
                content = news.content
            )
        }
    }
}



@Composable
fun VerificationScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Verification Screen")
    }
}

@Composable
fun CandidatesScreen() {
    // State to track the currently selected tab index
    var selectedTabIndex by remember { mutableStateOf(0) }

    // List of candidates with their details
    val candidates = listOf(
        Candidate(
            name = "Doug Ford",
            party = "Progressive Conservative",
            birthdate = "November 20, 1964",
            birthplace = "Etobicoke, Ontario",
            politicalViews = "Prioritizing tax cuts, economic growth, and reduced government spending while advocating for business-friendly policies and infrastructure development.",
            imageUrl = R.drawable.doug_candidate,
            wikiUrl = "https://en.wikipedia.org/wiki/Doug_Ford"
        ),
        Candidate(
            name = "Marit Stiles",
            party = "New Democratic",
            birthdate = "September 20, 1969",
            birthplace = "St. John's, Newfoundland",
            politicalViews = "Advocating for stronger public services, workers' rights, affordable housing, and increased government investment in education and healthcare.",
            imageUrl = R.drawable.marit_candidate,
            wikiUrl = "https://en.wikipedia.org/wiki/Marit_Stiles"
        ),
        Candidate(
            name = "Bonnie Crombie",
            party = "Liberal",
            birthdate = "February 5, 1960",
            birthplace = "Toronto, Ontario",
            politicalViews = "Advocating for economic growth, housing affordability, improved public transit, and strong public services while emphasizing business-friendly policies and social equity.",
            imageUrl = R.drawable.bonnie_candidate,
            wikiUrl = "https://en.wikipedia.org/wiki/Bonnie_Crombie"
        ),
        Candidate(
            name = "Mike Schreiner",
            party = "Green",
            birthdate = "June 9, 1969",
            birthplace = "WaKeeney, Kansas",
            politicalViews = "Environmental sustainability, climate action, affordable housing, and strengthening public services while promoting a transition to a clean economy.",
            imageUrl = R.drawable.mike_candidate,
            wikiUrl = "https://en.wikipedia.org/wiki/Mike_Schreiner"
        )
    )

    Column(modifier = Modifier.fillMaxSize()) {
        // Tab Row
        TabRow(selectedTabIndex = selectedTabIndex) {
            candidates.forEachIndexed { index, candidate ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(candidate.name) }
                )
            }
        }

        // Tab Content
        when (selectedTabIndex) {
            in candidates.indices -> CandidateDetails(candidates[selectedTabIndex])
            else -> Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No candidate selected")
            }
        }
    }
}

// Composable to display candidate details
@Composable
fun CandidateDetails(candidate: Candidate) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Image
        Image(
            painter = painterResource(id = candidate.imageUrl),
            contentDescription = candidate.name,
            modifier = Modifier
                .size(200.dp)
                .clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Name
        Text(
            text = candidate.name,
            style = MaterialTheme.typography.headlineLarge,
            textAlign = TextAlign.Center
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Party
        Text(
            text = "Party: ${candidate.party}",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Birthdate
        Text(
            text = "Birthdate: ${candidate.birthdate}",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Birthplace
        Text(
            text = "Birthplace: ${candidate.birthplace}",
            style = MaterialTheme.typography.headlineMedium
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Political Views
        Text(
            text = "Political Views:",
            style = MaterialTheme.typography.headlineMedium
        )
        Text(
            text = candidate.politicalViews,
            style = MaterialTheme.typography.bodyMedium,
        )
        Spacer(modifier = Modifier.height(16.dp))
    }
}


// Custom Fonts
val CustomTypography = Typography(
    headlineLarge = TextStyle(
        color = Color.Black,
        fontSize = 25.sp,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Bold,
        letterSpacing = 1.5.sp,

    ),
    headlineMedium = TextStyle(
        color = Color.Black,
        fontSize = 20.sp,
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Medium,
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily.SansSerif,
        fontWeight = FontWeight.Normal,
        fontSize = 18.sp,
        color = Color.DarkGray
    )
)

@Composable
fun VotingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text("Settings Screen")
    }
}



// Method that posts X update
@Composable
fun PostItem(avatarResId: Int, name: String, date: String, content: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        Row(verticalAlignment = androidx.compose.ui.Alignment.CenterVertically) {
            androidx.compose.foundation.Image(
                painter = androidx.compose.ui.res.painterResource(id = avatarResId),
                contentDescription = "Avatar",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .align(androidx.compose.ui.Alignment.CenterVertically),
            )
            Row(modifier = Modifier.padding(start = 8.dp)) {
                Text(
                    text = name,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                )
                Spacer(modifier = Modifier.width(4.dp)) // Add spacing between name and icon

                // Verified Icon
                Icon(
                    Icons.Default.CheckCircle,
                    contentDescription = "Verified",
                    tint = androidx.compose.ui.graphics.Color(0xFF179EF4),
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))

                Text(
                    text = date,
                    fontWeight = androidx.compose.ui.text.font.FontWeight.Light
                )
            }
        }
        Text(
            text = content,
            modifier = Modifier.padding(top = 8.dp)
        )
    }
}

@Composable
fun NewsItem(imageResId: Int, date: String, author: String, source: String, content: String) {
    Column(modifier = Modifier.padding(16.dp)) {
        // News Image
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "News Image",
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .clip(MaterialTheme.shapes.medium),
            contentScale = ContentScale.Crop
        )
        Spacer(modifier = Modifier.height(8.dp))

        // Date
        Text(
            text = date,
            style = MaterialTheme.typography.headlineMedium,
            color = Color.Gray,
        )
        Spacer(modifier = Modifier.height(4.dp))

        // Author
        Row (modifier = Modifier.padding(2.dp)) {
            // Author
            Text(
                text = author,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray,
            )
            Spacer(modifier = Modifier.width(15.dp))

            // Source
            Text(
                text = source,
                style = MaterialTheme.typography.headlineMedium,
                color = Color.Gray,
            )
        }
        Spacer(modifier = Modifier.height(4.dp))

        // Content
        Text(
            text = content,
            style = MaterialTheme.typography.bodyMedium,
            textAlign = TextAlign.Justify
        )
    }
}