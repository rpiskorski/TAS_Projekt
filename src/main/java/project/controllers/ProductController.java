package project.controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.model.Category;
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
        String pname = product.getName();
        String pmanuName= product.getManufacturer_name();
        int catId = product.getCat().getId();

        Map<String,Object> map = new HashMap<>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        if(this.categoryService.getCategory(catId)==null || !pname.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*$") || pname.length()>50
                || !pmanuName.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*$") || pmanuName.length()>50){
            map.put("message","Wprowadzono niepoprawne dane");
            map.put("product","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }


        Product product1 = productService.addProduct(product);
        if(product1 == null){
            map.put("message","Nie udało się wprowadzić produktu");
            map.put("product","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }
        else {
            map.put("message","Produkt został pomyślnie dodany");
            map.put("product",product1);
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.CREATED);
        }
    }

    //Edit Product
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value="/products/{id}",method= RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> edit(@RequestBody @Valid @NotNull Product product,
                                                     @PathVariable int id,
                                                     HttpServletRequest httpServletRequest)
    {

        String pname = product.getName();
        String pmanuName= product.getManufacturer_name();
        int catId = product.getCat().getId();

        Map<String,Object> map = new HashMap<>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        Category c = this.categoryService.getCategory(catId);
        if(c==null || !pname.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*$") || pname.length()>50
                || !pmanuName.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*$") || pmanuName.length()>50){
            map.put("message","Wprowadzono niepoprawne dane");
            map.put("product","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }


        Product product1 = productService.editProduct(pname,pmanuName,c,id);
        if(product1 == null){
            map.put("message","Nie udało się edytować produktu");
            map.put("product","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }
        else {
            map.put("message","Produkt został pomyślnie edytowany");
            map.put("product",product1);
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.CREATED);
        }
    }


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

        if(name!=null && !name.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*$"))
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

            map.put("message","Produkt został usunięty");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        }
        else{
            map.put("message","Produkt nie istnieje");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.BAD_REQUEST);
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




    }


