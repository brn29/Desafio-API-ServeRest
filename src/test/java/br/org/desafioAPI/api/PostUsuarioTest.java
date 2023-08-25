package br.org.desafioAPI.api;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import br.org.desafioAPI.testData.UsuarioTestData;
import br.org.desafioAPI.model.Usuario;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class PostUsuarioTest {

    private String URL_ServeRest = "https://serverest.dev/";
    private UsuarioTestData usuarioTestData;

    @BeforeEach
    void init(){
        RestAssured.baseURI = URL_ServeRest;
        usuarioTestData = new UsuarioTestData();
    }

    @Test
    @DisplayName("Deve cadastrar um novo usuário")
    void cadastraNovoUsuario() {
        Usuario usuario = usuarioTestData.postUsuario();
        Response resposta = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("usuarios");

        assertEquals(201, resposta.getStatusCode());
        usuarioTestData.setUsuarioID(resposta.jsonPath().getString("_id"));
        assertTrue(resposta.getBody().asString().contains("Cadastro realizado com sucesso"));
        System.out.println("O ID do usuário criado é: " + usuarioTestData.getUsuarioID());

        String usuarioID = resposta.jsonPath().getString("_id");
        Response respostaDEL = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/usuarios/" + usuarioID);
    }

    @Test
    @DisplayName("Deve retornar erro cadastrar um novo usuário com email já cadastrado")
    void erroCadastrarUsuarioComEmailRepetido() {
        Usuario usuario = usuarioTestData.postUsuario();
        Response resposta1 = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("usuarios");
        System.out.println("O ID do usuário criado é: " + usuarioTestData.getUsuarioID());


        Response resposta2 = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("usuarios");
        assertEquals(400, resposta2.getStatusCode());
        assertTrue(resposta2.getBody().asString().contains("Este email já está sendo usado"));

        String usuarioID = resposta1.jsonPath().getString("_id");
        Response respostaDEL = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/usuarios/" + usuarioID);
    }
}
