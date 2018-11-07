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
import project.services.CategoryService;
import project.services.ProductService;


import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.xml.ws.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;


    //Add Product
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value="/products",method= RequestMethod.POST)
    public ResponseEntity<Product> create(@RequestBody @Valid @NotNull Product product)
    {
        Product product1 = productService.addProduct(product);
        if(product1 == null){
            return new ResponseEntity<Product>(product1,HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<Product>(product1, HttpStatus.CREATED);
        }
    }

    //Get All Products
    @RequestMapping(value="/products",method=RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(value = "page",required = false) Integer pageNumber){

        int numberOfPages = this.productService.getNumberOfPages();
        if(numberOfPages!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                return new ResponseEntity<List<Product>>(productService.getAllProducts(PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Product>>(productService.getAllProducts(PageRequest.of(0, 10)), HttpStatus.OK);
            }
//        else if(pageNumber==null){
//            return new ResponseEntity<List<Product>>(productService.getAllProducts(PageRequest.of(0,10)),HttpStatus.OK);
//        }
//        else{
//            HttpHeaders headers = new HttpHeaders();
//            //headers.add("FirstPage", "/products");
//            headers.setContentType(MediaType.APPLICATION_JSON_UTF8);
//            HttpEntity<Object> httpEntity = new HttpEntity<Object>(headers);
//            RestTemplate restTemplate = new RestTemplate();
//            ResponseEntity<List<Product>> responseEntity = restTemplate.exchange("http://localhost:8080/api/products",HttpMethod.GET,httpEntity, new ParameterizedTypeReference<List<Product>>(){});
//            return responseEntity;
//        }
        }
        else{
            List<Product> nullProductList=null;
            return new ResponseEntity<List<Product>>(nullProductList,HttpStatus.NOT_FOUND);
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
    public ResponseEntity<List<Product>> getProductsInCategory(@PathVariable int categoryid,@RequestParam(value = "page",required = false) Integer pageNumber)
    {

        int numberOfPagesInCategory=this.productService.getNumberOfPagesInCategory(categoryid);
        if(numberOfPagesInCategory!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                return new ResponseEntity<List<Product>>(this.productService.getAllProductsInCategory(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Product>>(this.productService.getAllProductsInCategory(categoryid, PageRequest.of(0, 10)), HttpStatus.OK);
            }
        }
        else{
            List<Product> nullProductList=null;
            return new ResponseEntity<List<Product>>(nullProductList,HttpStatus.NOT_FOUND);
        }
    }


    //Get Products Ordered By Name Or Raiting In Category
    @RequestMapping(value="/products/sort/{categoryid}",method=RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllSortedProductsInCategory(@PathVariable int categoryid,@RequestParam(value = "page",required = false) Integer pageNumber,@RequestParam(value="type",required = true) String type,
                                              @RequestParam(value="order",required=true) String order){

        int numberOfPagesInCategory=this.productService.getNumberOfPagesInCategory(categoryid);
        if(numberOfPagesInCategory!=0) {
            if (type.contentEquals("name")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByNameInCategoryAsc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByNameInCategoryAsc(categoryid, PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByNameInCategoryDesc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByNameInCategoryDesc(categoryid, PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                }

            } else if (type.contentEquals("raiting")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByRaitingInCategoryAsc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByRaitingInCategoryAsc(categoryid, PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByRaitingInCategoryDesc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByRaitingInCategoryDesc(categoryid, PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                }

            }

        }

            List<Product> nullProductList=null;
            return new ResponseEntity<List<Product>>(nullProductList,HttpStatus.NOT_FOUND);


    }

    //Get Products By Name

    @RequestMapping(value="/products/name/{name}",method=RequestMethod.GET)
    public ResponseEntity<List<Product>> getProductsByName(@PathVariable String name,@RequestParam(value = "page",required = false) Integer pageNumber) {

        int numberOfPages = this.productService.getNumberOfPagesWithName(name);
        if(numberOfPages!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                return new ResponseEntity<List<Product>>(this.productService.getProductsByName(name, PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Product>>(this.productService.getProductsByName(name, PageRequest.of(0, 10)), HttpStatus.OK);
            }
        }else{
            List<Product> nullProductList=null;
            return new ResponseEntity<List<Product>>(nullProductList,HttpStatus.NOT_FOUND);
        }
    }

    //Get Products By Id

    @RequestMapping(value="/products/{id}",method=RequestMethod.GET)
    public ResponseEntity<Product> getProductsById(@PathVariable int id){
        Product product = this.productService.getProductsById(id);
        if(product!=null){
            return new ResponseEntity<Product>(product,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Product>(product,HttpStatus.BAD_REQUEST);
        }
    }

    //Get Products Ordered By Name
    @RequestMapping(value="/products/sort",method=RequestMethod.GET)
    public ResponseEntity<List<Product>> getAllSortedProducts(@RequestParam(value = "page",required = false) Integer pageNumber,
            @RequestParam(value="type",required = true) String type,
                                              @RequestParam(value="order",required=true) String order){

        int numberOfPages = this.productService.getNumberOfPages();
        if(numberOfPages!=0) {
            if (type.contentEquals("name")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByNameAsc(PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByNameAsc(PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByNameDesc(PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByNameDesc(PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                }

            } else if (type.contentEquals("raiting")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByRaitingAsc(PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByRaitingAsc(PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByRaitingDesc(PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Product>>(this.productService.getProductsOrderByRaitingDesc(PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                }

            }
        }
            List<Product> nullProductList=null;
            return new ResponseEntity<List<Product>>(nullProductList,HttpStatus.NOT_FOUND);
        }




    }


