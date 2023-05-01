import com.pengrad.telegrambot.BotUtils;
import com.pengrad.telegrambot.model.Update;
import models.User;
import service.ConnectorYDB;
import service.MessageHandler;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@WebServlet("/")
public class AppTelegramBot extends HttpServlet {

    private MessageHandler messageHandler = new MessageHandler();
    private static Map<Long, User> userHashMap = new HashMap<>();

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

        try {
            request.setCharacterEncoding("UTF-8");
            String stringRequest = new String(request.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
            Update update = BotUtils.parseUpdate(stringRequest);

            /**
             * Мы проверяем, есть ли в обновлении сообщение, а в сообщении есть ли текст
             */
            if (update.message() != null && update.message().text() != null) {

                com.pengrad.telegrambot.model.User userfromUpdate = update.message().from();
                long userIDfromUpdate = userfromUpdate.id();



                //Если пользователь новый, добавляем его в список
                if (userHashMap.isEmpty() || !userHashMap.containsKey(userIDfromUpdate)) {

                    /**=====Здесь необходимо пойти в базу данных и добавить туда нового пользователя или взять существующего======*/
                    User userBot = ConnectorYDB.getUserFromYDB(userIDfromUpdate, userfromUpdate.firstName());

                    //Добавляем пользователя в список
                    userHashMap.put(userIDfromUpdate, userBot);
                }


                /**=====Узнаем ID чата и приступаем к обработке поступившего сообщения======*/
                String userChatID = update.message().chat().id().toString();
                messageHandler.handleMessage(update.message().text(),
                        userChatID,
                        userHashMap.get(userIDfromUpdate));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
