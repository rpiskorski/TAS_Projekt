package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import project.model.Category;
import project.model.Product;
import project.services.CategoryService;
import project.services.ProductService;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    //Add Product
    @RequestMapping(value="/product",method= RequestMethod.POST)
    public Product create(@RequestBody @Valid @NotNull Product product)
    {

        return productService.addProduct(product);
    }

    //Get All Products
    @RequestMapping(value="/products",method=RequestMethod.GET)
    public List<Product> getAllProducts(){
        return productService.getAllProducts();
    }

    //Delete Product
    @RequestMapping(value = "/product/{id}",method = RequestMethod.DELETE)
    public void deleteProduct(@PathVariable int id){
        this.productService.deleteProduct(id);

    }

    //Get All Products in Category
    @RequestMapping(value="/products/{categoryid}",method=RequestMethod.GET)
    public List<Product> getProductsInCategory(@PathVariable int categoryid)
    {
        //Category myCategory = this.categoryService.getCategory(categoryid);
        return this.productService.getAllProductsInCategory(categoryid);
    }

    //Get Products By Name

    @RequestMapping(value="/products/name/{name}",method=RequestMethod.GET)
    public List<Product> getProductsByName(@PathVariable String name){
        return this.productService.getProductsByName(name);
    }

//    //Get Products In Ascending Order Considering Raiting
//    @RequestMapping(value="/products/asc", method=RequestMethod.GET)
//    public List<Product> getAllByRaitingAsc(){
//        return this.productService.getAllByRaitingAsc();
//    }

    //Get Products In Descending Order Considering Raiting
//    @RequestMapping(value="/products/desc", method=RequestMethod.GET)
//    public List<Product> getAllByRaitingDesc(){
//        return this.productService.getAllByRaitingDesc();
//    }

}
