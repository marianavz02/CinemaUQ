package model;

import model.entity.Usuario;

import java.util.ArrayList;
import java.util.List;

public class CinemaUQContext {

    private static CinemaUQContext instance;
    List<Usuario>usuarios= new ArrayList<>();
    private Usuario currentUser;
//singleton

    private CinemaUQContext() {
        // Constructor privado para evitar instanciación externa
    }

    public static CinemaUQContext getInstance() {
        if (instance == null) {
            instance = new CinemaUQContext();
        }
        return instance;
    }

    public Usuario getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(Usuario usuario) {
        this.currentUser = usuario;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public List<Usuario>setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
        return usuarios;
    }

    public void agreagarUsuario(Usuario usuario) {
        usuarios.add(usuario);
    }
}