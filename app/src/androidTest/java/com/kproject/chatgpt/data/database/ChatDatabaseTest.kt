package com.kproject.chatgpt.data.database

import android.database.sqlite.SQLiteConstraintException
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kproject.chatgpt.commom.model.AIModel
import com.kproject.chatgpt.commom.model.AIModelOptions
import com.kproject.chatgpt.data.database.dao.MessageDao
import com.kproject.chatgpt.data.database.dao.RecentChatDao
import com.kproject.chatgpt.data.database.entity.MessageEntity
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
        recentChatDao.addRecentChat(recentChat1)
        recentChatDao.addRecentChat(recentChat2)

        val recentChats = recentChatDao.getAllRecentChats().first()

        assertThat(recentChats.isNotEmpty()).isTrue()
        assertThat(recentChats.contains(recentChat1)).isTrue()
        assertThat(recentChats.contains(recentChat2)).isTrue()
    }

    @Test
    fun addRecentChat_should_return_your_specific_chatId() = runBlocking {
        val chatId1 = recentChatDao.addRecentChat(recentChat1)
        val chatId2 = recentChatDao.addRecentChat(recentChat2)

        assertThat(chatId1 == 1L).isTrue()
        assertThat(chatId2 == 2L).isTrue()
    }

    @Test
    fun getRecentChatById_should_return_the_corresponding_RecentChat() = runBlocking {
        recentChatDao.addRecentChat(recentChat1)
        val recentChat = recentChatDao.getRecentChatById(recentChat1.chatId)
        assertThat(recentChat == recentChat1).isTrue()
    }

    @Test
    fun deleteRecentChat_should_be_successful() = runBlocking {
        recentChatDao.addRecentChat(recentChat1)
        val recentChats = recentChatDao.getAllRecentChats().first()
        assertThat(recentChats.isNotEmpty()).isTrue()

        recentChatDao.deleteRecentChat(recentChat1)
        val updatedRecentChats = recentChatDao.getAllRecentChats().first()
        assertThat(updatedRecentChats.isEmpty()).isTrue()
    }

    @Test
    fun updateRecentChat_should_be_successful() = runBlocking {
        recentChatDao.addRecentChat(recentChat2)
        val recentChats = recentChatDao.getAllRecentChats().first()
        assertThat(recentChats.isNotEmpty()).isTrue()

        val updatedAIModelOptions = AIModelOptions(
            aiModel = AIModel.TextCurie001,
            maxTokens = 200,
            temperature = 1f
        )
        val updatedRecentChat = recentChat2.copy(
            chatName = "New Message Title",
            lastMessageSentByUser = true,
            aiModelOptions = updatedAIModelOptions
        )
        recentChatDao.updateRecentChat(updatedRecentChat)

        val recentChat = recentChatDao.getRecentChatById(updatedRecentChat.chatId)
        assertThat(recentChat == updatedRecentChat).isTrue()
        assertThat(recentChat.aiModelOptions.aiModel == AIModel.TextCurie001).isTrue()
    }

    @Test(expected = SQLiteConstraintException::class)
    fun addMessage_without_a_RecentChat_with_the_same_id_registered_before_should_throws_exception() = runBlocking {
        messageDao.addMessage(message1)
        messageDao.addMessage(message2)
        messageDao.addMessage(message3)
    }

    @Test
    fun addMessage_should_be_successful() = runBlocking {
        recentChatDao.addRecentChat(recentChat1)
        messageDao.addMessage(message1)
        messageDao.addMessage(message2)
        messageDao.addMessage(message3)

        val messageList = messageDao.getMessagesByChatId(message1.chatId).first()
        assertThat(messageList.size == 3).isTrue()
    }

    @Test
    fun deleteRecentChat_should_also_delete_all_messages_associated_with_the_chat() = runBlocking {
        recentChatDao.addRecentChat(recentChat2)
        messageDao.addMessage(message4)
        messageDao.addMessage(message5)

        val messageList = messageDao.getMessagesByChatId(recentChat2.chatId).first()
        assertThat(messageList.size == 2).isTrue()

        recentChatDao.deleteRecentChat(recentChat2)

        val updatedMessageList = messageDao.getMessagesByChatId(recentChat2.chatId).first()
        assertThat(updatedMessageList.isEmpty()).isTrue()
    }

    @Test
    fun deleteMessagesByChatId_should_be_successful() = runBlocking {
        recentChatDao.addRecentChat(recentChat2)
        messageDao.addMessage(message4)
        messageDao.addMessage(message5)

        val messageList = messageDao.getMessagesByChatId(recentChat2.chatId).first()
        assertThat(messageList.size == 2).isTrue()

        messageDao.deleteMessagesFromChatId(recentChat2.chatId)

        val updatedMessageList = messageDao.getMessagesByChatId(recentChat2.chatId).first()
        assertThat(updatedMessageList.isEmpty()).isTrue()
    }

    companion object {
        // RecentChats
        private val recentChat1 = RecentChatEntity(
            chatId = 1,
            chatName = "Message 1",
            usedTokens = 500,
            lastMessage = "Hello",
            lastMessageDate = Date(),
            lastMessageSentByUser = false,
            chatMode = false,
            aiModelOptions = AIModelOptions()
        )
        private val recentChat2 = RecentChatEntity(
            chatId = 2,
            chatName = "Message 2",
            usedTokens = 600,
            lastMessage = "Hi",
            lastMessageDate = Date(),
            lastMessageSentByUser = false,
            chatMode = false,
            aiModelOptions = AIModelOptions()
        )

        // Messages
        val message1 = MessageEntity(
            chatId = 1,
            message = "Are the tests in this class good?",
            sentByUser = true,
            sendDate = Date()
        )
        val message2 = MessageEntity(
            chatId = 1,
            message = "Nope!",
            sentByUser = false,
            sendDate = Date()
        )
        val message3 = MessageEntity(
            chatId = 1,
            message = "Yare yare daze :(",
            sentByUser = true,
            sendDate = Date()
        )

        val message4 = MessageEntity(
            chatId = 2,
            message = "Hello",
            sentByUser = true,
            sendDate = Date()
        )
        val message5 = MessageEntity(
            chatId = 2,
            message = "Hi user",
            sentByUser = false,
            sendDate = Date()
        )
    }
}