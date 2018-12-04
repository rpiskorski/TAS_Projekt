package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import project.model.User;
import project.repositories.UserRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

import static org.springframework.security.web.context.HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY;

@Service
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authManager;

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
