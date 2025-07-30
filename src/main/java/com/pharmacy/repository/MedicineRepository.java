package com.pharmacy.repository;

import com.pharmacy.model.Medicine;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MedicineRepository extends MongoRepository<Medicine, String> {
    List<Medicine> findByNameContainingIgnoreCase(String name);
    List<Medicine> findByBatchContainingIgnoreCase(String batch);
    List<Medicine> findByStockLessThanEqual(int threshold); // Changed from quantity to stock
}