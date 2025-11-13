package com.example.citationeapp.ui.theme.components

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.citationeapp.ui.theme.black
import com.example.citationeapp.ui.theme.grey
import com.example.citationeapp.ui.theme.padding12
import com.example.citationeapp.ui.theme.padding16
import com.example.citationeapp.ui.theme.padding32
import com.example.citationeapp.ui.theme.padding6
import com.example.citationeapp.ui.theme.padding8
import com.example.citationeapp.ui.theme.primary

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun <T> PagerCarousel(
    items: List<T>,
    modifier: Modifier = Modifier,
    itemContent: @Composable (item: T, pageIndex: Int) -> Unit
) {
    val pagerState = rememberPagerState(pageCount = { items.size })

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier
                .fillMaxWidth()
                .height(150.dp)
        ) { page ->
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.fillMaxSize()
            ) {
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        padding16,
                        Alignment.CenterHorizontally
                    )
                ) {
                    if (page > 0) {
                        Icon(
                            imageVector = Icons.Filled.ChevronLeft,
                            contentDescription = "Précédent",
                            tint = black,
                            modifier = Modifier.size(padding32)
                        )
                    }
                    itemContent(items[page], page)
                    if (page < items.lastIndex) {
                        Icon(
                            imageVector = Icons.Filled.ChevronRight,
                            contentDescription = "Suivant",
                            tint = black,
                            modifier = Modifier.size(padding32)
                        )
                    }
                }
            }
        }
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = padding12)
        ) {
            items.forEachIndexed { index, _ ->
                val isSelected = pagerState.currentPage == index
                Box(
                    modifier = Modifier
                        .padding(padding6)
                        .size(if (isSelected) padding16 else padding8)
                        .background(
                            color = if (isSelected) primary else grey,
                            shape = RoundedCornerShape(50)
                        )
                )
            }
        }
    }
}