package com.example.jumiaScrapper.service;

import com.example.jumiaScrapper.model.Product;
import com.example.jumiaScrapper.model.ProductReprository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class NewWebScrapper {

    private final ProductReprository productReprository;
    private final TelegrameService telegramService;
    private static final long CLEAR_LIST_FIXED_RATE = 600000;
    private static final long GENERAL_FIXED_RATE = 30000;
    private static final long GENERAL_PHONE_RATE = 30000;

    private static List<String> doNotDisturbProduct = new ArrayList<>();

    private  List<String> disturbingProducts = List.of("GE779FS45RINDNAFAMZ","GE779FS3QTR2XNAFAMZ");
    NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(new Locale("en", "NG"));
    String niveaPage1url  = "https://www.jumia.com.ng/mlp-nivea-store-deals/?shop_premium_services=shop_express";
    boolean intialStartUp = true;
    String largeAppliancesUrl = "https://www.jumia.com.ng/home-improvement-appliances/?shop_premium_services=shop_express";
    boolean largeAppliancesintialStartUp = true;
    String infinixUrl = "https://www.jumia.com.ng/catalog/?q=infinix+phones&shop_premium_services=shop_express";
    boolean infinixIntialStartUp = true;

    String tecnoUrl = "https://www.jumia.com.ng/catalog/?q=tecno+phones&shop_premium_services=shop_express";
    boolean tecnoIntialStartUp = true;

    String grocriesUrl = "https://www.jumia.com.ng/groceries/?shop_premium_services=shop_express";
    boolean groceriesStartUp = true;
    String redmiUrl = "https://www.jumia.com.ng/catalog/?q=redmi&shop_premium_services=shop_express&price=5000-659500";
    boolean redmiStartUp = true;

    String itelUrl = "https://www.jumia.com.ng/catalog/?q=itel+phones&shop_premium_services=shop_express";
    boolean itelStartUp = true;

    String allProductFromLowestUrl = "https://www.jumia.com.ng/catalog/?sort=lowest-price&shop_premium_services=shop_express";
    boolean allProductFromLowestStartUp = true;

    String allProductNewInUrl = "https://www.jumia.com.ng/catalog/?sort=newest&shop_premium_services=shop_express";

    boolean allProductNewInStartUp = true;

    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 30000)
    public void niveaScrapper() {
        log.info("Starting all nivea Scrapper");
        String productName = "Nivea";
        String url = "";
        boolean pageStillRemaining =  true;
        int pageNumber = 1;
        while (pageStillRemaining) {
            String documentClass = "article.prd._fb.col.c-prd";
            String productNameClass = "h3.name";
            String productPriceClass = "div.prc";

            if (pageNumber > 1) {
                url = niveaPage1url + "&page=" + pageNumber + "#catalog-listing";
            } else {
                url = niveaPage1url;
            }
            try {
                final Document document = Jsoup.connect(url).get();
                List<Product> products = document.select(documentClass)
                        .stream()
                        .map(row -> {
                            String name = row.select(productNameClass).text();
                            String pricestr = row.select(productPriceClass).text();
                            Element link = row.selectFirst("a.core");
                            String dataId = link.attr("data-gtm-id");
                            long price = Long.valueOf(pricestr.replaceAll("[^\\d]", ""));
                            String formattedPrice = currencyFormat.format(price);
//                            try {
//
//                            } catch (Exception e) {
//                                log.info("cannot convert string **************");
//                                e.printStackTrace();
//                            }


                            log.info("Product: {}, Price: {}, SKU: {}", name, formattedPrice, dataId);

                            return Optional.ofNullable(productReprository.findByDataId(dataId))
                                    .map(product -> {
                                        handleExistingProduct(name, formattedPrice, price, product);
                                        return product;
                                    })
                                    .orElseGet(() -> handleNewProduct(name, formattedPrice, dataId, price));
                        })
                        .toList();

                int productSize =  products.size();

                String message ="total count for " + productName + " page " + pageNumber + " is " + products.size();
                log.info(message);
                if (intialStartUp) {
                    telegramService.sendTelegramMessage(message);
                }
                if (productSize == 0) {
                    pageStillRemaining = false;
                } else {
                    pageNumber++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        intialStartUp = false;

    }

    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 40000)
    public void largeAppScrapper() {
        log.info("Starting all large app Scrapper");
        String productName = "Large Appliances";
        String url = "";
        boolean pageStillRemaining =  true;
        int pageNumber = 1;
        while (pageStillRemaining) {
            String documentClass = "article.prd._fb.col.c-prd";
            String productNameClass = "h3.name";
            String productPriceClass = "div.prc";

            if (pageNumber > 1) {
                url = largeAppliancesUrl + "&page=" + pageNumber + "#catalog-listing";
            } else {
                url = largeAppliancesUrl;
            }
            try {
                final Document document = Jsoup.connect(url).get();
                List<Product> products = document.select(documentClass)
                        .stream()
                        .map(row -> {
                            String name = row.select(productNameClass).text();
                            String pricestr = row.select(productPriceClass).text();
                            Element link = row.selectFirst("a.core");
                            String dataId = link.attr("data-gtm-id");
                            long price = Long.valueOf(pricestr.replaceAll("[^\\d]", ""));
                            String formattedPrice = currencyFormat.format(price);
//                            try {
//
//                            } catch (Exception e) {
//                                log.info("cannot convert string **************");
//                                e.printStackTrace();
//                            }


                            log.info("Product: {}, Price: {}, SKU: {}", name, formattedPrice, dataId);

                            return Optional.ofNullable(productReprository.findByDataId(dataId))
                                    .map(product -> {
                                        handleExistingProduct(name, formattedPrice, price, product);
                                        return product;
                                    })
                                    .orElseGet(() -> handleNewProduct(name, formattedPrice, dataId, price));
                        })
                        .toList();

                int productSize =  products.size();

                String message ="total count for " + productName + " page " + pageNumber + " is " + products.size();
                log.info(message);
                if (largeAppliancesintialStartUp) {
                    telegramService.sendTelegramMessage(message);
                }
                if (productSize == 0) {
                    pageStillRemaining = false;
                } else {
                    pageNumber++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        largeAppliancesintialStartUp = false;

    }

    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 45000)
    public void infinixScrapper() {
        log.info("Starting all infinix Scrapper");
        String productName = "Infinix";
        String url = "";
        boolean pageStillRemaining =  true;
        int pageNumber = 1;
        while (pageStillRemaining) {
            String documentClass = "article.prd._fb.col.c-prd";
            String productNameClass = "h3.name";
            String productPriceClass = "div.prc";

            if (pageNumber > 1) {
                url = infinixUrl + "&page=" + pageNumber + "#catalog-listing";
            } else {
                url = infinixUrl;
            }
            try {
                final Document document = Jsoup.connect(url).get();
                List<Product> products = document.select(documentClass)
                        .stream()
                        .map(row -> {
                            String name = row.select(productNameClass).text();
                            String pricestr = row.select(productPriceClass).text();
                            Element link = row.selectFirst("a.core");
                            String dataId = link.attr("data-gtm-id");
                            long price = Long.valueOf(pricestr.replaceAll("[^\\d]", ""));
                            String formattedPrice = currencyFormat.format(price);
//                            try {
//
//                            } catch (Exception e) {
//                                log.info("cannot convert string **************");
//                                e.printStackTrace();
//                            }


                            log.info("Product: {}, Price: {}, SKU: {}", name, formattedPrice, dataId);

                            return Optional.ofNullable(productReprository.findByDataId(dataId))
                                    .map(product -> {
                                        handleExistingProduct(name, formattedPrice, price, product);
                                        return product;
                                    })
                                    .orElseGet(() -> handleNewProduct(name, formattedPrice, dataId, price));
                        })
                        .toList();

                int productSize =  products.size();

                String message ="total count for " + productName + " page " + pageNumber + " is " + products.size();
                log.info(message);
                if (infinixIntialStartUp) {
                    telegramService.sendTelegramMessage(message);
                }
                if (productSize == 0) {
                    pageStillRemaining = false;
                } else {
                    pageNumber++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        infinixIntialStartUp = false;

    }

    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 50000)
    public void tecnoScrapper() {
        log.info("Starting all Tecno Scrapper");
        String productName = "Tecno";
        String url = "";
        boolean pageStillRemaining =  true;
        int pageNumber = 1;
        while (pageStillRemaining) {
            String documentClass = "article.prd._fb.col.c-prd";
            String productNameClass = "h3.name";
            String productPriceClass = "div.prc";

            if (pageNumber > 1) {
                url = tecnoUrl + "&page=" + pageNumber + "#catalog-listing";
            } else {
                url = tecnoUrl;
            }
            try {
                final Document document = Jsoup.connect(url).get();
                List<Product> products = document.select(documentClass)
                        .stream()
                        .map(row -> {
                            String name = row.select(productNameClass).text();
                            String pricestr = row.select(productPriceClass).text();
                            Element link = row.selectFirst("a.core");
                            String dataId = link.attr("data-gtm-id");
                            long price = Long.valueOf(pricestr.replaceAll("[^\\d]", ""));
                            String formattedPrice = currencyFormat.format(price);
//                            try {
//
//                            } catch (Exception e) {
//                                log.info("cannot convert string **************");
//                                e.printStackTrace();
//                            }


                            log.info("Product: {}, Price: {}, SKU: {}", name, formattedPrice, dataId);

                            return Optional.ofNullable(productReprository.findByDataId(dataId))
                                    .map(product -> {
                                        handleExistingProduct(name, formattedPrice, price, product);
                                        return product;
                                    })
                                    .orElseGet(() -> handleNewProduct(name, formattedPrice, dataId, price));
                        })
                        .toList();

                int productSize =  products.size();

                String message ="total count for " + productName + " page " + pageNumber + " is " + products.size();
                log.info(message);
                if (tecnoIntialStartUp) {
                    telegramService.sendTelegramMessage(message);
                }
                if (productSize == 0) {
                    pageStillRemaining = false;
                } else {
                    pageNumber++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        tecnoIntialStartUp = false;

    }

    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 50000)
    public void groceriesScrapper() {
        log.info("Starting grocerie Tecno Scrapper");
        String productName = "Groceries";
        String url = "";
        boolean pageStillRemaining =  true;
        int pageNumber = 1;
        while (pageStillRemaining) {
            String documentClass = "article.prd._fb.col.c-prd";
            String productNameClass = "h3.name";
            String productPriceClass = "div.prc";

            if (pageNumber > 1) {
                url = grocriesUrl + "&page=" + pageNumber + "#catalog-listing";
                log.info("calling this 2 url" + url);
            } else {
                url = grocriesUrl;
                log.info("calling this 1 url" + url);
            }
            try {
                final Document document = Jsoup.connect(url).get();
                List<Product> products = document.select(documentClass)
                        .stream()
                        .map(row -> {
                            String name = row.select(productNameClass).text();
                            String pricestr = row.select(productPriceClass).text();
                            Element link = row.selectFirst("a.core");
                            String dataId = link.attr("data-gtm-id");
                            long price = Long.valueOf(pricestr.replaceAll("[^\\d]", ""));
                            String formattedPrice = currencyFormat.format(price);
//                            try {
//
//                            } catch (Exception e) {
//                                log.info("cannot convert string **************");
//                                e.printStackTrace();
//                            }


                            log.info("Product: {}, Price: {}, SKU: {}", name, formattedPrice, dataId);

                            return Optional.ofNullable(productReprository.findByDataId(dataId))
                                    .map(product -> {
                                        handleExistingProduct(name, formattedPrice, price, product);
                                        return product;
                                    })
                                    .orElseGet(() -> handleNewProduct(name, formattedPrice, dataId, price));
                        })
                        .toList();

                int productSize =  products.size();

                String message ="total count for " + productName + " page " + pageNumber + " is " + products.size();
                log.info(message);
                if (groceriesStartUp) {
                    telegramService.sendTelegramMessage(message);
                }
                if (productSize == 0) {
                    pageStillRemaining = false;
                } else {
                    pageNumber++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        groceriesStartUp= false;

    }

    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 50000)
    public void redmiScrapper() {
        log.info("Starting redmi Tecno Scrapper");
        String productName = "Redmi";
        String url = "";
        boolean pageStillRemaining =  true;
        int pageNumber = 1;
        while (pageStillRemaining) {
            String documentClass = "article.prd._fb.col.c-prd";
            String productNameClass = "h3.name";
            String productPriceClass = "div.prc";

            if (pageNumber > 1) {
                url = redmiUrl + "&page=" + pageNumber + "#catalog-listing";
            } else {
                url = redmiUrl;
            }
            try {
                final Document document = Jsoup.connect(url).get();
                List<Product> products = document.select(documentClass)
                        .stream()
                        .map(row -> {
                            String name = row.select(productNameClass).text();
                            String pricestr = row.select(productPriceClass).text();
                            Element link = row.selectFirst("a.core");
                            String dataId = link.attr("data-gtm-id");
                            long price = Long.valueOf(pricestr.replaceAll("[^\\d]", ""));
                            String formattedPrice = currencyFormat.format(price);
//                            try {
//
//                            } catch (Exception e) {
//                                log.info("cannot convert string **************");
//                                e.printStackTrace();
//                            }


                            log.info("Product: {}, Price: {}, SKU: {}", name, formattedPrice, dataId);

                            return Optional.ofNullable(productReprository.findByDataId(dataId))
                                    .map(product -> {
                                        handleExistingProduct(name, formattedPrice, price, product);
                                        return product;
                                    })
                                    .orElseGet(() -> handleNewProduct(name, formattedPrice, dataId, price));
                        })
                        .toList();

                int productSize =  products.size();

                String message ="total count for " + productName + " page " + pageNumber + " is " + products.size();
                log.info(message);
                if (redmiStartUp) {
                    telegramService.sendTelegramMessage(message);
                }
                if (productSize == 0) {
                    pageStillRemaining = false;
                } else {
                    pageNumber++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        redmiStartUp= false;

    }

    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 50000)
    public void itelScrapper() {
        log.info("Starting itel  Scrapper");
        String productName = "itel";
        String url = "";
        boolean pageStillRemaining =  true;
        int pageNumber = 1;
        while (pageStillRemaining) {
            String documentClass = "article.prd._fb.col.c-prd";
            String productNameClass = "h3.name";
            String productPriceClass = "div.prc";

            if (pageNumber > 1) {
                url = itelUrl + "&page=" + pageNumber + "#catalog-listing";
            } else {
                url = itelUrl;
            }
            try {
                final Document document = Jsoup.connect(url).get();
                List<Product> products = document.select(documentClass)
                        .stream()
                        .map(row -> {
                            String name = row.select(productNameClass).text();
                            String pricestr = row.select(productPriceClass).text();
                            Element link = row.selectFirst("a.core");
                            String dataId = link.attr("data-gtm-id");
                            long price = Long.valueOf(pricestr.replaceAll("[^\\d]", ""));
                            String formattedPrice = currencyFormat.format(price);
//                            try {
//
//                            } catch (Exception e) {
//                                log.info("cannot convert string **************");
//                                e.printStackTrace();
//                            }


                            log.info("Product: {}, Price: {}, SKU: {}", name, formattedPrice, dataId);

                            return Optional.ofNullable(productReprository.findByDataId(dataId))
                                    .map(product -> {
                                        handleExistingProduct(name, formattedPrice, price, product);
                                        return product;
                                    })
                                    .orElseGet(() -> handleNewProduct(name, formattedPrice, dataId, price));
                        })
                        .toList();

                int productSize =  products.size();

                String message ="total count for " + productName + " page " + pageNumber + " is " + products.size();
                log.info(message);
                if (itelStartUp) {
                    telegramService.sendTelegramMessage(message);
                }
                if (productSize == 0) {
                    pageStillRemaining = false;
                } else {
                    pageNumber++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        itelStartUp= false;

    }

    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 50000)
    public void allProductStartingFromLowestScrapper() {
        log.info("Starting allProductStartingFromLowest  Scrapper");
        String productName = "All product  from lowest";
        String url = "";
        boolean pageStillRemaining =  true;
        int pageNumber = 1;
        while (pageStillRemaining) {
            String documentClass = "article.prd._fb.col.c-prd";
            String productNameClass = "h3.name";
            String productPriceClass = "div.prc";

            if (pageNumber > 1) {
                url = allProductFromLowestUrl + "&page=" + pageNumber + "#catalog-listing";
            } else {
                url = allProductFromLowestUrl;
            }
            try {
                final Document document = Jsoup.connect(url).get();
                List<Product> products = document.select(documentClass)
                        .stream()
                        .map(row -> {
                            String name = row.select(productNameClass).text();
                            String pricestr = row.select(productPriceClass).text();
                            Element link = row.selectFirst("a.core");
                            String dataId = link.attr("data-gtm-id");
                            long price = Long.valueOf(pricestr.replaceAll("[^\\d]", ""));
                            String formattedPrice = currencyFormat.format(price);
//                            try {
//
//                            } catch (Exception e) {
//                                log.info("cannot convert string **************");
//                                e.printStackTrace();
//                            }


                            log.info("Product: {}, Price: {}, SKU: {}", name, formattedPrice, dataId);

                            return Optional.ofNullable(productReprository.findByDataId(dataId))
                                    .map(product -> {
                                        handleExistingProduct(name, formattedPrice, price, product);
                                        return product;
                                    })
                                    .orElseGet(() -> handleNewProduct(name, formattedPrice, dataId, price));
                        })
                        .toList();

                int productSize =  products.size();

                String message ="total count for " + productName + " page " + pageNumber + " is " + products.size();
                log.info(message);
                if (allProductFromLowestStartUp) {
                    telegramService.sendTelegramMessage(message);
                }
                if (productSize == 0 || pageNumber > 14) {
                    pageStillRemaining = false;
                } else {
                    pageNumber++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        allProductFromLowestStartUp= false;

    }
    @Scheduled(fixedRate = GENERAL_FIXED_RATE, initialDelay = 90000)
    public void allProductNewInScrapper() {
        log.info("Starting allProduct  New In  Scrapper");
        String productName = "All product New In";
        String url = "";
        boolean pageStillRemaining =  true;
        int pageNumber = 1;
        while (pageStillRemaining) {
            String documentClass = "article.prd._fb.col.c-prd";
            String productNameClass = "h3.name";
            String productPriceClass = "div.prc";

            if (pageNumber > 1) {
                url = allProductNewInUrl + "&page=" + pageNumber + "#catalog-listing";
            } else {
                url = allProductNewInUrl;
            }
            try {
                final Document document = Jsoup.connect(url).get();
                List<Product> products = document.select(documentClass)
                        .stream()
                        .map(row -> {
                            String name = row.select(productNameClass).text();
                            String pricestr = row.select(productPriceClass).text();
                            Element link = row.selectFirst("a.core");
                            String dataId = link.attr("data-gtm-id");
                            long price = Long.valueOf(pricestr.replaceAll("[^\\d]", ""));
                            String formattedPrice = currencyFormat.format(price);
//                            try {
//
//                            } catch (Exception e) {
//                                log.info("cannot convert string **************");
//                                e.printStackTrace();
//                            }


                            log.info("Product: {}, Price: {}, SKU: {}", name, formattedPrice, dataId);

                            return Optional.ofNullable(productReprository.findByDataId(dataId))
                                    .map(product -> {
                                        handleExistingProduct(name, formattedPrice, price, product);
                                        return product;
                                    })
                                    .orElseGet(() -> handleNewProduct(name, formattedPrice, dataId, price));
                        })
                        .toList();

                int productSize =  products.size();

                String message ="total count for " + productName + " page " + pageNumber + " is " + products.size();
                log.info(message);
                if (allProductNewInStartUp) {
                    telegramService.sendTelegramMessage(message);
                }
                if (productSize == 0 || pageNumber > 30) {
                    pageStillRemaining = false;
                } else {
                    pageNumber++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        allProductNewInStartUp= false;

    }


    private void handleExistingProduct(String name, String formattedPrice, long price, Product product) {
        if (!disturbingProducts.contains(product.getDataId())) {

            log.info("Found product: {}", product);
            if (product.getPrice() > price) {
                double percentageDecrease = calculatePercentageDecrease(product.getPrice(), price);
                String message = "Price decreased for " + name + " from " + currencyFormat.format(product.getPrice()) + " to " + formattedPrice + "percentage decrease is " + percentageDecrease;
                log.info(message);
                if (!doNotDisturbProduct.contains(product.getDataId())) {
                    telegramService.sendTelegramMessage(message);
                }
            } else if (product.getPrice() < price) {
                double percentageincrease = calculatePercentageIncrease(product.getPrice(), price);
                String message = "Price increased for " + name + " from " + currencyFormat.format(product.getPrice()) + " to " + formattedPrice + "percentage increase is " + percentageincrease;
                log.info(message);
                if (!doNotDisturbProduct.contains(product.getDataId())) {
                    telegramService.sendTelegramMessage(message);
                }
            } else {
                String message = "Price remains the same for " + name + " at " + formattedPrice;
                log.info(message);
            }
            product.setPrice(price);
            product.setInStock(true);
            productReprository.save(product);
        }
    }

    private Product handleNewProduct(String name, String formattedPrice, String dataId,long price) {
        if (!dataId.equals("GE779BN4GXV5DNAFAMZ")) {
            Product newProduct = new Product(name, price, dataId, true);

            String message = "New product found: " + name + " with price " + formattedPrice + " SKU " + dataId;
            log.info(message);

            Product foundProduct = productReprository.findByDataId(dataId);

            if (Objects.isNull(foundProduct) && !newProduct.getDataId().isEmpty()) {
                telegramService.sendTelegramMessage(message);
            }

            log.info("save product to db");
            productReprository.save(newProduct);

            return newProduct;
        }
      return  new Product("test", 1235, "SKUTEST", false);
    }

    private double calculatePercentageIncrease(long oldValue, long newValue) {
        if (oldValue == 0) {
            return (newValue > 0) ? 100.0 : 0.0;
        }

        double percentageIncrease = ((double) (newValue - oldValue) / Math.abs(oldValue)) * 100.0;
        return Math.round(percentageIncrease * 100.0) / 100.0; // Round to two decimal places
    }

    private double calculatePercentageDecrease(long oldValue, long newValue) {
        if (oldValue == 0) {
            return (newValue > 0) ? 100.0 : 0.0;
        }

        double percentageDecrease = ((double) (oldValue - newValue) / Math.abs(oldValue)) * 100.0;
        return Math.round(percentageDecrease * 100.0) / 100.0; // Round to two decimal places
    }

    public static void addItem(String productId) {
        doNotDisturbProduct.add(productId);
    }

    public static void removeItem(String productId) {
        doNotDisturbProduct.remove(productId);
    }
}
