package com.kproject.chatgpt.presentation.screens.chat

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.extensions.getFormattedDate
import com.kproject.chatgpt.presentation.model.Message
import com.kproject.chatgpt.presentation.model.RecentChat
import com.kproject.chatgpt.presentation.model.fakeChatList
import com.kproject.chatgpt.presentation.screens.chat.components.AIModelOptionsAlertDialog
import com.kproject.chatgpt.presentation.screens.components.EmptyListInfo
import com.kproject.chatgpt.presentation.screens.components.ProgressIndicator
import com.kproject.chatgpt.presentation.screens.components.SimpleAlertDialog
import com.kproject.chatgpt.presentation.screens.components.TopBar
import com.kproject.chatgpt.presentation.screens.utils.Utils
import com.kproject.chatgpt.presentation.theme.PreviewTheme
import com.kproject.chatgpt.presentation.theme.SimplePreview

@Composable
fun ChatScreen(
    onNavigateBack: () -> Unit
) {
    val chatViewModel: ChatViewModel = hiltViewModel()
    var showAIModelOptionsDialog by remember { mutableStateOf(false) }
    val chatUiState = chatViewModel.chatUiState

    Content(
        uiState = chatUiState,
        onMessageValueChange = { message ->
            chatViewModel.onMessageValueChange(message)
        },
        onSendMessage = { message ->
            chatViewModel.sendMessage(message)
        },
        onNavigateBack = onNavigateBack,
        onOptionsClick = { showAIModelOptionsDialog = true }
    )

    AIModelOptionsAlertDialog(
        showDialog = showAIModelOptionsDialog,
        onDismiss = { showAIModelOptionsDialog = false },
        currentAIModelOptions = chatUiState.recentChat.aiModelOptions,
        onSaveAIModelOptions = { aiModelOptions ->
            chatViewModel.updateAIModelOptions(aiModelOptions)
        }
    )

    chatUiState.apiResponseErrorInfo?.let { info ->
        SimpleAlertDialog(
            showDialog = true,
            onDismiss = {
                chatViewModel.onApiResponseErrorInfoChange(null)
            },
            title = stringResource(id = info.titleResId),
            message = stringResource(id = info.descriptionResId),
            onClickButtonOk = {}
        )
    }
}

@Composable
private fun Content(
    modifier: Modifier = Modifier,
    uiState: ChatUiState,
    onMessageValueChange: (String) -> Unit,
    onSendMessage: (String) -> Unit,
    onNavigateBack: () -> Unit,
    onOptionsClick: () -> Unit
) {
    Column {
        CustomTopBar(
            recentChat = uiState.recentChat,
            onNavigateBack = onNavigateBack,
            onOptionsClick = onOptionsClick
        )

        if (uiState.isLoading) {
            ProgressIndicator(modifier = Modifier.weight(1f))
        } else {
            ChatList(
                chatList = uiState.messageList,
                modifier = Modifier
                    .weight(1f)
            )
        }

        Spacer(Modifier.height(8.dp))

        ChatTextField(
            message = uiState.message,
            enabled = !uiState.isWaitingApiResponse,
            onMessageValueChange = { message ->
                onMessageValueChange.invoke(message)
            },
            onSendMessage = { message ->
                onSendMessage.invoke(message)
            },
            modifier = Modifier.padding(12.dp)
        )
    }
}

