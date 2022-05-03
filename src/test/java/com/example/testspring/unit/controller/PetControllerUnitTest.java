package com.example.testspring.unit.controller;

import com.example.testspring.integration.controller.PetController;
import com.example.testspring.model.Pet;
import com.example.testspring.service.MessageService;
import com.example.testspring.service.PetService;
import io.restassured.http.ContentType;
import io.restassured.module.mockmvc.RestAssuredMockMvc;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;

import java.util.List;

@WebMvcTest
public class PetControllerUnitTest {

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
    public void deveRetornarSucesso_quandoCadastrarPet() {
        Pet pet = new Pet("Godofredo", 6, "Genérico");
        Mockito.when(this.petService.save(pet))
                .thenReturn(new Pet(1L, "Godofredo", 6, "Genérico"));

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
        Pet pet = new Pet(1L,"Jorge", 6, "Genérico");
        Mockito.when(this.petService.update(1L, pet))
                .thenReturn(new Pet(1L, "Godofredo", 6, "Genérico"));

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
    public void deveRetornarSucesso_quandoListarPets() {
        Mockito.when(this.petService.findAll())
                .thenReturn(List.of(new Pet(1L, "Rajado", 4, "Genérico")));

        RestAssuredMockMvc.given().accept(ContentType.JSON)
                .when()
                    .get("/pet")
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarSucesso_quandoBuscarPet() {
        Mockito.when(this.petService.findById(1L))
            .thenReturn(new Pet(1L, "Rajado", 4, "Genérico"));

        RestAssuredMockMvc.given().accept(ContentType.JSON)
                .when()
                    .get("/pet/{id}", 1L)
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveRetornarNaoEncontrado_quandoBuscarPet() {
        Mockito.when(this.petService.findById(2L))
                .thenReturn(null);

        RestAssuredMockMvc.given().accept(ContentType.JSON)
                .when()
                    .get("/pet/{id}", 2L)
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    @Test
    public void deveRetornarSucesso_quandoDeletarPet() {
        Mockito.when(this.petService.deleteById(1L))
                .thenReturn(true);

        RestAssuredMockMvc.given().accept(ContentType.JSON)
                .when()
                    .delete("/pet/{id}", 1L)
                .then()
                    .statusCode(HttpStatus.NO_CONTENT.value());
    }

    @Test
    public void deveRetornarNaoEncontrado_quandoDeletarPet() {
        Mockito.when(this.petService.deleteById(2L))
                .thenReturn(false);

        RestAssuredMockMvc.given().accept(ContentType.JSON)
                .when()
                    .delete("/pet/{id}", 2L)
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }
}
