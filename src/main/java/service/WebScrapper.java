package service;

import com.example.jumiaScrapper.config.PriceAlertBot;
import com.twilio.rest.api.v2010.account.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@NoArgsConstructor
@Data
@Slf4j
public class WebScrapper {

    @Autowired
    private  PriceAlertBot alertBot;


    private static final long CLEAR_LIST_FIXED_RATE = 600000;
    private static final long GENERAL_FIXED_RATE = 30000;

    List<Product> samsungProducts = new ArrayList<>();
    List<Product> tecnoProducts = new ArrayList<>();
    List<Product> infinixProducts = new ArrayList<>();
    List<Product> itelProducts = new ArrayList<>();
    List<Product> soldOutProducts = new ArrayList<>();

    List<Product> newFoundProducts = new ArrayList<>();
    List<Product> computingProducts = new ArrayList<>();
    List<Product> allPhonesProducts = new ArrayList<>();
    List<Product> groceries = new ArrayList<>();
    private int samsungPreviousCount = -1;
    private int tecnoPreviousCount = -1;

    private int itelPreviousCount = -1;
    private int computingPreviousCount = -1;
    private int infinixPreviousCount = -1;

    private int allPhonesPreviousCount = -1;
    private int groceriesPreviousCount = -1;


    @Scheduled(fixedRate = GENERAL_FIXED_RATE)
    public void scarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        log.info("starting jumia webScrapper Service");
        final String url = "https://www.jumia.com.ng/catalog/?q=samsung+phones&shop_premium_services=shop_express&price=30000-358700#catalog-listing";
        String documentClass = "article.prd._fb.col.c-prd";
        String productNameClass = "h3.name";
        String productPriceClass = "div.prc";
        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();



        System.out.println("PREVIOUS PRODUCTS");
        System.out.println(samsungProducts);
        try {
            final Document document = Jsoup.connect(url).get();
            int count =  0;
            List<Product> newProducts = new ArrayList<>();
            for(Element row: document.select(documentClass)) {
                final String name = row.select(productNameClass).text();
                final String pricestr = row.select(productPriceClass).text();
                Element link = row.selectFirst("a.core");
                String dataId = link.attr("data-id");
                String numStr = pricestr.replaceAll("[^\\d]", "");
                int price = Integer.parseInt(numStr);
                log.info("this is product {} and price {} and sku {}", name, price, dataId);
                Optional<Product> productOpt = samsungProducts.stream()
                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
                        .findFirst();

                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    log.info("this is found product {}", product);
                    count++;
                    if (product.getPrice() > price) {
                        String message = "Price decreased for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                        alertBot.sendTelegramMessage(message);
                    } else if (product.getPrice() < price) {
                        String message = "Price increased for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                        alertBot.sendTelegramMessage(message);
                    } else {
                        String message = "Price remain the same for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                    }
                    product.setPrice(price);
                    newProducts.add(product);
                } else {
                    // This is a new product
                    Product newProduct = new Product(name, price, dataId);
                    if(!newProduct.getDataId().isEmpty()) {
                        newProducts.add(newProduct);
                        samsungProducts.add(newProduct);
                        count++;
                    }
                    String message = "New product found: " + name + " with price " + price + " SKU " + dataId;
                    log.info(message);
                    if(samsungPreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
                            .isEmpty()) {
                        alertBot.sendTelegramMessage(message);
                        newFoundProducts.add(newProduct);
                    }

                }
            }
            if (samsungPreviousCount != -1 && count != samsungPreviousCount) {
                if(samsungPreviousCount > count) {
                  String message =  "Samsung count has reduced to" + count;
                    log.info(message);
                    alertBot.sendTelegramMessage(message);

                } else {
                    String message = "Samsung count has increased to" + count;
                    log.info(message);
                    alertBot.sendTelegramMessage(message);
                }
            }

            List<Product> missingProducts = new ArrayList<>();
            if(!newProducts.isEmpty()) {
                for (Product product : samsungProducts) {
                    if (!newProducts.contains(product)) {
                        missingProducts.add(product);
                    }
                }
            }
            log.info("MISSING PRODUCTS");
            log.info("This is missing product {}" , missingProducts);

            if (samsungPreviousCount != -1 && !missingProducts.isEmpty()) {
                System.out.println("The following products were not found in the new web scrape:");
                for (Product product : missingProducts) {
                    String message = "this product has sold out " + product.getName();
                    log.info(message);
                    if(!soldOutProducts.contains(product)) {
                        alertBot.sendTelegramMessage(message);
                    }
                    soldOutProducts.add(product);
                }
            }

            String message ="total count for samsung is " + count;
            log.info(message);
            if(samsungPreviousCount == -1) {
                alertBot.sendTelegramMessage(message);
            }
            if(!newProducts.isEmpty()) {
                samsungProducts = newProducts;
            }
            samsungPreviousCount = count;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }





