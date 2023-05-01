package db;

import appconfig.AppConfig;
import com.yandex.ydb.table.query.Params;
import com.yandex.ydb.table.result.ResultSetReader;
import com.yandex.ydb.table.values.PrimitiveValue;
import models.User;
import utils.ThrowingConsumer;

import java.util.ArrayList;
import java.util.List;

public class UserDao implements Dao<User> {

    private final EntityManager entityManager;

    public UserDao() {
        if (AppConfig.isRunInCloud()) {
            entityManager = new EntityManager(System.getenv("DATABASE"), System.getenv("ENDPOINT"));
        } else {
            entityManager = new EntityManager(AppConfig.getDATABASE(), AppConfig.getENDPOINT());
        }
    }

    @Override
    public void saveNewUser(User userBot) {
        entityManager.execute("DECLARE $idUser AS Uint64;" +
                        "DECLARE $firstName AS Utf8;" +
                        "DECLARE $userDateAuth AS Datetime;" +
                        "INSERT INTO UsersTable (idUser, firstName, userDateAuth)" +
                        "VALUES ($idUser, $firstName, $userDateAuth)",
                Params.of("$idUser", PrimitiveValue.uint64(userBot.getId()),
                        "$firstName", PrimitiveValue.utf8(userBot.getFirstName()),
                        "$userDateAuth", PrimitiveValue.datetime(userBot.getUserDateAuth()))
        );
    }

    @Override
    public User findUserByID(long idUserBot) {
        List<User> users = new ArrayList<>();
        entityManager.execute("DECLARE $idUser AS Uint64;" +
                        "SELECT * FROM UsersTable WHERE idUser = $idUser", Params.of("$idUser", PrimitiveValue.uint64(idUserBot)),
                ThrowingConsumer.unchecked(result -> {
                    ResultSetReader resultSetReader = result.getResultSet(0);
                    while (resultSetReader.next()) {
                        users.add(User.fromResultSet(resultSetReader));
                    }
                }));
        if (users.size() == 1) {
            return users.get(0);
        } else {
            return null;
        }
    }


}
