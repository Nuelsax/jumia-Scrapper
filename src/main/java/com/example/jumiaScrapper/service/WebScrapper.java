//package com.example.jumiaScrapper.service;
//
//import com.twilio.rest.api.v2010.account.Message;
//import lombok.RequiredArgsConstructor;
//import lombok.extern.slf4j.Slf4j;
//import com.example.jumiaScrapper.model.Product;
//import com.example.jumiaScrapper.model.ProductReprository;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//import org.jsoup.nodes.Element;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.stereotype.Service;
//
//import javax.sound.sampled.AudioInputStream;
//import javax.sound.sampled.AudioSystem;
//import javax.sound.sampled.Clip;
//import javax.sound.sampled.LineUnavailableException;
//import javax.sound.sampled.UnsupportedAudioFileException;
//import java.io.File;
//import java.io.IOException;
//import java.text.NumberFormat;
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Locale;
//import java.util.Objects;
//import java.util.Optional;
//import java.util.Set;
//
//@Service
//@RequiredArgsConstructor
//@Slf4j
//public class WebScrapper {
//
//
//    private final ProductReprository productReprository;
//    private final TelegrameService telegramService;
//    private static final long CLEAR_LIST_FIXED_RATE = 600000;
//    private static final long GENERAL_FIXED_RATE = 300000;
//    private static final long GENERAL_PHONE_RATE = 30000;
//
//    private static List<String> doNotDisturbProduct = new ArrayList<>();
//
//    String niveaPage1url  = "https://www.jumia.com.ng/mlp-nivea-store-deals/?shop_premium_services=shop_express";
//    List<Product> niveaPage1Products = new ArrayList<>();
//    boolean intialStartUp = true;
//    int niveaPage1PreviousCount = -1;
//    String niveaPage1Name = "nivea page 1";
//
//    String niveaPage2url  = "https://www.jumia.com.ng/mlp-nivea-store-deals/?shop_premium_services=shop_express&page=2#catalog-listing";
//    List<Product> niveaPage2Products = new ArrayList<>();
//    int niveaPage2PreviousCount = -1;
//    String niveaPage2Name = "nivea page 2";
//
//    /////////////////////////////////////////////////////////////////////////
//    String largeappPage1url  = "https://www.jumia.com.ng/home-improvement-appliances/?shop_premium_services=shop_express";
//    List<Product> largeappPage1Products = new ArrayList<>();
//    int largeappPage1PreviousCount = -1;
//    String largeappPage1Name = "Large Appliancies page 1";
//
//    String largeappPage2url  = "https://www.jumia.com.ng/home-improvement-appliances/?shop_premium_services=shop_express&page=2#catalog-listing";
//    List<Product> largeappPage2Products = new ArrayList<>();
//    int largeappPage2PreviousCount = -1;
//    String largeappPage2Name = "Large Appliancies page 2";
//    String largeappPage3url  = "https://www.jumia.com.ng/home-improvement-appliances/?shop_premium_services=shop_express&page=3#catalog-listing";
//    List<Product> largeappPage3Products = new ArrayList<>();
//    int largeappPage3PreviousCount = -1;
//    String largeappPage3Name = "Large Appliancies page 3";
//    String largeappPage4url  = "https://www.jumia.com.ng/home-improvement-appliances/?shop_premium_services=shop_express&page=4#catalog-listing";
//    List<Product> largeappPage4Products = new ArrayList<>();
//    int largeappPage4PreviousCount = -1;
//    String largeappPage4Name = "Large Appliancies page 4";
//
//    /////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    String infinixPage2url  = "https://www.jumia.com.ng/catalog/?q=infinix+phones&shop_premium_services=shop_express&price=20000-300000&page=2#catalog-listing";
//    List<Product> infinixPage2Products = new ArrayList<>();
//    int infinixPage2PreviousCount = -1;
//    String infinixPage2Name = "infinix Page 2";
//
//
//    ////////////////////////////////////////////////////////////////////////////////////////////////////////////
//    String groceriesPage1url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express#catalog-listing";
//    List<Product>groceriesPage1Products = new ArrayList<>();
//    int groceriesPage1PreviousCount = -1;
//    String groceriesPage1Name = "groceries Page 1";
//
//
//    String groceriesPage2url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=2#catalog-listing";
//    List<Product>groceriesPage2Products = new ArrayList<>();
//    int groceriesPage2PreviousCount = -1;
//    String groceriesPage2Name = "groceries Page 2";
//
//    String groceriesPage3url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=3#catalog-listing";
//    List<Product>groceriesPage3Products = new ArrayList<>();
//    int groceriesPage3PreviousCount = -1;
//    String groceriesPage3Name = "groceries Page 3";
//
//    String groceriesPage4url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=4#catalog-listing";
//    List<Product> groceriesPage4Products = new ArrayList<>();
//    int groceriesPage4PreviousCount = -1;
//    String groceriesPage4Name = "groceries Page 4";
//
//    String groceriesPage5url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=5#catalog-listing";
//    List<Product> groceriesPage5Products = new ArrayList<>();
//    int groceriesPage5PreviousCount = -1;
//    String groceriesPage5Name = "groceries Page 5";
//
//    String groceriesPage6url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=6#catalog-listing";
//    List<Product> groceriesPage6Products = new ArrayList<>();
//    int groceriesPage6PreviousCount = -1;
//    String groceriesPage6Name = "groceries Page 6";
//
//    String groceriesPage7url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=7#catalog-listing";
//    List<Product> groceriesPage7Products = new ArrayList<>();
//    int groceriesPage7PreviousCount = -1;
//    String groceriesPage7Name = "groceries Page 7";
//
//    String groceriesPage8url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=8#catalog-listing";
//    List<Product> groceriesPage8Products = new ArrayList<>();
//    int groceriesPage8PreviousCount = -1;
//    String groceriesPage8Name = "groceries Page 8";
//
//    String groceriesPage9url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=9#catalog-listing";
//    List<Product> groceriesPage9Products = new ArrayList<>();
//    int groceriesPage9PreviousCount = -1;
//    String groceriesPage9Name = "groceries Page 9";
//
//    String groceriesPage10url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=10#catalog-listing";
//    List<Product> groceriesPage10Products = new ArrayList<>();
//    int groceriesPage10PreviousCount = -1;
//    String groceriesPage10Name = "groceries Page 10";
//
//    String groceriesPage11url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=11#catalog-listing";
//    List<Product> groceriesPage11Products = new ArrayList<>();
//    int groceriesPage11PreviousCount = -1;
//    String groceriesPage11Name = "groceries Page 11";
//
//    String groceriesPage12url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=12#catalog-listing";
//    List<Product> groceriesPage12Products = new ArrayList<>();
//    int groceriesPage12PreviousCount = -1;
//    String groceriesPage12Name = "groceries Page 12";
//
//    String groceriesPage13url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=13#catalog-listing";
//    List<Product> groceriesPage13Products = new ArrayList<>();
//    int groceriesPage13PreviousCount = -1;
//    String groceriesPage13Name = "groceries Page 13";
//
//    String groceriesPage14url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=14#catalog-listing";
//    List<Product> groceriesPage14Products = new ArrayList<>();
//    int groceriesPage14PreviousCount = -1;
//    String groceriesPage14Name = "groceries Page 14";
//
//    String groceriesPage15url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=15#catalog-listing";
//    List<Product> groceriesPage15Products = new ArrayList<>();
//    int groceriesPage15PreviousCount = -1;
//    String groceriesPage15Name = "groceries Page 15";
//
//    String groceriesPage16url  = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express&page=16#catalog-listing";
//    List<Product> groceriesPage16Products = new ArrayList<>();
//    int groceriesPage16PreviousCount = -1;
//    String groceriesPage16Name = "groceries Page 16";
//
//
//
//
//    //////////////////////////////////////////////////////////////////////////////////////////////////////////////
//
//    List<Product> samsungProducts = new ArrayList<>();
//    List<Product> samsungPage2Products = new ArrayList<>();
//    List<Product> tecnoProducts = new ArrayList<>();
//    List<Product> infinixProducts = new ArrayList<>();
//    List<Product> itelProducts = new ArrayList<>();
//    List<Product> redmiProducts = new ArrayList<>();
//    List<Product> soldOutProducts = new ArrayList<>();
//    List<Product> tecnoPage2Products = new ArrayList<>();
//
//    Set<String> permanentSoldOutList = new HashSet<>();
//
//    List<Product> newFoundProducts = new ArrayList<>();
//    List<Product> computingProducts = new ArrayList<>();
//    List<Product> allPhonesProducts = new ArrayList<>();
//    List<Product> groceries = new ArrayList<>();
//    private int samsungPreviousCount = -1;
//    private int samsungPage2PreviousCount = -1;
//    private int tecnoPreviousCount = -1;
//
//    private int itelPreviousCount = -1;
//    private int computingPreviousCount = -1;
//    private int infinixPreviousCount = -1;
//
//    private int redmiPreviousCount = -1;
//    private int tecnoPage2PreviousCount = -1;
//
//    private int allPhonesPreviousCount = -1;
//    private int groceriesPreviousCount = -1;
//    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "NG"));
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 33000)
//    public void niveaPage1() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(niveaPage1url, niveaPage1Products,niveaPage1PreviousCount,niveaPage1Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 36000)
//    public void niveaPage2() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(niveaPage2url, niveaPage2Products,niveaPage2PreviousCount,niveaPage2Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 39000)
//    public void largeappPage1() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(largeappPage1url, largeappPage1Products,largeappPage1PreviousCount,largeappPage1Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 42000)
//    public void largeappPage2() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(largeappPage2url, largeappPage2Products,largeappPage2PreviousCount,largeappPage2Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 45000)
//    public void largeappPage3() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(largeappPage3url, largeappPage3Products,largeappPage3PreviousCount,largeappPage3Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 48000)
//    public void largeappPage4() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(largeappPage4url, largeappPage4Products,largeappPage4PreviousCount,largeappPage4Name);
//    }
//
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 51000)
//    public void infinixPage2() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(infinixPage2url, infinixPage2Products,infinixPage2PreviousCount,infinixPage2Name);
//    }
//
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 54000)
//    public void groceriesPage1() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage1url, groceriesPage1Products,groceriesPage1PreviousCount,groceriesPage1Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 57000)
//    public void groceriesPage2() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage2url, groceriesPage2Products,groceriesPage2PreviousCount,groceriesPage2Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 60000)
//    public void groceriesPage3() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage3url, groceriesPage3Products, groceriesPage3PreviousCount, groceriesPage3Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 63000)
//    public void groceriesPage4() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage4url, groceriesPage4Products, groceriesPage4PreviousCount, groceriesPage4Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 66000)
//    public void groceriesPage5() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage5url, groceriesPage5Products, groceriesPage5PreviousCount, groceriesPage5Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 69000)
//    public void groceriesPage6() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage6url, groceriesPage6Products, groceriesPage6PreviousCount, groceriesPage6Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 72000)
//    public void groceriesPage7() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage7url, groceriesPage7Products, groceriesPage7PreviousCount, groceriesPage7Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 75000)
//    public void groceriesPage8() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage8url, groceriesPage8Products, groceriesPage8PreviousCount, groceriesPage8Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 78000)
//    public void groceriesPage9() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage9url, groceriesPage9Products, groceriesPage9PreviousCount, groceriesPage9Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 81000)
//    public void groceriesPage10() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage10url, groceriesPage10Products, groceriesPage10PreviousCount, groceriesPage10Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 84000)
//    public void groceriesPage11() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage11url, groceriesPage11Products, groceriesPage11PreviousCount, groceriesPage11Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 87000)
//    public void groceriesPage12() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage12url, groceriesPage12Products, groceriesPage12PreviousCount, groceriesPage12Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 90000)
//    public void groceriesPage13() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage13url, groceriesPage13Products, groceriesPage13PreviousCount, groceriesPage13Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 93000)
//    public void groceriesPage14() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage14url, groceriesPage14Products, groceriesPage14PreviousCount, groceriesPage14Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 96000)
//    public void groceriesPage15() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage15url, groceriesPage15Products, groceriesPage15PreviousCount, groceriesPage15Name);
//    }
//
//    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 990000)
//    public void groceriesPage16() throws UnsupportedAudioFileException, LineUnavailableException, IOException {
//        scarpeAndComparePricesRefactor(groceriesPage16url, groceriesPage16Products, groceriesPage16PreviousCount, groceriesPage16Name);
//    }
//
//
//
//    @Scheduled(fixedRate = GENERAL_PHONE_RATE)
//    public void scarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        log.info("starting jumia webScrapper Service");
//        final String url = "https://www.jumia.com.ng/catalog/?q=samsung+phones&shop_premium_services=shop_express&price=30000-358700#catalog-listing";
//        String documentClass = "articlev.prd._fb.col.c-prd";
//        String productNameClass = "h3.name";
//        String productPriceClass = "div.prc";
//        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav");
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//
//
//
//        System.out.println("PREVIOUS PRODUCTS");
//        System.out.println(samsungProducts);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            int count =  0;
//            List<Product> newProducts = new ArrayList<>();
//            for(Element row: document.select(documentClass)) {
//                final String name = row.select(productNameClass).text();
//                final String pricestr = row.select(productPriceClass).text();
//                Element link = row.selectFirst("a.core");
//                String dataId = link.attr("data-id");
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                int price = Integer.parseInt(numStr);
//                String formatedPrice = currencyFormat.format(price);
//                log.info("this is product {} and price {} and sku {}", name, formatedPrice, dataId);
//                Optional<Product> productOpt = samsungProducts.stream()
//                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
//                        .findFirst();
//
//                if (productOpt.isPresent()) {
//                    Product product = productOpt.get();
//                    log.info("this is found product {}", product);
//                    count++;
//                    if (product.getPrice() > price) {
//                        String message = "Price decreased for " + name + " from " + currencyFormat.format(product.getPrice())  + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else if (product.getPrice() < price) {
//                        String message = "Price increased for " + name + " from " + currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else {
//                        String message = "Price remain the same for " + name + " from " + currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                    }
//                    product.setPrice(price);
//                    newProducts.add(product);
//                } else {
//                    // This is a new product
//                    Product newProduct = new Product(name, price, dataId);
//                    if (!newProduct.getDataId().isEmpty()) {
//                        newProducts.add(newProduct);
//                        samsungProducts.add(newProduct);
//                        count++;
//                    }
//                    String message = "New product found: " + name + " with price " + currencyFormat.format(price) + " SKU " + dataId;
//                    log.info(message);
//                    if (samsungPreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
//                            .isEmpty()) {
//                        telegramService.sendTelegramMessage(message);
//                        newFoundProducts.add(newProduct);
//                    }
//
//                }
//            }
//            if (samsungPreviousCount != -1 && count != samsungPreviousCount) {
//                if(samsungPreviousCount > count) {
//                  String message =  "Samsung count has reduced to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//
//                } else {
//                    String message = "Samsung count has increased to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//                }
//            }
//
//            List<Product> missingProducts = new ArrayList<>();
//            if(!newProducts.isEmpty()) {
//                for (Product product : samsungProducts) {
//                    if (!newProducts.contains(product)) {
//                        missingProducts.add(product);
//                    }
//                }
//            }
//            log.info("MISSING PRODUCTS");
//            log.info("This is missing product {}" , missingProducts);
//
//            if (samsungPreviousCount != -1 && !missingProducts.isEmpty()) {
//                System.out.println("The following products were not found in the new web scrape:");
//                for (Product product : missingProducts) {
//                    String message = "this product has sold out " + product.getName() + "with price " + currencyFormat.format(product.getPrice()) + "and SKU " + product.getDataId();
//                    log.info(message);
////                    if(!soldOutProducts.contains(product) && !permanentSoldOutList.contains(product.getDataId())) {
////                        telegramService.sendTelegramMessage(message);
////                    }
//                    soldOutProducts.add(product);
//                }
//            }
//
//            String message ="total count for samsung is " + count;
//            log.info(message);
//            if(samsungPreviousCount == -1) {
//                telegramService.sendTelegramMessage(message);
//            }
//            if(!newProducts.isEmpty()) {
//                samsungProducts = newProducts;
//            }
//            samsungPreviousCount = count;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//    @Scheduled(fixedRate = GENERAL_PHONE_RATE, initialDelay = 3000)
//    public void samsungPage2() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        final String url = "https://www.jumia.com.ng/catalog/?q=samsung+phones&shop_premium_services=shop_express&price=30000-358700&page=2#catalog-listing";
//        String documentClass = "article.prd._fb.col.c-prd";
//        String productNameClass = "h3.name";
//        String productPriceClass = "div.prc";
//        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav");
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//
//
//
//        System.out.println("PREVIOUS PRODUCTS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println(samsungPage2Products);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            int count =  0;
//            List<Product> newProducts = new ArrayList<>();
//            for(Element row: document.select(documentClass)) {
//                final String name = row.select(productNameClass).text();
//                final String pricestr = row.select(productPriceClass).text();
//                Element link = row.selectFirst("a.core");
//                String dataId = link.attr("data-id");
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                int price = Integer.parseInt(numStr);
//                String formatedPrice = currencyFormat.format(price);
//                log.info("this is product {} and price {} and sku {}", name, formatedPrice, dataId);
//                Optional<Product> productOpt = samsungPage2Products.stream()
//                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
//                        .findFirst();
//
//                if (productOpt.isPresent()) {
//                    Product product = productOpt.get();
//                    log.info("this is found product {}", product);
//                    count++;
//                    if (product.getPrice() > price) {
//                        String message = "Price decreased for " + name + " from " + currencyFormat.format(product.getPrice())  + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else if (product.getPrice() < price) {
//                        String message = "Price increased for " + name + " from " + currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else {
//                        String message = "Price remain the same for " + name + " from " + currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                    }
//                    product.setPrice(price);
//                    newProducts.add(product);
//                } else {
//                    // This is a new product
//                    Product newProduct = new Product(name, price, dataId);
//                    if (!newProduct.getDataId().isEmpty()) {
//                        newProducts.add(newProduct);
//                        samsungPage2Products.add(newProduct);
//                        count++;
//                    }
//                    String message = "New product found: " + name + " with price " + currencyFormat.format(price) + " SKU " + dataId;
//                    log.info(message);
//                    if (samsungPage2PreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
//                            .isEmpty()) {
//                        telegramService.sendTelegramMessage(message);
//                        newFoundProducts.add(newProduct);
//                    }
//
//                }
//            }
//            if (samsungPage2PreviousCount != -1 && count != samsungPage2PreviousCount) {
//                if(samsungPage2PreviousCount > count) {
//                    String message =  "Samsung page 2 count has reduced to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//
//                } else {
//                    String message = "Samsung page 2 count has increased to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//                }
//            }
//
//            List<Product> missingProducts = new ArrayList<>();
//            if(!newProducts.isEmpty()) {
//                for (Product product : samsungPage2Products) {
//                    if (!newProducts.contains(product)) {
//                        missingProducts.add(product);
//                    }
//                }
//            }
//            log.info("MISSING PRODUCTS");
//            log.info("This is missing product {}" , missingProducts);
//
//            if (samsungPage2PreviousCount != -1 && !missingProducts.isEmpty()) {
//                System.out.println("The following products were not found in the new web scrape:");
//                for (Product product : missingProducts) {
//                    String message = "this product has sold out " + product.getName() + "with price " + currencyFormat.format(product.getPrice()) + "and SKU " + product.getDataId();
//                    log.info(message);
////                    if(!soldOutProducts.contains(product) && !permanentSoldOutList.contains(product.getDataId())) {
////                        telegramService.sendTelegramMessage(message);
////                    }
//                    soldOutProducts.add(product);
//                }
//            }
//
//            String message ="total count for samsung page 2 is " + count;
//            log.info(message);
//            if(samsungPage2PreviousCount == -1) {
//                telegramService.sendTelegramMessage(message);
//            }
//            if(!newProducts.isEmpty()) {
//                samsungPage2Products = newProducts;
//            }
//            samsungPage2PreviousCount = count;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//
//
//    @Scheduled(fixedRate = GENERAL_PHONE_RATE, initialDelay = 6000)
//    public void tecnoScarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        log.info("starting jumia webScrapper Service for tecno");
//        final String url = "https://www.jumia.com.ng/catalog/?q=tecno+phones&shop_premium_services=shop_express&price=30000-368899#catalog-listing";
//        String documentClass = "article.prd._fb.col.c-prd";
//        String productNameClass = "h3.name";
//        String productPriceClass = "div.prc";
//        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav");
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//
//
//        log.info("PREVIOUS PRODUCTS");
//        log.info("this is product {}", tecnoProducts);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            int count =  0;
//            List<Product> newProducts = new ArrayList<>();
//            for(Element row: document.select(documentClass)) {
//                final String name = row.select(productNameClass).text();
//                final String pricestr = row.select(productPriceClass).text();
//                Element link = row.selectFirst("a.core");
//                String dataId = link.attr("data-id");
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                int price = Integer.parseInt(numStr);
//                String formatedPrice = currencyFormat.format(price);
//                log.info("this is product {} and price {}", name, formatedPrice);
//                log.info(dataId);
//
//
//                Optional<Product> productOpt = tecnoProducts.stream()
//                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
//                        .findFirst();
//
//                if (productOpt.isPresent()) {
//                    Product product = productOpt.get();
//                    log.info("this is found product {}", product);
//                    count++;
//                    if (product.getPrice() > price) {
//                        String message = "Price decreased for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else if (product.getPrice() < price) {
//                        String message = "Price increased for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else {
//                        String message = "Price remain the same for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                    }
//                    product.setPrice(price);
//                    newProducts.add(product);
//                } else {
//                    // This is a new product
//                    Product newProduct = new Product(name, price, dataId);
//                    if(!newProduct.getDataId().isEmpty()) {
//                        newProducts.add(newProduct);
//                        tecnoProducts.add(newProduct);
//                        count++;
//                    }
//                    if(tecnoPreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
//                            .isEmpty()) {
//                        String message = "New product found: " + name + " with price " +  currencyFormat.format(price);
//                        telegramService.sendTelegramMessage(message);
//                        newFoundProducts.add(newProduct);
//                    }
//
//                }
//            }
//            if (tecnoPreviousCount != -1 && count != tecnoPreviousCount) {
//                if(tecnoPreviousCount > count) {
//                    String message =  "Tecno count has reduced to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//
//                } else {
//                    String message = "Tecno count has increased to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//                }
//            }
//
//            List<Product> missingProducts = new ArrayList<>();
//            if(!newProducts.isEmpty()) {
//                for (Product product : tecnoProducts) {
//                    if (!newProducts.contains(product)) {
//                        missingProducts.add(product);
//                    }
//                }
//            }
//            log.info("MISSING PRODUCTS");
//            log.info("This is missing product {}" , missingProducts);
//            System.out.println(missingProducts);
//            if (tecnoPreviousCount != -1 && !missingProducts.isEmpty()) {
//                System.out.println("The following products were not found in the new web scrape:");
//                for (Product product : missingProducts) {
//                    String message = "this product has sold out " + product.getName() + "with price " +  currencyFormat.format(product.getPrice()) + "and SKU " + product.getDataId();
//                    log.info(message);
////                    if(!soldOutProducts.contains(product) && !permanentSoldOutList.contains(product.getDataId())) {
////                        telegramService.sendTelegramMessage(message);
////                    }
//                    soldOutProducts.add(product);
//                }
//            }
//
//            String message ="total count for Tecno is " + count;
//            log.info(message);
//            if(tecnoPreviousCount == -1) {
//                telegramService.sendTelegramMessage(message);
//            }
//            if(count != 0) {
//                tecnoProducts = newProducts;
//            }
//            tecnoPreviousCount = count;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    @Scheduled(fixedRate = GENERAL_PHONE_RATE, initialDelay = 9000)
//    public void infinixScarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        log.info("starting jumia webScrapper Service infinix");
//        final String url = "https://www.jumia.com.ng/catalog/?q=infinix+phones&shop_premium_services=shop_express&price=20000-300000#catalog-listing";
//        String documentClass = "article.prd._fb.col.c-prd";
//        String productNameClass = "h3.name";
//        String productPriceClass = "div.prc";
//        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav");
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//
//        log.info("PREVIOUS PRODUCTS");
//        log.info("this is product {}", infinixProducts);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            int count =  0;
//            List<Product> newProducts = new ArrayList<>();
//            for(Element row: document.select(documentClass)) {
//                final String name = row.select(productNameClass).text();
//                final String pricestr = row.select(productPriceClass).text();
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                Element link = row.selectFirst("a.core");
//                String dataId = link.attr("data-id");
//                int price = Integer.parseInt(numStr);
//                log.info("this is product {} and price {}", name,  currencyFormat.format(price));
//
//                Optional<Product> productOpt = infinixProducts.stream()
//                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
//                        .findFirst();
//                if (productOpt.isPresent()) {
//                    Product product = productOpt.get();
//                    log.info("this is found product {}", product);
//                    count++;
//                    if (product.getPrice() > price) {
//                        String message = "Price decreased for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else if (product.getPrice() < price) {
//                        String message = "Price increased for " + name + " from " + currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else {
//                        String message = "Price remain the same for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                    }
//                    product.setPrice(price);
//                    newProducts.add(product);
//                } else {
//                    // This is a new product
//                    Product newProduct = new Product(name, price, dataId);
//                    if(!newProduct.getDataId().isEmpty()) {
//                        newProducts.add(newProduct);
//                        infinixProducts.add(newProduct);
//                        count++;
//                    }
//                    if(infinixPreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
//                            .isEmpty()) {
//                        String message = "New product found: " + name + " with price " + currencyFormat.format(price);
//                        telegramService.sendTelegramMessage(message);
//                        newFoundProducts.add(newProduct);
//                    }
//
//                }
//            }
//            if (infinixPreviousCount != -1 && count != infinixPreviousCount) {
//                if(infinixPreviousCount > count) {
//                    String message =  "Infinix count has reduced to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//
//                } else {
//                    String message = "Infinix count has increased to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//                }
//            }
//
//            List<Product> missingProducts = new ArrayList<>();
//            if(!newProducts.isEmpty()) {
//                for (Product product : infinixProducts) {
//                    if (!newProducts.contains(product)) {
//                        missingProducts.add(product);
//                    }
//                }
//            }
//            log.info("MISSING PRODUCTS");
//            log.info("This is missing product {}" , missingProducts);
//
//            if (infinixPreviousCount != -1 && !missingProducts.isEmpty()) {
//                System.out.println("The following products were not found in the new web scrape:");
//                for (Product product : missingProducts) {
//                    String message = "this product has sold out " + product.getName() + "with price " + currencyFormat.format(product.getPrice()) + "and SKU " + product.getDataId();
//                    log.info(message);
////                    if(!soldOutProducts.contains(product) && !permanentSoldOutList.contains(product.getDataId())) {
////                        telegramService.sendTelegramMessage(message);
////                    }
//                    soldOutProducts.add(product);
//                }
//            }
//
//            String message ="total count for infinix is " + count;
//            log.info(message);
//            if(infinixPreviousCount == -1) {
//                telegramService.sendTelegramMessage(message);
//            }
//
//            if(count != 0) {
//                infinixProducts = newProducts;
//            }
//            infinixPreviousCount = count;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    @Scheduled(fixedRate = GENERAL_PHONE_RATE, initialDelay = 12000)
//    public void computingScarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        log.info("starting jumia webScrapper Service");
//        final String url = "https://www.jumia.com.ng/computers-tablets/?shop_premium_services=shop_express&shipped_from=country_local";
//        String documentClass = "article.prd._fb.col.c-prd";
//        String productNameClass = "h3.name";
//        String productPriceClass = "div.prc";
//        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav");
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//
//        log.info("PREVIOUS PRODUCTS");
//        log.info("this is product {}", computingProducts);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            int count =  0;
//            List<Product> newProducts = new ArrayList<>();
//            for(Element row: document.select(documentClass)) {
//                final String name = row.select(productNameClass).text();
//                final String pricestr = row.select(productPriceClass).text();
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                Element link = row.selectFirst("a.core");
//                String dataId = link.attr("data-id");
//                int price = Integer.parseInt(numStr);
//                log.info("this is product {} and price {}", name,  currencyFormat.format(price));
//
//                Optional<Product> productOpt = computingProducts.stream()
//                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
//                        .findFirst();
//                if (productOpt.isPresent()) {
//                    Product product = productOpt.get();
//                    log.info("this is found product {}", product);
//                    count++;
//                    if (product.getPrice() > price) {
//                        String message = "Price decreased for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else if (product.getPrice() < price) {
//                        String message = "Price increased for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else {
//                        String message = "Price remain the same for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                    }
//                    product.setPrice(price);
//                    newProducts.add(product);
//                } else {
//                    // This is a new product
//                    Product newProduct = new Product(name, price, dataId);
//                    if(!newProduct.getDataId().isEmpty()) {
//                        newProducts.add(newProduct);
//                        computingProducts.add(newProduct);
//                        count++;
//                    }
//                    if(computingPreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
//                            .isEmpty()) {
//                        String message = "New product found: " + name + " with price " +  currencyFormat.format(price);
//                        telegramService.sendTelegramMessage(message);
//                        newFoundProducts.add(newProduct);
//                    }
//
//                }
//            }
//            if (computingPreviousCount != -1 && count != computingPreviousCount) {
//                if(computingPreviousCount > count) {
//                    String message =  "Computing count has reduced to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//
//                } else {
//                    String message = "Computing count has increased to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//                }
//            }
//
//            List<Product> missingProducts = new ArrayList<>();
//            if(!newProducts.isEmpty()) {
//                for (Product product : computingProducts) {
//                    if (!newProducts.contains(product)) {
//                        missingProducts.add(product);
//                    }
//                }
//            }
//
//            log.info("MISSING PRODUCTS");
//            log.info("This is missing product {}" , missingProducts);
//
//            if (computingPreviousCount != -1 && !missingProducts.isEmpty()) {
//                System.out.println("The following products were not found in the new web scrape:");
//                for (Product product : missingProducts) {
//                    String message = "this product has sold out " + product.getName() + "with price " +  currencyFormat.format(product.getPrice()) + "and SKU " + product.getDataId();
//                    log.info(message);
////                    if(!soldOutProducts.contains(product) && !permanentSoldOutList.contains(product.getDataId())) {
////                        telegramService.sendTelegramMessage(message);
////                    }
//                    soldOutProducts.add(product);
//                }
//            }
//
//            String message ="total count for computing is " + count;
//            log.info(message);
//            if(computingPreviousCount == -1) {
//                telegramService.sendTelegramMessage(message);
//            }
//
//            if(!newProducts.isEmpty()) {
//                computingProducts = newProducts;
//            }
//
//            computingPreviousCount = count;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Scheduled(fixedRate = GENERAL_PHONE_RATE, initialDelay = 15000)
//    public void itelScarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        log.info("starting jumia webScrapper Service");
//        final String url = "https://www.jumia.com.ng/catalog/?q=itel+phones&shop_premium_services=shop_express&price=20000-95890#catalog-listing";
//        String documentClass = "article.prd._fb.col.c-prd";
//        String productNameClass = "h3.name";
//        String productPriceClass = "div.prc";
//        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav");
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//
//
//        log.info("PREVIOUS PRODUCTS");
//        log.info("this is product {}", itelProducts);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            int count =  0;
//            List<Product> newProducts = new ArrayList<>();
//            for(Element row: document.select(documentClass)) {
//                final String name = row.select(productNameClass).text();
//                final String pricestr = row.select(productPriceClass).text();
//                Element link = row.selectFirst("a.core");
//                Element status = row.selectFirst("div.bdg._oos._xs");
//                if (status != null) {
//                    System.out.println(status);
//                }
//                String dataId = link.attr("data-id");
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                int price = Integer.parseInt(numStr);
//                log.info("this is product {} and price {}", name,  currencyFormat.format(price));
//
//                Optional<Product> productOpt = itelProducts.stream()
//                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
//                        .findFirst();
//                if (productOpt.isPresent()) {
//                    Product product = productOpt.get();
//                    log.info("this is found product {}", product);
//                    count++;
//                    if (product.getPrice() > price) {
//                        String message = "Price decreased for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " +  currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else if (product.getPrice() < price) {
//                        String message = "Price increased for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else {
//                        String message = "Price remain the same for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                    }
//                    product.setPrice(price);
//                    newProducts.add(product);
//                } else {
//                    // This is a new product
//                    Product newProduct = new Product(name, price, dataId);
//                    if(!newProduct.getDataId().isEmpty()) {
//                        newProducts.add(newProduct);
//                        itelProducts.add(newProduct);
//                        count++;
//                    }
//                    if(itelPreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
//                            .isEmpty()) {
//                        String message = "New product found: " + name + " with price " + price;
//                        telegramService.sendTelegramMessage(message);
//                        newFoundProducts.add(newProduct);
//                    }
//
//                }
//            }
//            if (itelPreviousCount != -1 && count != itelPreviousCount) {
//                if(itelPreviousCount > count) {
//                    String message =  "Itel count has reduced to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//
//                } else {
//                    String message = "Itel count has increased to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//                }
//            }
//
//            List<Product> missingProducts = new ArrayList<>();
//            if(!newProducts.isEmpty()) {
//                for (Product product : itelProducts) {
//                    if (!newProducts.contains(product)) {
//                        missingProducts.add(product);
//                    }
//                }
//            }
//
//            log.info("MISSING PRODUCTS");
//            log.info("This is missing product {}" , missingProducts);
//
//            if (itelPreviousCount != -1 && !missingProducts.isEmpty()) {
//                System.out.println("The following products were not found in the new web scrape:");
//                for (Product product : missingProducts) {
//                    String message = "this product has sold out " + product.getName() + "with price " +  currencyFormat.format(product.getPrice()) + "and SKU " + product.getDataId();;
//                    log.info(message);
////                    if(!soldOutProducts.contains(product) && !permanentSoldOutList.contains(product.getDataId())) {
////                        telegramService.sendTelegramMessage(message);
////                    }
//                    soldOutProducts.add(product);
//                }
//            }
//
//            String message ="total count for itel is " + count;
//            log.info(message);
//            if(itelPreviousCount == -1) {
//                telegramService.sendTelegramMessage(message);
//            }
//
//            if(!newProducts.isEmpty()) {
//                itelProducts = newProducts;
//            }
//            itelPreviousCount = count;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//    @Scheduled(fixedRate = GENERAL_PHONE_RATE, initialDelay = 18000)
//    public void redmiScarpeAndComparePrices()  {
//        log.info("starting redmi webScrapper Service");
//        final String url = "https://www.jumia.com.ng/catalog/?q=redmi&shop_premium_services=shop_express&price=20000-210000#catalog-listing";
//        String documentClass = "article.prd._fb.col.c-prd";
//        String productNameClass = "h3.name";
//        String productPriceClass = "div.prc";
//
//
//        System.out.println("PREVIOUS PRODUCTS");
//        System.out.println(redmiProducts);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            int count =  0;
//            List<Product> newProducts = new ArrayList<>();
//            for(Element row: document.select(documentClass)) {
//                final String name = row.select(productNameClass).text();
//                final String pricestr = row.select(productPriceClass).text();
//                Element link = row.selectFirst("a.core");
//                String dataId = link.attr("data-id");
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                int price = Integer.parseInt(numStr);
//                log.info("this is product {} and price {} and sku {}", name,  currencyFormat.format(price), dataId);
//                Optional<Product> productOpt = redmiProducts.stream()
//                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
//                        .findFirst();
//
//                if (productOpt.isPresent()) {
//                    Product product = productOpt.get();
//                    log.info("this is found product {}", product);
//                    count++;
//                    if (product.getPrice() > price) {
//                        String message = "Price decreased for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " +  currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else if (product.getPrice() < price) {
//                        String message = "Price increased for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " +  currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else {
//                        String message = "Price remain the same for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " +  currencyFormat.format(price);
//                        log.info(message);
//                    }
//                    product.setPrice(price);
//                    newProducts.add(product);
//                } else {
//                    // This is a new product
//                    Product newProduct = new Product(name, price, dataId);
//                    if(!newProduct.getDataId().isEmpty()) {
//                        newProducts.add(newProduct);
//                        redmiProducts.add(newProduct);
//                        count++;
//                    }
//                    String message = "New product found: " + name + " with price " +  currencyFormat.format(price) + " SKU " + dataId;
//                    log.info(message);
//                    if(redmiPreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
//                            .isEmpty()) {
//                        telegramService.sendTelegramMessage(message);
//                        newFoundProducts.add(newProduct);
//                    }
//
//                }
//            }
//            if (redmiPreviousCount != -1 && count != redmiPreviousCount) {
//                if(redmiPreviousCount > count) {
//                    String message =  "Redmi count has reduced to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//
//                } else {
//                    String message = "Redmi count has increased to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//                }
//            }
//
//            List<Product> missingProducts = new ArrayList<>();
//            if(!newProducts.isEmpty()) {
//                for (Product product : redmiProducts) {
//                    if (!newProducts.contains(product)) {
//                        missingProducts.add(product);
//                    }
//                }
//            }
//            log.info("MISSING PRODUCTS");
//            log.info("This is missing product {}" , missingProducts);
//
//            if (redmiPreviousCount != -1 && !missingProducts.isEmpty()) {
//                System.out.println("The following products were not found in the new web scrape:");
//                for (Product product : missingProducts) {
//                    String message = "this product has sold out " + product.getName() + "with price " +  currencyFormat.format(product.getPrice()) + "and SKU " + product.getDataId();
//                    log.info(message);
////                    if(!soldOutProducts.contains(product) && !permanentSoldOutList.contains(product.getDataId())) {
////                        telegramService.sendTelegramMessage(message);
////                    }
//                    soldOutProducts.add(product);
//                }
//            }
//
//            String message ="total count for Redmi is " + count;
//            log.info(message);
//            if(redmiPreviousCount == -1) {
//                telegramService.sendTelegramMessage(message);
//            }
//            if(!newProducts.isEmpty()) {
//                redmiProducts = newProducts;
//            }
//            redmiPreviousCount = count;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
//    @Scheduled(fixedRate = GENERAL_PHONE_RATE, initialDelay = 18000)
//    public void tecnoPage2AndComparePrices()  {
//        log.info("starting jumia webScrapper Service");
//        final String url = "https://www.jumia.com.ng/catalog/?q=tecno+phones&shop_premium_services=shop_express&price=19999-373900&page=2#catalog-listing";
//        String documentClass = "article.prd._fb.col.c-prd";
//        String productNameClass = "h3.name";
//        String productPriceClass = "div.prc";
//
//
//
//        System.out.println("PREVIOUS PRODUCTS");
//        System.out.println(tecnoPage2Products);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            int count =  0;
//            List<Product> newProducts = new ArrayList<>();
//            for(Element row: document.select(documentClass)) {
//                final String name = row.select(productNameClass).text();
//                final String pricestr = row.select(productPriceClass).text();
//                Element link = row.selectFirst("a.core");
//                String dataId = link.attr("data-id");
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                int price = Integer.parseInt(numStr);
//                log.info("this is product {} and price {} and sku {}", name,  currencyFormat.format(price), dataId);
//                Optional<Product> productOpt = tecnoPage2Products.stream()
//                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
//                        .findFirst();
//
//                if (productOpt.isPresent()) {
//                    Product product = productOpt.get();
//                    log.info("this is found product {}", product);
//                    count++;
//                    if (product.getPrice() > price) {
//                        String message = "Price decreased for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " +  currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else if (product.getPrice() < price) {
//                        String message = "Price increased for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " +  currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else {
//                        String message = "Price remain the same for " + name + " from " +  currencyFormat.format(product.getPrice()) + " to " +  currencyFormat.format(price);
//                        log.info(message);
//                    }
//                    product.setPrice(price);
//                    newProducts.add(product);
//                } else {
//                    // This is a new product
//                    Product newProduct = new Product(name, price, dataId);
//                    if(!newProduct.getDataId().isEmpty()) {
//                        newProducts.add(newProduct);
//                        tecnoPage2Products.add(newProduct);
//                        count++;
//                    }
//                    String message = "New product found: " + name + " with price " +  currencyFormat.format(price) + " SKU " + dataId;
//                    log.info(message);
//                    if(tecnoPage2PreviousCount!= -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
//                            .isEmpty()) {
//                        telegramService.sendTelegramMessage(message);
//                        newFoundProducts.add(newProduct);
//                    }
//
//                }
//            }
//            if (tecnoPage2PreviousCount != -1 && count != tecnoPage2PreviousCount) {
//                if(tecnoPage2PreviousCount > count) {
//                    String message =  "tecnopage2 count has reduced to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//
//                } else {
//                    String message = "tecnopage2 count has increased to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//                }
//            }
//
//            List<Product> missingProducts = new ArrayList<>();
//            if(!newProducts.isEmpty()) {
//                for (Product product : tecnoPage2Products) {
//                    if (!newProducts.contains(product)) {
//                        missingProducts.add(product);
//                    }
//                }
//            }
//            log.info("MISSING PRODUCTS");
//            log.info("This is missing product {}" , missingProducts);
//
//            if (tecnoPage2PreviousCount != -1 && !missingProducts.isEmpty()) {
//                System.out.println("The following products were not found in the new web scrape:");
//                for (Product product : missingProducts) {
//                    String message = "this product has sold out " + product.getName() + "with price " +  currencyFormat.format(product.getPrice()) + "and SKU " + product.getDataId();
//                    log.info(message);
////                    if(!soldOutProducts.contains(product) && !permanentSoldOutList.contains(product.getDataId())) {
////                        telegramService.sendTelegramMessage(message);
////                    }
//                    soldOutProducts.add(product);
//                }
//            }
//
//            String message ="total count for tecno page 2 is " + count;
//            log.info(message);
//            if(tecnoPage2PreviousCount == -1) {
//                telegramService.sendTelegramMessage(message);
//            }
//            if(!newProducts.isEmpty()) {
//                tecnoPage2Products = newProducts;
//            }
//            tecnoPage2PreviousCount = count;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//
////    @Scheduled(fixedRate = 30000, initialDelay = 75000)
////    public void allPhonesLessthan20000ScarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
////        log.info("starting jumia webScrapper Service");
////        final String url = "https://www.jumia.com.ng/catalog/?q=phones&shop_premium_services=shop_express&price=20000-368899#catalog-listing ";
////        String documentClass = "article.prd._fb.col.c-prd";
////        String productNameClass = "h3.name";
////        String productPriceClass = "div.prc";
////        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav  ");
////        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
////        Clip clip = AudioSystem.getClip();
////        clip.open(audioInputStream);
////
////        log.info("PREVIOUS PRODUCTS");
////        log.info("this is product {}", allPhonesProducts);
////        try {
////            final Document document = Jsoup.connect(url).get();
////            int count =  0;
////            List<Product> newProducts = new ArrayList<>();
////            for(Element row: document.select(documentClass)) {
////                final String name = row.select(productNameClass).text();
////                final String pricestr = row.select(productPriceClass).text();
////                String numStr = pricestr.replaceAll("[^\\d]", "");
////                int price = Integer.parseInt(numStr);
////
////                Optional<Product> productOpt = allPhonesProducts.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst();
////                if (productOpt.isPresent()) {
////                    Product product = productOpt.get();
////                    count++;
////                    if (product.getPrice() > price) {
////                        String message = "Price decreased for " + name + " from " + product.getPrice() + " to " + price;
////                        log.info(message);
////                        clip.start();
////                        telegramService.sendTelegramMessage(message);
////                    } else if (product.getPrice() < price) {
////                        String message = "Price increased for " + name + " from " + product.getPrice() + " to " + price;
////                        log.info(message);
////                        telegramService.sendTelegramMessage(message);
////                    } else {
////                        String message = "Price remain the same for " + name + " from " + product.getPrice() + " to " + price;
////                        log.info(message);
////                    }
////                    product.setPrice(price);
////                    newProducts.add(product);
////                } else {
////                    // This is a new product
////                    Product newProduct = new Product(name, price);
////                    newProducts.add(newProduct);
////                    allPhonesProducts.add(newProduct);
////                    count++;
////                    if(allPhonesPreviousCount != -1) {
////                        String message = "New product found: " + name + " with price " + price;
////                        telegramService.sendTelegramMessage(message);
////                    }
////
////                }
////            }
////            if (allPhonesPreviousCount != -1 && count != allPhonesPreviousCount) {
////                if(allPhonesPreviousCount > count) {
////                    String message =  "All phones less than 20000 count has reduced to" + count;
////                    log.info(message);
////                    telegramService.sendTelegramMessage(message);
////
////                } else {
////                    String message = "All phones less than 20000 count has increased to" + count;
////                    log.info(message);
////                    telegramService.sendTelegramMessage(message);
////                }
////            }
////
////            List<Product> missingProducts = new ArrayList<>();
////            for (Product product : allPhonesProducts) {
////                if (!newProducts.contains(product)) {
////                    missingProducts.add(product);
////                }
////            }
////            log.info("MISSING PRODUCTS");
////            log.info("This is missing product {}" , missingProducts);
////
////            if (allPhonesPreviousCount != -1 && !missingProducts.isEmpty()) {
////                System.out.println("The following products were not found in the new web scrape:");
////                for (Product product : missingProducts) {
////                    String message = "this product has sold out" + product.getName();
////                    log.info(message);
////                    if(!soldOutProducts.contains(product)) {;
////                        telegramService.sendTelegramMessage(message);
////                    }
////                    soldOutProducts.add(product);
////                    log.info(message);
////                    telegramService.sendTelegramMessage(message);
////                }
////            }
////
////            String message ="total count for all phone is " + count;
////            log.info(message);
////            if(allPhonesPreviousCount == -1) {
////                telegramService.sendTelegramMessage(message);
////            }
////            allPhonesProducts = newProducts;
////            allPhonesPreviousCount = count;
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////    }
//
//
////    @Scheduled(fixedRate = 30000)
////    public void sampleScarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
////        log.info("starting jumia webScrapper Service");
////        final String url = "https://www.jumia.com.ng/catalog/?q=tecno+phones&shop_premium_services=shop_express&price=30000-368899#catalog-listing";
////        String documentClass = "article.prd._fb.col.c-prd";
////        String productNameClass = "h3.name";
////        String productPriceClass = "div.prc";
////        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav  ");
////        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
////        Clip clip = AudioSystem.getClip();
////        clip.open(audioInputStream);
////
////        log.info("PREVIOUS PRODUCTS");
////        log.info("this is product {}", products);
////        try {
////            final Document document = Jsoup.connect(url).get();
////            int count =  0;
////            List<Product> newProducts = new ArrayList<>();
////            for(Element row: document.select(documentClass)) {
////                final String name = row.select(productNameClass).text();
////                final String pricestr = row.select(productPriceClass).text();
////                String numStr = pricestr.replaceAll("[^\\d]", "");
////                int price = Integer.parseInt(numStr);
////
////                Optional<Product> productOpt = products.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst();
////                if (productOpt.isPresent()) {
////                    Product product = productOpt.get();
////                    count++;
////                    if (product.getPrice() > price) {
////                        String message = "Price decreased for " + name + " from " + product.getPrice() + " to " + price;
////                        log.info(message);
////                        clip.start();
////                        telegramService.sendTelegramMessage(message);
////                    } else if (product.getPrice() < price) {
////                        String message = "Price increased for " + name + " from " + product.getPrice() + " to " + price;
////                        log.info(message);
////                        telegramService.sendTelegramMessage(message);
////                    } else {
////                        String message = "Price remain the same for " + name + " from " + product.getPrice() + " to " + price;
////                        log.info(message);
////                    }
////                    product.setPrice(price);
////                    newProducts.add(product);
////                } else {
////                    // This is a new product
////                    Product newProduct = new Product(name, price);
////                    newProducts.add(newProduct);
////                    products.add(newProduct);
////                    count++;
////                    if(previousCount != -1) {
////                        String message = "New product found: " + name + " with price " + price;
////                        telegramService.sendTelegramMessage(message);
////                    }
////
////                }
////            }
////            if (previousCount != -1 && count != previousCount) {
////                if(previousCount > count) {
////                    String message =  "Samsung count has reduced to" + count;
////                    log.info(message);
////                    telegramService.sendTelegramMessage(message);
////
////                } else {
////                    String message = "Samsung count has increased to" + count;
////                    log.info(message);
////                    telegramService.sendTelegramMessage(message);
////                }
////            }
////
////            List<Product> missingProducts = new ArrayList<>();
////            for (Product product : products) {
////                if (!newProducts.contains(product)) {
////                    missingProducts.add(product);
////                }
////            }
////            log.info("MISSING PRODUCTS");
////            log.info("This is missing product {}" , missingProducts);
////
////            if (previousCount != -1 && !missingProducts.isEmpty()) {
////                System.out.println("The following products were not found in the new web scrape:");
////                 for (Product product : missingProducts) {
////                    String message = "this product has sold out" + product.getName();
////                    log.info(message);
////                    if(!soldOutProducts.contains(product)) {;
////                        telegramService.sendTelegramMessage(message);
////                    }
////                    soldOutProducts.add(product);
////                    log.info(message);
////                    telegramService.sendTelegramMessage(message);
////                }
////            }
////
////            String message ="total count for samsung is " + count;
////            log.info(message);
////            if(previousCount == -1) {
////                telegramService.sendTelegramMessage(message);
////            }
////            products = newProducts;
////        } catch (Exception e) {
////            e.printStackTrace();
////        }
////
////    }
//
////
////    @Scheduled(fixedRate = 600000, initialDelay = 60000)
//    public void clearSoldOutAndNewProducts() {
//       newFoundProducts.clear();
//        soldOutProducts.clear();
//        telegramService.sendTelegramMessage("clearing sold out list and new found product list");
//        System.out.println("Scheduler is running every 10 minutes...");
//    }
//
//
//
//    public void sendTwiloMessage(String scrapperMessage) {
//        try {
//            Message message = Message.creator(
//                            new com.twilio.type.PhoneNumber("+2348136834144"),
//                            new com.twilio.type.PhoneNumber("+15672922694"),
//                            scrapperMessage)
//                    .create();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//    }
//
//    public  void addDataIdToPermanentSoldOutList(String dataId) {
//        telegramService.sendTelegramMessage("adding " + dataId + " to" + " permanent sold out list");
//    }
//
//    public void clearPermanentSoldOutList() {
//        permanentSoldOutList.clear();
//        telegramService.sendTelegramMessage("clearing permanent sold out list");
//        System.out.println("clearing permanent sold out list");
//    }
//
//    public void scarpeAndComparePricesRefactor2(String url, List<Product> productType, Integer productPreviousCount, String productPagename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        log.info("starting jumia webScrapper Service");
//        String documentClass = "article.prd._fb.col.c-prd";
//        String productNameClass = "h3.name";
//        String productPriceClass = "div.prc";
//        int count =  0;
//        System.out.println("PREVIOUS PRODUCTS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println(productType);
//        System.out.println("PREVIOUS COUNT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println(productPreviousCount);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            List<Product> newProducts = new ArrayList<>();
//            for(Element row: document.select(documentClass)) {
//                final String name = row.select(productNameClass).text();
//                final String pricestr = row.select(productPriceClass).text();
//                Element link = row.selectFirst("a.core");
//                String dataId = link.attr("data-id");
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                int price = Integer.parseInt(numStr);
//                String formatedPrice = currencyFormat.format(price);
//                log.info("this is product {} and price {} and sku {}", name, formatedPrice, dataId);
//                Optional<Product> productOpt = productType.stream()
//                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
//                        .findFirst();
//
//                if (productOpt.isPresent()) {
//                    Product product = productOpt.get();
//                    log.info("this is found product {}", product);
//                    count++;
//                    if (product.getPrice() > price) {
//                        String message = "Price decreased for " + name + " from " + currencyFormat.format(product.getPrice())  + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else if (product.getPrice() < price) {
//                        String message = "Price increased for " + name + " from " + currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else {
//                        String message = "Price remain the same for " + name + " from " + currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                    }
//                    product.setPrice(price);
//                    newProducts.add(product);
//                } else {
//                    // This is a new product
//                    Product newProduct = new Product(name, price, dataId);
//                    if (!newProduct.getDataId().isEmpty()) {
//                        newProducts.add(newProduct);
//                        productType.add(newProduct);
//                        count++;
//                    }
//                    String message = "New product found: " + name + " with price " + currencyFormat.format(price) + " SKU " + dataId;
//                    log.info(message);
//                    if (productPreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
//                            .isEmpty()) {
//                        telegramService.sendTelegramMessage(message);
//                        newFoundProducts.add(newProduct);
//                    }
//
//                }
//            }
//            if (productPreviousCount != -1 && count != productPreviousCount) {
//                if (productPreviousCount > count) {
//                    String message = productPagename + " has reduced to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//
//                } else {
//                    String message = productPagename + "has increased to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//                }
//            }
//
//            List<Product> missingProducts = new ArrayList<>();
//            if(!newProducts.isEmpty()) {
//                for (Product product : productType) {
//                    if (!newProducts.contains(product)) {
//                        missingProducts.add(product);
//                    }
//                }
//            }
//            log.info("MISSING PRODUCTS");
//            log.info("This is missing product {}" , missingProducts);
//
//            if (productPreviousCount != -1 && !missingProducts.isEmpty()) {
//                System.out.println("The following products were not found in the new web scrape:");
//                for (Product product : missingProducts) {
//                    String message = "this product has sold out " + product.getName() + "with price " + currencyFormat.format(product.getPrice()) + "and SKU " + product.getDataId();
//                    log.info(message);
////                    if(!soldOutProducts.contains(product) && !permanentSoldOutList.contains(product.getDataId())) {
////                        telegramService.sendTelegramMessage(message);
////                    }
//                    soldOutProducts.add(product);
//                }
//            }
//
//            String message ="total count for " + productPagename + " is " + count;
//            log.info(message);
//            if(productPreviousCount == -1) {
//                telegramService.sendTelegramMessage(message);
//            }
//            if(!newProducts.isEmpty()) {
//                productType = newProducts;
//            }
//            System.out.println("Got here 11111111111111111111111111111111111111111111111111111111111");
//           setPreviousCount(productPagename, count);
//            System.out.println("Got here 2222222222222222222222222222222222222222222222222222");
//            log.info("productPrevios count is {}", productPreviousCount);
//            log.info("new products is {}", productType);
//            System.out.println("Got here 33333333333333333333333333333333333333333333333333333333333333333333333333333");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//    public void setPreviousCount(String productPageName, int count) {
//        switch (productPageName) {
//            case "nivea page 1":
//                niveaPage1PreviousCount = count;
//                break;
//            case "nivea page 2":
//                niveaPage2PreviousCount = count;
//                break;
//            case "Large Appliancies page 1":
//               largeappPage1PreviousCount = count;
//                break;
//            case "Large Appliancies page 2":
//               largeappPage2PreviousCount = count;
//                break;
//            case "Large Appliancies page 3":
//               largeappPage3PreviousCount = count;
//                break;
//            case "Large Appliancies page 4":
//                largeappPage4PreviousCount = count;
//                break;
//            case "infinix Page 2":
//               infinixPage2PreviousCount = count;
//                break;
//            case "groceries Page 1":
//               groceriesPage1PreviousCount = count;
//                break;
//            case "groceries Page 2":
//                groceriesPage2PreviousCount = count;
//                break;
//            case "groceries Page 3":
//                groceriesPage3PreviousCount = count;
//                break;
//            case "groceries Page 4":
//                groceriesPage4PreviousCount = count;
//                break;
//            case "groceries Page 5":
//                groceriesPage5PreviousCount = count;
//                break;
//            case "groceries Page 6":
//                groceriesPage6PreviousCount = count;
//                break;
//            case "groceries Page 7":
//                groceriesPage7PreviousCount = count;
//                break;
//            case "groceries Page 8":
//                groceriesPage8PreviousCount = count;
//                break;
//            case "groceries Page 9":
//                groceriesPage9PreviousCount = count;
//                break;
//            case "groceries Page 10":
//                groceriesPage10PreviousCount = count;
//                break;
//            case "groceries Page 11":
//                groceriesPage11PreviousCount = count;
//                break;
//            case "groceries Page 12":
//                groceriesPage12PreviousCount = count;
//                break;
//            case "groceries Page 13":
//                groceriesPage13PreviousCount = count;
//                break;
//            case "groceries Page 14":
//                groceriesPage14PreviousCount = count;
//                break;
//            case "groceries Page 15":
//                groceriesPage15PreviousCount = count;
//                break;
//            case "groceries Page 16":
//                groceriesPage16PreviousCount = count;
//                break;
//        }
//    }
//
//    public void scarpeAndComparePricesRefactor(String url, List<Product> productType, Integer productPreviousCount, String productPagename) throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        log.info("starting jumia webScrapper Service");
//        String documentClass = "article.prd._fb.col.c-prd";
//        String productNameClass = "h3.name";
//        String productPriceClass = "div.prc";
//        int count =  0;
//        System.out.println("PREVIOUS PRODUCTS!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println(productType);
//        System.out.println("PREVIOUS COUNT!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
//        System.out.println(productPreviousCount);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            List<Product> newProducts = new ArrayList<>();
//            for(Element row: document.select(documentClass)) {
//                final String name = row.select(productNameClass).text();
//                final String pricestr = row.select(productPriceClass).text();
//                Element link = row.selectFirst("a.core");
//                String dataId = link.attr("data-id");
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                int price = Integer.parseInt(numStr);
//                String formatedPrice = currencyFormat.format(price);
//                log.info("this is product {} and price {} and sku {}", name, formatedPrice, dataId);
//                Optional<Product> productOpt = Optional.ofNullable(productReprository.findByDataId(dataId));
//
//                if (productOpt.isPresent()) {
//                    Product product = productOpt.get();
//                    log.info("this is found product {}", product);
//                    count++;
//                    if (product.getPrice() > price) {
//                        String message = "Price decreased for " + name + " from " + currencyFormat.format(product.getPrice())  + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else if (product.getPrice() < price) {
//                        String message = "Price increased for " + name + " from " + currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                        telegramService.sendTelegramMessage(message);
//                    } else {
//                        String message = "Price remain the same for " + name + " from " + currencyFormat.format(product.getPrice()) + " to " + currencyFormat.format(price);
//                        log.info(message);
//                    }
//                    product.setPrice(price);
//                    product.setInStock(true);
//                    productReprository.save(product);
//                } else {
//                    // This is a new product
//                    Product newProduct = new Product(name, price, dataId, true);
//                    if (!newProduct.getDataId().isEmpty()) {
//                        newProducts.add(newProduct);
//                        productType.add(newProduct);
//                        count++;
//                        ///
//                    }
//                    String message = "New product found: " + name + " with price " + currencyFormat.format(price) + " SKU " + dataId;
//                    log.info(message);
//                    Product foundProduct = productReprository.findByDataId(dataId);
//                    if (productPreviousCount != -1 && Objects.isNull(foundProduct) && !newProduct.getDataId()
//                            .isEmpty()) {
//                        telegramService.sendTelegramMessage(message);
//                        newFoundProducts.add(newProduct);
//                    }
//                    productReprository.save(newProduct);
//
//                }
//            }
//            if (productPreviousCount != -1 && count != productPreviousCount) {
//                if (productPreviousCount > count) {
//                    String message = productPagename + " has reduced to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//
//                } else {
//                    String message = productPagename + "has increased to" + count;
//                    log.info(message);
//                    telegramService.sendTelegramMessage(message);
//                }
//            }
//
//            List<Product> missingProducts = new ArrayList<>();
//            if(!newProducts.isEmpty()) {
//                for (Product product : productType) {
//                    if (!newProducts.contains(product)) {
//                        missingProducts.add(product);
//                        Product savedProduct = productReprository.findByDataId(product.getDataId());
//                        savedProduct.setInStock(false);
//                        productReprository.save(savedProduct);
//                    } else {
//                       Product restocked = productReprository.findByDataId(product.getDataId());
//                       if(Objects.nonNull(restocked) && !restocked.isInStock()) {
//                           restocked.setInStock(true);
//                           String message = " product back in stock: " + restocked.getName() + " with price " + currencyFormat.format(restocked.getPrice()) + " SKU " + restocked.getDataId();
//                           telegramService.sendTelegramMessage(message);
//                           productReprository.save(restocked);
//                       }
//
//                    }
//                }
//            }
//            log.info("MISSING PRODUCTS");
//            log.info("This is missing product {}" , missingProducts);
//
//            if (productPreviousCount != -1 && !missingProducts.isEmpty()) {
//                System.out.println("The following products were not found in the new web scrape:");
//                for (Product product : missingProducts) {
//                    String message = "this product has sold out " + product.getName() + "with price " + currencyFormat.format(product.getPrice()) + "and SKU " + product.getDataId();
//                    log.info(message);
////                    if(!soldOutProducts.contains(product) && !permanentSoldOutList.contains(product.getDataId())) {
////                        telegramService.sendTelegramMessage(message);
////                    }
//                    soldOutProducts.add(product);
//                }
//            }
//
//            String message ="total count for " + productPagename + " is " + count;
//            log.info(message);
//            if(productPreviousCount == -1) {
//                telegramService.sendTelegramMessage(message);
//            }
//            if(!newProducts.isEmpty()) {
//                productType = newProducts;
//            }
//            System.out.println("Got here 11111111111111111111111111111111111111111111111111111111111");
//            setPreviousCount(productPagename, count);
//            System.out.println("Got here 2222222222222222222222222222222222222222222222222222");
//            log.info("productPrevios count is {}", productPreviousCount);
//            log.info("new products is {}", productType);
//            System.out.println("Got here 33333333333333333333333333333333333333333333333333333333333333333333333333333");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//}
//
//
//
//
