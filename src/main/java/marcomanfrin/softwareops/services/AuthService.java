package marcomanfrin.softwareops.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import marcomanfrin.softwareops.DTO.LoginDTO;
import marcomanfrin.softwareops.entities.User;
import marcomanfrin.softwareops.exceptions.UnauthorizedException;
import marcomanfrin.softwareops.security.JWTTools;

import java.util.Objects;
import java.util.Optional;

@Service
public class AuthService {
	@Autowired
	private UserService userService;
	@Autowired
	private JWTTools jwtTools;
//	@Autowired
//	private PasswordEncoder bcrypt;

	public String checkCredentialsAndGenerateToken(LoginDTO body) {
		Optional<User> found = this.userService.getUserByEmail(body.email());

//		if (bcrypt.matches(body.password(), found.getPasswordHash())) {
//			return jwtTools.createToken(found);
//		}
        if(Objects.equals(found.get().getPasswordHash(), body.password())){
            return jwtTools.createToken(found.orElse(null));
        }
        else {
			throw new UnauthorizedException("Credenziali errate");
		}
	}
}