    @Scheduled(fixedRate = GENERAL_FIXED_RATE)
    public void tecnoScarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        log.info("starting jumia webScrapper Service for tecno");
        final String url = "https://www.jumia.com.ng/catalog/?q=tecno+phones&shop_premium_services=shop_express&price=30000-368899#catalog-listing";
        String documentClass = "article.prd._fb.col.c-prd";
        String productNameClass = "h3.name";
        String productPriceClass = "div.prc";
        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();


        log.info("PREVIOUS PRODUCTS");
        log.info("this is product {}", tecnoProducts);
        try {
            final Document document = Jsoup.connect(url).get();
            int count =  0;
            List<Product> newProducts = new ArrayList<>();
            for(Element row: document.select(documentClass)) {
                final String name = row.select(productNameClass).text();
                final String pricestr = row.select(productPriceClass).text();
                Element link = row.selectFirst("a.core");
                String dataId = link.attr("data-id");
                String numStr = pricestr.replaceAll("[^\\d]", "");
                int price = Integer.parseInt(numStr);
                log.info("this is product {} and price {}", name, price);
                log.info(dataId);

                Optional<Product> productOpt = tecnoProducts.stream()
                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
                        .findFirst();

                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    log.info("this is found product {}", product);
                    count++;
                    if (product.getPrice() > price) {
                        String message = "Price decreased for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                        alertBot.sendTelegramMessage(message);
                    } else if (product.getPrice() < price) {
                        String message = "Price increased for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                        alertBot.sendTelegramMessage(message);
                    } else {
                        String message = "Price remain the same for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                    }
                    product.setPrice(price);
                    newProducts.add(product);
                } else {
                    // This is a new product
                    Product newProduct = new Product(name, price, dataId);
                    if(!newProduct.getDataId().isEmpty()) {
                        newProducts.add(newProduct);
                        tecnoProducts.add(newProduct);
                        count++;
                    }
                    if(tecnoPreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
                            .isEmpty()) {
                        String message = "New product found: " + name + " with price " + price;
                        alertBot.sendTelegramMessage(message);
                        newFoundProducts.add(newProduct);
                    }

                }
            }
            if (tecnoPreviousCount != -1 && count != tecnoPreviousCount) {
                if(tecnoPreviousCount > count) {
                    String message =  "Tecno count has reduced to" + count;
                    log.info(message);
                    alertBot.sendTelegramMessage(message);

                } else {
                    String message = "Tecno count has increased to" + count;
                    log.info(message);
                    alertBot.sendTelegramMessage(message);
                }
            }

            List<Product> missingProducts = new ArrayList<>();
            if(!newProducts.isEmpty()) {
                for (Product product : tecnoProducts) {
                    if (!newProducts.contains(product)) {
                        missingProducts.add(product);
                    }
                }
            }
            log.info("MISSING PRODUCTS");
            log.info("This is missing product {}" , missingProducts);
            System.out.println(missingProducts);
            if (tecnoPreviousCount != -1 && !missingProducts.isEmpty()) {
                System.out.println("The following products were not found in the new web scrape:");
                for (Product product : missingProducts) {
                    String message = "this product has sold out" + product.getName();
                    log.info(message);
                    if(!soldOutProducts.contains(product)) {;
                        alertBot.sendTelegramMessage(message);
                    }
                    soldOutProducts.add(product);
                }
            }

            String message ="total count for Tecno is " + count;
            log.info(message);
            if(tecnoPreviousCount == -1) {
                alertBot.sendTelegramMessage(message);
            }
            if(count != 0) {
                tecnoProducts = newProducts;
            }
            tecnoPreviousCount = count;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 30000)
    public void infinixScarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        log.info("starting jumia webScrapper Service infinix");
        final String url = "https://www.jumia.com.ng/catalog/?q=infinix+phones&shop_premium_services=shop_express&price=20000-129900#catalog-listing";
        String documentClass = "article.prd._fb.col.c-prd";
        String productNameClass = "h3.name";
        String productPriceClass = "div.prc";
        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();

