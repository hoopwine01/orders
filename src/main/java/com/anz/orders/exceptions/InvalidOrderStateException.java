package com.anz.orders.exceptions;

import com.anz.orders.entity.OrderStatus;

public class InvalidOrderStateException extends RuntimeException {

    public InvalidOrderStateException(OrderStatus current, OrderStatus target) {
        super("Cannot change order status from " + current + " to " + target);
    }

}
