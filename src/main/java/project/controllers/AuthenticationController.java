package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.config.TokenHelper;

import project.model.LoginForm;
import project.model.User;
import project.services.UserService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    TokenHelper tokenHelper;

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login",method = RequestMethod.POST)
    public ResponseEntity generateToken(@RequestBody LoginForm loginForm){
        System.out.println(loginForm.getName());
        final Authentication authentication = this.authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(loginForm.getName(),loginForm.getPassword()));


        SecurityContextHolder.getContext().setAuthentication(authentication);

        Optional<User> userFromDatabase = this.userService.getUsersByName(loginForm.getName());
        String token="";
        Map<String,Object> map = new HashMap<>();
        if(userFromDatabase.isPresent()){
            token = this.tokenHelper.generateToken(userFromDatabase.get());
            map.put("token",token);
            return ResponseEntity.ok(map);
        }else{

            map.put("message","Wrong login or password!");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }

    }

}
