package com.Hindol.Week20.LangChain.Service;

import com.Hindol.Week20.LangChain.Configuration.StockAPIConfiguration;
import dev.langchain4j.agent.tool.P;
import dev.langchain4j.agent.tool.Tool;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class StockInformationService {
    private final StockAPIConfiguration stockAPIConfiguration;
    private final RestClient restClient;

    @Tool("Return the stock price for given stock symbol")
    public String getStockPrice(@P("Stock symbol separated by ,") String stockSymbol) {
        log.info("Fetching Stock Price for Stock Symbol - {}", stockSymbol);
        return fetchData("/quote/" + stockSymbol);
    }

    @Tool("Return the Company Profile for given stock symbol")
    public String getCompanyProfile(@P("Stock symbol separated by ,") String stockSymbol) {
        log.info("Fetching Company Profile for Stock Symbol - {}", stockSymbol);
        return fetchData("/profile/" + stockSymbol);
    }

    @Tool("Return the Balance Sheet Statement for given stock symbol")
    public List<String> getBalanceSheetStatement(@P("Stock symbol separated by ,") String stockSymbol) {
        log.info("Fetching Balance Sheet Statement for Stock Symbol - {}", stockSymbol);
        return fetchDataForMultipleSymbol(stockSymbol, "/balance-sheet-statement/");
    }

    @Tool("Return the Income Statement for given stock symbol")
    public List<String> getIncomeStatements(@P("Stock symbol separated by ,") String stockSymbol) {
        log.info("Fetching Income Statement for Stock Symbol - {}", stockSymbol);
        return fetchDataForMultipleSymbol(stockSymbol, "/income-statement/");
    }

    @Tool("Return the Cash Flow Statement for given stock symbol")
    public List<String> getCashFlowStatements(@P("Stock symbol separated by ,") String stockSymbol) {
        log.info("Fetching Cash Flow Statement for Stock Symbol - {}", stockSymbol);
        return fetchDataForMultipleSymbol(stockSymbol, "/cash-flow-statement/");
    }

    private List<String> fetchDataForMultipleSymbol(String stockSymbols, String s) {
        List<String> data = new ArrayList<>();
        for (String symbol : stockSymbols.split(",")) {
            String response = fetchData(s + symbol);
            data.add(response);
        }
        return data;
    }

    private String fetchData(String s) {
        return  restClient.get()
                .uri(s + "?apikey=" + stockAPIConfiguration.getAPIKey())
                .retrieve()
                .body(String.class)
                .replaceAll("\\s+", " ").trim();
    }
}
