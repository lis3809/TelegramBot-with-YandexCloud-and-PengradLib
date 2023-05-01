package service;

import db.Dao;
import db.UserDao;
import models.User;

public class ConnectorYDB {

    private static Dao<User> userDao = new UserDao();

    public static User getUserFromYDB(long userIDfromUpdate, String firstName) {

        //Создаем newUserBot
        User newUserBot = new User(userIDfromUpdate, firstName);

        //Ищем этого пользователя в базе данных
        User oldUserBot = userDao.findUserByID(newUserBot.getId());

        if (oldUserBot == null) {
            //Сохраняем нового пользователя в базу данных
            userDao.saveNewUser(newUserBot);
            //Возвращаем нового пользователя
            System.out.println("В базу данных добавлен новый пользователь");
            return newUserBot;

        } else {
            System.out.println("Такой пользователь уже есть в базе данных");
            return oldUserBot;
        }
    }
}