package br.org.desafioAPI.testData;

import br.org.desafioAPI.model.Usuario;
public class UsuarioTestData {

    private String usuarioID;

    public Usuario postUsuario() {
        Usuario rafflesia = new Usuario(
                "Rafflesia",
                "rafflesia@traptrix.com",
                "Plantinhas123",
                "true");
        return rafflesia;
    }

    public Usuario getUsuario() {
        Usuario pudica = new Usuario(
                "Pudica",
                "pudica@traptrix.com",
                "Plantinhas123",
                "true");
        return pudica;
    }

    public Usuario delUsuario() {
        Usuario sera = new Usuario(
                "Sera",
                "sera@traptrix.com",
                "Plantinhas123",
                "true");
        return sera;
    }

    public Usuario putUsuario() {
        Usuario mantis = new Usuario(
                "Mantis",
                "mantis@traptrix.com",
                "Plantinhas123",
                "true");
        return mantis;
    }

    public Usuario putUsuario2() {
        Usuario allomerus = new Usuario(
                "Allomerus",
                "allomerus@traptrix.com",
                "Plantinhas123",
                "true");
        return allomerus;
    }

    public String setUsuarioID(String usuarioID) {
        return this.usuarioID = usuarioID;
    }

    public String getUsuarioID() {
        return this.usuarioID;
    }

}
