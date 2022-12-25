package com.kproject.chatgpt.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kproject.chatgpt.presentation.model.RecentChat
import com.kproject.chatgpt.presentation.screens.components.EmptyListInfo
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.model.fakeRecentChatsList
import com.kproject.chatgpt.presentation.screens.components.TopBar
import com.kproject.chatgpt.presentation.theme.CompletePreview
import com.kproject.chatgpt.presentation.theme.PreviewTheme

@Composable
fun HomeScreen(

) {
    val uiState = HomeUiState(
        isLoading = false,
        recentChatsList = fakeRecentChatsList
    )
    HomeScreenContent(homeUiState = uiState)
}

@Composable
private fun HomeScreenContent(
    homeUiState: HomeUiState
) {
    Scaffold(
        topBar = {
           TopBar(
               title = stringResource(id = R.string.app_name),
               actions = {
                   IconButton(
                       onClick = {

                       }
                   ) {
                       Icon(
                           imageVector = Icons.Default.Settings,
                           contentDescription = null,
                           tint = MaterialTheme.colors.onSurface
                       )
                   }
               }
           )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { }
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_chat),
                    null,
                    tint = MaterialTheme.colors.onPrimary
                )
            }
        }
    ) { paddingValues ->
        Content(
            modifier = Modifier.padding(paddingValues),
            homeUiState = homeUiState
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState
) {
    Column(
        modifier = modifier
    ) {
        RecentChatsList(
            recentChatsList = homeUiState.recentChatsList,
            onClick = {}
        )
    }
}

@Composable
private fun RecentChatsList(
    modifier: Modifier = Modifier,
    recentChatsList: List<RecentChat>,
    onClick: (index: Int) -> Unit
) {
    if (recentChatsList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            itemsIndexed(recentChatsList) { index, recentChat ->
                RecentChatsListItem(
                    recentChat = recentChat,
                    onClick = {
                        onClick(index)
                    }
                )
            }
        }
    } else {
        EmptyListInfo(
            iconResId = R.drawable.ic_chat,
            title = stringResource(id = R.string.info_title_empty_recent_chats_list),
            description = stringResource(id = R.string.info_description_empty_recent_chats_list)
        )
    }
}

@Composable
private fun RecentChatsListItem(
    recentChat: RecentChat,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick() }
            .padding(8.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val chatIcon = if (recentChat.chatMode) {
                R.drawable.ic_chat
            } else {
                R.drawable.ic_manage_search
            }
            Image(
                imageVector = ImageVector.vectorResource(id = chatIcon),
                contentDescription = null,
                contentScale = ContentScale.Inside,
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onPrimary),
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .fillMaxSize()
                    .background(MaterialTheme.colors.onSecondary),
            )

            Spacer(Modifier.width(8.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            ) {
                Text(
                    text = recentChat.chatName,
                    color = MaterialTheme.colors.onPrimary,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    fontSize = 18.sp,
                )
                Spacer(Modifier.height(4.dp))
                val messageTextColor = if (recentChat.lastMessageSentByUser) {
                    MaterialTheme.colors.onPrimary
                } else {
                    MaterialTheme.colors.onSecondary
                }
                Text(
                    text = recentChat.lastMessage,
                    color = messageTextColor,
                    maxLines = 1,
                    fontSize = 16.sp
                )
            }

            Text(
                text = recentChat.lastMessageDate,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 12.sp
            )
        }
    }
}

@CompletePreview
@Composable
private fun Preview() {
    PreviewTheme {
        val uiState = HomeUiState(
            isLoading = false,
            recentChatsList = fakeRecentChatsList
        )
        HomeScreenContent(
            homeUiState = uiState
        )
    }
}