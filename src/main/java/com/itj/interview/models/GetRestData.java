package com.itj.interview.models;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class GetRestData {

    private static final Logger LOGGER = LogManager.getLogger(GetRestData.class);
    private static final String FINANCIAL_MODELING_PREP_BASE_URL = "https://financialmodelingprep.com/api/v3";
    private static final String FINANCIAL_MODELING_PREP_API_KEY = "efffd6f379b321e3b885925577bee979";

    public String fetchDataFromFinancialModelingPrep(String symbol, String field) throws Exception {
        String url = FINANCIAL_MODELING_PREP_BASE_URL + "/quote/" + symbol + "?apikey=" + FINANCIAL_MODELING_PREP_API_KEY;
        Document doc = Jsoup.connect(url).ignoreContentType(true).get();
        String json = doc.select("body").text();

        /* Use the Logger to print the JSON response for debugging
        LOGGER.debug("JSON Response: " + json);*/

        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(json);

        // Check if the JSON response is an array and extract the first element
        if (jsonNode.isArray()) {
            JsonNode firstElement = jsonNode.get(0);
            JsonNode fieldNode = firstElement.get(field);

            if (fieldNode != null) {
                System.out.println("\nValidation Successfully!\n");
                return fieldNode.asText();
            } else {
                throw new IllegalArgumentException("Field '" + field + "' not found in the JSON response.");
            }
        } else {
            throw new IllegalArgumentException("Invalid JSON response format from the API.");
        }
    }
}
