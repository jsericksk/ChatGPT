package com.kproject.chatgpt.presentation.screens.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kproject.chatgpt.R
import com.kproject.chatgpt.presentation.theme.CompletePreview
import com.kproject.chatgpt.presentation.theme.PreviewTheme

@Composable
fun EmptyListInfo(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int,
    title: String,
    description: String,
    buttonTitle: String = "",
    onClickButton: () -> Unit = {}
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colors.primary,
            modifier = Modifier.size(110.dp)
        )
        Text(
            text = title,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(all = 6.dp)
        )
        Text(
            text = description,
            textAlign = TextAlign.Center,
            color = MaterialTheme.colors.onPrimary,
            fontSize = 16.sp,
            modifier = Modifier.padding(all = 6.dp)
        )
        Spacer(Modifier.height(8.dp))
        if (buttonTitle.isNotBlank()) {
            Button(
                onClick = onClickButton
            ) {
                Text(
                    text = buttonTitle,
                    fontSize = 16.sp,
                    color = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
fun EmptyInfo(
    modifier: Modifier = Modifier,
    @DrawableRes iconResId: Int,
    title: String,
    description: String,
    buttonTitle: String = "",
    onClickButton: () -> Unit = {}
) {
    EmptyListInfo(
        modifier = modifier,
        iconResId = iconResId,
        title = title,
        description = description,
        buttonTitle = buttonTitle,
        onClickButton = onClickButton
    )
}

@CompletePreview
@Composable
private fun Preview() {
    PreviewTheme {
        EmptyListInfo(
            iconResId = R.drawable.ic_chat,
            title = stringResource(id = R.string.info_title_empty_recent_chats_list),
            description = stringResource(id = R.string.info_description_empty_recent_chats_list)
        )
    }
}

@CompletePreview
@Composable
private fun EmptyInfoPreview() {
    PreviewTheme {
        EmptyInfo(
            iconResId = R.drawable.ic_key_off,
            title = stringResource(id = R.string.info_title_empty_api_key),
            description = stringResource(id = R.string.info_description_empty_api_key),
            buttonTitle = stringResource(id = R.string.access_url)
        )
    }
}