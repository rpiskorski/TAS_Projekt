package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.model.Product;
import project.services.CategoryService;
import project.services.ProductService;


import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    //Add Product
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value="/products",method= RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> create(@RequestBody @Valid @NotNull Product product,
                                                     HttpServletRequest httpServletRequest)
    {
        Map<String,Object> map = new HashMap<>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        Product product1 = productService.addProduct(product);
        if(product1 == null){
            map.put("message","Failed to add product!");
            map.put("product","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }
        else {
            map.put("message","Product added successfully!");
            map.put("product",product1);
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.CREATED);
        }
    }
//*******************************************************************************************************************************
//    //Get All Products
//    @RequestMapping(value="/products",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map<String,Object>> getAllProducts(@RequestParam(value = "page",required = false) Integer pageNumber,
//                                                             HttpServletRequest httpServletRequest){
//
//        int numberOfProducts = this.productService.getNumberOfProducts();
//        int numberOfPages = this.productService.getNumberOfPages(numberOfProducts);
//
//        Map<String,Object> map = new HashMap<String,Object>();
//        List<Product> productList;
//        Object token = httpServletRequest.getAttribute("token");
//        map.put("token",token);
//        map.put("sumOfProducts",numberOfProducts);
//        map.put("sumOfPages",numberOfPages);
//
//        if(numberOfPages!=0) {
//            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
//                productList = productService.getAllProducts(PageRequest.of(pageNumber.intValue() - 1, 9));
//
//                map.put("listOfProducts",productList);
//
//                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
//            } else {
//                productList = productService.getAllProducts(PageRequest.of(0, 9));
//                map.put("listOfProducts",productList);
//
//                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
//            }
//
//        }
//        else{
//            map.put("listOfProducts","empty");
//            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
//        }
//
//    }
    //***************************************************************************************************************************



        //Get All Products
    @RequestMapping(value="/products",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getAllProducts(@RequestParam(value = "page",required = false) Integer pageNumber,
                                                             @RequestParam(value = "name",required = false) String name,
                                                             @RequestParam(value = "manuName",required = false) String manuName,
                                                             @RequestParam(value = "catId",required = false) Integer categoryId,
                                                             @RequestParam(value = "type",required = false) String type,
                                                             @RequestParam(value = "order",required = false) String order,
                                                             HttpServletRequest httpServletRequest){

        boolean sortFlag = false;

        if(name!=null && !name.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*"))
        {
            name=null;
        }
        if(manuName!=null && !manuName.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*$"))
        {
            manuName=null;
        }
        if(categoryId!=null){
            if(this.categoryService.getCategory(categoryId.intValue())==null){
                categoryId = null;
            }
        }
        if(type!=null) {
            if (type.contentEquals("rating")) {
                type = "avg_rating";
            } else if (type.contentEquals("name")) {
                type = "name";
            }
            else{
                type=null;
            }
        }
        if(order!=null) {
            if(order.contentEquals("asc")){
                order = "ASC";
            }else if(order.contentEquals("desc")){
                order = "DESC";
            }else{
                order=null;
            }
        }

        if(order!=null && type!=null){
            sortFlag = true;
        }

        Object token = httpServletRequest.getAttribute("token");
        Map<String,Object> map = this.productService.getProductsAdvancedSearch(name,manuName,categoryId,sortFlag,type,order,pageNumber);


        map.put("token",token);
        if(map.get("listOfProducts")=="empty"){
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
        }


    }









    //Delete Product
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/products/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> deleteProduct(@PathVariable int id,
                                                            HttpServletRequest httpServletRequest){
        Map<String,Object> map = new HashMap<String,Object>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);


        if(this.productService.getProductsById(id)!=null) {
            this.productService.deleteProduct(id);

            map.put("message","Product has been deleted");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        }
        else{
            map.put("message","Product does not exist!");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
        }
    }

    //Get All Products in Category
    @RequestMapping(value="/products/cat/{categoryid}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getProductsInCategory(@PathVariable int categoryid,
                                                                    @RequestParam(value = "page",required = false) Integer pageNumber,
                                                                    HttpServletRequest httpServletRequest)
    {
        int numberOfProductsInCategory=this.productService.getNumberOfProductsInCategory(categoryid);
        int numberOfPagesInCategory=this.productService.getNumberOfPages(numberOfProductsInCategory);


        Map<String,Object> map = new HashMap<String,Object>();
        List<Product> productList;

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        map.put("sumOfProducts",numberOfProductsInCategory);
        map.put("sumOfPages",numberOfPagesInCategory);

        if(numberOfPagesInCategory!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                productList = this.productService.getAllProductsInCategory(categoryid, PageRequest.of(pageNumber.intValue() - 1, 9));
                map.put("listOfProducts",productList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } else {

                productList = this.productService.getAllProductsInCategory(categoryid, PageRequest.of(0, 9));

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
    @RequestMapping(value="/products/sort/{categoryid}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getAllSortedProductsInCategory(@PathVariable int categoryid,
                                                                             @RequestParam(value = "page",required = false) Integer pageNumber,
                                                                             @RequestParam(value="type",required = true) String type,
                                                                             @RequestParam(value="order",required=true) String order,
                                                                             HttpServletRequest httpServletRequest){

        int numberOfProductsInCategory=this.productService.getNumberOfProductsInCategory(categoryid);
        int numberOfPagesInCategory=this.productService.getNumberOfPages(numberOfProductsInCategory);

        Map<String,Object> map = new HashMap<String,Object>();
        List<Product> productList;

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        map.put("sumOfProducts",numberOfProductsInCategory);
        map.put("sumOfPages",numberOfPagesInCategory);

        if(numberOfPagesInCategory!=0) {
            if (type.contentEquals("name")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                        productList = this.productService.getProductsOrderByNameInCategoryAsc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByNameInCategoryAsc(categoryid, PageRequest.of(0, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                        productList = this.productService.getProductsOrderByNameInCategoryDesc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByNameInCategoryDesc(categoryid, PageRequest.of(0, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                }

            } else if (type.contentEquals("rating")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                        productList = this.productService.getProductsOrderByRaitingInCategoryAsc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByRaitingInCategoryAsc(categoryid, PageRequest.of(0, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                        productList = this.productService.getProductsOrderByRaitingInCategoryDesc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByRaitingInCategoryDesc(categoryid, PageRequest.of(0, 9));
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
    @RequestMapping(value="/products/cat/{categoryid}/name/{name}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getProductsByNameInCategory(@PathVariable String name,
                                                                          @PathVariable Integer categoryid,
                                                                          @RequestParam(value = "page",required = false) Integer pageNumber,
                                                                          HttpServletRequest httpServletRequest)
        {

            int numberOfProducts = this.productService.getNumberOfProductsWithNameInCategory(categoryid.intValue(),name);
            int numberOfPages = this.productService.getNumberOfPages(numberOfProducts);

            Map<String,Object> map = new HashMap<String,Object>();
            List<Product> productList;

            Object token = httpServletRequest.getAttribute("token");
            map.put("token",token);

            map.put("sumOfProducts",numberOfProducts);
            map.put("sumOfPages",numberOfPages);

            if(numberOfPages!=0) {
                if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                    productList = this.productService.getProductsByNameInCategory(categoryid.intValue(),name, PageRequest.of(pageNumber.intValue() - 1, 9));
                    map.put("listOfProducts",productList);

                    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                } else {

                    productList = this.productService.getProductsByNameInCategory(categoryid.intValue(),name, PageRequest.of(0, 9));
                    map.put("listOfProducts",productList);

                    return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                }
            }else{

                map.put("listOfProducts","empty");
                return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
            }
        }
//************************************
    //Get All Products by manufacturer
    @RequestMapping(value="/products/manufacturer/{manu}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getProductsByManufacturer(@PathVariable String manu,
                                                                    @RequestParam(value = "page",required = false) Integer pageNumber,
                                                                    HttpServletRequest httpServletRequest)
    {
        int numberOfProductsByManufacturer=this.productService.getNumberOfProductsByManufacturer(manu);
        int numberOfPagesByManufacturer=this.productService.getNumberOfPages(numberOfProductsByManufacturer);


        Map<String,Object> map = new HashMap<String,Object>();
        List<Product> productList;

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        map.put("sumOfProducts",numberOfProductsByManufacturer);
        map.put("sumOfPages",numberOfPagesByManufacturer);

        if(numberOfPagesByManufacturer!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesByManufacturer) {

                productList = this.productService.getAllProductsByManufacturer(manu, PageRequest.of(pageNumber.intValue() - 1, 9));
                map.put("listOfProducts",productList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } else {

                productList = this.productService.getAllProductsByManufacturer(manu, PageRequest.of(0, 9));

                map.put("listOfProducts",productList);
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            }
        }
        else{

            map.put("listOfProducts","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
        }
    }


    //Get Products Ordered By Name Or Raiting By manufacturer
    @RequestMapping(value="/products/manufacturer/{manu}/sort",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getAllSortedProductsByManufacturer(@PathVariable String manu,
                                                                             @RequestParam(value = "page",required = false) Integer pageNumber,
                                                                             @RequestParam(value="type",required = true) String type,
                                                                             @RequestParam(value="order",required=true) String order,
                                                                             HttpServletRequest httpServletRequest){

        int numberOfProductsByManufacturer=this.productService.getNumberOfProductsByManufacturer(manu);
        int numberOfPagesByManufacturer=this.productService.getNumberOfPages(numberOfProductsByManufacturer);

        Map<String,Object> map = new HashMap<String,Object>();
        List<Product> productList;

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        map.put("sumOfProducts",numberOfProductsByManufacturer);
        map.put("sumOfPages",numberOfPagesByManufacturer);

        if(numberOfPagesByManufacturer!=0) {
            if (type.contentEquals("name")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesByManufacturer) {

                        productList = this.productService.getProductsOrderByNameByManufacturerAsc(manu, PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByNameByManufacturerAsc(manu, PageRequest.of(0, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesByManufacturer) {

                        productList = this.productService.getProductsOrderByNameByManufacturerDesc(manu, PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByNameByManufacturerDesc(manu, PageRequest.of(0, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                }

            } else if (type.contentEquals("rating")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesByManufacturer) {

                        productList = this.productService.getProductsOrderByRaitingByManufacturerAsc(manu, PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByRaitingByManufacturerAsc(manu, PageRequest.of(0, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesByManufacturer) {

                        productList = this.productService.getProductsOrderByRaitingByManufacturerDesc(manu, PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByRaitingByManufacturerDesc(manu, PageRequest.of(0, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                }

            }

        }

        map.put("listOfProducts","empty");
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);


    }
    //**********************************


        //Get Products By Name
    @RequestMapping(value="/products/name/{name}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getProductsByName(@PathVariable String name,
                                                                @RequestParam(value = "page",required = false) Integer pageNumber,
                                                                HttpServletRequest httpServletRequest) {

        int numberOfProducts = this.productService.getNumberOfProductsWithName(name);
        int numberOfPages = this.productService.getNumberOfPages(numberOfProducts);


        Map<String,Object> map = new HashMap<String,Object>();
        List<Product> productList;
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        map.put("sumOfProducts",numberOfProducts);
        map.put("sumOfPages",numberOfPages);

        if(numberOfPages!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                productList = this.productService.getProductsByName(name, PageRequest.of(pageNumber.intValue() - 1, 9));
                map.put("listOfProducts",productList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } else {

                productList = this.productService.getProductsByName(name, PageRequest.of(0, 9));
                map.put("listOfProducts",productList);

                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            }
        }else{

            map.put("listOfProducts","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
        }
    }

    //Get Products By Id

    @RequestMapping(value="/products/{id}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getProductsById(@PathVariable int id,
                                                              HttpServletRequest httpServletRequest){

        Map<String,Object> map = new HashMap<String,Object>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        Product product = this.productService.getProductsById(id);
        if(product!=null){
            map.put("product",product);
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
        }
        else{
            map.put("product","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }
    }

    //Get Products Ordered By Name
    @RequestMapping(value="/products/sort",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getAllSortedProducts(@RequestParam(value = "page",required = false) Integer pageNumber,
                                                                   @RequestParam(value="type",required = true) String type,
                                                                   @RequestParam(value="order",required=true) String order,
                                                                   HttpServletRequest httpServletRequest){

        int numberOfProducts = this.productService.getNumberOfProducts();
        int numberOfPages = this.productService.getNumberOfPages(numberOfProducts);

        Map<String,Object> map = new HashMap<String,Object>();
        List<Product> productList;

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        map.put("sumOfProducts",numberOfProducts);
        map.put("sumOfPages",numberOfPages);

        if(numberOfPages!=0) {
            if (type.contentEquals("name")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                        productList = this.productService.getProductsOrderByNameAsc(PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByNameAsc(PageRequest.of(0, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                        productList = this.productService.getProductsOrderByNameDesc(PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByNameDesc(PageRequest.of(0, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                }

            } else if (type.contentEquals("rating")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                        productList = this.productService.getProductsOrderByRaitingAsc(PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByRaitingAsc(PageRequest.of(0, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                        productList = this.productService.getProductsOrderByRaitingDesc(PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfProducts",productList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        productList = this.productService.getProductsOrderByRaitingDesc(PageRequest.of(0, 9));
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


