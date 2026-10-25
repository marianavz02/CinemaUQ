package model.usecases;

import model.entity.Usuario;

public interface IAutenticacionUseCase {
    Usuario login(String email, String password) throws Exception;
    void logout(Usuario usuario);
}