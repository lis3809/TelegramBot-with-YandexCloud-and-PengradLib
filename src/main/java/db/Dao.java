package db;

import models.User;

public interface Dao<T> {

    void saveNewUser(User userBot);

    T findUserByID(long id);

}