package com.company.frontend;

import com.company.backend.Payment;
import com.company.backend.Scheduler;
import org.telegram.telegrambots.api.objects.Message;
import org.telegram.telegrambots.api.objects.User;

import java.lang.reflect.Array;
import java.util.*;

public class Secretary {
    private Scheduler scheduler;

    public Secretary(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    public String run(Message message) {
        if (message.getText() != null) {
            List<String> list = new LinkedList<String>();
            Collections.addAll(list, message.getText().split(" "));
            list.remove(0);

            if(message.getText().startsWith("/add")){
                return addPraticipant(message);
            } else if (message.getText().startsWith("/pay")){
                return pay(message);
            }else if (message.getText().startsWith("/get")){
                if (scheduler.getParticipant(message.getChatId()).size() > 0) {
                    return scheduler.getParticipant(message.getChatId()).toString();
                } else
                    return "В списке ещё нет участников.";
            }else if (message.getText().startsWith("/calc")){
                if (scheduler.getParticipant(message.getChatId()).size() > 0) {
                    return scheduler.calculate(message.getChatId());
                } else
                    return "В списке ещё нет участников.";
            }else if (message.getText().startsWith("/help")){
                return Text.HELP;
            }
/*
            switch (message.getText().split(" ")[0]) {
                case ("/add"):
                    return addPraticipant(message);
                case ("/pay"):
                    return pay(message);
                case ("/get"):
                    if (scheduler.getParticipant(message.getChatId()).size() > 0) {
                        return scheduler.getParticipant(message.getChatId()).toString();
                    } else
                        return "В списке ещё нет участников.";
                case ("/calc"):
                    if (scheduler.getParticipant(message.getChatId()).size() > 0) {
                        return scheduler.calculate(message.getChatId());
                    } else
                        return "В списке ещё нет участников.";
                case ("/help"):
                    return Text.HELP;
            }
            */
        } else if (message.getNewChatMembers() != null) {
            for (User user : message.getNewChatMembers()) {
                if (user.getFirstName().equals(Config.botUsername)) {
                    scheduler.addSession(message.getChatId());
                    return Text.WELCOME;
                }
            }
        } else if (message.getLeftChatMember().getFirstName().equals(Config.botUsername)) {
            scheduler.deleteSession(message.getChatId());
            return null;
        }
        return null;
    }

    private String addPraticipant(Message message) {
        String messageText = message.getText();
        Set<String> set = new HashSet<String>(Arrays.asList(Parser.parsePatricipantsList(messageText)));
        System.out.println(set.toString());
        if (set.size() > 0) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String newName : set) {
                if (scheduler.getParticipant(message.getChatId()).contains(newName)) {
                    stringBuilder.append(newName + " уже есть в списке участников.\n");
                } else if (!newName.equals("")) {
                    scheduler.addParticipant(message.getChatId(), newName);
                    stringBuilder.append(newName + " добавлен в список участников.\n");
                }
            }
            return stringBuilder.toString();

        } else return "Добавлять некого";
    }

    private String pay(Message message) {
        Payment payment = Parser.parsePayment(message.getText());
        StringBuilder stringBuilder = new StringBuilder("");
        Set<String> setOfParticipant = scheduler.getParticipant(message.getChatId());
        System.out.println(payment.getCreditor() + " " + payment.getCredited());
        if (!setOfParticipant.contains(payment.getCreditor()))
            stringBuilder.append(payment.getCreditor() + " не состоит в списке участников.\n");
        for (String name : payment.getCredited()) {
            if (!setOfParticipant.contains(name))
                stringBuilder.append(name + " не состоит в списке участников.\n");
            if (name.equals(payment.getCreditor())){
                stringBuilder.append("Кредитор " + name + " не должен быть в списке кредитуемых\n");
            }
        }
        if (stringBuilder.toString().equals("")) {
            if (payment.getSum() != null) {
                scheduler.addPayment(message.getChatId(), payment);
                return "Платеж добавлен.";
            } else return "Неправильно введена или отсутствует сумма";
        } else {
            stringBuilder.append("Платеж добавлен не был.");
            return stringBuilder.toString();
        }
    }
}
