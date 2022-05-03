package com.example.testspring.controller;

import com.example.testspring.model.Pet;
import com.example.testspring.service.MessageService;
import com.example.testspring.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping(value = "/pet")
public class PetController {
    @Autowired
    private PetService petService;
    @Autowired
    private MessageService messageService;

    @GetMapping
    public List<Pet> findAll() {
        return petService.findAll();
    }

    @GetMapping(value = "/{id}")
    public Pet findOne(@PathVariable Long id) {
        Pet pet = petService.findById(id);

        if (pet == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("pet.not-found"));
        }

        return pet;
    }

    @PostMapping
    public Pet save(@RequestBody Pet pet) {
        return petService.save(pet);
    }

    @PutMapping(value = "/{id}")
    public Pet update(@PathVariable Long id, @RequestBody Pet pet) {
        Pet petReturned = petService.update(id, pet);

        if (petReturned == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("pet.not-found"));
        }

        return petReturned;
    }

    @DeleteMapping(value = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        boolean deleted = petService.deleteById(id);

        if (!deleted) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, messageService.get("pet.not-found"));
        }
    }
}
