package com.itj.interview.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class YahooScrapedData {

    private static final Logger LOGGER = LogManager.getLogger(YahooScrapedData.class);
    private static final String yahooFinanceUrl = "https://finance.yahoo.com/quote/TSLA?p=TSLA";

    public Map<String, String> scrapeYahooFinanceData() throws IOException {
        Map<String, String> financialData = new HashMap<>();
        Document doc = Jsoup.connect(yahooFinanceUrl).get();

        LOGGER.info("----------------Data extracted from Yahoo Finance----------------" + "\n");

        Elements currentStockPriceElement = doc.select("fin-streamer[class='Fw(b) Fz(36px) Mb(-4px) D(ib)']");
        String currentStockPriceValue = currentStockPriceElement.first().ownText();
        financialData.put("Current Stock Price", currentStockPriceValue);
        LOGGER.info("a. Current Stock Price: " + currentStockPriceValue);

        Elements marketCapElement = doc.select("td[data-test='MARKET_CAP-value']");
        String marketCapitalization = marketCapElement.first().ownText();
        financialData.put("Market Capitalization", marketCapElement.text());
        LOGGER.info("b. Market Capitalization: " + marketCapitalization);

        Elements peRatioElement = doc.select("td[data-test='PE_RATIO-value']");
        String peRatio = peRatioElement.first().ownText();
        financialData.put("Price-to-Earnings (P/E) Ratio", peRatioElement.text());
        LOGGER.info("c. Price-to-Earnings (P/E) Ratio: " + peRatio);

        Elements dividendYieldElement = doc.select("td[data-test='DIVIDEND_AND_YIELD-value']");
        String dividendYield = dividendYieldElement.first().ownText();
        financialData.put("Dividend Yield", dividendYieldElement.text());
        LOGGER.info("d. Dividend Yield: " + dividendYield);

        Elements earningsPerShareElement = doc.select("td[data-test='EPS_RATIO-value']");
        String earningsPerShare = earningsPerShareElement.first().ownText();
        financialData.put("Earnings per Share (EPS)", earningsPerShareElement.text());
        LOGGER.info("e. Earnings per Share (EPS): " + earningsPerShare);

        return financialData;
    }

    public String scrapeDividendYieldWebPage() throws IOException {
        Document doc = Jsoup.connect(yahooFinanceUrl).get();
        Elements dividendYieldElement = doc.select("td[data-test='DIVIDEND_AND_YIELD-value']");
        return dividendYieldElement.first().ownText();
    }
}
