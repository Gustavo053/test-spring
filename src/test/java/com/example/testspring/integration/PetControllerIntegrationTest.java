package com.example.testspring.integration;

import com.example.testspring.controller.PetController;
import com.example.testspring.model.Pet;
import com.example.testspring.service.MessageService;
import com.example.testspring.service.PetService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;

@WebMvcTest
public class PetControllerIntegrationTest {
    @Autowired
    private PetController petController;
    @MockBean
    private PetService petService;
    @MockBean
    private MessageService messageService;

    @BeforeEach
    public void setup() {
        RestAssuredMockMvc.standaloneSetup(this.petController);
    }

    @Test
    public void deveRetornarSucesso_quandoBuscarPets() {
        List<Pet> pets = List.of(new Pet(1L, "Rajado", 4, "Genérico"));
        Mockito.when(petService.findAll()).thenReturn(pets);

        List<Pet> petsResult = petService.findAll();
        Assertions.assertEquals(pets, petsResult);

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .when()
                    .get("/pet")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarSucesso_quandoBuscarPet() {
        Pet pet = new Pet(1L, "Rajado", 4, "Genérico");
        Mockito.when(petService.findById(1L)).thenReturn(pet);

        Pet petResult = petService.findById(1L);
        Assertions.assertEquals(pet, petResult);

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .when()
                    .get("/pet/{id}", 1L)
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarSucesso_quandoCadastrarPet() {
        Pet pet = new Pet("Rajado", 4, "Genérico");
        Mockito.when(petService.save(pet)).thenReturn(new Pet(1L, "Rajado", 4, "Genérico"));

        Pet petResult = petService.save(pet);
        Assertions.assertEquals(new Pet(1L, "Rajado", 4, "Genérico"), petResult);

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                    .post("/pet")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarSucesso_quandoAtualizarPet() {
        Pet pet = new Pet(1L, "Rajado", 4, "Genérico");
        Mockito.when(petService.update(1L, pet)).thenReturn(pet);

        Pet petResult = petService.update(1L, pet);
        Assertions.assertEquals(pet, petResult);

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                    .put("/pet/{id}", 1L)
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarErro_quandoAtualizarPet() {
        Pet pet = new Pet(1L, "Rajado", 4, "Genérico");
        Mockito.when(petService.update(1L, pet)).thenReturn(null);

        Pet petResult = petService.update(1L, pet);
        Assertions.assertEquals(null, petResult);

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(pet)
                .when()
                    .put("/pet/{id}", 1L)
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornarSucesso_quandoRemoverPet() {
        Mockito.when(petService.deleteById(1L)).thenReturn(true);

        boolean result = petService.deleteById(1L);
        Assertions.assertEquals(true, result);

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .when()
                    .delete("/pet/{id}", 1L)
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deveRetornarErro_quandoRemoverPet() {
        Mockito.when(petService.deleteById(1L)).thenReturn(false);

        boolean result = petService.deleteById(1L);
        Assertions.assertEquals(false, result);

        RestAssuredMockMvc.given()
                .accept(ContentType.JSON)
                .when()
                    .delete("/pet/{id}", 1L)
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
