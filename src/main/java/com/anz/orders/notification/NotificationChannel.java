package com.anz.orders.notification;

import com.anz.orders.entity.Order;

public interface NotificationChannel {
    void notify(Order order);
    String channelName();
}
