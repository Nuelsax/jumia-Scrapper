package com.example.jumiaScrapper.config;

//
//@Service
//public class PriceAlertBot extends TelegramLongPollingBot {
//
//    private static List<Long> numbers;
//
//    public PriceAlertBot() {
//        numbers = new ArrayList<>();
//        numbers.add(923477427L);
//        numbers.add(5005052357L);
//    }
//
//    @Override
//    public void onUpdateReceived(Update update) {
//        if(update.getMessage().getText().regionMatches(true, 0, "SET", 0, 3)) {
//            System.out.println("YES MATCHES!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//            String dataId = update.getMessage().getText().substring(3).trim();
////            WebScrapper.addDataIdToPermanentSoldOutList(dataId);
//        }
//        String message = "Welcome to jumia scrapping service";
//       sendTelegramMessage(message);
//
//    }
//
//    @Override
//    public String getBotUsername() {
//        // TODO
//        return "devNuelBot";
//    }
//
//    @Override
//    public String getBotToken() {
//        // TODO
//        return "6010261347:AAGIpJh8L2mFNWafzbu_-P5Tc-SKSoedZ-4";
//    }
//
//    public void sendTelegramMessage(String message)  {
//
//        for (Long number : numbers) {
//            System.out.println(number);
//            SendMessage response = new SendMessage();
//            response.setChatId(number);
//            response.setText(message);
//            try {
//                execute(response);
//                System.out.println("sending");
//            } catch (TelegramApiException e) {
//                e.printStackTrace();
//            }
//        }
//
//            }
//}
