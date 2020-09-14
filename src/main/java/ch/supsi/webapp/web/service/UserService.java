package ch.supsi.webapp.web.service;

import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.repository.RoleRepository;
import ch.supsi.webapp.web.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> getAll(){
        return userRepository.findAll();
    }

    public User findById (String id){
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user){
        return userRepository.save(user);
    }

    public void delete (User user){
        userRepository.delete(user);
    }

}