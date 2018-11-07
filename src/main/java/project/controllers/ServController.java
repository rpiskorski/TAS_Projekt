package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value="/services",method= RequestMethod.POST)
    public ResponseEntity<Serv> create(@RequestBody @Valid @NotNull Serv service)
    {
        Serv service1 = servService.addService(service);
        if(service1 == null){
            return new ResponseEntity<Serv>(service1,HttpStatus.BAD_REQUEST);
        }
        else {
            return new ResponseEntity<Serv>(service1, HttpStatus.CREATED);
        }

    }

    //Get All Services
    @RequestMapping(value="/services",method=RequestMethod.GET)
    public ResponseEntity<List<Serv>> getAllServices(@RequestParam(value = "page",required = false) Integer pageNumber){
        int numberOfPages = this.servService.getNumberOfPages();
        if(numberOfPages!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                return new ResponseEntity<List<Serv>>(servService.getAllServices(PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Serv>>(servService.getAllServices(PageRequest.of(0, 10)), HttpStatus.OK);
            }
        }
            else{
                List<Serv> nullServiceList=null;
                return new ResponseEntity<List<Serv>>(nullServiceList,HttpStatus.NOT_FOUND);
            }
    }

    //Delete Service
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/services/{id}",method = RequestMethod.DELETE)
    public ResponseEntity<String> deleteService(@PathVariable int id){
        this.servService.deleteService(id);
        return new ResponseEntity<String>("Service has been deleted",HttpStatus.OK);


    }

    //Get All Services in Category
    @RequestMapping(value="/services/cat/{categoryid}",method=RequestMethod.GET)
    public ResponseEntity<List<Serv>> getServicesInCategory(@PathVariable int categoryid,@RequestParam(value = "page",required = false) Integer pageNumber)
    {
        int numberOfPagesInCategory=this.servService.getNumberOfPagesInCategory(categoryid);
        if(numberOfPagesInCategory!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                return new ResponseEntity<List<Serv>>(this.servService.getAllServicesInCategory(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Serv>>(this.servService.getAllServicesInCategory(categoryid, PageRequest.of(0, 10)), HttpStatus.OK);
            }
        }
        else{
            List<Serv> nullServiceList=null;
            return new ResponseEntity<List<Serv>>(nullServiceList,HttpStatus.NOT_FOUND);
        }
    }


    //Get Services Ordered By Name Or Raiting In Category
    @RequestMapping(value="/services/sort/{categoryid}",method=RequestMethod.GET)
    public ResponseEntity<List<Serv>> getAllSortedServicesInCategory(@PathVariable int categoryid,
                                                                     @RequestParam(value = "page",required = false) Integer pageNumber,
                                                     @RequestParam(value="type",required = true) String type, @RequestParam(value="order",required=true) String order){
        int numberOfPagesInCategory=this.servService.getNumberOfPagesInCategory(categoryid);
        if(numberOfPagesInCategory!=0) {
            if (type.contentEquals("name")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByNameInCategoryAsc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByNameInCategoryAsc(categoryid, PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByNameInCategoryDesc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByNameInCategoryDesc(categoryid, PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                }

            } else if (type.contentEquals("raiting")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByRaitingInCategoryAsc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByRaitingInCategoryAsc(categoryid, PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByRaitingInCategoryDesc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByRaitingInCategoryDesc(categoryid, PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                }

            }

        }

        List<Serv> nullServiceList=null;
        return new ResponseEntity<List<Serv>>(nullServiceList,HttpStatus.NOT_FOUND);

    }

    //Get Service By Name

    @RequestMapping(value="/services/name/{name}",method=RequestMethod.GET)
    public ResponseEntity<List<Serv>> getServicesByName(@PathVariable String name,@RequestParam(value = "page",required = false) Integer pageNumber){

        int numberOfPages = this.servService.getNumberOfPagesWithName(name);
        if(numberOfPages!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                return new ResponseEntity<List<Serv>>(this.servService.getServicesByName(name, PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
            } else {
                return new ResponseEntity<List<Serv>>(this.servService.getServicesByName(name, PageRequest.of(0, 10)), HttpStatus.OK);
            }
        }else{
            List<Serv> nullServiceList=null;
            return new ResponseEntity<List<Serv>>(nullServiceList,HttpStatus.NOT_FOUND);
        }
    }


    //Get Service By Id
    @RequestMapping(value="/services/{id}",method=RequestMethod.GET)
    public ResponseEntity<Serv> getServicesById(@PathVariable int id){
        Serv service = this.servService.getServicesById(id);
        if(service!=null){
            return new ResponseEntity<Serv>(service,HttpStatus.OK);
        }
        else{
            return new ResponseEntity<Serv>(service,HttpStatus.BAD_REQUEST);
        }
    }



    //Get Services Ordered By Name
    @RequestMapping(value="/services/sort",method=RequestMethod.GET)
    public ResponseEntity<List<Serv>> getAllSortedServices(@RequestParam(value = "page",required = false) Integer pageNumber,@RequestParam(value="type",required = true) String type,
                                              @RequestParam(value="order",required=true) String order){

        int numberOfPages = this.servService.getNumberOfPages();
        if(numberOfPages!=0) {
            if (type.contentEquals("name")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByNameAsc(PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByNameAsc(PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByNameDesc(PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByNameDesc(PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                }

            } else if (type.contentEquals("raiting")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByRaitingAsc(PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByRaitingAsc(PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByRaitingDesc(PageRequest.of(pageNumber.intValue() - 1, 10)), HttpStatus.OK);
                    } else {
                        return new ResponseEntity<List<Serv>>(this.servService.getServicesOrderByRaitingDesc(PageRequest.of(0, 10)), HttpStatus.OK);
                    }
                }

            }
        }
        List<Serv> nullServiceList=null;
        return new ResponseEntity<List<Serv>>(nullServiceList,HttpStatus.NOT_FOUND);
    }

}
