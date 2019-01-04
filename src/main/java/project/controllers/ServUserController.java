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
    public ResponseEntity<Map<String,Object>> createComment(@RequestBody @Nullable String comment,
                                                            @RequestParam(value="rating",required = false) Integer rating,
                                                            @PathVariable int id ,
                                                            HttpServletRequest httpServletRequest){

        Map<String,Object> map = new HashMap<>();

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        //Get current logged user
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser=userRepository.findOneByName(userDetails.getUsername());

        Serv s = this.servService.getServicesById(id);

            if(s==null){
                map.put("message","Service does not exist!");

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
            }else {

                if (this.servUserService.checkIfExists(id, currentUser.getId())) {

                    map.put("message","Your comment already exists!");
                    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);

                } else {


                    ServUser servUser = new ServUser();

                    if(rating==null){
                        if(comment==null){

                            map.put("message","Add a comment or rating first!");
                            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
                        }
                    }
                    else{
                        if (rating.intValue() >= 0 && rating.intValue()<=6) {
                            servUser.setRating(rating.intValue());
                        }else{
                            map.put("message","Rating has to be equal or greater than 0 and equal or greater than 6!");
                            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
                        }
                       }

                    servUser.setComment(comment);
                    servUser.setService(s);
                    servUser.setUserS(currentUser);
                    this.servUserService.add(servUser);

                    map.put("message","Comment added successfully!");
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
                map.put("message","Service does not exist!");
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
            } else {
                if (this.servUserService.checkIfExists(commentId)) {

                    ServUser su = this.servUserService.getServUser(commentId);
                    int OwnerId = su.getUserS().getId();

                    if (currentUser.getId() == OwnerId || currentUser.getRole().getName().contentEquals("ADMIN")) {
                        this.servUserService.delete(commentId);

                        map.put("message","Comment deleted successfully!");
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {
                        map.put("message","You don't have permission to delete this comment!");
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else {
                    map.put("message","Comment does not exist!");
                    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.NOT_FOUND);
                }
            }
//        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value="/services/{id}/comments/{commentId}",method=RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> editComment(@RequestBody @Nullable String comment,
                                              @RequestParam(value="rating",required = false) Integer rating,
                                              @PathVariable int commentId,@PathVariable int id,
                                              HttpServletRequest httpServletRequest)
    {
        Map<String,Object> map = new HashMap<>();

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        if(this.servService.getServicesById(id)!=null && this.servUserService.editComment(comment,rating,commentId)){

            map.put("message","Edited successfully!");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
        }
        else{
            map.put("message","Comment does not exist or you don't have permission!");
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
