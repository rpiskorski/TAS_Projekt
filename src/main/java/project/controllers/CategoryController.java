package project.controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.model.Category;
import project.model.Product;
import project.services.CategoryService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    //ADD Category
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value="/categories",method= RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> create(@RequestBody @Valid @NotNull Category category,
                                                     HttpServletRequest httpServletRequest){

        Map<String,Object> map = new HashMap<>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        Category category1 =this.categoryService.addCategory(category);
        if(category1!=null) {
            map.put("message","Category added successfully!");
            map.put("category",category1);
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.CREATED);
        }else{
            map.put("message","Failed to add category!");
            map.put("category","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }
    }
    //Delete Category
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value="/categories/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> delete(@PathVariable int id,
                       HttpServletRequest httpServletRequest){

        Map<String,Object> map = new HashMap<>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        if(this.categoryService.getCategory(id)!=null){
            this.categoryService.deleteCategory(id);
            map.put("message","Category has been deleted");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
        }else{
            map.put("message","Category does not exist");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }


    }
    //GET Category by id
    @RequestMapping(value="/categories/{id}",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> get(@PathVariable int id,
                        HttpServletRequest httpServletRequest) {

        Map<String, Object> map = new HashMap<>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token", token);

        Category category = this.categoryService.getCategory(id);
        if (category != null) {
            map.put("category", category);
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        } else {
            map.put("category", "empty");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
        }
    }

    //GET ALL Categories
    @RequestMapping(value="/categories",method = RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getCategories(HttpServletRequest httpServletRequest){

        Map<String, Object> map = new HashMap<>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token", token);


        List<Category> categoryList;
        categoryList = this.categoryService.getCategories();
        if(categoryList!=null){
            map.put("listOfCategories",categoryList);
        }else{
            map.put("listOfCategories","empty");
        }



        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);

        }

    }

