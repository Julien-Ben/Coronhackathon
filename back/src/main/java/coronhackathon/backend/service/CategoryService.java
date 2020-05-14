package coronhackathon.backend.service;

import coronhackathon.backend.entity.Category;
import coronhackathon.backend.entity.Challenge;
import coronhackathon.backend.entity.User;
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
        return getCategoryByName(name).getId();
    }

    public Category getCategory(long id) {
        return checkCategoryExists(categoryRepository.findById(id),"id", ""+id);
    }

    public Category getCategoryByName(String name) {
        return checkCategoryExists(categoryRepository.findByName(name),"name", ""+name);
    }

    public List<Category> allCategories() {
        return categoryRepository.findAll();
    }

    public long numberOfCategories() {
        return categoryRepository.count();
    }

    private Category checkCategoryExists(Optional<Category> oc, String name, String value){
        if(!oc.isPresent()){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "category with "+name+" : " + value + " not found");
        }
        return oc.get();
    }
}
