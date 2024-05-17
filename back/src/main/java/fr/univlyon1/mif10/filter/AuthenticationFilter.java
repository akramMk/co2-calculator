package fr.univlyon1.mif10.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;

import com.auth0.jwt.exceptions.JWTVerificationException;
import fr.univlyon1.mif10.util.JwtHelper;
import java.io.IOException;

@Component
@WebFilter
public class AuthenticationFilter extends HttpFilter {
    private static final String[] WHITELIST = {"/", "/authenticate","/index.html", "/hello", "/db",
            "/users", "/users/", "/login", "/logout" , "/api-docs", "/api-docs.yaml",
            "/user/user", "/swagger-ui.html", "/swagger-ui/"};


    @Override
    protected void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        // Permet de retrouver la fin de l'URL (après l'URL du contexte) -> indépendant de l'URL de déploiement
        String url = request.getRequestURI().replace(request.getContextPath(), "");

        // 1) Laisse passer les URLs ne nécessitant pas d'authentification
        for(String tempUrl: WHITELIST) {
            //if(url.equals(tempUrl) ) { TODO: Uncomment this line
                chain.doFilter(request, response);
                return;
           //} TODO: Uncomment this line
        }

        // 2) Traite les requêtes qui doivent être authentifiées
        // Note :
        //   le paramètre false dans request.getSession(false) permet de récupérer null si la session n'est pas déjà créée.
        //   Sinon, l'appel de la méthode getSession() la crée automatiquement.
        // 2) Traite les requêtes qui doivent être authentifiées
        String token = request.getHeader("Authorization"); // Example: Header "Authorization: Bearer your_token_here"
        if (token == null || !token.startsWith("Bearer ")) {
            System.out.println("Token missing !");
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED,
                    "Token is missing. Vous devez vous connecter pour accéder au site.");
            return;
        } else {
            try {
                String JWT = token.replace("Bearer ", "");

                // Validate the token using the TodosM1if03JwtHelper
                if (JwtHelper.verifyToken(JWT, request.getHeader("Origin")) != null) {
                    // Assuming the token contains user information, you can set it as a request
                    // attribute
//                        request.setAttribute("token", token.substring(7));
                    request.setAttribute("token",JWT);
                    chain.doFilter(request, response);
                    return;
                }

            } catch (JWTVerificationException e) {
                // Token is invalid
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Token is invalid. Vous devez vous connecter pour accéder au site.");
                return;
            }

        }
        System.out.println("Tentative blockée!");
        // 3) Bloque les autres requêtes
        response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Vous devez vous connecter pour accéder au site.");
    }
}
