package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.model.Serv;
import project.model.ServUser;
import project.model.User;
import project.repositories.UserRepository;
import project.services.ServService;
import project.services.ServUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ServUserController {

    @Autowired
    private ServUserService servUserService;

    @Autowired
    private ServService servService;

    @Autowired
    private UserRepository userRepository;


    //Add Comment etc.
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value="/services/{id}/comments",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> createComment(@RequestBody @Valid @NotNull ServUser serviceUser,
                                                            //@RequestParam(value="rating",required = false) Integer rating,
                                                            @PathVariable int id ,
                                                            HttpServletRequest httpServletRequest){

        String comment = serviceUser.getComment();
        int rating = serviceUser.getRating();

        Map<String,Object> map = new HashMap<>();

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        //Get current logged user
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser=userRepository.findOneByName(userDetails.getUsername());

        Serv s = this.servService.getServicesById(id);

            if(s==null){
                map.put("message","Usługa nie istnieje");

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
            }else {

                if (this.servUserService.checkIfExists(id, currentUser.getId())) {

                    map.put("message","Twój komentarz już istnieje");
                    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);

                } else {


                    ServUser servUser = new ServUser();


                        if (rating == 1 || rating == 2 || rating == 3 || rating == 4 || rating == 5 || rating == 6) {
                            servUser.setRating(rating);
                        }else{
                            map.put("message","Ocena musi być wieksza od 0 i mniejsza bądź równa 6");
                            map.put("comment","empty");
                            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
                        }


                    servUser.setComment(comment);
                    servUser.setService(s);
                    servUser.setUserS(currentUser);
                    ServUser serviceUser1 = this.servUserService.add(servUser);

                    map.put("message","Komentarz został pomyślnie dodany");
                    map.put("comment",serviceUser1);
                    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.CREATED);
                }
            }

    }

    //Delete Comment
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value = "/services/{id}/comments/{commentId}",method=RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> deleteComment(@PathVariable int commentId,
                                                            @PathVariable int id,
                                                            HttpServletRequest httpServletRequest)
    {
        Map<String,Object> map = new HashMap<>();

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);


        //Get current logged user
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser=userRepository.findOneByName(userDetails.getUsername());

            if (this.servService.getServicesById(id) == null) {
                map.put("message","Usługa nie istnieje");
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
            } else {
                if (this.servUserService.checkIfExists(commentId)) {

                    ServUser su = this.servUserService.getServUser(commentId);
                    int OwnerId = su.getUserS().getId();

                    if (currentUser.getId() == OwnerId || currentUser.getRole().getName().contentEquals("ADMIN")) {
                        this.servUserService.delete(commentId);

                        map.put("message","Komentarz został usunięty");
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {
                        map.put("message","Nie masz uprawnień by usunąć ten komentarz");
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else {
                    map.put("message","Komentarz nie istnieje");
                    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_FOUND);
                }
            }
//        }
    }

    //Edit
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value="/services/{id}/comments/{commentId}",method=RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> editComment(@RequestBody @Valid @NotNull ServUser serviceUser,
                                              //@RequestParam(value="rating",required = false) Integer rating,
                                              @PathVariable int commentId,@PathVariable int id,
                                              HttpServletRequest httpServletRequest)
    {

        Map<String,Object> map = new HashMap<>();

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        int rating = 0;

        String comment = serviceUser.getComment();
        if(serviceUser.getRating() == 1 || serviceUser.getRating() == 2 || serviceUser.getRating() == 3 || serviceUser.getRating() == 4 || serviceUser.getRating() == 5 || serviceUser.getRating() == 6){
            rating = serviceUser.getRating();
//            System.out.println(rating);
        }else{
            map.put("message","Ocena musi być wieksza od 0 i mniejsza bądź równa 6");
            map.put("comment","empty");
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
        }

        ServUser su = this.servUserService.editComment(comment,rating,commentId);

        if(this.servService.getServicesById(id)!=null && su!=null){

            map.put("message","Komentarz został pomyślnie edytowany");
            map.put("comment",su);
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
        }
        else{
            map.put("message","Komentarz nie istnieje albo nie masz uprawnień do jego edycji");
            map.put("comment","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }

    }



    //Get Comment by id
    @RequestMapping(value="services/{id}/comments/{commentId}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getCommentsById(@PathVariable int id,
                                                              @PathVariable int commentId,
                                                              HttpServletRequest httpServletRequest){
        Map<String,Object> map = new HashMap<>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        Serv service = this.servService.getServicesById(id);
        ServUser su = this.servUserService.getServUser(commentId);

        if(service!=null && su!=null){
            map.put("comment",su);
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
        }
        else{
            map.put("comment","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }
    }

    //Get all comments
    @RequestMapping(value="services/{id}/comments",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getComments(@RequestParam(value = "page",required = false) Integer pageNumber,
                                                          @PathVariable int id,
                                                          HttpServletRequest httpServletRequest){
        Serv service = this.servService.getServicesById(id);

        int numberOfPages = this.servUserService.getNumberOfPagesForService(id);
        int numberOfServUsers = this.servUserService.getNumberOfServiceUsersForService(id);

        Map<String,Object> map = new HashMap<String,Object>();
        List<ServUser> serviceUsersList;

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        map.put("sumOfComments",numberOfServUsers);
        map.put("sumOfPages",numberOfPages);


        if(numberOfPages!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                serviceUsersList = this.servUserService.getAllServiceUsers(id, PageRequest.of(pageNumber.intValue() - 1, 10));
                map.put("listOfComments",serviceUsersList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } else {

                serviceUsersList = this.servUserService.getAllServiceUsers(id, PageRequest.of(0, 10));
                map.put("listOfComments",serviceUsersList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            }
        }else{

            map.put("listOfComments","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
        }

    }




}
