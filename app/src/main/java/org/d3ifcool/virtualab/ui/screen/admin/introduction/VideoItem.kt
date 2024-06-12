package org.d3ifcool.virtualab.ui.screen.admin.introduction

import org.d3ifcool.virtualab.R

data class VideoItem(val poster: Int, val source: Int)

val videoList: List<VideoItem> = listOf(
    VideoItem(
        poster = R.drawable.video_thumbnail,
        source = R.raw.sample_video
    ),
)