package com.company.frontend;

public class Text {
    public static final String WELCOME = "Вы начали использовать альфа-версию бота для расчета долгов, " +
            "поздравляем!\n" +
            "Введите команду \"/help\" для получения списка команд.\n" +
            "Приятной работы.";
    public static final String PATRICIPANTS_ADDED = "В сессию были добавлены следующие участники:\n";
    public static final String NO_PATRICIPANTS = "введите имя хотя бы одного участника";
    public static final String NON_UNIQUE_PATRICIPANTS = "не уникальные";
    public static final String HELP = "Kоманды и их синтаксис:\n" +
            "\"/add name1, name2, ...\" - добавление участников, где name1, name2 - их имена\n" +
            "\"/pay creditor: credited1, credited2, credited3, ...: 1000\" - добавление платы, " +
            "где creditor - имя того кто платит," +
            "credited1, credited2, credited3, ... - имена тех за кого платит" +
            "1000 - сумма, которую платит\n" +
            "\"/get\" - выводит на экран список всех участников\n" +
            "\"/calc\" - выводит расчет\n" +
            "\"/help\" - помощь.";
}
