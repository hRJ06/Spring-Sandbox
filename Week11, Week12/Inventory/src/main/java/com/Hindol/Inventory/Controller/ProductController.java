package com.Hindol.Inventory.Controller;

import com.Hindol.Inventory.Client.OrdersOpenFeignClient;
import com.Hindol.Inventory.DTO.OrderRequestDTO;
import com.Hindol.Inventory.DTO.ProductDTO;
import com.Hindol.Inventory.Service.ProductService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    /* private final DiscoveryClient discoveryClient;
    private final RestClient restClient; */
    private final OrdersOpenFeignClient ordersOpenFeignClient;

    @GetMapping("/fetchOrder")
    public String fetchFromOrder(HttpServletRequest httpServletRequest) {
        log.info("Custom Header : {}",httpServletRequest.getHeader("X-Custom-Header"));
//        ServiceInstance orderService = discoveryClient.getInstances("Order-Service").get(0);
//        return restClient.get()
//                .uri(orderService.getUri() + "/orders/core/hello")
//                .retrieve()
//                .body(String.class);
        return ordersOpenFeignClient.helloOrders();
    }
    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllInventory() {
        List<ProductDTO> inventories = productService.getAllInventory();
        return ResponseEntity.ok(inventories);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PutMapping("/reduce-stocks")
    public ResponseEntity<Double> reduceStocks(@RequestBody OrderRequestDTO orderRequestDTO) {
        Double totalPrice = productService.reduceStocks(orderRequestDTO);
        return ResponseEntity.ok(totalPrice);

    }
}
