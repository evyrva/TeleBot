package com.company.backend;

import com.sun.javafx.collections.MappingChange;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public  class Scheduler {
    private static Map<Long, Session> sessionMap = new HashMap<>();

    public Scheduler() {
        File dirData = new File("Data");
        if (dirData.exists()) {
            if (dirData.list().length > 0) {
                for (String fileName : dirData.list()) {
                    loadSession(Long.valueOf(fileName.replaceAll("data_", "").replaceAll(".dat", "")));
                }
            }
        } else dirData.mkdir();
    }

    public void addSession(long chatId){
        sessionMap.put(chatId, new Session(chatId));
        saveSession(chatId);
    }

    public void addSession(long chatId, List<String> names){
        sessionMap.put(chatId, new Session(chatId));
        for (String name : names){
            sessionMap.get(chatId).addUser(name);
        }
        saveSession(chatId);
    }

    public void deleteSession(long chatId){
        sessionMap.remove(chatId);
        File file = new File("Data","data_"+String.valueOf(chatId)+".dat");
        file.delete();
    }

    public void addParticipant(long chatId, String name){
        sessionMap.get(chatId).addUser(name);
        saveSession(chatId);
    }

    public void addParticipant(long chatId, List<String> names){
        for (String name : names){
            sessionMap.get(chatId).addUser(name);
        }
        saveSession(chatId);
    }

    public void addParticipant(long chatId, Set<String> names){
        for (String name : names){
            sessionMap.get(chatId).addUser(name);
        }
        saveSession(chatId);
    }

    public void addPayment(long chatId, Payment payment){
        sessionMap.get(chatId).addPayment(payment);
        saveSession(chatId);
    }

    public String calculate(long chatId){
        return sessionMap.get(chatId).calc();
    }

    public Set<String> getParticipant(long chatId){
        return sessionMap.get(chatId).getUsers().keySet();
    }

    private void saveSession(long chatId){
        File file = new File("Data", "data_"+String.valueOf(chatId)+".dat");
        try {
            //проверяем, что если файл не существует то создаем его
            if (!file.exists()) {
                file.createNewFile();
            }
        } catch (IOException e){
            e.printStackTrace();
        }
        try(FileOutputStream fileOutput = new FileOutputStream(file);
            ObjectOutputStream outputStream = new ObjectOutputStream(fileOutput)){
            outputStream.writeObject(sessionMap.get(chatId));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private void loadSession(long chatId){
        File file = new File("Data", "data_" + String.valueOf(chatId) + ".dat");
        try(FileInputStream fileInputStream = new FileInputStream(file);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)){
            if (!sessionMap.containsKey(chatId)){
                sessionMap.put(chatId, new Session(chatId));
            }
            sessionMap.replace(chatId, (Session) objectInputStream.readObject());
        } catch (IOException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e){
            e.printStackTrace();
        }
    }

}
