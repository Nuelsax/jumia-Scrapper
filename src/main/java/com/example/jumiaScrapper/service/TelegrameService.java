package com.example.jumiaScrapper.service;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service
public class TelegrameService extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {
        if(update.getMessage().getText().regionMatches(true, 0, "SET", 0, 3)) {
            System.out.println("YES MATCHES!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
            String dataId = update.getMessage().getText().substring(3).trim();
//            addDataIdToPermanentSoldOutList(dataId);
        }

        if(update.getMessage().getText().regionMatches(true, 0, "CLEAR", 0, 3)) {
//            clearPermanentSoldOutList();
        }
        String message = "Welcome to jumia scrapping service";
        sendTelegramMessage(message);

    }

    @Override
    public String getBotUsername() {
        // TODO
        return "devNuelBot";
    }

    @Override
    public String getBotToken() {
        // TODO
        return "6010261347:AAGIpJh8L2mFNWafzbu_-P5Tc-SKSoedZ-4";
    }

    public void sendTelegramMessage(String message)  {
        numbers = new ArrayList<>();
        numbers.add(923477427L);
        numbers.add(5005052357L);
        for (Long number : numbers) {
            System.out.println(number);
            SendMessage response = new SendMessage();
            response.setChatId(number);
            response.setText(message);
            try {
                execute(response);
                System.out.println("sending");
            } catch (TelegramApiException e) {
                e.printStackTrace();
            }
        }

    }
    private static List<Long> numbers;
}
