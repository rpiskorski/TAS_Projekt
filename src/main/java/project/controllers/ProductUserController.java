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
import project.model.Product;
import project.model.ProductUser;
import project.model.User;
import project.repositories.UserRepository;
import project.services.ProductService;
import project.services.ProductUserService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @RequestMapping(value="/products/{id}/comments",method=RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> createComment(@RequestBody @Nullable String comment,
                                                @RequestParam(value="rating",required = false) Integer rating,
                                                @PathVariable int id,
                                                HttpServletRequest httpServletRequest){


        Map<String,Object> map = new HashMap<>();

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        //Get current logged user
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser=userRepository.findOneByName(userDetails.getUsername());

        Product p = this.productService.getProductsById(id);



            if(p==null){
                map.put("message","Product does not exist!");
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
            }else {

                if (this.productUserService.checkIfExists(id, currentUser.getId())) {
                    map.put("message","Your comment already exists!");
                    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
                } else {

                    ProductUser productUser = new ProductUser();
                    if(rating==null){
                        if(comment == null){
                            map.put("message","Add a comment or rating first!");
                            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
                        }
                        }
                    else {
                        if (rating.intValue() >= 0 && rating.intValue()<=6) {
                            productUser.setRating(rating.intValue());
                        }else{
                            map.put("message","Rating has to be equal or greater than 0 and equal or greater than 6!");
                            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
                        }
                    }
                    productUser.setComment(comment);
                    productUser.setProduct(p);
                    productUser.setUserP(currentUser);
                    this.productUserService.add(productUser);
                    map.put("message","Comment added successfully!");
                    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.CREATED);
                }
            }
//        }
    }

    //Delete Comment
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value = "/products/{id}/comments/{commentId}",method=RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
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


            if (this.productService.getProductsById(id) == null) {
                map.put("message","Product does not exist!");
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.BAD_REQUEST);
            } else {
                if (this.productUserService.checkIfExists(commentId)) {

                    ProductUser pu = this.productUserService.getProductUser(commentId);
                    int OwnerId = pu.getUserP().getId();

                    if (currentUser.getId() == OwnerId || currentUser.getRole().getName().contentEquals("ADMIN")) {
                        this.productUserService.delete(commentId);
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
        }




    //Edit
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value="/products/{id}/comments/{commentId}",method=RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> editComment(@RequestBody @Nullable String comment,
                                                          @RequestParam(value="rating",required = false) Integer rating,
                                                          @PathVariable int commentId,
                                                          @PathVariable int id,
                                                          HttpServletRequest httpServletRequest) {

        Map<String,Object> map = new HashMap<>();

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        if(this.productService.getProductsById(id)!=null && this.productUserService.editComment(comment,rating,commentId)){
            map.put("message","Edited successfully!");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
        }
        else{
            map.put("message","Comment does not exist or you don't have permission!");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }
   }

    //Get comment by id
    @RequestMapping(value="products/{id}/comments/{commentId}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getCommentsById(@PathVariable int id,
                                                              @PathVariable int commentId,
                                                              HttpServletRequest httpServletRequest){
        Map<String,Object> map = new HashMap<>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        Product product = this.productService.getProductsById(id);
        ProductUser pu = this.productUserService.getProductUser(commentId);
        if(product!=null && pu!=null){
            map.put("comment",pu);
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
        }
        else{
            map.put("comment","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }
    }

    //Get all comments
    @RequestMapping(value="products/{id}/comments",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getComments(@RequestParam(value = "page",required = false) Integer pageNumber,
                                                          @PathVariable int id,
                                                          HttpServletRequest httpServletRequest){
        Product product = this.productService.getProductsById(id);


        int numberOfPages = this.productUserService.getNumberOfPagesForProduct(id);
        int numberOfProductUsers = this.productUserService.getNumberOfProductUsersForProduct(id);


        Map<String,Object> map = new HashMap<String,Object>();
        List<ProductUser> productUsersList;

        map.put("sumOfComments",numberOfProductUsers);
        map.put("sumOfPages",numberOfPages);

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);


        if(numberOfPages!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                productUsersList = this.productUserService.getAllProductUsers(id, PageRequest.of(pageNumber.intValue() - 1, 10));
                map.put("listOfComments",productUsersList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } else {

                productUsersList = this.productUserService.getAllProductUsers(id, PageRequest.of(0, 10));
                map.put("listOfComments",productUsersList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            }
        }else{

            map.put("listOfComments","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
        }

    }
}
