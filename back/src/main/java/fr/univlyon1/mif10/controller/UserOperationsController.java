package fr.univlyon1.mif10.controller;

import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import fr.univlyon1.mif10.dto.LoginDTO;
import fr.univlyon1.mif10.dto.UserDTO;
import fr.univlyon1.mif10.service.UserService;
import fr.univlyon1.mif10.util.JwtHelper;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserOperationsController implements ControllerBase {

    private final UserService userService;

    @Autowired
    public UserOperationsController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<Void> login(@RequestBody LoginDTO loginDTO,
                                      HttpServletRequest request) {
        String origin = request.getHeader("Origin");
        UserDTO user = userService.findByLogin(loginDTO.getLogin());
        System.out.println("###### In Login ######");
        if (user != null) {
            if (user.authenticate(loginDTO.getPassword())) {
                String token = JwtHelper.generateToken(loginDTO.getLogin(), origin);
                userService.updateConnected(user);
                HttpHeaders headers = new HttpHeaders();
                headers.add("Authorization", "Bearer " + token);
                headers.add("Access-Control-Expose-Headers", "Authorization"); // Expose the Authorization header
                return ResponseEntity.status(HttpStatus.OK).headers(headers).build();
            } else {
                // wrong password
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        } else {
            // user not found
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    /**
     * Réalise la déconnexion
     */
    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestHeader("Authorization") String jwt,
                                       @RequestHeader("Origin") String origin) {
        try {
            String token = jwt.replace("Bearer ", "");
            String login = JwtHelper.verifyToken(token, origin);
            UserDTO user = userService.findByLogin(login);
            if ( user != null){
                user.disconnect();
                userService.updateConnected(user);
            }
        } catch (JWTVerificationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok().build();
    }

    /**
     * Méthode destinée au serveur Node pour valider l'authentification d'un utilisateur.
     * @param jwt Le token JWT qui se trouve dans le header "Authentication" de la requête
     * @param origin L'origine de la requête (pour la comparer avec celle du client, stockée dans le token JWT)
     * @return Une réponse vide avec un code de statut approprié (204, 400, 401).
     */
    @GetMapping("/authenticate")
    public ResponseEntity<Void> authenticate(@RequestHeader("Authorization") String jwt, @RequestHeader("Origin") String origin) {
        // Extraire le token du header Authorization
        String token = jwt.replace("Bearer ", "");
        try {
            JwtHelper.verifyToken(token, origin);
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        catch (JWTVerificationException e){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
}
