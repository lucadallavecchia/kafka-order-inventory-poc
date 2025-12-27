package com.ldv.orderservice.mapper;


import com.ldv.orderservice.dto.OrderItemDto;
import com.ldv.orderservice.dto.OrderRequest;
import com.ldv.orderservice.entity.Order;
import com.ldv.orderservice.entity.OrderItem;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE // Useful to ignore fields like ID/Status managed in a manual way
)
public interface OrderMapper {

    Order toEntity(OrderRequest request);

    OrderItem toOrderItem(OrderItemDto dto);

}
