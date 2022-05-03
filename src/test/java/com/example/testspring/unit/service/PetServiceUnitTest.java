package com.example.testspring.unit.service;

import com.example.testspring.model.Pet;
import com.example.testspring.service.PetService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.List;

public class PetServiceUnitTest {
    @Mock
    private PetService petService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void deveDevolverPets_quandoBuscarPets() {
        List pets = List.of(new Pet(1L, "Rajado", 4, "Genérico"));
        Mockito.when(petService.findAll()).thenReturn(pets);

        List<Pet> petsResult = petService.findAll();
        Assertions.assertEquals(pets, petsResult);
    }

    @Test
    public void deveDevolverPet_quandoBuscarPet() {
        Pet pet = new Pet(1L, "Rajado", 4, "Genérico");
        Mockito.when(petService.findById(1L)).thenReturn(pet);

        Pet petResult = petService.findById(1L);
        Assertions.assertEquals(petResult, pet);
    }

    @Test
    public void deveDevolverPet_quandoCadastrarPet() {
        Pet pet = new Pet("Rajado", 4, "Genérico");
        Mockito.when(petService.save(pet)).thenReturn(new Pet(1L, "Rajado", 4, "Genérico"));

        Pet petResult = petService.save(pet);
        Assertions.assertEquals(petResult, new Pet(1L, "Rajado", 4, "Genérico"));
    }

    @Test
    public void deveDevolverPet_quandoAtualizarPet() {
        Pet pet = new Pet(1L, "Rajado", 4, "Genérico");
        Mockito.when(petService.update(1L, pet)).thenReturn(new Pet(1L, "Rajado", 4, "Genérico"));

        Pet petResult = petService.update(1L, pet);
        Assertions.assertEquals(petResult, new Pet(1L, "Rajado", 4, "Genérico"));
    }

    @Test
    public void deveDevolverErro_quandoAtualizarPet() {
        Pet pet = new Pet(1L, "Rajado", 4, "Genérico");
        Mockito.when(petService.update(1L, pet)).thenReturn(null);

        Pet petResult = petService.update(1L, pet);
        Assertions.assertEquals(null, petResult);
    }

    @Test
    public void deveRetornarSucesso_quandoRemoverPet() {
        Mockito.when(petService.deleteById(1L)).thenReturn(true);

        boolean result = petService.deleteById(1L);
        Assertions.assertEquals(true, result);
    }

    @Test
    public void deveRetornarErro_quandoRemoverPet() {
        Mockito.when(petService.deleteById(2L)).thenReturn(false);

        boolean result = petService.deleteById(2L);
        Assertions.assertEquals(false, result);
    }
}
