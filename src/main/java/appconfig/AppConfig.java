package appconfig;

public class AppConfig {

    private static final boolean runInCloud = false;          //При запуске локально пишем false, при запуске в YandexCloud - true

    private static final String nameBot = "Бот Барсик";

    //При запуске на локальном компьютере заполняем эти переменные
    private static final String botToken = "";
    private static final String tokenYC = ""; //yc iam create-token
    private static final String DATABASE = "";
    private static final String ENDPOINT = "";

    /*При запуске в облаке в CloudFunctions должны быть определены следующие переменные
    BOT_TOKEN
    DATABASE
    ENDPOINT
    */

    public static boolean isRunInCloud() {
        return runInCloud;
    }
    public static String getBotToken() {
        if (isRunInCloud()) {
            return System.getenv("BOT_TOKEN");
        } else {
            return botToken;
        }
    }
    public static String getTokenYC() {
        return tokenYC;
    }
    public static String getDATABASE() {
        return DATABASE;
    }
    public static String getENDPOINT() {
        return ENDPOINT;
    }
    public static String getNameBot() {
        return nameBot;
    }

}