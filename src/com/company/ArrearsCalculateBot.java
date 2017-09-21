package com.company;

import com.company.backend.Scheduler;
import com.company.backend.Session;
import com.company.frontend.Config;
import com.company.frontend.Secretary;
import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.api.methods.updatingmessages.EditMessageText;
import org.telegram.telegrambots.api.objects.Update;
import org.telegram.telegrambots.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static java.lang.Math.toIntExact;

public class ArrearsCalculateBot extends TelegramLongPollingBot {
    private Scheduler scheduler;
    private Secretary secretary;


    public ArrearsCalculateBot() {
        super();
        scheduler = new Scheduler();
        secretary = new Secretary(scheduler);
    }

    @Override
    public String getBotToken() {
        return Config.token;
    }

    @Override
    public String getBotUsername() {
        return "vyrvabot";
    }


    @Override
    public void onUpdateReceived(Update update) {

        // We check if the update has a message and the message has text
        System.out.println(update.toString());
        if (update.hasMessage()){
            SendMessage message = new SendMessage()
                    .setChatId(update.getMessage().getChatId())
                    .setText(secretary.run(update.getMessage()));
            sendMsg(message);
        }

    }

    public void sendMsg(SendMessage message){
        try {
            if (message.getText() != null)
                sendMessage(message); // Sending our message object to user
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}

