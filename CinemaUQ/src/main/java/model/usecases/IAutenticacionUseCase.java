package model.usecases;

import model.Usuario;

public interface IAutenticacionUseCase {
    Usuario login(String email, String password) throws Exception;
    void logout(Usuario usuario);
}