@Composable
private fun CustomTopBar(
    modifier: Modifier = Modifier,
    recentChat: RecentChat,
    onNavigateBack: () -> Unit,
    onOptionsClick: () -> Unit
) {
    val chatIcon = if (recentChat.chatMode) R.drawable.ic_chat else R.drawable.ic_manage_search
    TopBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Image(
                    imageVector = ImageVector.vectorResource(id = chatIcon),
                    contentDescription = null,
                    contentScale = ContentScale.Inside,
                    colorFilter = ColorFilter.tint(MaterialTheme.colors.onSurface),
                    modifier = Modifier.size(40.dp)
                )
                Spacer(Modifier.width(6.dp))
                Column {
                    Text(
                        text = recentChat.chatName,
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        modifier = Modifier
                            .fillMaxWidth()
                    )

                    Text(
                        text = stringResource(
                            id = R.string.used_tokens,
                            recentChat.usedTokens.toString()
                        ),
                        color = MaterialTheme.colors.onSurface,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Normal,
                        maxLines = 1,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigateBack) {
                Icon(
                    imageVector = Icons.Default.ArrowBack,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
        actions = {
            IconButton(onClick = onOptionsClick) {
                Icon(
                    imageVector = Icons.Default.Settings,
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        },
        modifier = modifier
    )
}

@Composable
private fun ChatList(
    modifier: Modifier = Modifier,
    chatList: List<Message>
) {
    if (chatList.isNotEmpty()) {
        val lazyListState = rememberLazyListState()

        /**
         * Always scroll to the last message when opening the screen or when
         * the list size changes (sends or receives a new message).
         */
        LaunchedEffect(key1 = chatList.size) {
            lazyListState.scrollToItem(chatList.size)
        }

        LazyColumn(
            state = lazyListState,
            modifier = modifier.fillMaxSize()
        ) {
            itemsIndexed(chatList) { index, message ->
                ChatListItem(
                    message = message
                )
            }
        }
    } else {
        EmptyListInfo(
            iconResId = R.drawable.ic_chat,
            title = stringResource(id = R.string.info_title_empty_chat_list),
            description = stringResource(id = R.string.info_description_empty_chat_list),
            modifier = modifier
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
private fun ChatListItem(
    modifier: Modifier = Modifier,
    message: Message
) {
    val backgroundTextColor =
            if (message.sentByUser) MaterialTheme.colors.surface else MaterialTheme.colors.primary
    val alignment = if (message.sentByUser) Alignment.End else Alignment.Start
    val messageTextPadding = if (alignment == Alignment.End) {
        PaddingValues(start = 46.dp)
    } else {
        PaddingValues(end = 46.dp)
    }
    val shape = if (message.sentByUser) {
        RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = 20.dp, bottomEnd = 10.dp)
    } else {
        RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = 8.dp, bottomEnd = 20.dp)
    }

    var showCopyTextOption by remember { mutableStateOf(false) }

    Column(
        modifier =
        modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 12.dp)
    ) {
        Box(modifier = Modifier.align(alignment)) {
            Text(
                text = message.message,
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp,
                modifier = Modifier
                    .padding(messageTextPadding)
                    .background(
                        color = backgroundTextColor,
                        shape = shape
                    )
                    .clip(shape)
                    .combinedClickable(
                        onClick = {},
                        onLongClick = {
                            showCopyTextOption = true
                        }
                    )
                    .padding(8.dp)
            )

            CopyTextDropdownMenu(
                showCopyTextOption = showCopyTextOption,
                onDismiss = { showCopyTextOption = false },
                textToCopy = message.message
            )
        }

        Text(
            text = message.sendDate.getFormattedDate(),
            color = MaterialTheme.colors.onPrimary,
            maxLines = 1,
            fontSize = 13.sp,
            modifier = Modifier
                .padding(start = 6.dp, end = 6.dp)
                .align(alignment)
        )
    }
}

@Composable
fun ChatTextField(
    modifier: Modifier = Modifier,
    message: String,
    enabled: Boolean,
    onMessageValueChange: (String) -> Unit,
    onSendMessage: (String) -> Unit
) {
    Column(modifier = modifier) {
        AnimatedVisibility(
            visible = !enabled,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            WaitingApiResponseLoadingIndicator()
        }

        Spacer(Modifier.height(8.dp))

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TextField(
                value = message,
                onValueChange = { value ->
                    onMessageValueChange.invoke(value)
                },
                textStyle = TextStyle(
                    color = MaterialTheme.colors.onSurface,
                    fontSize = 18.sp
                ),
                placeholder = {
                    Text(
                        text = stringResource(id = R.string.message),
                        color = MaterialTheme.colors.onSurface.copy(0.5f)
                    )
                },
                enabled = enabled,
                maxLines = 4,
                shape = RoundedCornerShape(14.dp),
                colors = TextFieldDefaults.textFieldColors(
                    cursorColor = MaterialTheme.colors.onSurface,
                    backgroundColor = MaterialTheme.colors.surface,
                    leadingIconColor = Color.White,
                    trailingIconColor = Color.White,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent,
                    focusedLabelColor = MaterialTheme.colors.secondary,
                    unfocusedLabelColor = MaterialTheme.colors.onSecondary,
                ),
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
            )

            Spacer(Modifier.width(8.dp))

            IconButton(
                onClick = { onSendMessage.invoke(message) },
                enabled = message.isNotBlank(),
                modifier = Modifier.background(
                    color = MaterialTheme.colors.secondary,
                    shape = CircleShape
                )
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = R.drawable.ic_send),
                    contentDescription = null,
                    tint = MaterialTheme.colors.onSurface
                )
            }
        }
    }
}

@Composable
private fun WaitingApiResponseLoadingIndicator() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .size(60.dp)
            .background(color = MaterialTheme.colors.primary, shape = CircleShape),
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = R.drawable.chatbot_icon),
            contentDescription = null,
            tint = MaterialTheme.colors.onSurface,
            modifier = Modifier
                .size(30.dp)

        )

        ProgressIndicator(
            color = MaterialTheme.colors.onPrimary,
            strokeWidth = 3.dp,
            modifier = Modifier.matchParentSize()
        )
    }
}

@Composable
private fun CopyTextDropdownMenu(
    showCopyTextOption: Boolean,
    onDismiss: () -> Unit,
    textToCopy: String
) {
    val context = LocalContext.current
    DropdownMenu(
        expanded = showCopyTextOption,
        onDismissRequest = onDismiss,
        modifier = Modifier
            .background(MaterialTheme.colors.surface)
            .clickable {
                Utils.copyToClipBoard(context, textToCopy)
                onDismiss.invoke()
            }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(12.dp)
        ) {
            Icon(
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_content_copy),
                contentDescription = null,
                tint = MaterialTheme.colors.onSurface
            )
            Spacer(Modifier.width(8.dp))
            Text(
                text = stringResource(id = R.string.copy_text),
                color = MaterialTheme.colors.onSurface,
                fontSize = 16.sp
            )
        }
    }
}

@SimplePreview
@Composable
private fun Preview() {
    PreviewTheme {
        val uiState = ChatUiState(
            messageList = fakeChatList,
            recentChat = RecentChat(
                chatName = "Android Questions",
                chatMode = false,
                usedTokens = 450
            )
        )
        Content(
            uiState = uiState,
            onMessageValueChange = {},
            onSendMessage = {},
            onNavigateBack = {},
            onOptionsClick = {}
        )
    }
}

@SimplePreview
@Composable
private fun CustomTopBarPreview() {
    PreviewTheme {
        CustomTopBar(
            recentChat = RecentChat(
                chatName = "Android Questions",
                chatMode = false,
                usedTokens = 300
            ),
            onNavigateBack = {},
            onOptionsClick = {}
        )
    }
}