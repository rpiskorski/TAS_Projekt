package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.model.Product;
import project.model.ProductUser;
import project.model.User;
import project.repositories.UserRepository;
import project.services.ProductService;
import project.services.ProductUserService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductUserController {

    @Autowired
    private ProductUserService productUserService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserRepository userRepository;


    //Add Comment etc.
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value="/products/{id}",method=RequestMethod.POST)
    public ResponseEntity<String> createComment(@RequestBody @NotNull String comment,@RequestParam(value="rating",required = false) int rating,@PathVariable int id ){

        //Get current logged user
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser=userRepository.findOneByName(userDetails.getUsername());

        if(currentUser==null){

            return new ResponseEntity<String>("Log in first to add a comment!", HttpStatus.BAD_REQUEST);
        }
        else{

            if(this.productUserService.checkIfExists(id,currentUser.getId())) {

                return new ResponseEntity<String>("Your comment already exists!", HttpStatus.BAD_REQUEST);

            }else {

                ProductUser productUser = new ProductUser();
                productUser.setComment(comment);
                productUser.setRating(rating);
                productUser.setProduct(this.productService.getProductsById(id));
                productUser.setUserP(currentUser);
                this.productUserService.add(productUser);
                return new ResponseEntity<String>("Comment added successfully!", HttpStatus.CREATED);
            }
        }
    }

    //Delete Comment
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value = "/products/{id}/{commentId}",method=RequestMethod.DELETE)
    public ResponseEntity<String> deleteComment(@PathVariable int commentId)
    {
        //Get current logged user
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser=userRepository.findOneByName(userDetails.getUsername());

        if(currentUser==null){

            return new ResponseEntity<String>("Log in first to delete a comment!", HttpStatus.BAD_REQUEST);
        }
        else {
            if (this.productUserService.checkIfExists(commentId)) {

                ProductUser pu = this.productUserService.getProductUser(commentId);
                int OwnerId = pu.getUserP().getId();

                if(currentUser.getId()==OwnerId || currentUser.getRole().getName().contentEquals("ADMIN")) {
                    this.productUserService.delete(commentId);
                    return new ResponseEntity<String>("Comment deleted successfully!", HttpStatus.OK);
                }else{
                    return new ResponseEntity<String>("You don't have permission to delete this comment!", HttpStatus.OK);
                }
            } else {
                return new ResponseEntity<String>("Comment does not exist!", HttpStatus.NOT_FOUND);
            }
        }
    }

//
//    @RequestMapping(value = "/productuser",method = RequestMethod.POST)
//    public ResponseEntity<ProductUser> create(@RequestBody @Valid @NotNull ProductUser productUser){
//
//        ProductUser pu = this.productUserService.add(productUser);
//        if(pu!=null) {
//            return new ResponseEntity<ProductUser>(pu, HttpStatus.CREATED);
//        }
//        else{
//            return new ResponseEntity<ProductUser>(pu, HttpStatus.BAD_REQUEST);
//        }
//    }
}
