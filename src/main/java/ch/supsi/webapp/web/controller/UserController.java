package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.Role;
import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.service.RoleService;
import ch.supsi.webapp.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;

    @GetMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE )
    public List<User> get() {
        return userService.getAll();
    }

    @GetMapping(value="/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> get(@PathVariable String id) {
        User user = userService.findById(id);
        if (user!=null){
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else  {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping(value="/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> post(@RequestBody User user){
        if(user.getUserName()== null || user.getRole()==null || roleService.findById(user.getRole().getRoleName())==null) {
            return new ResponseEntity<>( HttpStatus.BAD_REQUEST);
        } else{
            Role currRole= roleService.findById(user.getRole().getRoleName());
            user.setRole(currRole);
            userService.save(user);
            return new ResponseEntity<>(user, HttpStatus.CREATED);
        }
    }

    @PutMapping(value="/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> put (@PathVariable String id, @RequestBody User newUser ) {
        User user = userService.findById(id);
        if (user==null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        User userToAdd =user;
        userToAdd.setUserName(id);
        if (newUser.getRole()!=null)
            userToAdd.setRole(newUser.getRole());
        userService.save(userToAdd);
        return new ResponseEntity<>(userToAdd, HttpStatus.OK);
    }


    @DeleteMapping(value = "/users/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> delete (@PathVariable String id ) {
        String success = "{\n" +
                " \"success\": \"true\"\n" +
                "}";
        User user = userService.findById(id);
        if (user==null)
            return new ResponseEntity<>( HttpStatus.NOT_FOUND);
        userService.delete(user);
        return new ResponseEntity<>(success, HttpStatus.OK);
    }

}
