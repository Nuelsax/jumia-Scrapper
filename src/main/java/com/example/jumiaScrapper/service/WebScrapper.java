package com.example.jumiaScrapper.service;

import model.Product;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//public class WebScrapper {
//
//    public void scarpeAndComparePrices() {
//        System.out.println("calling webscrapperzzzzzz");
//        final String url = "https://www.jumia.co.ke/catalog/?q=samsung+phones&sort=highest-price&shop_premium_services=shop_express&price=8000-61199#catalog-listing";
//        List<Product> products = new ArrayList<>();
//        System.out.println("PREVIOUS PRODUCTS");
//        System.out.println(products);
//        try {
//            final Document document = Jsoup.connect(url).get();
//            int count =  0;
//            for(Element row: document.select("article.prd._fb.col.c-prd")) {
//                final String name = row.select("h3.name").text();
//                final String pricestr = row.select("div.prc").text();
//                String numStr = pricestr.replaceAll("[^\\d]", "");
//                int price = Integer.parseInt(numStr);
//
//                Optional<Product> productOpt = products.stream().filter(p -> p.getName().equals(name)).findFirst();
//                Product product = productOpt.orElse(new Product(name, price));
//                products.remove(product);
//                products.add(new Product(name, price));
//                products.add(new Product(name, price));
//                count++;
//                if (product.getPrice() > price) {
//                    System.out.println("Price decreased for " + name + " from " + product.getPrice() + " to " + price);
//                }  else if (product.getPrice() < price) {
//                    System.out.println("Price increased for " + name + " from " + product.getPrice() + " to " + price);
//                } else {
//                    System.out.println("Price remain the same for " + name);
//                }
//            }
//            System.out.println("total count for samsung is " + count);
////		 System.out.println(document.outerHtml());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//    }
//
//
//}
