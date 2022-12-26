package com.kproject.chatgpt.presentation.screens.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.theme.CompletePreview
import com.kproject.chatgpt.presentation.theme.PreviewTheme
import com.kproject.chatgpt.presentation.utils.ConversationMode

@Composable
fun ModeSelectionAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onModeSelected: (ConversationMode) -> Unit
) {
    if (showDialog) {
        var selectedMode by remember { mutableStateOf(ConversationMode.None) }

        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Column(
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.background,
                            shape = RoundedCornerShape(14.dp)
                        )
                        .padding(18.dp)
                ) {
                    // Content
                    Box(
                        modifier = Modifier
                            .verticalScroll(rememberScrollState())
                            .weight(1f, fill = false)
                    ) {
                        Column {
                            CardItem(
                                icon = R.drawable.ic_chat,
                                title = stringResource(id = R.string.chat_mode),
                                description = stringResource(id = R.string.chat_mode_description),
                                isSelected = selectedMode == ConversationMode.ChatMode,
                                onClick = {
                                    selectedMode = ConversationMode.ChatMode
                                }
                            )
                            Spacer(Modifier.height(6.dp))
                            CardItem(
                                icon = R.drawable.ic_manage_search,
                                title = stringResource(id = R.string.search_mode),
                                description = stringResource(id = R.string.search_mode_description),
                                isSelected = selectedMode == ConversationMode.SearchMode,
                                onClick = {
                                    selectedMode = ConversationMode.SearchMode
                                }
                            )
                        }
                    }

                    Spacer(Modifier.height(16.dp))

                    // Action buttons
                    val anyModeIsSelected = selectedMode != ConversationMode.None
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
                                onModeSelected.invoke(selectedMode)
                            },
                            enabled = anyModeIsSelected
                        ) {
                            Text(
                                text = stringResource(id = R.string.button_continue).uppercase(),
                                color = if (anyModeIsSelected) {
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

@Composable
private fun CardItem(
    @DrawableRes icon: Int,
    title: String,
    description: String,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    val border = if (isSelected) {
        BorderStroke(width = 4.dp, color = MaterialTheme.colors.onSecondary)
    } else {
        BorderStroke(width = 0.dp, color = Color.Transparent)
    }
    Card(
        backgroundColor = MaterialTheme.colors.surface,
        shape = RoundedCornerShape(16.dp),
        border = border,
        modifier = Modifier
            .padding(8.dp)
            .clickable { onClick.invoke() }
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .background(
                        color = MaterialTheme.colors.primary,
                        shape = RoundedCornerShape(12.dp)
                    )
                    .padding(8.dp)
                    .align(Alignment.CenterHorizontally)
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(id = icon),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    text = title,
                    color = MaterialTheme.colors.onPrimary,
                    fontSize = 18.sp,
                )
            }
            Spacer(Modifier.height(12.dp))
            Text(
                text = description,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 16.sp
            )
        }
    }
}

@CompletePreview
@Composable
private fun Preview() {
    PreviewTheme {
        ModeSelectionAlertDialog(
            showDialog = true,
            onDismiss = {},
            onModeSelected = {}
        )
    }
}