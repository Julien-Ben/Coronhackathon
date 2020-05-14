package coronhackathon.backend.controller;


import coronhackathon.backend.entity.Category;
import coronhackathon.backend.entity.Challenge;

import coronhackathon.backend.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
@RestController
public class CategoryController {
    @Autowired
    private CompletedService completedService;
    @Autowired
    private CategoryService categoryService;


    /**
     * @param id of the category
     * @return the category corresponding to the id
     */
    @RequestMapping(path = "/api/getCategory/{id}", method = RequestMethod.GET)
    public Category getCategory(@PathVariable long id) {
        return categoryService.getCategory(id);
    }

    /**
     * @param name of the category
     * @return the category corresponding to the name
     */
    @RequestMapping(path = "/api/getCategoryByName/{name}", method = RequestMethod.GET)
    public Category getCategoryByName(@PathVariable String name) {
        return categoryService.getCategoryByName(name);
    }

    /**
     * Returns a list with all the categories
     *
     * @param
     * @return a list with all the categories
     */
    @GetMapping("/api/allCategories")
    public List<Category> allCategories() {
        return categoryService.allCategories();
    }

    /**
     * Returns the number of categories
     *
     * @param
     * @return a long, the number of categories
     */
    @GetMapping("/api/numberOfCategories")
    public long numberOfCategories() {
        return categoryService.numberOfCategories();
    }


    /**
     * Returns all challenges completed by User in a certain category
     *
     * @param userId     Id of User
     * @param categoryId category of the challenges completed by the user
     * @return completed challenges of a certain category as a list
     */
    @RequestMapping(path = "/api/getCompletedByCat/{userId}/{categoryId}", method = RequestMethod.GET)
    public List<Challenge> getCompletedChallengesByCategory(@PathVariable long userId, @PathVariable long categoryId) {
        return completedService.getCompletedChallengesByCategory(userId, categoryId);
    }

    @RequestMapping(path = "/api/getMyCompletedByCat/{categoryId}", method = RequestMethod.GET)
    public List<Challenge> getCompletedChallengesByCategory(Principal principal, @PathVariable long categoryId) {
        return completedService.getCompletedChallengesByCategory(principal.getName(), categoryId);
    }

    /**
     * Returns all challenges completed by User in a certain category
     *
     * @param userId Id of User
     * @param name   category of the challenges completed by the user
     * @return completed challenges of a certain category as a list
     */
    @RequestMapping(path = "/api/getCompletedByCatName/{userId}/{name}", method = RequestMethod.GET)
    public List<Challenge> getCompletedChallengesByCategory(@PathVariable long userId, @PathVariable String name) {
        return completedService.getCompletedChallengesByCategory(userId, name);
    }

    @RequestMapping(path = "/api/getMyCompletedByCatName/{name}", method = RequestMethod.GET)
    public List<Challenge> getCompletedChallengesByCategory(Principal principal, @PathVariable String name) {
        return completedService.getCompletedChallengesByCategory(principal.getName(), name);
    }

}
