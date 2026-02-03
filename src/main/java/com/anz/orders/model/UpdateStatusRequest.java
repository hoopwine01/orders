package com.anz.orders.model;

import com.anz.orders.entity.OrderStatus;

// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateStatusRequest {

   
    private OrderStatus status; // optional if you want backend default

   
    private String description;

}
