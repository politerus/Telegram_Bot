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
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText().toLowerCase();
            SendMessage message = new SendMessage();

            switch (messageText) {
                case "привіт":
                    message = createMessage(chatId, "Привіт! Як до тебе можна звертатись?");
                    break;
                case "як справи?":
                    message = createMessage(chatId, "Дякую, все добре! Як у тебе?");
                    break;
                case "дякую, добре":
                case "все добре":
                    message = createMessage(chatId, "Це чудово! Чим можу допомогти?");
                    break;
                case "до побачення":
                    message = createMessage(chatId, "До побачення! Буду чекати на нашу наступну зустріч.");
                    break;
                default:
                    if (messageText.startsWith("мене звати ")) {
                        String name = messageText.substring("мене звати ".length());
                        message = createMessage(chatId, "Радий знайомству, " + name + "! Я – *JavaUa_Bot*");
                        message.setParseMode("Markdown");
                    } else if (messageText.startsWith("допоможи мені ")) {
                        String question = messageText.substring("допоможи мені ".length());
                        message = createMessage(chatId, "Вибач, я ще не вмію " + question + " Але я обов'язково навчусь!");
                        message.setParseMode("Markdown");
                    } else {
                        message = createMessage(chatId, "Вибач, я не розумію. Спробуй щось інше.");
                    }
            }

            try {
                execute(message);
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyFirstTelegramBot());
    }
}