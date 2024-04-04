package com.saitawngpha.tabexample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import com.saitawngpha.tabexample.ui.theme.TabExampleTheme

class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tabItems = listOf(
            TabItem(
                title = "Home",
                unselectedIcon = Icons.Outlined.Home,
                selectedIcon = Icons.Filled.Home
            ),
            TabItem(
                title = "Browser",
                unselectedIcon = Icons.Outlined.ShoppingCart,
                selectedIcon = Icons.Filled.ShoppingCart
            ),
            TabItem(
                title = "Account",
                unselectedIcon = Icons.Outlined.AccountCircle,
                selectedIcon = Icons.Filled.AccountCircle
            ),
            TabItem(
                title = "Info",
                unselectedIcon = Icons.Outlined.Info,
                selectedIcon = Icons.Filled.Info
            ),
        )
        setContent {
            TabExampleTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {

                    var selectedTabIndex by remember { mutableIntStateOf(0) }
                    val pagerState = rememberPagerState {
                        tabItems.size
                    }

                    LaunchedEffect(selectedTabIndex) {
                        pagerState.animateScrollToPage(selectedTabIndex)
                    }
                    LaunchedEffect(pagerState.currentPage, pagerState.isScrollInProgress) {
                        if (!pagerState.isScrollInProgress) {
                            selectedTabIndex = pagerState.currentPage
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        TabRow(selectedTabIndex = selectedTabIndex) {
                            tabItems.forEachIndexed { index, item ->
                                Tab(
                                    selected = index == selectedTabIndex,
                                    onClick = {
                                        selectedTabIndex = index
                                    },
                                    text = { Text(text = item.title)},
                                    icon = {
                                        Icon(
                                            imageVector = if (index == selectedTabIndex) item.selectedIcon else item.unselectedIcon,
                                            contentDescription = "Select Icon"
                                        )
                                    }
                                )
                            }
                        }

                        HorizontalPager(
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f),
                            state = pagerState
                        ) {index ->
                            Box(modifier = Modifier
                                .fillMaxSize(),
                                contentAlignment = Alignment.Center
                            ){
                                Text(text = tabItems[index].title )
                            }
                        }
                    }

                }
            }
        }
    }
}


data class TabItem(
    val title: String,
    val unselectedIcon: ImageVector,
    val selectedIcon: ImageVector
)