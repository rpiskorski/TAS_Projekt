package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import project.model.Serv;
import project.services.ServService;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ServController {

    @Autowired
    private ServService servService;

    //Add Service
    @RequestMapping(value="/services",method= RequestMethod.POST)
    public Serv create(@RequestBody @Valid @NotNull Serv service)
    {

        return this.servService.addService(service);
    }

    //Get All Services
    @RequestMapping(value="/services",method=RequestMethod.GET)
    public List<Serv> getAllServices(){
        return this.servService.getAllServices();
    }

    //Delete Service
    @RequestMapping(value = "/services/{id}",method = RequestMethod.DELETE)
    public void deleteService(@PathVariable int id){
        this.servService.deleteService(id);

    }

    //Get All Services in Category
    @RequestMapping(value="/services/cat/{categoryid}",method=RequestMethod.GET)
    public List<Serv> getServicesInCategory(@PathVariable int categoryid)
    {
        //Category myCategory = this.categoryService.getCategory(categoryid);
        return this.servService.getAllServicesInCategory(categoryid);
    }


    //Get Services Ordered By Name Or Raiting In Category
    @RequestMapping(value="/services/sort/{categoryid}",method=RequestMethod.GET)
    public List<Serv> getAllSortedServicesInCategory(@PathVariable int categoryid,
                                                     @RequestParam(value="type",required = true) String type,
                                                        @RequestParam(value="order",required=true) String order){
        if(type.contentEquals("name")){

            if(order.contentEquals("asc")){
                return this.servService.getServicesOrderByNameInCategoryAsc(categoryid);
            }else if(order.contentEquals("desc")){
                return this.servService.getServicesOrderByNameInCategoryDesc(categoryid);
            }

        }
        else if(type.contentEquals("raiting")){

            if(order.contentEquals("asc")){
                return this.servService.getServicesOrderByRaitingInCategoryAsc(categoryid);
            }else if(order.contentEquals("desc")){
                return this.servService.getServicesOrderByRaitingInCategoryDesc(categoryid);
            }
        }
        return null;

    }

    //Get Service By Name

    @RequestMapping(value="/services/name/{name}",method=RequestMethod.GET)
    public List<Serv> getServicesByName(@PathVariable String name){
        return this.servService.getServicesByName(name);
    }


    //Get Service By Id
    @RequestMapping(value="/services/{id}",method=RequestMethod.GET)
    public Serv getServicesById(@PathVariable int id){
        return this.servService.getServicesById(id);
    }



    //Get Services Ordered By Name
    @RequestMapping(value="/services/sort",method=RequestMethod.GET)
    public List<Serv> getAllSortedServices(@RequestParam(value="type",required = true) String type,
                                              @RequestParam(value="order",required=true) String order){
        if(type.contentEquals("name")){

            if(order.contentEquals("asc")){
                return this.servService.getServicesOrderByNameAsc();
            }else if(order.contentEquals("desc")){
                return this.servService.getServicesOrderByNameDesc();
            }

        }
        else if(type.contentEquals("raiting")){

            if(order.contentEquals("asc")){
                return this.servService.getServicesOrderByRaitingAsc();
            }else if(order.contentEquals("desc")){
                return this.servService.getServicesOrderByRaitingDesc();
            }
        }
        return null;
//        return productService.getAllProducts();
    }

}
