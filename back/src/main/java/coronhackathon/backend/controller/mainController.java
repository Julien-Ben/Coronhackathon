package coronhackathon.backend.controller;

import coronhackathon.backend.entity.User;
import coronhackathon.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class mainController {
    @Autowired
    private UserService userService;

    @GetMapping("/")
    public String hello(@RequestParam(defaultValue = "Moi") String name){ //Ajouter ?name=Votreprenom à la fin de l'URL
        return "Vous êtes bien sur l'application Spring de "+name;
    }

    @GetMapping("/api/allUsers")
    public List<User> allUsers(){
        return userService.getAllUsers();
    }

    @PostMapping("api/addUser")
    public void addUser(@RequestBody User user) {
        userService.insert(user);
    }
}