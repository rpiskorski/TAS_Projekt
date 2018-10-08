package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.model.Category;
import project.services.CategoryService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {


    @Autowired
    private CategoryService categoryService;

    //ADD Category
    @RequestMapping(value="/category",method= RequestMethod.POST)
    public Category create(@RequestBody @Valid @NotNull Category category){
        return this.categoryService.addCategory(category);
    }
    //Delete Category
    @RequestMapping(value="/category/{id}",method = RequestMethod.DELETE)
    public void delete(@PathVariable int id){
        this.categoryService.deleteCategory(id);
    }
    //GET Category by id
    @RequestMapping(value="/category/{id}",method = RequestMethod.GET)
    public Category get(@PathVariable int id){
        return this.categoryService.getCategory(id);
    }

    //GET ALL Categories
    @RequestMapping(value="/categories",method = RequestMethod.GET)
    public List<Category> getCategories(){
        return this.categoryService.getCategories();
    }
}
