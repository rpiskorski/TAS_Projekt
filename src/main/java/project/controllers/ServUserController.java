package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    @RequestMapping(value="/services/{id}/comments",method=RequestMethod.POST)
    public ResponseEntity<String> createComment(@RequestBody @Nullable String comment, @RequestParam(value="rating",required = false) Integer rating, @PathVariable int id ){

        //Get current logged user
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser=userRepository.findOneByName(userDetails.getUsername());

        Serv s = this.servService.getServicesById(id);

        if(currentUser==null){

            return new ResponseEntity<String>("Log in first to add a comment!", HttpStatus.BAD_REQUEST);
        }
        else{

            if(s==null){
                return new ResponseEntity<String>("Service does not exist!", HttpStatus.BAD_REQUEST);
            }else {

                if (this.servUserService.checkIfExists(id, currentUser.getId())) {

                    return new ResponseEntity<String>("Your comment already exists!", HttpStatus.BAD_REQUEST);

                } else {


                    ServUser servUser = new ServUser();

                    if(rating==null){
                        if(comment==null){

                            return new ResponseEntity<String>("Add a comment or rating first!", HttpStatus.BAD_REQUEST);
                        }
                    }
                    else{
                        if (rating.intValue() >= 0 && rating.intValue()<=6) {
                            servUser.setRating(rating.intValue());
                        }else{
                            return new ResponseEntity<String>("Rating has to be equal or greater than 0 and equal or greater than 6!", HttpStatus.BAD_REQUEST);
                        }
                       }

                    servUser.setComment(comment);
                    servUser.setService(s);
                    servUser.setUserS(currentUser);
                    this.servUserService.add(servUser);
                    return new ResponseEntity<String>("Comment added successfully!", HttpStatus.CREATED);
                }
            }
        }
    }

    //Delete Comment
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value = "/services/{id}/comments/{commentId}",method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteComment(@PathVariable int commentId,@PathVariable int id)
    {
        //Get current logged user
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser=userRepository.findOneByName(userDetails.getUsername());

        if(currentUser==null){

            return new ResponseEntity<String>("Log in first to delete a comment!", HttpStatus.BAD_REQUEST);
        }
        else {
            if (this.servService.getServicesById(id) == null) {
                return new ResponseEntity<String>("Service does not exist!", HttpStatus.BAD_REQUEST);
            } else {
                if (this.servUserService.checkIfExists(commentId)) {

                    ServUser su = this.servUserService.getServUser(commentId);
                    int OwnerId = su.getUserS().getId();

                    if (currentUser.getId() == OwnerId || currentUser.getRole().getName().contentEquals("ADMIN")) {
                        this.servUserService.delete(commentId);
                        return new ResponseEntity<String>("Comment deleted successfully!", HttpStatus.OK);
                    } else {
                        return new ResponseEntity<String>("You don't have permission to delete this comment!", HttpStatus.OK);
                    }
                } else {
                    return new ResponseEntity<String>("Comment does not exist!", HttpStatus.NOT_FOUND);
                }
            }
        }
    }

    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value="/services/{id}/comments/{commentId}",method=RequestMethod.PUT)
    public ResponseEntity<String> editComment(@RequestBody @Nullable String comment, @RequestParam(value="rating",required = false) Integer rating, @PathVariable int commentId,@PathVariable int id) {

        if(this.servService.getServicesById(id)!=null && this.servUserService.editComment(comment,rating,commentId)){
            return new ResponseEntity<String>("Edited successfully!",HttpStatus.OK);
        }
        else{
            return new ResponseEntity<String>("Comment does not exist or you don't have permission!",HttpStatus.BAD_REQUEST);
        }

    }

    @RequestMapping(value="services/{id}/comments/{commentId}",method = RequestMethod.GET)
    public ResponseEntity<ServUser> getCommentsById(@PathVariable int id,@PathVariable int commentId){
        Serv service = this.servService.getServicesById(id);
        ServUser su = this.servUserService.getServUser(commentId);
        if(service!=null && su!=null){
            return new ResponseEntity<ServUser>(su,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<ServUser>(su,HttpStatus.BAD_REQUEST);
        }
    }




}
