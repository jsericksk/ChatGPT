package com.kproject.chatgpt.presentation.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.extensions.getFormattedDate
import com.kproject.chatgpt.presentation.model.*
import com.kproject.chatgpt.presentation.screens.components.*
import com.kproject.chatgpt.presentation.screens.home.components.ApiKeyAlertDialog
import com.kproject.chatgpt.presentation.screens.home.components.ModeSelectionAlertDialog
import com.kproject.chatgpt.presentation.theme.CompletePreview
import com.kproject.chatgpt.presentation.theme.PreviewTheme


@Composable
fun HomeScreen(
    onNavigateToChatScreen: (chatArgs: ChatArgs) -> Unit,
) {
    val homeViewModel: HomeViewModel = hiltViewModel()
    val uiState = homeViewModel.homeUiState
    var showApiKeyAlertDialog by remember { mutableStateOf(false) }

    val context = LocalContext.current

    HomeScreenContent(
        homeUiState = uiState,
        onApiKeyOptionClick = {
            showApiKeyAlertDialog = true
        },
        onAppThemeOptionClick = {

        },
        onStartNewChat = { chatName, conversationMode ->
            val chatArgs = ChatArgs(
                chatId = UnspecifiedChatId,
                apiKey = uiState.apiKey,
                chatName = chatName,
                isChatMode = (conversationMode == ConversationMode.ChatMode)
            )
            onNavigateToChatScreen.invoke(chatArgs)
        },
        onChatSelected = { recentChat ->
            val chatArgs = ChatArgs(
                chatId = recentChat.chatId,
                apiKey = uiState.apiKey,
                chatName = UnspecifiedChatName,
                isChatMode = recentChat.chatMode
            )
            onNavigateToChatScreen.invoke(chatArgs)
        },
        onRenameChat = { newChatName, recentChat ->
            homeViewModel.renameRecentChat(newChatName, recentChat)
        },
        onDeleteChat = { recentChat ->
            homeViewModel.deleteRecentChat(recentChat)
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
    onStartNewChat: (chatName: String, conversationMode: ConversationMode) -> Unit,
    onChatSelected: (recentChat: RecentChat) -> Unit,
    onRenameChat: (newTitle: String, recentChat: RecentChat) -> Unit,
    onDeleteChat: (recentChat: RecentChat) -> Unit
) {
    var showOptionsMenu by remember { mutableStateOf(false) }
    var showNewChatDialog by remember { mutableStateOf(false) }
    var showChatOptionsDropdownMenu by remember { mutableStateOf(false) }
    var selectedRecentChat by remember { mutableStateOf(RecentChat()) }

    var showRenameChatDialog by remember { mutableStateOf(false) }
    var showDeleteChatDialog by remember { mutableStateOf(false) }

    var chatName by remember { mutableStateOf("") }

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
                            imageVector = Icons.Default.MoreVert,
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
            FloatingActionButton(onClick = { showNewChatDialog = true }) {
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
            onChatSelected = { recentChat ->
                onChatSelected.invoke(recentChat)
            },
            onShowChatOptions = { recentChat ->
                selectedRecentChat = recentChat
                chatName = recentChat.chatName
                showChatOptionsDropdownMenu = true
            }
        )

        ChatOptionsDropdownMenu(
            showOptionsMenu = showChatOptionsDropdownMenu,
            onDismiss = { showChatOptionsDropdownMenu = false },
            onRenameChatOptionClick = { showRenameChatDialog = true },
            onDeleteChatOptionClick = { showDeleteChatDialog = true }
        )

        NewChatAlertDialog(
            showDialog = showNewChatDialog,
            onDismiss = { showNewChatDialog = false },
            onStartNewChat = { chatName, conversationMode ->
                onStartNewChat.invoke(chatName, conversationMode)
            }
        )

        // Rename Chat Dialog
        AlertDialogWithTextField(
            showDialog = showRenameChatDialog,
            onDismiss = {
                showRenameChatDialog = false
            },
            title = stringResource(id = R.string.rename_chat),
            textFieldValue = chatName,
            textFieldPlaceholder = stringResource(id = R.string.insert_chat_name),
            okButtonEnabled = chatName.isNotBlank(),
            okButtonTitle = stringResource(id = R.string.button_save),
            onTextValueChange = {
                chatName = it
            },
            onClickButtonOk = {
                onRenameChat.invoke(chatName, selectedRecentChat)
            }
        )

        // Delete Chat Dialog
        SimpleAlertDialog(
            showDialog = showDeleteChatDialog,
            onDismiss = { showDeleteChatDialog = false },
            title = stringResource(id = R.string.delete_chat),
            message = stringResource(id = R.string.delete_chat_confirmation),
            onClickButtonOk = {
                onDeleteChat.invoke(selectedRecentChat)
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
    onChatSelected: (recentChat: RecentChat) -> Unit,
    onShowChatOptions: (recentChat: RecentChat) -> Unit
) {
    if (homeUiState.isLoading) {
        ProgressIndicator()
    } else {
        RecentChatsList(
            recentChatsList = homeUiState.recentChatsList,
            onClick = { recentChat ->
                onChatSelected.invoke(recentChat)
            },
            onLongClick = { recentChat ->
                onShowChatOptions.invoke(recentChat)
            },
            modifier = modifier,
        )
    }
}

@Composable
private fun RecentChatsList(
    modifier: Modifier = Modifier,
    recentChatsList: List<RecentChat>,
    onClick: (recentChat: RecentChat) -> Unit,
    onLongClick: (recentChat: RecentChat) -> Unit
) {
    if (recentChatsList.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxSize()
        ) {
            itemsIndexed(recentChatsList) { index, recentChat ->
                RecentChatsListItem(
                    recentChat = recentChat,
                    onClick = {
                        onClick(recentChat)
                    },
                    onLongClick = {
                        onLongClick.invoke(recentChat)
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

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun RecentChatsListItem(
    recentChat: RecentChat,
    onClick: () -> Unit,
    onLongClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .combinedClickable(
                onClick = onClick,
                onLongClick = onLongClick
            )
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

                if (recentChat.lastMessage.isNotBlank()) {
                    Spacer(Modifier.height(4.dp))
                    val messageTextColor = if (recentChat.lastMessageSentByUser) {
                        MaterialTheme.colors.onPrimary
                    } else {
                        MaterialTheme.colors.secondary
                    }
                    Text(
                        text = recentChat.lastMessage,
                        color = messageTextColor,
                        maxLines = 1,
                        fontSize = 16.sp
                    )
                }
            }

            Text(
                text = recentChat.lastMessageDate.getFormattedDate(),
                color = MaterialTheme.colors.onPrimary,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
private fun ChatOptionsDropdownMenu(
    showOptionsMenu: Boolean,
    onDismiss: () -> Unit,
    onRenameChatOptionClick: () -> Unit,
    onDeleteChatOptionClick: () -> Unit
) {
    DropdownMenu(
        expanded = showOptionsMenu,
        onDismissRequest = onDismiss,
        modifier = Modifier.background(MaterialTheme.colors.surface)
    ) {
        DropdownMenuItem(
            onClick = {
                onDismiss.invoke()
                onRenameChatOptionClick.invoke()
            }
        ) {
            Text(
                text = stringResource(id = R.string.rename_chat),
                color = MaterialTheme.colors.onSurface
            )
        }

        DropdownMenuItem(
            onClick = {
                onDismiss.invoke()
                onDeleteChatOptionClick.invoke()
            }
        ) {
            Text(
                text = stringResource(id = R.string.delete_chat),
                color = MaterialTheme.colors.onSurface
            )
        }
    }
}

@Composable
private fun NewChatAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onStartNewChat: (chatName: String, conversationMode: ConversationMode) -> Unit
) {
    var showModeSelectionDialog by remember { mutableStateOf(false) }
    var textFieldValue by remember { mutableStateOf("") }
    var chatName by remember { mutableStateOf("") }

    AlertDialogWithTextField(
        showDialog = showDialog,
        onDismiss = {
            textFieldValue = ""
            onDismiss.invoke()
        },
        title = stringResource(id = R.string.chat_name),
        textFieldValue = textFieldValue,
        textFieldPlaceholder = stringResource(id = R.string.insert_chat_name),
        okButtonEnabled = textFieldValue.isNotBlank(),
        okButtonTitle = stringResource(id = R.string.button_continue),
        onTextValueChange = {
            textFieldValue = it
            chatName = it
        },
        onClickButtonOk = { showModeSelectionDialog = true }
    )

    ModeSelectionAlertDialog(
        showDialog = showModeSelectionDialog,
        onDismiss = { showModeSelectionDialog = false },
        onModeSelected = { conversationMode ->
            onStartNewChat.invoke(chatName, conversationMode)
        }
    )
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
            onStartNewChat = { _, _ -> },
            onChatSelected = {},
            onRenameChat = { _, _ -> },
            onDeleteChat = {}
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