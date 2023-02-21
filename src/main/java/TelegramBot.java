import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;

public class TelegramBot extends TelegramLongPollingBot {

    public static final String BOT_TOKEN = "6240344332:AAE_sZ_x-mco5-sgXEY_pA3H5h5KmrvNk70";
    public static final String BOT_NAME = "NasaParserBot";

    public static final String URI = "https://api.nasa.gov/planetary/apod?api_key=U1W8OdvCg0CPLbHFsJtB1Mfc8EEuzhUzAsEsVEVq";

    public static long chatId;

    public TelegramBot() throws TelegramApiException {
        TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
        botsApi.registerBot(this);

    }

    @Override
    public String getBotUsername() {
        return BOT_NAME;
    }

    @Override
    public String getBotToken() {
        return BOT_TOKEN;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            chatId = update.getMessage().getChatId();
            switch (update.getMessage().getText()) {
                case "/start" -> sendMessage("Привет, я бот который отправляет тебе " +
                        "актуальную фотографию с официального сайта NASA.\n" +
                        "Чтобы я отправил тебе фотографию, напиши: /photo");
                case "/photo" -> {
                    try {
                        sendMessage("Ссылка на фотографию:\n" + Util.getUrl(URI));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
                default -> sendMessage("Не могу распознать команду.");
            }
        }
    }

    public void sendMessage(String messageText){
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(messageText);
        try {
            execute(message);
        }
        catch (TelegramApiException e){
            e.printStackTrace();
        }
    }
}
