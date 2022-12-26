package com.kproject.chatgpt.presentation.screens.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.model.RecentChat
import com.kproject.chatgpt.presentation.model.fakeRecentChatsList
import com.kproject.chatgpt.presentation.screens.components.EmptyListInfo
import com.kproject.chatgpt.presentation.screens.components.TopBar
import com.kproject.chatgpt.presentation.screens.home.components.ModeSelectionAlertDialog
import com.kproject.chatgpt.presentation.theme.CompletePreview
import com.kproject.chatgpt.presentation.theme.PreviewTheme

@Composable
fun HomeScreen(
    onNavigateToChatScreen: (chatId: Long) -> Unit
) {
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

        }
    )

    ApiKeyAlertDialog(
        showDialog = showApiKeyAlertDialog,
        onDismiss = { showApiKeyAlertDialog = false },
        apiKey = "skskfjjifwojewpi3jjdjd3we",
        onApiKeyChange = { newApiKey ->

        }
    )
}

@Composable
private fun HomeScreenContent(
    homeUiState: HomeUiState,
    onApiKeyOptionClick: () -> Unit,
    onAppThemeOptionClick: () -> Unit
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
            homeUiState = homeUiState
        )
        
        ModeSelectionAlertDialog(
            showDialog = showModeSelectionDialog, 
            onDismiss = { showModeSelectionDialog = false }, 
            onModeSelected = { conversationMode ->  
                
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
        onDismissRequest = onDismiss
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
                colorFilter = ColorFilter.tint(MaterialTheme.colors.onSecondary),
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .fillMaxSize()
                    .background(MaterialTheme.colors.onSurface.copy(alpha = 0.6f)),
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

@Composable
private fun ApiKeyAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    shape: Shape = RoundedCornerShape(14.dp),
    apiKey: String,
    onApiKeyChange: (String) -> Unit
) {
    if (showDialog) {
        var currentApiKey by rememberSaveable { mutableStateOf(apiKey) }

        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Column(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.background,
                            shape = shape
                        )
                        .padding(18.dp)
                ) {
                    // Title
                    Text(
                        text = stringResource(id = R.string.api_key),
                        fontSize = 18.sp,
                        color = MaterialTheme.colors.onPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )

                    Spacer(Modifier.height(16.dp))

                    // Content
                    TextField(
                        value = currentApiKey,
                        onValueChange = { value ->
                            currentApiKey = value
                        },
                        textStyle = TextStyle(
                            color = MaterialTheme.colors.onPrimary,
                            fontSize = 18.sp
                        ),
                        placeholder = {
                            Text(
                                text = stringResource(id = R.string.insert_api_key),
                                color = MaterialTheme.colors.onPrimary.copy(alpha = 0.4f)
                            )
                        },
                        maxLines = 1,
                        shape = RoundedCornerShape(12.dp),
                        colors = TextFieldDefaults.textFieldColors(
                            cursorColor = MaterialTheme.colors.onPrimary,
                            backgroundColor = MaterialTheme.colors.onSecondary,
                            leadingIconColor = MaterialTheme.colors.onSurface,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Spacer(Modifier.height(16.dp))

                    // Action buttons
                    val saveButtonIsEnabled = currentApiKey.isNotBlank()
                    Row(
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        TextButton(onClick = onDismiss) {
                            Text(
                                text = stringResource(id = R.string.button_cancel).uppercase(),
                                color = MaterialTheme.colors.secondary
                            )
                        }
                        Spacer(Modifier.width(6.dp))
                        TextButton(
                            onClick = {
                                onDismiss.invoke()
                                onApiKeyChange.invoke(currentApiKey)
                            },
                            enabled = saveButtonIsEnabled
                        ) {
                            Text(
                                text = stringResource(id = R.string.button_save).uppercase(),
                                color = if (saveButtonIsEnabled) {
                                    MaterialTheme.colors.secondary
                                } else {
                                    MaterialTheme.colors.secondary.copy(alpha = 0.5f)
                                }
                            )
                        }
                    }
                }
            }
        )
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
            onAppThemeOptionClick = {}
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
            onApiKeyChange = {}
        )
    }
}