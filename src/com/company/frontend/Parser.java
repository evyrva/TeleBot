package com.company.frontend;

import com.company.backend.Payment;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

public class Parser {
    public static String[] parsePatricipantsList(String message) {
        return message.substring("/add".length())
                .trim()
                .replaceAll(","," ")
                .split(" +");
    }

    public static Payment parsePayment(String message) {
        String[] paymentString = message.substring("/pay".length())
                .trim()
                .replaceAll("[,: ]+", " ")
                .split(" +");
        List<String> creditedList = new LinkedList<>();
        for (int i = 1; i < paymentString.length-1; i++) {
            creditedList.add(paymentString[i]);
        }
        Payment payment = null;
        if (paymentString[paymentString.length-1].matches("^([0-9]+|[0-9]+\\.|[0-9]+\\.[0-9]+)$")) {
            try {
                payment = new Payment(paymentString[0], creditedList, Double.valueOf(paymentString[paymentString.length - 1]));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        } else {
            payment = new Payment(paymentString[0], creditedList, null);
        }
        return payment;
    }
}
