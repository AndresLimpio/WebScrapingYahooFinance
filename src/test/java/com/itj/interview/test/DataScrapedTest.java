package com.itj.interview.test;

import com.itj.interview.models.GetRestData;
import com.itj.interview.models.YahooScrapedData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testng.Assert;
import java.io.IOException;
import java.util.Map;

public class DataScrapedTest {

    private static final Logger LOGGER = LogManager.getLogger(DataScrapedTest.class);
    private YahooScrapedData yahooScrapedData;
    private GetRestData getRestData;
    private Map<String, String> scrapedData;
    private static final String stockSymbol = "TSLA";

    @BeforeEach
    public void setUp() throws IOException {
        yahooScrapedData = new YahooScrapedData();
        getRestData = new GetRestData();
        scrapedData = yahooScrapedData.scrapeYahooFinanceData();
    }

    private void assertAndLogData(String key, String APIData) {
        LOGGER.info("API Data: " + APIData);
        LOGGER.info("Scraped Data: " + scrapedData.get(key));
        Assert.assertEquals(scrapedData.get(key), APIData);
    }

    @Test
    public void testCurrentStockPrice() throws Exception {
        String externalData = getRestData.fetchDataFromFinancialModelingPrep(stockSymbol,"price");
        assertAndLogData("Current Stock Price", externalData);
    }

    @Test
    public void testMarketCapitalization() throws Exception {
        String externalData = getRestData.fetchDataFromFinancialModelingPrep(stockSymbol, "marketCap");
        assertAndLogData("Market Capitalization", externalData);
    }

    @Test
    public void testPERatio() throws Exception {
        String externalData = getRestData.fetchDataFromFinancialModelingPrep(stockSymbol, "pe");
        assertAndLogData("Price-to-Earnings (P/E) Ratio", externalData);
    }

    @Test
    public void testDividendYield() throws Exception {
        String externalData = getRestData.fetchDataFromFinancialModelingPrep(stockSymbol, "dividendYield");
        assertAndLogData("Dividend Yield", externalData);
    }

    @Test
    public void testEPS() throws Exception {
        String externalData = getRestData.fetchDataFromFinancialModelingPrep(stockSymbol, "eps");
        assertAndLogData("Earnings per Share (EPS)", externalData);
    }

    @AfterEach
    public void tearDown() {
        scrapedData = null;
    }
}