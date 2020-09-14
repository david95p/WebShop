package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.Category;
import ch.supsi.webapp.web.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    private CategoryRepository categoryRepository;

    public List<Category> getAll(){
        return categoryRepository.findAll();
    }

    public Category findById (String id){
        return categoryRepository.findById(id).orElse(null);
    }

    public Category save(Category cat){
        return categoryRepository.save(cat);

    }

    public void delete (Category cat){
        categoryRepository.delete(cat);
    }
}
