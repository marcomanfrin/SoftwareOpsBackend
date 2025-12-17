package marcomanfrin.softwareops.controllers;

import marcomanfrin.softwareops.entities.User;
import marcomanfrin.softwareops.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {
    @Autowired
    private UserService usersService;

    @GetMapping
    public List<User> getUsers() {
        return this.usersService.getAllUsers();
    }
}
