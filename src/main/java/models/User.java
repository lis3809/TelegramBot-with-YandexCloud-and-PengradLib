package models;

import com.yandex.ydb.table.result.ResultSetReader;

import java.time.LocalDateTime;

public class User {
    private final long id;
    private final String firstName;
    private final LocalDateTime userDateAuth;                //Дата первого обращения пользователя к боту


    /**
     * ==================Конструктор===============================
     */
    public User(long id, String firstName) {
        this.id = id;
        this.firstName = firstName;
        this.userDateAuth = LocalDateTime.now();
    }

    public User(long id, String firstName, LocalDateTime userDateAuth) {
        this.id = id;
        this.firstName = firstName;
        this.userDateAuth = userDateAuth;
    }

    /**
     * ==================Метод для базы данных===============================
     */
    public static User fromResultSet(ResultSetReader resultSet) {

        long idUser = resultSet.getColumn("idUser").getUint64();
        String firstName = resultSet.getColumn("firstName").getUtf8();
        LocalDateTime userDateAuth = resultSet.getColumn("userDateAuth").getDatetime();

        return new User(idUser, firstName, userDateAuth);
    }

    public long getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public LocalDateTime getUserDateAuth() {
        return userDateAuth;
    }
}
