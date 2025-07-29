package com.pharmacy.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

@Data
@Document(collection = "medicines")
public class Medicine {
    @Id
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