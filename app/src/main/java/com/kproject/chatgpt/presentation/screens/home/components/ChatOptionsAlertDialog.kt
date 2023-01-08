package com.kproject.chatgpt.presentation.screens.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.theme.CompletePreview
import com.kproject.chatgpt.presentation.theme.PreviewTheme

@Composable
fun ChatOptionsAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onRenameChatOptionClick: () -> Unit,
    onClearChatOptionClick: () -> Unit,
    onDeleteChatOptionClick: () -> Unit,
) {
    if (showDialog) {
        val defaultDialogPadding = 24.dp
        Dialog(
            onDismissRequest = onDismiss,
            content = {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colors.background,
                            shape = RoundedCornerShape(defaultDialogPadding)
                        )
                        .padding(
                            top = defaultDialogPadding,
                            bottom = defaultDialogPadding
                        )
                        .fillMaxWidth()
                ) {
                    // Content
                    OptionItem(
                        iconResId = R.drawable.ic_edit,
                        title = stringResource(id = R.string.rename_chat),
                        onClick = {
                            onDismiss.invoke()
                            onRenameChatOptionClick.invoke()
                        }
                    )
                    OptionItem(
                        iconResId = R.drawable.ic_delete_sweep,
                        title = stringResource(id = R.string.clear_chat),
                        onClick = {
                            onDismiss.invoke()
                            onClearChatOptionClick.invoke()
                        }
                    )
                    OptionItem(
                        iconResId = R.drawable.ic_delete,
                        title = stringResource(id = R.string.delete_chat),
                        onClick = {
                            onDismiss.invoke()
                            onDeleteChatOptionClick.invoke()
                        }
                    )
                }
            }
        )
    }
}

@Composable
private fun OptionItem(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int,
    title: String,
    onClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .clickable {
                onClick.invoke()
            }
            .padding(14.dp)
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary,
            modifier = Modifier.size(28.dp)
        )
        Spacer(Modifier.width(8.dp))
        Text(
            text = title,
            fontSize = 18.sp,
            color = MaterialTheme.colors.onPrimary
        )
    }
}

@CompletePreview
@Composable
private fun Preview() {
    PreviewTheme {
        ChatOptionsAlertDialog(
            showDialog = true,
            onDismiss = {},
            onRenameChatOptionClick = {},
            onClearChatOptionClick = {},
            onDeleteChatOptionClick = {}
        )
    }
}