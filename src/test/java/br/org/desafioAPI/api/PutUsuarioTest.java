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

public class PutUsuarioTest {
    private String URL_ServeRest = "https://serverest.dev/";
    private UsuarioTestData usuarioTestData;

    @BeforeEach
    void init(){
        RestAssured.baseURI = URL_ServeRest;
        usuarioTestData = new UsuarioTestData();
    }

    @Test
    @DisplayName("Deve alterar um usuário")
    void alteraUsuarioPorID() {
        Usuario usuarioPOST = usuarioTestData.putUsuario();

        Response respostaPOST = given()
                .contentType(ContentType.JSON)
                .body(usuarioPOST)
                .when()
                .post("usuarios");

        String usuarioID = respostaPOST.jsonPath().getString("_id");
        System.out.println("O ID do usuário criado é: " + usuarioID);

        Usuario usuarioPUT2 = usuarioTestData.putUsuario2();
        Response respostaPUT = given()
                .contentType(ContentType.JSON)
                .body(usuarioPUT2)
                .when()
                .put("usuarios/" + usuarioID);

        assertEquals(200, respostaPUT.getStatusCode());
        assertTrue(respostaPUT.getBody().asString().contains("Registro alterado com sucesso"));
        System.out.println("O ID do usuário editado é: " + usuarioTestData.getUsuarioID());

        Response respostaDEL = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/usuarios/" + usuarioID);
    }

    @Test
    @DisplayName("Deve cadastrar um novo usuário ao tentar alterar um usuário inexistente")
    void criaNovoUsuarioTentandoalterarUsuarioInexistentePorID() {

        Usuario usuario = usuarioTestData.putUsuario();
        Response resposta = given()
                .contentType(ContentType.JSON)
                .body(usuario)
                .when()
                .put("usuarios/1111111111111111111111111111111111111111111111");

        assertEquals(201, resposta.getStatusCode());
        assertTrue(resposta.getBody().asString().contains("Cadastro realizado com sucesso"));

        String usuarioID = resposta.jsonPath().getString("_id");
        Response respostaDEL = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/usuarios/" + usuarioID);
    }

    @Test
    @DisplayName("Deve retornar erro alterar um usuário para um e-mail já cadastrado")
    void erroalterarUsuarioParaEmailRepetido() {
        Usuario usuarioPOST = usuarioTestData.postUsuario();

        Response respostaPOST1 = given()
                .contentType(ContentType.JSON)
                .body(usuarioPOST)
                .when()
                .post("usuarios");
        String usuarioID1 = respostaPOST1.jsonPath().getString("_id");

        Usuario usuarioPOST2 = usuarioTestData.putUsuario();
        Response respostaPOST2 = given()
                .contentType(ContentType.JSON)
                .body(usuarioPOST2)
                .when()
                .post("usuarios");

        String usuarioID2 = respostaPOST2.jsonPath().getString("_id");
        System.out.println("O ID do usuário criado é: " + usuarioID2);

        Usuario usuarioPUT = usuarioTestData.putUsuario2();
        Response respostaPUT = given()
                .contentType(ContentType.JSON)
                .body(usuarioPOST)
                .when()
                .put("usuarios/" + usuarioID2);

        assertEquals(400, respostaPUT.getStatusCode());
        assertTrue(respostaPUT.getBody().asString().contains("Este email já está sendo usado"));

        Response respostaDEL1 = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/usuarios/" + usuarioID1);

        Response respostaDEL2 = given()
                .contentType(ContentType.JSON)
                .when()
                .delete("/usuarios/" + usuarioID2);
    }
}
