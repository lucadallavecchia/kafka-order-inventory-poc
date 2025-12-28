package com.ldv.orderservice.mapper;


import com.ldv.orderservice.controller.dto.OrderItemDto;
import com.ldv.orderservice.controller.dto.OrderRequest;
import com.ldv.orderservice.controller.dto.OrderResponse;
import com.ldv.orderservice.entity.Order;
import com.ldv.orderservice.entity.OrderItem;
import com.ldv.orderservice.messaging.dto.OrderItemEvent;
import com.ldv.orderservice.messaging.dto.OrderPlacedEvent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE // Useful to ignore fields like ID/Status managed in a manual way
)
public interface OrderMapper {

    // From Request (controller dto) to Entity
    Order toEntity(OrderRequest request);

    // From Entity a Response (controller dto)
    OrderResponse toResponse(Order order);

    // From Entity to Event (messaging dto)
    @Mapping(source = "id", target = "orderId")
    OrderPlacedEvent toEvent(Order order);

    // Items managements (controller dto -> entity)
    OrderItem toOrderItemEntity(OrderItemDto dto);

    OrderItemDto toOrderItemDto(OrderItem entity);

    OrderItemEvent toOrderItemEvent(OrderItem entity);
}
