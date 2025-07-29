package com.pharmacy.service;

import com.pharmacy.dto.MedicineDTO;
import com.pharmacy.exception.ResourceNotFoundException;
import com.pharmacy.model.Medicine;
import com.pharmacy.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MedicineService {
    private final MedicineRepository medicineRepository;
    private final ModelMapper modelMapper;

    public List<MedicineDTO> getAllMedicines() {
        return medicineRepository.findAll()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public MedicineDTO getMedicineById(String id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + id));
        return convertToDto(medicine);
    }

    public MedicineDTO createMedicine(MedicineDTO medicineDTO) {
        Medicine medicine = convertToEntity(medicineDTO);
        Medicine savedMedicine = medicineRepository.save(medicine);
        return convertToDto(savedMedicine);
    }

    public MedicineDTO updateMedicine(String id, MedicineDTO medicineDTO) {
        Medicine existingMedicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + id));


        medicineDTO.setId(id);

        modelMapper.map(medicineDTO, existingMedicine);
        Medicine updatedMedicine = medicineRepository.save(existingMedicine);
        return convertToDto(updatedMedicine);
    }

    public void deleteMedicine(String id) {
        Medicine medicine = medicineRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Medicine not found with id: " + id));
        medicineRepository.delete(medicine);
    }

    public List<MedicineDTO> searchMedicines(String searchTerm) {
        List<Medicine> medicines = medicineRepository.findByNameContainingIgnoreCase(searchTerm);
        medicines.addAll(medicineRepository.findByBatchContainingIgnoreCase(searchTerm));
        return medicines.stream()
                .distinct()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<MedicineDTO> getLowStockMedicines(int threshold) {
        return medicineRepository.findByQuantityLessThanEqual(threshold)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private MedicineDTO convertToDto(Medicine medicine) {
        return modelMapper.map(medicine, MedicineDTO.class);
    }

    private Medicine convertToEntity(MedicineDTO medicineDTO) {
        return modelMapper.map(medicineDTO, Medicine.class);
    }
}