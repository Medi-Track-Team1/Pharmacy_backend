package com.pharmacy.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.time.LocalDate;

@Data
public class MedicineDTO {
    private String id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Batch number is required")
    private String batch;

    @PositiveOrZero(message = "Quantity must be zero or positive")
    private int quantity;

    @Positive(message = "Price must be positive")
    private double price;

    @Future(message = "Expiry date must be in the future")
    private LocalDate expiry;

    private String supplier;
}