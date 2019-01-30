package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import project.config.TokenHelper;

import project.model.LoginForm;
import project.model.User;
import project.services.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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

    @PreAuthorize("isAnonymous()")
    @RequestMapping(value = "/login",method = RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public Map<String,Object> generateToken(@RequestBody LoginForm loginForm,
                                            HttpServletRequest request,
                                            HttpServletResponse httpServletResponse){


        String token=null;
        Map<String,Object> map = new HashMap<>();

        Optional<User> userFromDatabase = this.userService.getUsersByName(loginForm.getName());

         Authentication authentication =null;


        if(userFromDatabase.isPresent()){

            authentication = this.authenticationManager
                    .authenticate(new UsernamePasswordAuthenticationToken(loginForm.getName(),loginForm.getPassword()));

            SecurityContextHolder.getContext().setAuthentication(authentication);

            token = this.tokenHelper.generateToken(userFromDatabase.get());
            map.put("token",token);
            map.put("message","Jesteś zalogowany jako "+loginForm.getName());
            httpServletResponse.setStatus(200);
            return map;
        }
            else{
            map.put("message","Niepoprawne login lub hasło");
            map.put("token",token);
            httpServletResponse.setStatus(460);
            return map;
        }



    }

}
