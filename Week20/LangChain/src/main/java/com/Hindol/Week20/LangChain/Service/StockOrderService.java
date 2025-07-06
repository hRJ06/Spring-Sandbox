package com.Hindol.Week20.LangChain.Service;

import com.Hindol.Week20.LangChain.Entity.OrderType;
import com.Hindol.Week20.LangChain.Entity.StockHoldingDetail;
import com.Hindol.Week20.LangChain.Entity.StockOrder;
import com.Hindol.Week20.LangChain.Repository.StockOrderRepository;
import dev.langchain4j.agent.tool.Tool;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StockOrderService {
    private final StockOrderRepository stockOrderRepository;

    @Tool
    public StockOrder createOrder(StockOrder order) {
        StockOrder newOrder = new StockOrder(
                null,
                "user_id",
                order.symbol(),
                order.quantity(),
                order.price(),
                order.orderType(),
                LocalDateTime.now()
        );
        return stockOrderRepository.save(newOrder);
    }


    @Tool
    public List<StockOrder> getAllOrders() {
        return (List<StockOrder>) stockOrderRepository.findAll();
    }

    public StockOrder getOrderById(Long id) {
        return stockOrderRepository.findById(id).orElse(null);
    }

    public List<StockOrder> getOrdersBySymbol(String symbol) {
        return stockOrderRepository.findBySymbol(symbol);
    }

    @Tool
    public List<StockHoldingDetail> getStockHoldingDetails() {
        List<StockOrder> stockOrder = (List<StockOrder>) stockOrderRepository.findAll();
        return stockOrder.stream()
                .collect(Collectors.groupingBy(StockOrder::symbol, Collectors.summingInt(order ->
                        order.orderType().equals(OrderType.BUY) ? order.quantity() : -order.quantity())))
                .entrySet().stream()
                .map(entry -> new StockHoldingDetail(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
