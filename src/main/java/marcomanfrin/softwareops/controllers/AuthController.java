package marcomanfrin.softwareops.controllers;

import marcomanfrin.softwareops.DTO.LoginDTO;
import marcomanfrin.softwareops.DTO.LoginRespDTO;
import marcomanfrin.softwareops.DTO.NewUserDTO;
import marcomanfrin.softwareops.DTO.NewUserRespDTO;
import marcomanfrin.softwareops.exceptions.ValidationException;
import marcomanfrin.softwareops.services.AuthService;
import marcomanfrin.softwareops.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public LoginRespDTO login(@RequestBody LoginDTO body) {
        return new LoginRespDTO(this.authService.checkCredentialsAndGenerateToken(body));
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public NewUserRespDTO createUser(@RequestBody @Validated NewUserDTO body, BindingResult validationResult) {

        if (validationResult.hasErrors()) {

            throw new ValidationException(validationResult.getFieldErrors().stream().map(fieldError -> fieldError.getDefaultMessage()).toList());
        }
        return this.userService.saveNewUser(body);
    }

    //**GET** `/auth/me`
}
