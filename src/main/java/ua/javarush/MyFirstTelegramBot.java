package ua.javarush;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendAudio;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.util.Map;

import static ua.javarush.TelegramBotContent.*;
import static ua.javarush.TelegramBotUtils.*;
import static ua.javarush.TelegramBotUtils.createMessage;

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
            sendMessage(chatId,0,"step_1_pic",STEP_1_TEXT,
                    Map.of("Злам холодильника", "step_1_btn"),false,"i_wanna");
        }

        if (update.hasCallbackQuery()) {

            if (update.getCallbackQuery().getData().equals("step_1_btn") && getGlories(chatId) == 0) {
                sendMessage(chatId,20,"step_2_pic",STEP_2_TEXT,
                        Map.of("Взяти сосиску! +20 слави", "step_2_btn",
                                "Взяти рибку! +20 слави", "step_2_btn",
                                "Скинути банку з огірками! +20 слави", "step_2_btn"),false,"i_wanna");
                }

            if (update.getCallbackQuery().getData().equals("step_2_btn") && getGlories(chatId) == 20) {
                sendMessage(chatId,20,"step_3_pic",STEP_3_TEXT,
                        Map.of("Злам робота пилососа", "step_3_btn"),false,"i_wanna");
                }

            if (update.getCallbackQuery().getData().equals("step_3_btn") && getGlories(chatId) == 40) {
                sendMessage(chatId,30,"step_4_pic",STEP_4_TEXT,
                        Map.of("Відправити робопилосос за їжею! +30 слави", "step_4_btn",
                                "Проїхатися на робопилососі! +30 слави", "step_4_btn",
                                "Тікати від робопилососа! +30 слави", "step_4_btn"),false,"i_wanna");
            }

            if (update.getCallbackQuery().getData().equals("step_4_btn") && getGlories(chatId) == 70) {
                sendMessage(chatId,30,"step_5_pic",STEP_5_TEXT,
                        Map.of("Одягнути та включити GoPro!", "step_5_btn"),false,"i_wanna");
            }

            if (update.getCallbackQuery().getData().equals("step_5_btn") && getGlories(chatId) == 100) {
                sendMessage(chatId,40,"step_6_pic",STEP_6_TEXT,
                        Map.of("Бігати дахами, знімати на GoPro! +40 слави", "step_6_btn",
                                "З GoPro нападати на інших котів із засідки! +40 слави", "step_6_btn",
                                "З GoPro нападати на собак із засідки! +40 слави", "step_6_btn"),false,"i_wanna");
            }

            if (update.getCallbackQuery().getData().equals("step_6_btn") && getGlories(chatId) == 140) {
                sendMessage(chatId,40,"step_7_pic",STEP_7_TEXT,
                        Map.of("Злам пароля", "step_7_btn"),false,"i_wanna");
            }

            if (update.getCallbackQuery().getData().equals("step_7_btn") && getGlories(chatId) == 180) {
                sendMessage(chatId,50,"step_8_pic",STEP_8_TEXT,
                        Map.of("Вийти на подвір'я", "step_8_btn"),false,"i_wanna");
            }

            if (update.getCallbackQuery().getData().equals("step_8_btn") && getGlories(chatId) == 230) {
                sendMessage(chatId,50,"final_pic",FINAL_TEXT,
                        Map.of(),true,"i_wanna");
            }


        }//перший if



        }
        private  void sendMessage(Long chatId,int glories,String picName,String text,Map<String,String> buttons,boolean isFinal,String audioName){
        addGlories(chatId,glories);
        SendPhoto photoMessage = createPhotoMessage(chatId,picName);
        executeAsync(photoMessage);


        SendMessage message = createMessage(chatId,text,buttons);
        sendApiMethodAsync(message);

            if (isFinal) {
                SendAudio audioMessage = createAudioMessage(chatId, audioName);
                executeAsync(audioMessage);
            }
        }

    public static void main(String[] args) throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(new MyFirstTelegramBot());
    }
}