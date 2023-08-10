package com.itj.interview.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

public class DividendYieldScraped {

    private static final Logger LOGGER = LogManager.getLogger(DividendYieldScraped.class);

    public String scrapeDividendYieldFromWebPage() throws Exception {
        String webpageUrl = "https://www.investing.com/equities/tesla-motors";
        Document doc = Jsoup.connect(webpageUrl).get();

        // Extract the dividend yield value
        Elements dividendYieldElement = doc.select("dd[data-test='dividend']");
        return dividendYieldElement.text();
    }
    public String scrapeDividendYieldFromYahoo() throws Exception {
        YahooScrapedData yahooScrapedData = new YahooScrapedData();
        return yahooScrapedData.scrapeDividendYieldWebPage();
    }
}