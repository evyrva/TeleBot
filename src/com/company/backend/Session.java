package com.company.backend;

import org.telegram.telegrambots.api.methods.send.SendMessage;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.exceptions.TelegramApiException;
import org.telegram.telegrambots.api.objects.Message;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class Session implements Serializable {
    private Map<String, Double> users;
    private long chatID;
    private int roundConst = 1;

    protected Session(long chatID) {
        this.chatID = chatID;
        users = new HashMap<>();
    }

    protected Session(Map<String, Double> users, long chatID) {
        this.users = users;
        this.chatID = chatID;
    }

    protected void addUser(String name){
        users.put(name, 0.0);
    }

    protected boolean deleteUser(String name){
        return users.remove(name, users.get(name));
    }

    private SendMessage sendMes(String text){
        return new SendMessage()
                .setChatId(chatID)
                .setText(text);
    }

    protected void addPayment(Payment payment){
        users.replace(payment.getCreditor(),users.get(payment.getCreditor()) + payment.getSum()- payment.getSum()/(payment.getCredited().size()+1.0));
        for (String user : payment.getCredited()){
            users.replace(user, users.get(user) - payment.getSum()/(payment.getCredited().size()+1.0));
        }
    }

    protected String calc(){
        System.out.println(users.entrySet());
        StringBuilder result = new StringBuilder();
        for (String string : Counter.calculateMap(users, roundConst)){
            result.append(string);
            result.append("\n");
        }
        return result.toString();
    }

    public Map<String, Double> getUsers() {
        return users;
    }
}
