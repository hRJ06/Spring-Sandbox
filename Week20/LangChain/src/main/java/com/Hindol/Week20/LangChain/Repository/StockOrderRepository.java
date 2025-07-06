package com.Hindol.Week20.LangChain.Repository;

import com.Hindol.Week20.LangChain.Entity.StockOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StockOrderRepository extends CrudRepository<StockOrder, Long> {
    List<StockOrder> findBySymbol(String symbol);
}
