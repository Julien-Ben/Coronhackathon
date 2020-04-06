package coronhackathon.backend.service;

import coronhackathon.backend.entity.Category;
import coronhackathon.backend.entity.Challenge;
import coronhackathon.backend.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;


    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public long getIdFromName(String name) {
        Optional<Category> oc = categoryRepository.findByName(name);
        if (!oc.isPresent())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category with name : " + name + " does not exist");
        return oc.get().getId();
    }

    public Optional<Category> getCategory(long id) {
        return categoryRepository.findById(id);
    }

    public Optional<Category> getCategoryByName(String name) {
        return categoryRepository.findByName(name);
    }

    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }

    public long numberOfCategories() {
        return categoryRepository.count();
    }

}
