package model.usecases;
import model.Usuario;
import model.CinemaUQContext;

public class AutenticacionUseCase implements IAutenticacionUseCase {
    @Override
    public Usuario login(String email, String password) throws Exception {
        // Lógica de validación
        // CinemaUQContext.getInstance().setCurrentUser(usuario);
        return null;
    }

    @Override
    public void logout(Usuario usuario) {
        CinemaUQContext.getInstance().setCurrentUser(null);
    }
}