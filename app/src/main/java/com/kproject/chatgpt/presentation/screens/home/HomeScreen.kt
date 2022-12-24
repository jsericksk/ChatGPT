package com.kproject.chatgpt.presentation.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kproject.chatgpt.presentation.model.RecentChat

@Composable
fun HomeScreen(

) {

}

@Composable
private fun HomeScreenContent(
    homeUiState: HomeUiState
) {

}


@Composable
private fun RecentChatsListItem(
    recentChat: RecentChat,
    loggedUserId: String,
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
            /**CustomImage(
                imageModel = latestMessage.userProfilePicture,
                modifier = Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .fillMaxSize()
            )*/

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
                val messageTextColor = if (latestMessage.senderId != loggedUserId) {
                    MaterialTheme.colors.secondary
                } else {
                    MaterialTheme.colors.onPrimary
                }
                Text(
                    text = recentChat.lastMessage,
                    color = messageTextColor,
                    maxLines = 1,
                    fontSize = 16.sp
                )
            }

            Text(
                text = recentChat.createdAt,
                color = MaterialTheme.colors.onPrimary,
                fontSize = 12.sp
            )
        }
    }
}
