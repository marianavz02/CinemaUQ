package model.usecases;
import model.CinemaUQContext;
import model.entity.Usuario;

import java.util.List;

public class AutenticacionUseCase implements IAutenticacionUseCase {

        @Override
        public Usuario login(String email, String password) throws Exception {
            List<Usuario> listaUsuarios = CinemaUQContext.getInstance().getUsuarios();

            if (listaUsuarios != null) {
                for (Usuario u : listaUsuarios) {
                    if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
                        // Guardar la sesión activa utilizando el Singleton
                        CinemaUQContext.getInstance().setCurrentUser(u);
                        return u;
                    }
                }
            }

            // Si no se encuentra el usuario, se lanza una excepción de error
            throw new Exception("Correo o contraseña incorrectos.");
        }

        @Override
        public void logout(Usuario usuario) {
            CinemaUQContext.getInstance().setCurrentUser(null);
        }
    }

