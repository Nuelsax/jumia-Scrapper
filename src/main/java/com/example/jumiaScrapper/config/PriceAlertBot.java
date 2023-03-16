package com.example.jumiaScrapper.config;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.util.ArrayList;
import java.util.List;

@Service

public class PriceAlertBot extends TelegramLongPollingBot {
    private List<Long> numbers;

    public PriceAlertBot() {
        numbers = new ArrayList<>();
        numbers.add(923477427L);
        numbers.add(5005052357L);
    }

    @Override
    public void onUpdateReceived(Update update) {
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
}
