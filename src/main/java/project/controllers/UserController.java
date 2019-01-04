package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;
import project.model.LoginForm;
import project.model.Role;
import project.model.User;
import project.services.RoleService;
import project.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/register",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> addUser(@RequestBody @Valid @NotNull LoginForm user,
                                                      HttpServletRequest request,
                                                      HttpServletResponse httpServletResponse){


        Map<String,Object> map = new HashMap<>();

        Object token = request.getAttribute("token");
        map.put("token",token);

        if(!user.getName().matches("^[a-zA-Z]{3,}[0-9]+") || !user.getPassword().matches("^[a-zA-Z]{3,}[0-9]+")){

            map.put("message","Niepoprawne login lub hasło");
            httpServletResponse.setStatus(460);
            return map;
        }

        if(user.getName().length()<5 || user.getName().length()>15 || user.getPassword().length()<5 || user.getPassword().length()>10){
            map.put("message","Niepoprawna długość loginu lub hasła");

            httpServletResponse.setStatus(461);
            return map;

        }


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
            map.put("message","Zostałeś pomyślnie zarejestrowany");
            httpServletResponse.setStatus(201);
            return map;

        }
        else{
            map.put("message","Wprowadzony użytkownik już istnieje");
            httpServletResponse.setStatus(462);
            return map;


        }


    }

    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/users/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
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
