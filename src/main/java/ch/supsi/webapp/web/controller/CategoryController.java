package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.Category;
import ch.supsi.webapp.web.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value="/categories", produces = MediaType.APPLICATION_JSON_VALUE )
    public List<Category> get() {
        return categoryService.getAll();
    }

    @GetMapping(value="/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> get(@PathVariable String id) {
        Category cat = categoryService.findById(id);
        if (cat!=null){
            return new ResponseEntity<>(cat, HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value="/categories", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> post(@RequestBody Category cat){
        if(cat.getCatName()!=null) {
            categoryService.save(cat);
            return new ResponseEntity<>(cat, HttpStatus.CREATED);
        } else
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
    }

    @PutMapping(value="/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Category> put (@PathVariable String id, @RequestBody Category newCat ) {
        Category cat = categoryService.findById(id);
        if (cat == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        Category catToAdd =cat;
        catToAdd.setCatName(id);
        if (newCat.getCatName()!=null)
            catToAdd.setCatName(newCat.getCatName());
        categoryService.save(catToAdd);
        return new ResponseEntity<>(catToAdd, HttpStatus.OK);
    }


    @DeleteMapping(value = "/categories/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete (@PathVariable String id ) {
        String success = "{\n" +
                " \"success\": \"true\"\n" +
                "}";
        Category cat = categoryService.findById(id);
        if (cat == null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        categoryService.delete(cat);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

}


