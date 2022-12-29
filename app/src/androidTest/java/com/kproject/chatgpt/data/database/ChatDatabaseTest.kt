package com.kproject.chatgpt.data.database

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kproject.chatgpt.commom.model.AIModelOptions
import com.kproject.chatgpt.data.database.dao.MessageDao
import com.kproject.chatgpt.data.database.dao.RecentChatDao
import com.kproject.chatgpt.data.database.entity.RecentChatEntity
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import java.util.*
import javax.inject.Inject
import javax.inject.Named

@HiltAndroidTest
class ChatDatabaseTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_database")
    lateinit var chatDatabase: ChatDatabase
    private lateinit var recentChatDao: RecentChatDao
    lateinit var messageDao: MessageDao

    @Before
    fun setUp() {
        hiltRule.inject()
        recentChatDao = chatDatabase.recentChatDao()
        messageDao = chatDatabase.messageDao()
    }

    @After
    fun tearDown() {
        chatDatabase.close()
    }

    @Test
    fun addRecentChat_should_return_the_same_items_in_the_RecentChats_list() = runBlocking {
        val recentChat1 = RecentChatEntity(
            chatId = 1,
            chatName = "Chat 1",
            usedTokens = 500,
            lastMessage = "Hello",
            lastMessageDate = Date(),
            lastMessageSentByUser = false,
            chatMode = false,
            aiModelOptions = AIModelOptions()
        )
        val recentChat2 = RecentChatEntity(
            chatId = 2,
            chatName = "Chat 2",
            usedTokens = 600,
            lastMessage = "Hi",
            lastMessageDate = Date(),
            lastMessageSentByUser = false,
            chatMode = false,
            aiModelOptions = AIModelOptions()
        )
        val recentChat3 = RecentChatEntity(
            chatId = 3,
            chatName = "Chat 3",
            usedTokens = 700,
            lastMessage = "Yeap",
            lastMessageDate = Date(),
            lastMessageSentByUser = true,
            chatMode = true,
            aiModelOptions = AIModelOptions()
        )

        recentChatDao.addRecentChat(recentChat1)
        recentChatDao.addRecentChat(recentChat2)
        recentChatDao.addRecentChat(recentChat3)

        val recentChats = recentChatDao.getAllRecentChats().first()

        assertThat(recentChats.isNotEmpty()).isTrue()
        assertThat(recentChats.contains(recentChat1)).isTrue()
        assertThat(recentChats.contains(recentChat2)).isTrue()
        assertThat(recentChats.contains(recentChat3)).isTrue()
    }

}