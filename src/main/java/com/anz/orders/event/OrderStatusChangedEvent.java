package com.anz.orders.event;

import com.anz.orders.entity.Order;

public class OrderStatusChangedEvent {

    private final Order order;

    public OrderStatusChangedEvent(Order order) {
        this.order = order;
    }

    public Order getOrder() {
        return order;
    }
}

