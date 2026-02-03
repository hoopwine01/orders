package com.anz.orders.service;

import java.time.Instant;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.anz.orders.entity.Order;
import com.anz.orders.entity.OrderStatus;
import com.anz.orders.event.OrderStatusChangedEvent;
import com.anz.orders.exceptions.InvalidOrderStateException;
import com.anz.orders.exceptions.OrderNotFoundException;
import com.anz.orders.repository.OrderRepository;

@Service
public class OrderService {

    private final OrderRepository repository;
    private final ApplicationEventPublisher eventPublisher;

    public OrderService(OrderRepository repository,
                        ApplicationEventPublisher eventPublisher) {
        this.repository = repository;
        this.eventPublisher = eventPublisher;
    }

    public Order createOrder(String description) {
        Order order = new Order();
        order.setStatus(OrderStatus.CREATED);
        order.setDescription(description);
        order.setCreatedAt(Instant.now());
        return repository.save(order);
    }

    public Order updateStatus(Long id, OrderStatus newStatus) throws OrderNotFoundException, InvalidOrderStateException, Exception {
        Order order = repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));

        validateTransition(order.getStatus(), newStatus);

        order.setStatus(newStatus);
        order.setUpdatedAt(Instant.now());
        Order saved = repository.save(order);

        eventPublisher.publishEvent(
                new OrderStatusChangedEvent(saved)
        );

        return saved;
    }

    private void validateTransition(OrderStatus current, OrderStatus target)throws InvalidOrderStateException, Exception{
        if (current != OrderStatus.CREATED) {
            throw new InvalidOrderStateException(current, target);
        }
    }

    public Order getOrder(Long id) throws OrderNotFoundException, Exception {
        return repository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Page<Order> findAll(Pageable pageable) throws Exception {
        return repository.findAll(pageable);
    }

    public Page<Order> findByStatus(OrderStatus status, Pageable pageable) throws Exception {
        return repository.findByStatus(status, pageable);
    }
}
