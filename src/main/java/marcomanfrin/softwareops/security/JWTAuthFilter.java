package marcomanfrin.softwareops.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import marcomanfrin.softwareops.entities.User;
import marcomanfrin.softwareops.exceptions.UnauthorizedException;
import marcomanfrin.softwareops.services.UserService;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Component
public class JWTAuthFilter extends OncePerRequestFilter {
	private final JWTTools jwtTools;
	private final UserService userService;

    public JWTAuthFilter(JWTTools jwtTools, UserService userService) {
        this.jwtTools = jwtTools;
        this.userService = userService;
    }

    @Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
		try {
            // AUTHENTICATION

			String authorizationHeader = request.getHeader("Authorization");

			if (authorizationHeader == null || !authorizationHeader.startsWith("Bearer "))
				throw new UnauthorizedException("Token mancante o non nel formato corretto");

			String accessToken = authorizationHeader.replace("Bearer ", "");

			jwtTools.verifyToken(accessToken);

            filterChain.doFilter(request, response);

            // AUTHORIZATION

			UUID userId = jwtTools.getIDFromToken(accessToken);
			User found = this.userService.getUserById(userId).get();

			Authentication authentication = new UsernamePasswordAuthenticationToken(
                    found,
                    null,
                    found.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);

			filterChain.doFilter(request, response);

		} catch (UnauthorizedException ex) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, ex.getMessage());
		} catch (Exception ex) {
			response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Problemi col token!");
		}

	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return new AntPathMatcher().match("/auth/**", request.getServletPath());
	}
}
