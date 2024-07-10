package org.d3ifcool.virtualab.ui.component

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.utils.isImage
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy

@Composable
fun ContentView(
    fileName: String,
    file: Any?,
    isVideo: Boolean?,
    onVideoCheck: (Boolean) -> Unit,
    isVideoPlaying: Boolean,
    onPlaying: (Boolean) -> Unit,
    isUri: Boolean,
    context: Context
) {
    file?.let {
        var uri: Uri? = null
        var stringUri: String? = null
        if (isUri) {
            uri = file as Uri
        } else {
            stringUri = file as String
        }
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(10.dp))
                .background(Color.LightGray)
                .padding(8.dp)
                .fillMaxWidth()
        ) {
            val contentResolver = context.contentResolver
            if (isVideo == null) {
                onVideoCheck(
                    !isImage(uri!!, contentResolver)
                )
            }
            if (isVideo == true) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(if (isVideoPlaying) Color.Black.copy(alpha = 0.8f) else Color.Transparent)
                        .clickable { onPlaying(true) },
                    contentAlignment = Alignment.Center
                ) {
                    if (!isVideoPlaying) {
                        if (isUri) {
                            val retriever = MediaMetadataRetriever()
                            retriever.setDataSource(context, uri)

                            val bitmap = retriever.frameAtTime

                            bitmap?.let {
                                Image(
                                    bitmap = it.asImageBitmap(),
                                    contentDescription = null,
                                    modifier = Modifier
                                        .clip(RoundedCornerShape(10.dp))
                                        .background(Color.Black)
                                        .alpha(0.65f)
                                        .aspectRatio(1f),
                                )
                                Icon(
                                    painter = painterResource(id = R.drawable.play_button),
                                    contentDescription = "Play Button",
                                    tint = Color.White
                                )
                            }
                        } else {
                            AsyncImage(
                                model = ImageRequest.Builder(LocalContext.current)
                                    .data("$stringUri/thumbnail")
                                    .diskCachePolicy(CachePolicy.DISABLED)
                                    .memoryCachePolicy(CachePolicy.DISABLED)
                                    .crossfade(true)
                                    .build(),
                                contentDescription = null,
                                contentScale = ContentScale.Crop,
                                placeholder = painterResource(id = R.drawable.loading_img),
                                error = painterResource(id = R.drawable.broken_image),
                                modifier = Modifier
                                    .clip(RoundedCornerShape(10.dp))
                                    .aspectRatio(1f)
                                    .padding(4.dp)
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.play_button),
                                contentDescription = "Play Button",
                                tint = Color.White
                            )
                        }
                    } else {
                        val exoPlayer = remember {
                            ExoPlayer.Builder(context).build().apply {
                                CookieHandler.setDefault(
                                    CookieManager(
                                        null,
                                        CookiePolicy.ACCEPT_ALL
                                    )
                                )
                                setMediaItem(
                                    if (isUri) {
                                        MediaItem.fromUri(uri!!)
                                    } else {
                                        MediaItem.fromUri(stringUri!!)
                                    }
                                )
                                prepare()
                                play()
                            }
                        }

                        AndroidView(
                            modifier = Modifier
                                .clip(RoundedCornerShape(15.dp))
                                .size(500.dp),
                            factory = { context ->
                                PlayerView(context).apply {
                                    player = exoPlayer
                                }
                            }
                        )
                        IconButton(
                            onClick = { onPlaying(false) },
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
                        DisposableEffect(Unit) {
                            onDispose {
                                onPlaying(false)
                                exoPlayer.release()
                            }
                        }
                    }
                }
                RegularText(
                    text = fileName,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center

                )
            }
        }
    }
}