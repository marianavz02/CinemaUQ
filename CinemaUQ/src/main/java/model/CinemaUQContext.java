package model;

import model.entity.Usuario;

public class CinemaUQContext {
    private static CinemaUQContext instance;
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
}