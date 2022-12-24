package com.kproject.chatgpt.presentation.screens.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.*
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
    description: String
) {
    Column(
        modifier = modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            imageVector = ImageVector.vectorResource(id = iconResId),
            contentDescription = null,
            tint = MaterialTheme.colors.onPrimary,
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
    }
}

@CompletePreview
@Composable
private fun Preview() {
    PreviewTheme {
        EmptyListInfo(
            iconResId = R.drawable.ic_chat,
            title = stringResource(id = R.string.info_title_recent_chats_list_empty),
            description = stringResource(id = R.string.info_description_recent_chats_list_empty)
        )
    }
}