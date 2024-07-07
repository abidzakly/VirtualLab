package org.d3ifcool.virtualab.ui.component

import android.content.Context
import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import java.net.CookieHandler
import java.net.CookieManager
import java.net.CookiePolicy

@Composable
fun VideoPlayer(media: Any, isUri: Boolean, appContext: Context, onPlaying: (Boolean) -> Unit) {
    Box {
        val exoPlayer = remember {
            ExoPlayer.Builder(appContext).build().apply {
                CookieHandler.setDefault(
                    CookieManager(
                        null,
                        CookiePolicy.ACCEPT_ALL
                    )
                )
                setMediaItem(
                    if (isUri) {
                        MediaItem.fromUri(media as Uri)
                    } else {
                        MediaItem.fromUri(media as String)
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