        log.info("PREVIOUS PRODUCTS");
        log.info("this is product {}", infinixProducts);
        try {
            final Document document = Jsoup.connect(url).get();
            int count =  0;
            List<Product> newProducts = new ArrayList<>();
            for(Element row: document.select(documentClass)) {
                final String name = row.select(productNameClass).text();
                final String pricestr = row.select(productPriceClass).text();
                String numStr = pricestr.replaceAll("[^\\d]", "");
                Element link = row.selectFirst("a.core");
                String dataId = link.attr("data-id");
                int price = Integer.parseInt(numStr);
                log.info("this is product {} and price {}", name, price);

                Optional<Product> productOpt = infinixProducts.stream()
                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
                        .findFirst();
                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    log.info("this is found product {}", product);
                    count++;
                    if (product.getPrice() > price) {
                        String message = "Price decreased for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                        alertBot.sendTelegramMessage(message);
                    } else if (product.getPrice() < price) {
                        String message = "Price increased for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                        alertBot.sendTelegramMessage(message);
                    } else {
                        String message = "Price remain the same for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                    }
                    product.setPrice(price);
                    newProducts.add(product);
                } else {
                    // This is a new product
                    Product newProduct = new Product(name, price, dataId);
                    if(!newProduct.getDataId().isEmpty()) {
                        newProducts.add(newProduct);
                        infinixProducts.add(newProduct);
                        count++;
                    }
                    if(infinixPreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
                            .isEmpty()) {
                        String message = "New product found: " + name + " with price " + price;
                        alertBot.sendTelegramMessage(message);
                        newFoundProducts.add(newProduct);
                    }

                }
            }
            if (infinixPreviousCount != -1 && count != infinixPreviousCount) {
                if(infinixPreviousCount > count) {
                    String message =  "Infinix count has reduced to" + count;
                    log.info(message);
                    alertBot.sendTelegramMessage(message);

                } else {
                    String message = "Infinix count has increased to" + count;
                    log.info(message);
                    alertBot.sendTelegramMessage(message);
                }
            }

            List<Product> missingProducts = new ArrayList<>();
            if(!newProducts.isEmpty()) {
                for (Product product : infinixProducts) {
                    if (!newProducts.contains(product)) {
                        missingProducts.add(product);
                    }
                }
            }
            log.info("MISSING PRODUCTS");
            log.info("This is missing product {}" , missingProducts);

            if (infinixPreviousCount != -1 && !missingProducts.isEmpty()) {
                System.out.println("The following products were not found in the new web scrape:");
                for (Product product : missingProducts) {
                    String message = "this product has sold out" + product.getName();
                    log.info(message);
                    if(!soldOutProducts.contains(product)) {;
                        alertBot.sendTelegramMessage(message);
                    }
                    soldOutProducts.add(product);
                }
            }

            String message ="total count for infinix is " + count;
            log.info(message);
            if(infinixPreviousCount == -1) {
                alertBot.sendTelegramMessage(message);
            }

            if(count != 0) {
                infinixProducts = newProducts;
            }
            infinixPreviousCount = count;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }



    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 45000)
    public void computingScarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        log.info("starting jumia webScrapper Service");
        final String url = "https://www.jumia.com.ng/computers-tablets/?shop_premium_services=shop_express&shipped_from=country_local";
        String documentClass = "article.prd._fb.col.c-prd";
        String productNameClass = "h3.name";
        String productPriceClass = "div.prc";
        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();

        log.info("PREVIOUS PRODUCTS");
        log.info("this is product {}", computingProducts);
        try {
            final Document document = Jsoup.connect(url).get();
            int count =  0;
            List<Product> newProducts = new ArrayList<>();
            for(Element row: document.select(documentClass)) {
                final String name = row.select(productNameClass).text();
                final String pricestr = row.select(productPriceClass).text();
                String numStr = pricestr.replaceAll("[^\\d]", "");
                Element link = row.selectFirst("a.core");
                String dataId = link.attr("data-id");
                int price = Integer.parseInt(numStr);
                log.info("this is product {} and price {}", name, price);

                Optional<Product> productOpt = computingProducts.stream()
                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
                        .findFirst();
                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    log.info("this is found product {}", product);
                    count++;
                    if (product.getPrice() > price) {
                        String message = "Price decreased for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                        alertBot.sendTelegramMessage(message);
                    } else if (product.getPrice() < price) {
                        String message = "Price increased for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                        alertBot.sendTelegramMessage(message);
                    } else {
                        String message = "Price remain the same for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                    }
                    product.setPrice(price);
                    newProducts.add(product);
                } else {
                    // This is a new product
                    Product newProduct = new Product(name, price, dataId);
                    if(!newProduct.getDataId().isEmpty()) {
                        newProducts.add(newProduct);
                        computingProducts.add(newProduct);
                        count++;
                    }
                    if(computingPreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
                            .isEmpty()) {
                        String message = "New product found: " + name + " with price " + price;
                        alertBot.sendTelegramMessage(message);
                        newFoundProducts.add(newProduct);
                    }

                }
            }
            if (computingPreviousCount != -1 && count != computingPreviousCount) {
                if(computingPreviousCount > count) {
                    String message =  "Computing count has reduced to" + count;
                    log.info(message);
                    alertBot.sendTelegramMessage(message);

                } else {
                    String message = "Computing count has increased to" + count;
                    log.info(message);
                    alertBot.sendTelegramMessage(message);
                }
            }

            List<Product> missingProducts = new ArrayList<>();
            if(!newProducts.isEmpty()) {
                for (Product product : computingProducts) {
                    if (!newProducts.contains(product)) {
                        missingProducts.add(product);
                    }
                }
            }

            log.info("MISSING PRODUCTS");
            log.info("This is missing product {}" , missingProducts);

            if (computingPreviousCount != -1 && !missingProducts.isEmpty()) {
                System.out.println("The following products were not found in the new web scrape:");
                for (Product product : missingProducts) {
                    String message = "this product has sold out" + product.getName();
                    log.info(message);
                    if(!soldOutProducts.contains(product)) {;
                        alertBot.sendTelegramMessage(message);
                    }
                    soldOutProducts.add(product);
                }
            }

            String message ="total count for computing is " + count;
            log.info(message);
            if(computingPreviousCount == -1) {
                alertBot.sendTelegramMessage(message);
            }

            if(!newProducts.isEmpty()) {
                computingProducts = newProducts;
            }

            computingPreviousCount = count;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 60000)
    public void itelScarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
        log.info("starting jumia webScrapper Service");
        final String url = "https://www.jumia.com.ng/catalog/?q=itel+phones&shop_premium_services=shop_express&price=20000-95890#catalog-listing";
        String documentClass = "article.prd._fb.col.c-prd";
        String productNameClass = "h3.name";
        String productPriceClass = "div.prc";
        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav");
        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
        Clip clip = AudioSystem.getClip();


        log.info("PREVIOUS PRODUCTS");
        log.info("this is product {}", itelProducts);
        try {
            final Document document = Jsoup.connect(url).get();
            int count =  0;
            List<Product> newProducts = new ArrayList<>();
            for(Element row: document.select(documentClass)) {
                final String name = row.select(productNameClass).text();
                final String pricestr = row.select(productPriceClass).text();
                Element link = row.selectFirst("a.core");
                Element status = row.selectFirst("div.bdg._oos._xs");
                if (status != null) {
                    System.out.println(status);
                }
                String dataId = link.attr("data-id");
                String numStr = pricestr.replaceAll("[^\\d]", "");
                int price = Integer.parseInt(numStr);
                log.info("this is product {} and price {}", name, price);

                Optional<Product> productOpt = itelProducts.stream()
                        .filter(p -> !p.getDataId().isEmpty() && p.getDataId().equalsIgnoreCase(dataId))
                        .findFirst();
                if (productOpt.isPresent()) {
                    Product product = productOpt.get();
                    log.info("this is found product {}", product);
                    count++;
                    if (product.getPrice() > price) {
                        String message = "Price decreased for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                        alertBot.sendTelegramMessage(message);
                    } else if (product.getPrice() < price) {
                        String message = "Price increased for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                        alertBot.sendTelegramMessage(message);
                    } else {
                        String message = "Price remain the same for " + name + " from " + product.getPrice() + " to " + price;
                        log.info(message);
                    }
                    product.setPrice(price);
                    newProducts.add(product);
                } else {
                    // This is a new product
                    Product newProduct = new Product(name, price, dataId);
                    if(!newProduct.getDataId().isEmpty()) {
                        newProducts.add(newProduct);
                        itelProducts.add(newProduct);
                        count++;
                    }
                    if(itelPreviousCount != -1 && !newFoundProducts.contains(newProduct) && !newProduct.getDataId()
                            .isEmpty()) {
                        String message = "New product found: " + name + " with price " + price;
                        alertBot.sendTelegramMessage(message);
                        newFoundProducts.add(newProduct);
                    }

                }
            }
            if (itelPreviousCount != -1 && count != itelPreviousCount) {
                if(itelPreviousCount > count) {
                    String message =  "Itel count has reduced to" + count;
                    log.info(message);
                    alertBot.sendTelegramMessage(message);

                } else {
                    String message = "Itel count has increased to" + count;
                    log.info(message);
                    alertBot.sendTelegramMessage(message);
                }
            }

            List<Product> missingProducts = new ArrayList<>();
            if(!newProducts.isEmpty()) {
                for (Product product : itelProducts) {
                    if (!newProducts.contains(product)) {
                        missingProducts.add(product);
                    }
                }
            }

            log.info("MISSING PRODUCTS");
            log.info("This is missing product {}" , missingProducts);

            if (itelPreviousCount != -1 && !missingProducts.isEmpty()) {
                System.out.println("The following products were not found in the new web scrape:");
                for (Product product : missingProducts) {
                    String message = "this product has sold out" + product.getName();
                    log.info(message);
                    if(!soldOutProducts.contains(product)) {;
                        alertBot.sendTelegramMessage(message);
                    }
                    soldOutProducts.add(product);
                }
            }

            String message ="total count for itel is " + count;
            log.info(message);
            if(itelPreviousCount == -1) {
                alertBot.sendTelegramMessage(message);
            }

            if(!newProducts.isEmpty()) {
                itelProducts = newProducts;
            }
            itelPreviousCount = count;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


//    @Scheduled(fixedRate = 30000, initialDelay = 75000)
//    public void allPhonesLessthan20000ScarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        log.info("starting jumia webScrapper Service");
//        final String url = "https://www.jumia.com.ng/catalog/?q=phones&shop_premium_services=shop_express&price=20000-368899#catalog-listing ";
//        String documentClass = "article.prd._fb.col.c-prd";
//        String productNameClass = "h3.name";
//        String productPriceClass = "div.prc";
//        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav  ");
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioInputStream);
//
//        log.info("PREVIOUS PRODUCTS");
//        log.info("this is product {}", allPhonesProducts);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            int count =  0;
//            List<Product> newProducts = new ArrayList<>();
//            for(Element row: document.select(documentClass)) {
//                final String name = row.select(productNameClass).text();
//                final String pricestr = row.select(productPriceClass).text();
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                int price = Integer.parseInt(numStr);
//
//                Optional<Product> productOpt = allPhonesProducts.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst();
//                if (productOpt.isPresent()) {
//                    Product product = productOpt.get();
//                    count++;
//                    if (product.getPrice() > price) {
//                        String message = "Price decreased for " + name + " from " + product.getPrice() + " to " + price;
//                        log.info(message);
//                        clip.start();
//                        alertBot.sendTelegramMessage(message);
//                    } else if (product.getPrice() < price) {
//                        String message = "Price increased for " + name + " from " + product.getPrice() + " to " + price;
//                        log.info(message);
//                        alertBot.sendTelegramMessage(message);
//                    } else {
//                        String message = "Price remain the same for " + name + " from " + product.getPrice() + " to " + price;
//                        log.info(message);
//                    }
//                    product.setPrice(price);
//                    newProducts.add(product);
//                } else {
//                    // This is a new product
//                    Product newProduct = new Product(name, price);
//                    newProducts.add(newProduct);
//                    allPhonesProducts.add(newProduct);
//                    count++;
//                    if(allPhonesPreviousCount != -1) {
//                        String message = "New product found: " + name + " with price " + price;
//                        alertBot.sendTelegramMessage(message);
//                    }
//
//                }
//            }
//            if (allPhonesPreviousCount != -1 && count != allPhonesPreviousCount) {
//                if(allPhonesPreviousCount > count) {
//                    String message =  "All phones less than 20000 count has reduced to" + count;
//                    log.info(message);
//                    alertBot.sendTelegramMessage(message);
//
//                } else {
//                    String message = "All phones less than 20000 count has increased to" + count;
//                    log.info(message);
//                    alertBot.sendTelegramMessage(message);
//                }
//            }
//
//            List<Product> missingProducts = new ArrayList<>();
//            for (Product product : allPhonesProducts) {
//                if (!newProducts.contains(product)) {
//                    missingProducts.add(product);
//                }
//            }
//            log.info("MISSING PRODUCTS");
//            log.info("This is missing product {}" , missingProducts);
//
//            if (allPhonesPreviousCount != -1 && !missingProducts.isEmpty()) {
//                System.out.println("The following products were not found in the new web scrape:");
//                for (Product product : missingProducts) {
//                    String message = "this product has sold out" + product.getName();
//                    log.info(message);
//                    if(!soldOutProducts.contains(product)) {;
//                        alertBot.sendTelegramMessage(message);
//                    }
//                    soldOutProducts.add(product);
//                    log.info(message);
//                    alertBot.sendTelegramMessage(message);
//                }
//            }
//
//            String message ="total count for all phone is " + count;
//            log.info(message);
//            if(allPhonesPreviousCount == -1) {
//                alertBot.sendTelegramMessage(message);
//            }
//            allPhonesProducts = newProducts;
//            allPhonesPreviousCount = count;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


