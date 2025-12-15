package com.ldv.orderservice.api.order;

import com.ldv.orderservice.domain.Order;
import com.ldv.orderservice.service.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<CreateOrderResponse> create(@Valid @RequestBody CreateOrderRequest request) {
        Order order = orderService.create(request);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new CreateOrderResponse(
                        order.id(),
                        order.status().name(),
                        order.createdAt()
                ));
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetOrderResponse> getById(@PathVariable UUID id) {

        return orderService.findById(id)
                .map(order -> {
                    var items = order.items().stream()
                            .map(i -> new GetOrderResponse.Item(i.sku(), i.quantity()))
                            .toList();

                    return ResponseEntity.ok(
                            new GetOrderResponse(
                                    order.id(),
                                    order.customerId(),
                                    items,
                                    order.status().name(),
                                    order.createdAt()
                            )
                    );
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
