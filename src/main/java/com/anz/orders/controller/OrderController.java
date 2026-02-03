package com.anz.orders.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.anz.orders.entity.OrderStatus;
import com.anz.orders.exceptions.OrderNotFoundException;
import com.anz.orders.model.CreateOrderRequest;
import com.anz.orders.model.UpdateStatusRequest;
import com.anz.orders.service.OrderService;

import lombok.extern.log4j.Log4j2;

@Log4j2
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private final OrderService service;

    public OrderController(OrderService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CreateOrderRequest req) {

        try {
            return ResponseEntity.ok(service.createOrder(req.getDescription()));
        }
        catch (Exception e) {
            log.error("Unexpected error creating order", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating order");
        }
        
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> get(@PathVariable Long id) {
        
        try {
            return ResponseEntity.ok(service.getOrder(id));
        }
        catch (OrderNotFoundException onfe) {
            log.error("Error retrieving order with id {}", id, onfe);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Order with id " + id + " not found");

        }
        catch (Exception e) {
            log.error("Unexpected error retrieving order with id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error retrieving order with id " + id);
        } 

    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateStatus(@PathVariable Long id,
                              @RequestBody UpdateStatusRequest req) {

        try {
            return ResponseEntity.ok(service.updateStatus(id, req.getStatus()));
        }
        catch (OrderNotFoundException onfe) {
            log.error("Error updating order with id {}", id, onfe);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Order with id of " + id + " to update");

        }
        catch (Exception e) {
            log.error("Unexpected error updating order with id {}", id, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating order with id " + id);
        }                  
    }

    @GetMapping("/search")
    public ResponseEntity<?> search(
            @RequestParam(required = false) OrderStatus status,
            Pageable pageable) {

        try {

            return status == null
                    ? ResponseEntity.ok(service.findAll(pageable))
                    : ResponseEntity.ok(service.findByStatus(status, pageable));
        }
        catch (Exception e) {
            log.error("Unexpected error searching orders", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error searching orders");
        }
    }
}

