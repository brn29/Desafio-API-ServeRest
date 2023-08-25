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

public class DeleteUsuarioTest {
    private String URL_ServeRest = "https://serverest.dev/";
    private UsuarioTestData usuarioTestData;

    @BeforeEach
    void init(){
        RestAssured.baseURI = URL_ServeRest;
        usuarioTestData = new UsuarioTestData();
    }

    @Test
    @DisplayName("Deve excluir um usuário")
    void excluiUsuarioPorID() {
        Usuario usuario = usuarioTestData.delUsuario();

        Response respostaPOST = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .post("usuarios");
        String usuarioID = respostaPOST.jsonPath().getString("_id");
        System.out.println("O ID do usuário criado é: " + usuarioID);

        Response respostaDEL = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/usuarios/" + usuarioID);

        assertEquals(200, respostaDEL.getStatusCode());
        assertTrue(respostaDEL.getBody().asString().contains("Registro excluído com sucesso"));
    }

    @Test
    @DisplayName("Deve excluir nenhum usuário")
    void naoExcluiUsuario() {
        Response resposta = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/usuarios/1111111111111111111111111111111111111111111111");

        assertEquals(200, resposta.getStatusCode());
        assertTrue(resposta.getBody().asString().contains("Nenhum registro excluído"));
    }
}
