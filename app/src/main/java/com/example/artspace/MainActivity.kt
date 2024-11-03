package com.example.artspace

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.artspace.ui.theme.ArtSpaceTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ArtSpaceTheme {
                ArtGalleryScreen()
            }
        }
    }
}

@Composable
fun ArtGalleryScreen() {
    val artworks = listOf(
        Artwork(R.drawable.kotak, "Berpadu padan papan dan rotan", "Oscar Piastri", "1700"),
        Artwork(R.drawable.purba, "Pra-Sejarah dan Buruannya", "Carlos Sainz", "2000"),
        Artwork(R.drawable.vangoh, "Starry Night", "Vincent Van G", "1900"),
    )

    var currentIndex by remember { mutableStateOf(0) }
    val currentArtwork = artworks[currentIndex]

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val isPortrait = configuration.orientation == android.content.res.Configuration.ORIENTATION_PORTRAIT

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = if (isPortrait) Arrangement.Center else Arrangement.SpaceAround
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFFE0E0E0))
                    .padding(16.dp)
                    .width(if (isPortrait) screenWidth * 0.9f else screenWidth * 0.6f)
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Image(
                        painter = painterResource(id = currentArtwork.imageRes),
                        contentDescription = currentArtwork.title,
                        modifier = Modifier
                            .fillMaxWidth(if (isPortrait) 0.8f else 0.6f)
                            .height(if (isPortrait) 300.dp else 200.dp)
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = currentArtwork.title,
                        fontSize = (screenWidth.value * 0.05).sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 4.dp)
                    )
                    Text(
                        text = "${currentArtwork.artist} (${currentArtwork.year})",
                        fontSize = (screenWidth.value * 0.04).sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier.fillMaxWidth()
            ) {
                Button(
                    onClick = {
                        currentIndex = if (currentIndex > 0) currentIndex - 1 else artworks.size - 1
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Previous", fontSize = (screenWidth.value * 0.04).sp)
                }
                Spacer(modifier = Modifier.width(10.dp))
                Button(
                    onClick = {
                        currentIndex = (currentIndex + 1) % artworks.size
                    },
                    modifier = Modifier.padding(8.dp)
                ) {
                    Text("Next", fontSize = (screenWidth.value * 0.04).sp)
                }
            }
        }
    }
}

data class Artwork(
    val imageRes: Int,
    val title: String,
    val artist: String,
    val year: String
)

@Preview(showBackground = true)
@Composable
fun ArtGalleryPreview() {
    ArtSpaceTheme {
        ArtGalleryScreen()
    }
}
