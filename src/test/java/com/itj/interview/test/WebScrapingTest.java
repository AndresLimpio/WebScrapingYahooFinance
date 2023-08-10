package com.itj.interview.test;

import com.itj.interview.models.DividendYieldScraped;
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
    private DividendYieldScraped dividendYieldScraped;
    private Map<String, String> scrapedData;
    private static final String stockSymbol = "TSLA";

    @BeforeEach
    public void setUp() throws IOException {
        yahooScrapedData = new YahooScrapedData();
        getRestData = new GetRestData();
        scrapedData = yahooScrapedData.scrapeYahooFinanceData();
        dividendYieldScraped = new DividendYieldScraped();
    }

    private void assertAndLogData(String key, String APIData) {
        if(scrapedData.get(key).equals(APIData)) {
            LOGGER.info("\nValidation Successfully!\n");
            LOGGER.info("API Data: " + APIData);
            LOGGER.info("Scraped Data: " + scrapedData.get(key));
            Assert.assertEquals(scrapedData.get(key), APIData);
        }else{
            Assert.fail("Expected API data: " + scrapedData.get(key) + ", but found: " + APIData);
        }
    }

    @Test
    public void testCurrentStockPrice() throws Exception {
        String externalData = getRestData.fetchDataFromFinancialModelingPrep(stockSymbol, "price");
        assertAndLogData("Current Stock Price", externalData);
    }

    @Test
    public void testMarketCapitalization() throws Exception {
        String externalData = getRestData.fetchDataFromFinancialModelingPrep(stockSymbol, "marketCap");

        // Remove "." and "B" and take the first 6 characters
        String formattedScrapedData = scrapedData.get("Market Capitalization")
                .replace(".", "")
                .replace("B", "")
                .substring(0, 6);

        String formattedAPIData = externalData
                .replace(".", "")
                .replace("B", "")
                .substring(0, 6);

        if (formattedScrapedData.equals(formattedAPIData)) {
            LOGGER.info("\nValidation Successfully!\n");
            LOGGER.info("API Data: " + externalData);
            LOGGER.info("Scraped Data: " + scrapedData.get("Market Capitalization"));
            Assert.assertEquals(formattedScrapedData, formattedAPIData);
        } else {
            Assert.fail("Expected API data: " + formattedScrapedData + ", but found: " + formattedAPIData);
        }
    }

    @Test
    public void testPERatio() throws Exception {
        String externalData = getRestData.fetchDataFromFinancialModelingPrep(stockSymbol, "pe");
        assertAndLogData("Price-to-Earnings (P/E) Ratio", externalData);
    }

    @Test
    public void testDividendYield() throws Exception {
        String yahooData = dividendYieldScraped.scrapeDividendYieldFromYahoo();
        String webpageData = dividendYieldScraped.scrapeDividendYieldFromWebPage();

        if (yahooData.equals(webpageData)){
            Assert.assertEquals(yahooData, webpageData);

            LOGGER.info("\nValidation Successfully!\n");
            LOGGER.info("Webpage Data: " + webpageData);
            LOGGER.info("Yahoo Data: " + yahooData);
        }else{
            Assert.fail("Expected webpage data: " + yahooData + ", but found: " + webpageData);
        }

        //Use this code in case you want compare with a premium API who have the Dividend Yield data
        /*String externalData = getRestData.fetchDataFromFinancialModelingPrep(stockSymbol, "dividendYield");
        assertAndLogData("Dividend Yield", externalData);*/
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