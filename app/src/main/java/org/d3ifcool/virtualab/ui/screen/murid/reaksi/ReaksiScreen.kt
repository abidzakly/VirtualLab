package org.d3ifcool.virtualab.ui.screen.murid.reaksi

import android.content.Context
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import org.d3ifcool.virtualab.R
import org.d3ifcool.virtualab.data.model.ApprovedArticle
import org.d3ifcool.virtualab.data.network.ApiService
import org.d3ifcool.virtualab.data.network.ApiStatus
import org.d3ifcool.virtualab.ui.component.BottomNav
import org.d3ifcool.virtualab.ui.component.ExtraSmallText
import org.d3ifcool.virtualab.ui.component.GradientPage
import org.d3ifcool.virtualab.ui.component.ImageDialog
import org.d3ifcool.virtualab.ui.component.LoadingState
import org.d3ifcool.virtualab.ui.component.MediumLargeText
import org.d3ifcool.virtualab.ui.component.MuridEmptyState
import org.d3ifcool.virtualab.ui.component.RegularText
import org.d3ifcool.virtualab.ui.component.SmallText
import org.d3ifcool.virtualab.ui.component.TopNav
import org.d3ifcool.virtualab.ui.theme.DarkBlueDarker
import org.d3ifcool.virtualab.ui.theme.LightBlue2
import org.d3ifcool.virtualab.utils.errorTextCheck

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ReaksiScreen(navController: NavHostController, viewModel: ContohReaksiScreenViewModel) {
    val context = LocalContext.current
    val isRefreshing by viewModel.isRefreshing.collectAsState()

    val refreshState = rememberPullRefreshState(
        refreshing = isRefreshing,
        onRefresh = { viewModel.refreshData() }
    )
    Scaffold(topBar = {
        TopNav(title = R.string.contoh_reaksi_title, navController = navController)
    },
        bottomBar = {
            BottomNav(navController = navController)
        }) {
        Box(
            modifier = Modifier
                .pullRefresh(refreshState)
                .padding(bottom = it.calculateBottomPadding())
        ) {
            ScreenContent(
                modifier = Modifier,
                viewModel,
                context
            )
            PullRefreshIndicator(
                refreshing = isRefreshing,
                state = refreshState,
                modifier = Modifier.align(Alignment.TopCenter),
                contentColor = Color.White,
                backgroundColor = DarkBlueDarker
            )
        }
    }
}

@Composable
private fun ScreenContent(
    modifier: Modifier,
    viewModel: ContohReaksiScreenViewModel,
    context: Context
) {
    val articlesData by viewModel.articleData.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val status by viewModel.apiStatus.collectAsState()
    var showImgDialog by remember { mutableStateOf(false) }


    GradientPage(
        modifier = modifier,
        image = R.drawable.reaksi_illustration,
        isDiffSize = true,
    ) {
        when (status) {
            ApiStatus.IDLE -> null
            ApiStatus.LOADING -> {
                LoadingState()
            }

            ApiStatus.FAILED -> {
                MuridEmptyState(
                    text = errorTextCheck(
                        errorMessage,
                        stringResource(id = R.string.empty_reaksi)
                    )
                ) {
                    viewModel.getApprovedArticles()
                    viewModel.clearMessage()
                }
            }

            ApiStatus.SUCCESS -> {
                var imageUrl by remember { mutableStateOf("") }
                Spacer(modifier = Modifier.height(24.dp))
                LazyColumn {
                    item {
                        Spacer(modifier = Modifier.height(22.dp))
                        RegularText(text = stringResource(R.string.reaksi_content_header))
                    }
                    items(articlesData) {
                        Spacer(modifier = Modifier.height(25.dp))
                        ItemList(data = it) {
                            imageUrl = ApiService.getArticleMedia(it.articleId)
                            showImgDialog = true
                        }
                    }
                }
                if (showImgDialog) {
                    ImageDialog(imageUrl = imageUrl, context = context) {
                        showImgDialog = false
                    }
                }
            }
        }
    }
}

@Composable
private fun ItemList(data: ApprovedArticle, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .clip(shape = RoundedCornerShape(10.dp))
            .background(LightBlue2),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        ItemContent(data = data) {
            onClick()
        }
    }
    Spacer(modifier = Modifier.height(33.dp))
}

@Composable
private fun ItemContent(data: ApprovedArticle, onClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 21.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        MediumLargeText(text = data.title, fontWeight = FontWeight.SemiBold)
        ExtraSmallText(text = "ditulis oleh: ${data.authorName}", fontWeight = FontWeight.Light)
        Spacer(modifier = Modifier.height(10.dp))
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(ApiService.getArticleMedia(data.articleId))
                .crossfade(true)
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Fit,
            placeholder = painterResource(id = R.drawable.loading_img),
            error = painterResource(id = R.drawable.broken_image),
            modifier = Modifier
                .height(225.dp)
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .clickable {
                    onClick()
                }
        )
        Spacer(modifier = Modifier.height(16.dp))
        SmallText(
            text = data.description,
            textAlign = TextAlign.Justify,
        )
    }
}

@Preview
@Composable
private fun Prev() {
//    ReaksiScreen(navController = rememberNavController())
}
