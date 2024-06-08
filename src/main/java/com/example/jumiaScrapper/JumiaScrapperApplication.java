package com.example.jumiaScrapper;

import com.example.jumiaScrapper.config.TwilioConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import com.example.jumiaScrapper.service.TelegrameService;

@SpringBootApplication
@EnableScheduling
public class JumiaScrapperApplication {
	@Autowired
	private TwilioConfig twilioConfig;

	@PostConstruct
	public void initTwilio() {
		try {
			TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
			botsApi.registerBot(new TelegrameService());
		} catch (TelegramApiException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		SpringApplication.run(JumiaScrapperApplication.class, args);
	}


}
