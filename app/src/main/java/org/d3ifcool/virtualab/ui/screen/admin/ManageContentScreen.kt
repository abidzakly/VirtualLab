package org.d3ifcool.virtualab.ui.screen.admin

import android.content.res.Configuration
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Check
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.SmallText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueText
import org.d3ifcool.virtualab.ui.theme.LightBlue

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManageContentScreen(navController: NavHostController) {
    var isVideoPlaying by remember { mutableStateOf(false) }
    var currentVideoSource by remember { mutableStateOf("") }
    val context = LocalContext.current
    if (isVideoPlaying) {
        Scaffold(topBar = {
            TopNav(
                title = R.string.introduction_title,
                navController = navController
            )
        },
            bottomBar = {
                BottomNav(currentRoute = Screen.AdminDashboard.route, navController = navController)
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(LightBlue)
                    .padding(top = 90.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                    items(videoList) {
                        VideoListItem(item = it, onClick = {
                            isVideoPlaying = true
                            currentVideoSource = it.source.toString()
                        })
                    }
                }
                ScreenContent(
                    modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
                    navController = navController
                )
            }
        }
        val exoPlayer = remember {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(Uri.parse("android.resource://" + context.packageName + "/" + R.raw.sample_video)))
                prepare()
                play()
            }
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
        ) {
            AndroidView(
                factory = { context ->
                    PlayerView(context).apply {
                        player = exoPlayer
                    }
                },
                modifier = Modifier.fillMaxSize()
            )
            IconButton(
                onClick = { isVideoPlaying = false },
                modifier = Modifier
                    .align(Alignment.TopEnd)
                    .padding(16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Close",
                    tint = Color.White
                )
            }
        }
        DisposableEffect(Unit) {
            onDispose {
                exoPlayer.release()
            }
        }
    } else {
        Scaffold(topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(
                                id = R.string.back_button
                            ),
                            tint = Color.Black
                        )
                    }
                },
                title = {
                    Text(text = stringResource(id = R.string.introduction_title))
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    titleContentColor = Color.Black
                ),
                actions = {
                    IconButton(onClick = {
                        navController.navigate(Screen.UpdateIntroContent.route)
                    }) {
                        Icon(
                            imageVector = Icons.Outlined.Edit,
                            contentDescription = stringResource(R.string.update_icon),
                            tint = Color.Black
                        )
                    }
                }
            )
        },
            bottomBar = {
                BottomNav(currentRoute = Screen.AdminDashboard.route, navController = navController)
            }) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        LightBlue
                    )
                    .padding(top = 90.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                    items(videoList) {
                        VideoListItem(item = it, onClick = {
                            isVideoPlaying = true
                            currentVideoSource = it.source.toString()
                        })
                    }
                }
                ScreenContent(
                    modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
                    navController = navController
                )
            }
        }
    }
}

@Composable
private fun ScreenContent(modifier: Modifier, navController: NavHostController) {
    Column(
        modifier = Modifier
            .padding(top = 32.dp)
            .fillMaxSize()
            .background(
                Color.White,
                shape = RoundedCornerShape(topStart = 40.dp, topEnd = 40.dp)
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
            MediumLargeText(
                text = stringResource(R.string.introduction_header),
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(
                    start = 30.dp,
                    top = 0.dp,
                    end = 30.dp,
                    bottom = 24.dp
                ),
                color = DarkBlueText
            )
            Column(modifier = Modifier.padding(horizontal = 50.dp)) {
                SmallText(
                    text = stringResource(R.string.introduction_desc),
                    textAlign = TextAlign.Justify,
                )
                Spacer(modifier = Modifier.height(12.dp))
                SmallText(
                    text = stringResource(R.string.introduction_desc2_title),
                    fontWeight = FontWeight.Bold,
                )
                SmallText(
                    text = stringResource(R.string.introduction_desc2),
                    textAlign = TextAlign.Justify,
                    modifier = modifier.padding(bottom = 24.dp)
                )
            }
        }
    }
}


@Composable
fun VideoListItem(item: VideoItem, onClick: () -> Unit) {
    AsyncImage(
        model = item.poster,
        contentDescription = "video thumbnail",
        modifier = Modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .background(color = Color.Black, shape = RoundedCornerShape(20.dp))
            .padding(horizontal = 16.dp)
    )
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun Prev() {
    ManageContentScreen(navController = rememberNavController())
}