//    @Scheduled(fixedRate = 30000)
//    public void sampleScarpeAndComparePrices() throws UnsupportedAudioFileException, IOException, LineUnavailableException {
//        log.info("starting jumia webScrapper Service");
//        final String url = "https://www.jumia.com.ng/catalog/?q=tecno+phones&shop_premium_services=shop_express&price=30000-368899#catalog-listing";
//        String documentClass = "article.prd._fb.col.c-prd";
//        String productNameClass = "h3.name";
//        String productPriceClass = "div.prc";
//        File  file = new File("src/main/resources/Fireboy-DML-Champ-ft-D-Smoke.wav  ");
//        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(file);
//        Clip clip = AudioSystem.getClip();
//        clip.open(audioInputStream);
//
//        log.info("PREVIOUS PRODUCTS");
//        log.info("this is product {}", products);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            int count =  0;
//            List<Product> newProducts = new ArrayList<>();
//            for(Element row: document.select(documentClass)) {
//                final String name = row.select(productNameClass).text();
//                final String pricestr = row.select(productPriceClass).text();
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                int price = Integer.parseInt(numStr);
//
//                Optional<Product> productOpt = products.stream().filter(p -> p.getName().equalsIgnoreCase(name)).findFirst();
//                if (productOpt.isPresent()) {
//                    Product product = productOpt.get();
//                    count++;
//                    if (product.getPrice() > price) {
//                        String message = "Price decreased for " + name + " from " + product.getPrice() + " to " + price;
//                        log.info(message);
//                        clip.start();
//                        alertBot.sendTelegramMessage(message);
//                    } else if (product.getPrice() < price) {
//                        String message = "Price increased for " + name + " from " + product.getPrice() + " to " + price;
//                        log.info(message);
//                        alertBot.sendTelegramMessage(message);
//                    } else {
//                        String message = "Price remain the same for " + name + " from " + product.getPrice() + " to " + price;
//                        log.info(message);
//                    }
//                    product.setPrice(price);
//                    newProducts.add(product);
//                } else {
//                    // This is a new product
//                    Product newProduct = new Product(name, price);
//                    newProducts.add(newProduct);
//                    products.add(newProduct);
//                    count++;
//                    if(previousCount != -1) {
//                        String message = "New product found: " + name + " with price " + price;
//                        alertBot.sendTelegramMessage(message);
//                    }
//
//                }
//            }
//            if (previousCount != -1 && count != previousCount) {
//                if(previousCount > count) {
//                    String message =  "Samsung count has reduced to" + count;
//                    log.info(message);
//                    alertBot.sendTelegramMessage(message);
//
//                } else {
//                    String message = "Samsung count has increased to" + count;
//                    log.info(message);
//                    alertBot.sendTelegramMessage(message);
//                }
//            }
//
//            List<Product> missingProducts = new ArrayList<>();
//            for (Product product : products) {
//                if (!newProducts.contains(product)) {
//                    missingProducts.add(product);
//                }
//            }
//            log.info("MISSING PRODUCTS");
//            log.info("This is missing product {}" , missingProducts);
//
//            if (previousCount != -1 && !missingProducts.isEmpty()) {
//                System.out.println("The following products were not found in the new web scrape:");
//                 for (Product product : missingProducts) {
//                    String message = "this product has sold out" + product.getName();
//                    log.info(message);
//                    if(!soldOutProducts.contains(product)) {;
//                        alertBot.sendTelegramMessage(message);
//                    }
//                    soldOutProducts.add(product);
//                    log.info(message);
//                    alertBot.sendTelegramMessage(message);
//                }
//            }
//
//            String message ="total count for samsung is " + count;
//            log.info(message);
//            if(previousCount == -1) {
//                alertBot.sendTelegramMessage(message);
//            }
//            products = newProducts;
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }


    @Scheduled(fixedRate = 600000, initialDelay = 60000)
    public void clearSoldOutAndNewProducts() {
       newFoundProducts.clear();
        soldOutProducts.clear();
        alertBot.sendTelegramMessage("clearing sold out list and new found product list");
        System.out.println("Scheduler is running every 10 minutes...");
    }



    public void sendTwiloMessage(String scrapperMessage) {
        try {
            Message message = Message.creator(
                            new com.twilio.type.PhoneNumber("+2348136834144"),
                            new com.twilio.type.PhoneNumber("+15672922694"),
                            scrapperMessage)
                    .create();
        } catch (Exception e) {
            System.out.println(e);
        }

    }

}
