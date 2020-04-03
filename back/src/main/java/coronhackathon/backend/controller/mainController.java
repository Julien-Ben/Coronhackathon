package coronhackathon.backend.controller;

import coronhackathon.backend.entity.User;
import coronhackathon.backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/api/addUser")
    /**
     * test with
     * curl -X POST localhost:8080/api/addUser -H 'Content-type:application/json' -d '{"username": "John Doe"}'
     *
     * security config modifications were needed to allow post requests. See dedicated file.
     */
    public void addUser(@RequestBody User user) {
        userService.insert(user);
    }
}