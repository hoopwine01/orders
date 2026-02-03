package com.anz.orders.notification;

import java.io.IOException;

import org.springframework.retry.annotation.Retryable;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpServerErrorException;

import com.anz.orders.entity.Order;

import lombok.extern.log4j.Log4j2;

@Component
@Log4j2
public class EmailNotificationChannel implements NotificationChannel {


    @Override
    public String channelName() {
        return "email";
    }

    @SuppressWarnings("deprecation")
    @Retryable(
        value = { IOException.class, HttpServerErrorException.class },
        maxAttempts = 3,
        backoff = @Backoff(delay = 2000, multiplier = 2)
    )

    @Override
    public void notify(Order order) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'notify'");
    }

    @Recover
    public void recover(Exception ex, Order order) {
        // Fallback logic after retries exhausted
        log.error("Email notification failed after retries for order {}", order.getId(), ex);
    }
}

