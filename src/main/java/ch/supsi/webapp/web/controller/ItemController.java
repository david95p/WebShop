package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.Category;
import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.service.CategoryService;
import ch.supsi.webapp.web.service.ItemService;
import ch.supsi.webapp.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
public class ItemController {
    @Autowired
    private ItemService itemService;
    @Autowired
    private UserService userService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping(value="/items", produces = MediaType.APPLICATION_JSON_VALUE )
    public List<Item> get() {
        return itemService.getAll();
    }

    @GetMapping(value="/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public  ResponseEntity<Item> get(@PathVariable int id) {
        Item item = itemService.findById(id);
        if (item != null){
            return new ResponseEntity<>(item, HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value="/items", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> post(@RequestBody Item item){
        if (item.getAuthor()==null || userService.findById(item.getAuthor().getUserName()) == null
                || item.getCategory() ==null || categoryService.findById(item.getCategory().getCatName())==null)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        Category thisCat = categoryService.findById(item.getCategory().getCatName());
        User thisUser = userService.findById(item.getAuthor().getUserName());
        item.setCategory(thisCat);
        item.setAuthor(thisUser);
        itemService.save(item);
        return new ResponseEntity<>(item, HttpStatus.CREATED);
    }

    @PutMapping(value="/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Item> put (@PathVariable int id, @RequestBody Item newitem ) {
        Item item = itemService.findById(id);
        if (item == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        item.setItemId(id);
        if (newitem.getTitle()!=null)
            item.setTitle(newitem.getTitle());
        if (newitem.getDescription()!=null)
            item.setDescription(newitem.getDescription());
        if (newitem.getAuthor()!=null)
            item.setAuthor(newitem.getAuthor());
        if (newitem.getCategory()!=null)
            item.setCategory(newitem.getCategory());
        if (newitem.getDate()!=null)
            item.setDate(newitem.getDate());
        if (newitem.getAdType()!=null)
            item.setAdType(newitem.getAdType());
        if (newitem.getImage()!=null)
            item.setImage(newitem.getImage());
        itemService.save(item);
        return new ResponseEntity<>(item, HttpStatus.OK);
    }

    @DeleteMapping(value = "/items/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete (@PathVariable int id ) {
        String success = "{\n" +
                " \"success\": \"true\"\n" +
                "}";
       Item item = itemService.findById(id);
        if (item== null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        itemService.delete(item);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }
}
