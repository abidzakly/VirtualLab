package org.d3ifcool.virtualab.ui.screen.admin.introduction

import android.content.Context
import android.content.res.Configuration
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.model.Introduction
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.navigation.Screen
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.SmallText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.DarkBlueText
import org.d3ifcool.virtualab.ui.theme.LightBlue
import org.d3ifcool.virtualab.ui.theme.Poppins
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterialApi::class)
@Composable
fun IntroContent(navController: NavHostController, viewModel: IntroContentViewModel) {
    val data by viewModel.data.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
    val videoUri by viewModel.videoUri.collectAsState()
    var isVideoPlaying by remember { mutableStateOf(false) }
    val isRefreshing by viewModel.isRefreshing.collectAsState()
    val context = LocalContext.current


    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )
    Box(modifier = Modifier.pullRefresh(refreshState)) {
        if (isVideoPlaying) {
            BackHandler {
                isVideoPlaying = false
            }
            Scaffold(topBar = {
                TopNav(
                    title = R.string.introduction_title,
                    navController = navController
                )
            },
                bottomBar = {
                    BottomNav(navController = navController)
                }) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(LightBlue)
                        .padding(top = 90.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                        item {
                            VideoListItem(
                                context = context,
                                onClick = {
                                    isVideoPlaying = false
                                })
                        }
                    }
                    ScreenContent(
                        modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
                        navController = navController,
                        data,
                        status
                    )
                }
            }
            val exoPlayer = remember {
                ExoPlayer.Builder(context).build().apply {
                    CookieHandler.setDefault(
                        CookieManager(
                            null,
                            CookiePolicy.ACCEPT_ALL
                        )
                    )
                    videoUri?.let { MediaItem.fromUri(it) }?.let { setMediaItem(it) }
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
                        Text(
                            text = stringResource(id = R.string.introduction_title),
                            fontFamily = Poppins
                        )
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
                    BottomNav(navController = navController)
                }
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            LightBlue
                        )
                        .padding(top = 90.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    when (status) {
                        ApiStatus.IDLE -> null
                        ApiStatus.LOADING -> {
                            CircularProgressIndicator(color = DarkBlueText)
                        }

                        ApiStatus.SUCCESS -> {
                            LazyVerticalGrid(columns = GridCells.Fixed(1)) {
                                item {
                                    VideoListItem(
                                        context = context
                                    ) {
                                        Toast.makeText(
                                            context,
                                            "Loading Video Player...",
                                            Toast.LENGTH_LONG
                                        ).show()
                                        isVideoPlaying = true
                                    }
                                }
                            }
                        }

                        ApiStatus.FAILED -> null
                    }
                    ScreenContent(
                        modifier = Modifier.padding(bottom = it.calculateBottomPadding()),
                        navController = navController,
                        data,
                        status
                    )
                }
            }
        }

        PullRefreshIndicator(
            refreshing = isRefreshing,
            state = refreshState,
            modifier = Modifier.align(Alignment.TopCenter),
            contentColor = Color.White,
            backgroundColor = DarkBlueDarker
        )
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    navController: NavHostController,
    data: Introduction? = null,
    status: ApiStatus = ApiStatus.LOADING
) {
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
        when (status) {
            ApiStatus.IDLE -> null
            ApiStatus.LOADING -> {
                LoadingState()
            }

            ApiStatus.SUCCESS -> {
                Spacer(modifier = Modifier.height(24.dp))
                Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
                    MediumLargeText(
                        text = data!!.title,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                start = 30.dp,
                                top = 0.dp,
                                end = 30.dp,
                                bottom = 24.dp
                            ),
                        color = DarkBlueText
                    )
                    Column(modifier = modifier.padding(horizontal = 50.dp)) {
                        SmallText(
                            text = data.description,
                            textAlign = TextAlign.Justify,
                        )
                        Spacer(modifier = Modifier.height(12.dp))
                    }
                }
            }

            ApiStatus.FAILED -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.empty_state_admin),
                            contentDescription = "Gambar Empty State"
                        )
                        Spacer(modifier = Modifier.height(28.dp))
                        RegularText(
                            text = "Belum ada data. ",
                            modifier = Modifier.fillMaxWidth(),
                            textAlign = TextAlign.Center
                        )
                        ClickableText(
                            text = AnnotatedString("Tambahkan sekarang"),
                            style = TextStyle(
                                fontFamily = Poppins,
                                fontSize = 16.sp,
                                color = DarkBlueText
                            )
                        ) {
                            navController.navigate(Screen.UpdateIntroContent.route)
                        }

                    }
                }
            }
        }
    }
}


@Composable
fun VideoListItem(context: Context, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Black.copy(alpha = 0.8f))
            .clickable { onClick() },
        contentAlignment = Alignment.Center
    ) {
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(ApiService.getIntroductionThumbnail())
                .crossfade(true)
                .diskCachePolicy(CachePolicy.DISABLED)
                .memoryCachePolicy(CachePolicy.DISABLED)
                .build(),
            contentDescription = "video thumbnail",
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.broken_image),
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(2f)
                .background(color = Color.Black, shape = RoundedCornerShape(20.dp))
                .padding(horizontal = 16.dp)
        )
        Icon(
            painter = painterResource(id = R.drawable.play_button),
            contentDescription = "Play Button",
            tint = Color.White
        )
    }
}

@Preview(showBackground = true)
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES, showBackground = true)
@Composable
private fun Prev() {
//    IntroContent(navController = rememberNavController())
}