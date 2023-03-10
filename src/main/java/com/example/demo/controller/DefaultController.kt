package com.example.demo.controller

import com.example.demo.MyBotController
import com.github.kshashov.telegram.api.MessageType
import com.github.kshashov.telegram.api.bind.annotation.BotController
import com.github.kshashov.telegram.api.bind.annotation.BotRequest
import com.pengrad.telegrambot.model.Chat
import com.pengrad.telegrambot.model.Message
import com.pengrad.telegrambot.model.User
import com.pengrad.telegrambot.request.SendMessage
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.math.*

@BotController
@Component
class DefaultController : MyBotController() {

    private val log = LoggerFactory.getLogger(this.javaClass)
    private var fx: Double? = null

    @BotRequest(type = [MessageType.ANY])
    fun defaultMessageHandler(chat: Chat, user: User, message: Message): SendMessage {
        log.info("request bot handler chat: $chat, user: $user, message: $message")
        val decimal = parseDecimal(message.text())
            ?: return SendMessage(chat.id(), "Raqam kiriting")
        return SendMessage(chat.id(), mathFunc(decimal))
    }


    private fun parseDecimal(content: String?): Double? {
        return try {
            content?.toDouble()
        } catch (_: NumberFormatException) {
            null
        }
    }

    private fun mathFunc(decimal: Double): String {
        fx = 1 / sqrt(2 * PI) * E.pow(-decimal * decimal / 2)
//        fx = (fx!! * 10000).roundToInt().toDouble() / 10000
        return fx.toString()
    }
}