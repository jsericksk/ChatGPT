package com.kproject.chatgpt.presentation.screens.home.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.screens.components.CustomAlertDialog
import com.kproject.chatgpt.presentation.theme.CompletePreview
import com.kproject.chatgpt.presentation.theme.PreviewTheme
import com.kproject.chatgpt.presentation.model.ConversationMode

@Composable
fun ModeSelectionAlertDialog(
    showDialog: Boolean,
    onDismiss: () -> Unit,
    onModeSelected: (ConversationMode) -> Unit
) {
    if (showDialog) {
        var selectedMode by remember { mutableStateOf(ConversationMode.None) }

        CustomAlertDialog(
            showDialog = showDialog,
            onDismiss = onDismiss,
            onClickButtonOk = {
                onModeSelected.invoke(selectedMode)
            },
            okButtonEnabled = selectedMode != ConversationMode.None,
            okButtonTitle = stringResource(id = R.string.button_continue),
            showTitle = false
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
                    modifier = Modifier.size(30.dp),
                    tint = MaterialTheme.colors.onPrimary
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