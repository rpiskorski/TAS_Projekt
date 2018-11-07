package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.User;
import project.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;

    public User addUser(User user){

        return this.userRepository.save(user);
    }

    public void deleteUser(int id){
        this.userRepository.deleteById(id);
    }

    public User getUserById(int id){
        return this.userRepository.findById(id);
    }

    public Optional<User> getUsersByName(String name){
        return this.userRepository.findByName(name);
    }

    public List<User> getAllUsers(){
        return this.userRepository.findAll();
    }

}
