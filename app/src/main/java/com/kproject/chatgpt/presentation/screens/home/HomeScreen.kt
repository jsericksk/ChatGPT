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
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.model.RecentChat
import com.kproject.chatgpt.presentation.model.fakeRecentChatsList
import com.kproject.chatgpt.presentation.screens.components.EmptyListInfo
import com.kproject.chatgpt.presentation.screens.components.TopBar
import com.kproject.chatgpt.presentation.screens.home.components.ApiKeyAlertDialog
import com.kproject.chatgpt.presentation.screens.home.components.ModeSelectionAlertDialog
import com.kproject.chatgpt.presentation.theme.CompletePreview
import com.kproject.chatgpt.presentation.theme.PreviewTheme
import com.kproject.chatgpt.presentation.utils.ConversationMode

@Composable
fun HomeScreen(
    onNavigateToChatScreen: (chatId: Long) -> Unit,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    var showApiKeyAlertDialog by remember { mutableStateOf(false) }

    val uiState = HomeUiState(
        isLoading = false,
        recentChatsList = fakeRecentChatsList
    )

    HomeScreenContent(
        homeUiState = uiState,
        onApiKeyOptionClick = {
            showApiKeyAlertDialog = true
        },
        onAppThemeOptionClick = {

        },
        onConversationModeSelected = {
            onNavigateToChatScreen.invoke(12345)
        },
        onNavigateToChatScreen = { chatId ->
            onNavigateToChatScreen.invoke(chatId)
        }
    )

    ApiKeyAlertDialog(
        showDialog = showApiKeyAlertDialog,
        onDismiss = { showApiKeyAlertDialog = false },
        apiKey = uiState.apiKey,
        onSaveApiKey = { apiKey ->
            homeViewModel.saveApiKey(apiKey)
        }
    )
}

@Composable
private fun HomeScreenContent(
    homeUiState: HomeUiState,
    onApiKeyOptionClick: () -> Unit,
    onAppThemeOptionClick: () -> Unit,
    onConversationModeSelected: (ConversationMode) -> Unit,
    onNavigateToChatScreen: (chatId: Long) -> Unit
) {
    var showOptionsMenu by remember { mutableStateOf(false) }
    var showModeSelectionDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopBar(
                title = stringResource(id = R.string.app_name),
                actions = {
                    IconButton(
                        onClick = {
                            showOptionsMenu = true
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Settings,
                            contentDescription = null,
                            tint = MaterialTheme.colors.onSurface
                        )
                    }

                    OptionsDropdownMenu(
                        showOptionsMenu = showOptionsMenu,
                        onDismiss = { showOptionsMenu = false },
                        onApiKeyOptionClick = onApiKeyOptionClick,
                        onAppThemeOptionClick = onAppThemeOptionClick
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showModeSelectionDialog = true }
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
            homeUiState = homeUiState,
            onNavigateToChatScreen = { chatId ->
                onNavigateToChatScreen.invoke(chatId)
            }
        )
        
        ModeSelectionAlertDialog(
            showDialog = showModeSelectionDialog, 
            onDismiss = { showModeSelectionDialog = false }, 
            onModeSelected = { conversationMode ->  
                onConversationModeSelected.invoke(conversationMode)
            }
        )
    }
}

@Composable
private fun OptionsDropdownMenu(
    showOptionsMenu: Boolean,
    onDismiss: () -> Unit,
    onApiKeyOptionClick: () -> Unit,
    onAppThemeOptionClick: () -> Unit
) {
    DropdownMenu(
        expanded = showOptionsMenu,
        onDismissRequest = onDismiss,
        modifier = Modifier.background(MaterialTheme.colors.surface)
    ) {
        DropdownMenuItem(
            onClick = {
                onDismiss.invoke()
                onApiKeyOptionClick.invoke()
            }
        ) {
            Text(
                text = stringResource(id = R.string.api_key),
                color = MaterialTheme.colors.onSurface
            )
        }

        DropdownMenuItem(
            onClick = {
                onDismiss.invoke()
                onAppThemeOptionClick.invoke()
            }
        ) {
            Text(
                text = stringResource(id = R.string.app_theme),
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    homeUiState: HomeUiState,
    onNavigateToChatScreen: (chatId: Long) -> Unit
) {
    Column(
        modifier = modifier
    ) {
        RecentChatsList(
            recentChatsList = homeUiState.recentChatsList,
            onClick = { chatId ->
                onNavigateToChatScreen.invoke(chatId.toLong())
            }
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
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .fillMaxSize()
                    .background(MaterialTheme.colors.surface),
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
            homeUiState = uiState,
            onApiKeyOptionClick = {},
            onAppThemeOptionClick = {},
            onConversationModeSelected = {},
            onNavigateToChatScreen = {}
        )
    }
}

@CompletePreview
@Composable
private fun ApiKeyAlertDialogPreview() {
    PreviewTheme {
        ApiKeyAlertDialog(
            showDialog = true,
            onDismiss = {},
            apiKey = "skskfjjifwojewpi3jjdjd3we",
            onSaveApiKey = {}
        )
    }
}