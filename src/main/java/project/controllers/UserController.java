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

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;
import java.security.Principal;
import java.util.*;

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
    public ResponseEntity<Map<String,Object>> addUser(@RequestBody @Valid @NotNull User user){

        Map<String,Object> map = new HashMap<>();

       Optional<User> users = this.userService.getUsersByName(user.getName());
        if(!users.isPresent()){
            User registerUser = new User();

            //Set name
            registerUser.setName(user.getName());

            //set enabled
            registerUser.setEnabled(true);

            //Set Role
            Role role = this.roleService.getRoleByName("USER");
            registerUser.setRole(role);

            //Set Password
            String pass= this.bCryptPasswordEncoder.encode(user.getPassword());
            registerUser.setPassword(pass);

            this.userService.addUser(registerUser);
            map.put("message","Account created successfully");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.CREATED);
        }
        else{
            map.put("message","Your login has already been taken");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);

        }

    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/users/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<Map<String,Object>> deleteUser(@PathVariable int id,HttpServletRequest request){
        User user = this.userService.getUserById(id);
        Map<String,Object> map = new HashMap<>();
        Object token = request.getAttribute("token");

        map.put("token",token);


        if(user==null){
            map.put("message","This user does not exist");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }
        else {
            this.userService.deleteUser(id);
            map.put("message","User deleted successfully");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.ACCEPTED);
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value="/users",method = RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllUsers(HttpServletRequest request){
        Map<String,Object> map = new HashMap<>();
        List<User> users = userService.getAllUsers();
        Object token = request.getAttribute("token");

        map.put("token",token);
        map.put("users",users);
        if(users.isEmpty()){
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NO_CONTENT);
        }
        else{
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
        }
    }


}
