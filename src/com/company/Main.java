package com.company;

import com.company.backend.Counter;
import com.company.backend.Payment;
import com.company.backend.Scheduler;
import com.company.frontend.Parser;
import org.telegram.telegrambots.ApiContextInitializer;
import org.telegram.telegrambots.TelegramBotsApi;
import org.telegram.telegrambots.exceptions.TelegramApiException;

import java.util.*;

public class Main {
    /*    static {
            Map<String, Double> map = new HashMap<>();
            map.put("Taras", -100.0);
            map.put("Egor", 100.0);
            map.put("Sasha", 100.0);
            map.put("Yura", -120.0);
            map.put("Katya", 300.0);
            map.put("Masha", 200.0);
            map.put("Pavel", -200.0);
            map.put("Sveta", -170.0);
            map.put("Olya", -110.0);
            for (String s : Counter.calculateMap(map, 1)) {
                System.out.println(s);
            }
        }
    */
    public static void main(String[] args) {
/*        // write your code here
        long chatId = 0000;
        long chatId1 = 1111;
        long chatId2 = 2222;
        Scheduler scheduler = new Scheduler();
        scheduler.addSession(chatId);
        scheduler.addSession(chatId1);
        scheduler.addSession(chatId2);


        List<String> names = new LinkedList<>();
        List<String> names1 = new LinkedList<>();
        Collections.addAll(names, "Egor", "Sasha", "Yura");
        Collections.addAll(names1, "Taras", "Sasha", "Yura");
        scheduler.addParticipant(chatId, "Taras");
        scheduler.addParticipant(chatId, names);
        Payment payment1 = new Payment("Taras", names, 1000.);
        Payment payment2 = new Payment("Egor", names1, 900.);
        scheduler.addPayment(chatId, payment1);
        System.out.println(scheduler.getParticipant(chatId).entrySet());
        scheduler.addPayment(chatId, payment2);
        scheduler.saveSession(chatId);

        System.out.println(scheduler.getParticipant(chatId).entrySet());
        System.out.println(scheduler.calculate(chatId));
*/

        ApiContextInitializer.init();

        TelegramBotsApi botsApi = new TelegramBotsApi();

        try {
            botsApi.registerBot(new ArrearsCalculateBot());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
//        double d = Double.valueOf("dfdasf");
//        System.out.println(new HashSet<String>(Arrays.asList(Parser.parsePayment("/pay 1: 2, 3, 4, 5: 1554"))));
    }
}
