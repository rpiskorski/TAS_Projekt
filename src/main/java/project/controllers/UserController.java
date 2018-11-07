package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import project.model.Role;
import project.model.User;
import project.services.RoleService;
import project.services.UserService;

import javax.validation.Valid;
import javax.validation.constraints.Null;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")

@RestController
@RequestMapping("/api")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private RoleService roleService;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @RequestMapping(value = "/register",method = RequestMethod.POST)
    //ResponseEntity zamiast RedirectView
    public ResponseEntity<String> addUser(@Valid @RequestBody User user){


       Optional<User> users = this.userService.getUsersByName(user.getName());
        if(!users.isPresent()){

            //Set Role
            Role role = this.roleService.getRoleByName("USER");
            user.setRole(role);

            //Set Password
            String pass= this.bCryptPasswordEncoder.encode(user.getPassword());
            user.setPassword(pass);

            this.userService.addUser(user);
            return new ResponseEntity<String>("Account Created Successfully",HttpStatus.CREATED);
        }
        else{

            return new ResponseEntity<String>("Your Login Has Already Been Taken",HttpStatus.BAD_REQUEST);

        }

    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/users/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteUser(@PathVariable int id){
        User user = this.userService.getUserById(id);
        if(user==null){
            return new ResponseEntity<String>("This User Does Not Exist",HttpStatus.BAD_REQUEST);
        }
        else {
            this.userService.deleteUser(id);
            return new ResponseEntity<String>("User Deleted",HttpStatus.ACCEPTED);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value="/users",method = RequestMethod.GET)
    public ResponseEntity<List<User>> getAllUsers(){
        List<User> users = userService.getAllUsers();
        if(users.isEmpty()){
            return new ResponseEntity<List<User>>(HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<List<User>>(users,HttpStatus.OK);
        }
    }


//    @RequestMapping(value="/login",method = RequestMethod.POST)
//    public ResponseEntity<String> login(@Valid @RequestBody User user){
//
//
//    }
}
