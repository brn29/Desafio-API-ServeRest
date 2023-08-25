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

public class GetUsuarioTest {
    private String URL_ServeRest = "https://serverest.dev/";
    private UsuarioTestData usuarioTestData;

    @BeforeEach
    void init(){
        RestAssured.baseURI = URL_ServeRest;
        usuarioTestData = new UsuarioTestData();
    }

    @Test
    @DisplayName("Deve buscar todos os usuários")
    void buscaTodosUsuarios() {
        Response resposta = given()
                .when()
                .get("usuarios");

        assertEquals(200, resposta.getStatusCode());
        String primeiroUsuarioNome = resposta.jsonPath().getString("usuarios[0].nome");
        String primeiroUsuarioEmail = resposta.jsonPath().getString("usuarios[0].email");
        String primeiroUsuarioSenha = resposta.jsonPath().getString("usuarios[0].password");

        assertFalse(primeiroUsuarioNome.isEmpty());
        assertFalse(primeiroUsuarioEmail.isEmpty());
        assertFalse(primeiroUsuarioSenha.isEmpty());
    }

    @Test
    @DisplayName("Deve buscar um usuário pelo ID")
    void buscaUsuarioPorID() {
        Usuario usuario = usuarioTestData.getUsuario();

        Response respostaPOST = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("usuarios");
        String usuarioID = respostaPOST.jsonPath().getString("_id");
        System.out.println("O ID do usuário criado é: " + usuarioID);

        Response respostaGET = given()
                .when()
                .get("usuarios/" + usuarioID);

        assertEquals(200, respostaGET.getStatusCode());
        assertEquals(usuario.getNome(), respostaGET.jsonPath().getString("nome"));
        assertEquals(usuario.getEmail(), respostaGET.jsonPath().getString("email"));
        assertEquals(usuario.getPassword(), respostaGET.jsonPath().getString("password"));

        Response respostaDEL = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/usuarios/" + usuarioID);
    }

    @Test
    @DisplayName("Deve retornar erro buscar um usuário com ID inexistente")
    void erroBuscarUsuarioComIDInexistente() {
        Response resposta = given()
                .when()
                .get("usuarios/1111111111111111111111111111111111111111111111");

        assertEquals(400, resposta.getStatusCode());
        assertTrue(resposta.getBody().asString().contains("Usuário não encontrado"));
    }
}
