package service;

import appconfig.AppConfig;
import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ReplyKeyboardRemove;
import com.pengrad.telegrambot.request.SendMessage;
import models.User;

public class MessageHandler {

    private final TelegramBot telegramBot = new TelegramBot(AppConfig.getBotToken());

    /**
     * =========Метод обработки сообщений=============
     */
    public void handleMessage(String userMessage, String userChatID, User user) {

        String[][] buttons = new String[][]{{"Привет!", "Пока!"}, {"Настройки"}};
        ReplyKeyboardMarkup replyKeyboardMarkup = new ReplyKeyboardMarkup(buttons);

        switch (userMessage) {
            case ("/start"):
                //Приветсвие при запуске
                telegramBot.execute(new SendMessage(userChatID, "Привет \uD83D\uDC4B, " + user.getFirstName() + "!"
                        + "\nМеня зовут " + AppConfig.getNameBot() + "!").replyMarkup(replyKeyboardMarkup));
                return;
            case ("Настройки"):
                telegramBot.execute(new SendMessage(userChatID, "Тут нечего настраивать. Всё итак работает! )").
                        replyMarkup(new ReplyKeyboardMarkup(buttons)));
                return;

            case ("Привет!"):
                telegramBot.execute(new SendMessage(userChatID, "Привет \uD83D\uDC4B, " + user.getFirstName() + "!").
                        replyMarkup(new ReplyKeyboardMarkup(buttons)));
                return;

            case ("Пока!"):
                telegramBot.execute(new SendMessage(userChatID, "Досвидания!").
                        replyMarkup(new ReplyKeyboardRemove()));
                return;

            default:
                //Отправляем сообщение пользователю
                telegramBot.execute(new SendMessage(userChatID, "Я еще маленький бот! \nМне далеко до ChatGPT4!").replyMarkup(replyKeyboardMarkup));
        }
    }

}
