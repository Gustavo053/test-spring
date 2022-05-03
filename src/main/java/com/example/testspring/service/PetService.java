package com.example.testspring.service;

import com.example.testspring.model.Pet;
import com.example.testspring.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PetService {
    @Autowired
    private PetRepository petRepository;

    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    public Pet findById(Long id) {
        Optional<Pet> petOptional = petRepository.findById(id);

        if (!petOptional.isPresent()) {
            return null;
        }

        return petOptional.get();
    }

    public Pet save(Pet pet) {
        return petRepository.save(pet);
    }

    public Pet update(Long id, Pet pet) {
        Optional<Pet> petOptional = petRepository.findById(id);

        if (!petOptional.isPresent()) {
            return null;
        }

        pet.setId(id);
        return petRepository.save(pet);
    }

    public boolean deleteById(Long id) {
        Optional<Pet> petOptional = petRepository.findById(id);

        if (!petOptional.isPresent()) {
            return false;
        }

        petRepository.deleteById(id);
        return true;
    }
}
