package ua.javarush;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

import static ua.javarush.TelegramBotContent.*;
import static ua.javarush.TelegramBotUtils.*;

public class MyFirstTelegramBot extends TelegramLongPollingBot {
    @Override
    public String getBotUsername() {
        // TODO: додай ім'я бота в лапки нижче
        return "java_ua_bot";
    }

    @Override
    public String getBotToken() {
        // TODO: додай токен бота в лапки нижче
        return "7121013400:AAEfQvpqd5aSj50NhoQ0vBCdbZUVe4CH608";
    }

    @Override
    public void onUpdateReceived(Update update) {
        Long chatId = getChatId(update);
        if (update.hasMessage() && update.getMessage().getText().equals("/start")) {
            addGlories(chatId,0);
          SendMessage message = createMessage(chatId,STEP_1_TEXT,Map.of(
                    "Злам холодильника", "step_1_btn"
            ));
          sendApiMethodAsync(message);
        }
        if (update.hasCallbackQuery()) {

            if (update.getCallbackQuery().getData().equals("step_1_btn") && getGlories(chatId) == 0) {
                addGlories(chatId,20);
                SendMessage message =   createMessage(chatId, STEP_2_TEXT,
                        Map.of("Взяти сосиску! +20 слави", "step_2_btn",
                                "Взяти рибку! +20 слави", "step_2_btn",
                                "Скинути банку з огірками! +20 слави", "step_2_btn"));
                sendApiMethodAsync(message);
            }

            if (update.getCallbackQuery().getData().equals("step_2_btn") && getGlories(chatId) == 20) {
                addGlories(chatId,20);
                SendMessage message =   createMessage(chatId, STEP_3_TEXT,
                        Map.of("Злам робота пилососа", "step_2_btn"));
                sendApiMethodAsync(message);
            }



        }//пергий if



        }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyFirstTelegramBot());
    }
}