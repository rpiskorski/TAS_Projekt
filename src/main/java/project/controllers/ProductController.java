package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import project.model.Category;
import project.model.Product;
import project.model.ProductUser;
import project.services.CategoryService;
import project.services.ProductService;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.ws.Response;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;


    //Add Product
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value="/products",method= RequestMethod.POST)
    public ResponseEntity<String> create(@RequestBody @Valid @NotNull Product product)
    {
        Product product1 = productService.addProduct(product);
        if(product1 == null){
            return new ResponseEntity<String>("Failed to add product!",HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<String>("Product added successfully!", HttpStatus.CREATED);
        }
    }

    //Get All Products

    @RequestMapping(value="/products",method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllProducts(@RequestParam(value = "page",required = false) Integer pageNumber){

        int numberOfPages = this.productService.getNumberOfPages();
        int numberOfProducts = this.productService.getNumberOfProducts();
        Map<String,Object> map = new HashMap<String,Object>();
        List<Product> productList;

        map.put("sumOfProducts",numberOfProducts);
        map.put("sumOfPages",numberOfPages);

        if(numberOfPages!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                productList = productService.getAllProducts(PageRequest.of(pageNumber.intValue() - 1, 10));

                map.put("listOfProducts",productList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } else {
                productList = productService.getAllProducts(PageRequest.of(0, 10));
                map.put("listOfProducts",productList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            }

        }
        else{
            map.put("listOfProducts","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
        }

    }

    //Delete Product
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/products/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteProduct(@PathVariable int id){

        this.productService.deleteProduct(id);
        return new ResponseEntity<String>("Product has been deleted",HttpStatus.OK);

    }

    //Get All Products in Category
    @RequestMapping(value="/products/cat/{categoryid}",method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getProductsInCategory(@PathVariable int categoryid,@RequestParam(value = "page",required = false) Integer pageNumber)
    {

        int numberOfPagesInCategory=this.productService.getNumberOfPagesInCategory(categoryid);
        int numberOfProductsInCategory=this.productService.getNumberOfProductsInCategory(categoryid);

        Map<String,Object> map = new HashMap<String,Object>();
        List<Product> productList;

        map.put("sumOfProducts",numberOfProductsInCategory);
        map.put("sumOfPages",numberOfPagesInCategory);

        if(numberOfPagesInCategory!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                productList = this.productService.getAllProductsInCategory(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10));
                map.put("listOfProducts",productList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } else {

                productList = this.productService.getAllProductsInCategory(categoryid, PageRequest.of(0, 10));

                map.put("listOfProducts",productList);
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            }
        }
        else{

            map.put("listOfProducts","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
        }
    }


    //Get Products Ordered By Name Or Raiting In Category
    @RequestMapping(value="/products/sort/{categoryid}",method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllSortedProductsInCategory(@PathVariable int categoryid,@RequestParam(value = "page",required = false) Integer pageNumber,@RequestParam(value="type",required = true) String type,
                                              @RequestParam(value="order",required=true) String order){

        int numberOfPagesInCategory=this.productService.getNumberOfPagesInCategory(categoryid);
        int numberOfProductsInCategory=this.productService.getNumberOfProductsInCategory(categoryid);

        Map<String,Object> map = new HashMap<String,Object>();
        List<Product> productList;

        map.put("sumOfProducts",numberOfProductsInCategory);
        map.put("sumOfPages",numberOfPagesInCategory);

        if(numberOfPagesInCategory!=0) {
            if (type.contentEquals("name")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                        productList = this.productService.getProductsOrderByNameInCategoryAsc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByNameInCategoryAsc(categoryid, PageRequest.of(0, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                        productList = this.productService.getProductsOrderByNameInCategoryDesc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByNameInCategoryDesc(categoryid, PageRequest.of(0, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                }

            } else if (type.contentEquals("raiting")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                        productList = this.productService.getProductsOrderByRaitingInCategoryAsc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByRaitingInCategoryAsc(categoryid, PageRequest.of(0, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                        productList = this.productService.getProductsOrderByRaitingInCategoryDesc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByRaitingInCategoryDesc(categoryid, PageRequest.of(0, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                }

            }

        }

            map.put("listOfProducts","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);


    }

    //Get Products By Name In Category
    @RequestMapping(value="/products/cat/{categoryid}/name/{name}",method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getProductsByNameInCategory(@PathVariable String name,@PathVariable Integer categoryid,@RequestParam(value = "page",required = false) Integer pageNumber)
        {
            int numberOfPages = this.productService.getNumberOfPagesWithNameInCategory(categoryid.intValue(),name);
            int numberOfProducts = this.productService.getNumberOfProductsWithNameInCategory(categoryid.intValue(),name);

            Map<String,Object> map = new HashMap<String,Object>();
            List<Product> productList;

            map.put("sumOfProducts",numberOfProducts);
            map.put("sumOfPages",numberOfPages);

            if(numberOfPages!=0) {
                if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                    productList = this.productService.getProductsByNameInCategory(categoryid.intValue(),name, PageRequest.of(pageNumber.intValue() - 1, 10));
                    map.put("listOfProducts",productList);

                    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                } else {

                    productList = this.productService.getProductsByNameInCategory(categoryid.intValue(),name, PageRequest.of(0, 10));
                    map.put("listOfProducts",productList);

                    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                }
            }else{

                map.put("listOfProducts","empty");
                return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
            }
        }




        //Get Products By Name
    @RequestMapping(value="/products/name/{name}",method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getProductsByName(@PathVariable String name,@RequestParam(value = "page",required = false) Integer pageNumber) {

        int numberOfPages = this.productService.getNumberOfPagesWithName(name);
        int numberOfProducts = this.productService.getNumberOfProductsWithName(name);

        Map<String,Object> map = new HashMap<String,Object>();
        List<Product> productList;

        map.put("sumOfProducts",numberOfProducts);
        map.put("sumOfPages",numberOfPages);

        if(numberOfPages!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                productList = this.productService.getProductsByName(name, PageRequest.of(pageNumber.intValue() - 1, 10));
                map.put("listOfProducts",productList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } else {

                productList = this.productService.getProductsByName(name, PageRequest.of(0, 10));
                map.put("listOfProducts",productList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            }
        }else{

            map.put("listOfProducts","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
        }
    }

    //Get Products By Id

    @RequestMapping(value="/products/{id}",method=RequestMethod.GET)
    public ResponseEntity<Object> getProductsById(@PathVariable int id){
        Product product = this.productService.getProductsById(id);
        if(product!=null){
            return new ResponseEntity<Object>(product,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Object>("Product does not exist!",HttpStatus.BAD_REQUEST);
        }
    }

    //Get Products Ordered By Name
    @RequestMapping(value="/products/sort",method=RequestMethod.GET)
    public ResponseEntity<Map<String,Object>> getAllSortedProducts(@RequestParam(value = "page",required = false) Integer pageNumber,
            @RequestParam(value="type",required = true) String type,
                                              @RequestParam(value="order",required=true) String order){

        int numberOfPages = this.productService.getNumberOfPages();
        int numberOfProducts = this.productService.getNumberOfProducts();
        Map<String,Object> map = new HashMap<String,Object>();
        List<Product> productList;

        map.put("sumOfProducts",numberOfProducts);
        map.put("sumOfPages",numberOfPages);

        if(numberOfPages!=0) {
            if (type.contentEquals("name")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                        productList = this.productService.getProductsOrderByNameAsc(PageRequest.of(pageNumber.intValue() - 1, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByNameAsc(PageRequest.of(0, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                        productList = this.productService.getProductsOrderByNameDesc(PageRequest.of(pageNumber.intValue() - 1, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByNameDesc(PageRequest.of(0, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                }

            } else if (type.contentEquals("raiting")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                        productList = this.productService.getProductsOrderByRaitingAsc(PageRequest.of(pageNumber.intValue() - 1, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByRaitingAsc(PageRequest.of(0, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                        productList = this.productService.getProductsOrderByRaitingDesc(PageRequest.of(pageNumber.intValue() - 1, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByRaitingDesc(PageRequest.of(0, 10));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                }

            }
        }

            map.put("listOfProducts","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
        }



    }


