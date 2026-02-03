package com.anz.orders.notification;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import com.anz.orders.entity.Order;

@Component
public class NotificationDispatcher {

    private final Map<String, NotificationChannel> channels;

    public NotificationDispatcher(List<NotificationChannel> channelList,
                                  NotificationProperties properties) {

        this.channels = channelList.stream()
                .filter(c -> properties.getChannels().contains(c.channelName()))
                .collect(Collectors.toMap(
                        NotificationChannel::channelName,
                        Function.identity()
                ));
    }

    public void dispatch(Order order) {
        channels.values().forEach(c -> c.notify(order));
    }
}

