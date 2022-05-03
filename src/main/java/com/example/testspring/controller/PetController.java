package com.example.testspring.controller;

import com.example.testspring.model.Pet;
import com.example.testspring.repository.PetRepository;
import com.example.testspring.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/pet")
public class PetController {
    @Autowired
    private PetRepository petRepository;
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Pet> findAll() {
        return petRepository.findAll();
    }

    @GetMapping(value = "/{id}")
    public Pet findOne(@PathVariable Long id) {
        Optional<Pet> petOptional = petRepository.findById(id);

        if (!petOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("pet.not-found"));
        }

        return petOptional.get();
    }

    @PostMapping
    public Pet save(@RequestBody Pet pet) {
        return petRepository.save(pet);
    }

    @PutMapping(value = "/{id}")
    public Pet update(@PathVariable Long id, @RequestBody Pet pet) {
        Optional<Pet> petOptional = petRepository.findById(id);

        if (!petOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("pet.not-found"));
        }

        pet.setId(id);
        return petRepository.save(pet);
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        Optional<Pet> petOptional = petRepository.findById(id);

        if (!petOptional.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("pet.not-found"));
        }

        petRepository.deleteById(id);
    }
}
