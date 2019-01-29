package project.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import project.model.Category;
import project.model.Serv;
import project.services.CategoryService;
import project.services.ServService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/api")
public class ServController {

    @Autowired
    private ServService servService;

    @Autowired
    private CategoryService categoryService;

    //Add Service
    @PreAuthorize("hasAnyAuthority('ADMIN','USER')")
    @RequestMapping(value="/services",method= RequestMethod.POST,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> create(@RequestBody @Valid @NotNull Serv service,
                                                     HttpServletRequest httpServletRequest)
    {

        String sname = service.getName();
        String sownerName= service.getOwner_name();
        String slocalization= service.getLocalization();
        int catId = service.getCat().getId();

        Map<String,Object> map = new HashMap<>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        if(this.categoryService.getCategory(catId)==null || !sname.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*$") || sname.length()>50
                || !sownerName.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*(Sp\\. z o\\. o\\.)$") || sownerName.length()>50
                || !slocalization.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ]+|\\s)+(ul\\.|os\\.|al\\.){1}\\s?[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ]+|\\s)+\\s?([1-9]{1}[0-9]*|[1-9]{1}[0-9]*\\/[1-9]{1}[0-9]*)")
                || slocalization.length()>60){

            //TODO regex do localization aby pasowala ul. ( po kolei w nawiasie z czego am sie skladac)
            //TODO edit  - dodac pole w productach owner id - ignore properties - controller edit taki jak dodawania  +
            //TODO sprawdzenie czy uzytkwnik jest adminem lub ownerem (jak w komentarzach) i czy produkt ma oceny -
            map.put("message","Wprowadzono niepoprawne dane");
            map.put("service","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }


        Serv service1 = servService.addService(service);
        if(service1 == null){
            map.put("message","Failed to add service!");
            map.put("service","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }
        else {
            map.put("message","Service added successfully!");
            map.put("service",service1);
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.CREATED);
        }

    }

    //Edit Service
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value="/services/{id}",method= RequestMethod.PUT,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> edit(@RequestBody @Valid @NotNull Serv service,
                                                     @PathVariable int id,
                                                     HttpServletRequest httpServletRequest)
    {

        String sname = service.getName();
        String sownerName= service.getOwner_name();
        String slocalization= service.getLocalization();
        int catId = service.getCat().getId();

        Map<String,Object> map = new HashMap<>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        Category c = this.categoryService.getCategory(catId);
        if(c==null || !sname.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*$") || sname.length()>50
                || !sownerName.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*(Sp\\. z o\\. o\\.)$") || sownerName.length()>50
                || !slocalization.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ]+|\\s)+(ul\\.|os\\.|al\\.){1}\\s?[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ]+|\\s)+\\s?([1-9]{1}[0-9]*|[1-9]{1}[0-9]*\\/[1-9]{1}[0-9]*)")
                || slocalization.length()>60){


            map.put("message","Wprowadzono niepoprawne dane");
            map.put("service","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }


        Serv service1 = servService.editService(sname,sownerName,slocalization,c,id);
        if(service1 == null){
            map.put("message","Nie udało się edytować usługi");
            map.put("service","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }
        else {
            map.put("message","Usługa została pomyślnie edytowana");
            map.put("service",service1);
            return new ResponseEntity<Map<String,Object>>(map, HttpStatus.CREATED);
        }

    }

//    //Get All Services
//
//    @RequestMapping(value="/services",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<Map<String,Object>> getAllServices(@RequestParam(value = "page",required = false) Integer pageNumber,
//                                                             HttpServletRequest httpServletRequest){
//
//        int numberOfServices = this.servService.getNumberOfServices();
//        int numberOfPages = this.servService.getNumberOfPages(numberOfServices);
//
//        Map<String,Object> map = new HashMap<String,Object>();
//        List<Serv> serviceList;
//        Object token = httpServletRequest.getAttribute("token");
//        map.put("token",token);
//        map.put("sumOfServices",numberOfServices);
//        map.put("sumOfPages",numberOfPages);
//
//        if(numberOfPages!=0) {
//            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
//                serviceList = servService.getAllServices(PageRequest.of(pageNumber.intValue() - 1, 9));
//
//                map.put("listOfServices",serviceList);
//                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
//            } else {
//                serviceList = servService.getAllServices(PageRequest.of(0, 9));
//                map.put("listOfServices",serviceList);
//                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
//            }
//        }
//            else{
//                map.put("listOfServices","empty");
//                return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
//            }
//    }

    //Get All Services
    @RequestMapping(value="/services",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getAllServices(@RequestParam(value = "page",required = false) Integer pageNumber,
                                                             @RequestParam(value = "name",required = false) String name,
                                                             @RequestParam(value = "ownerName",required = false) String ownerName,
                                                             @RequestParam(value = "localization",required = false) String localization,
                                                             @RequestParam(value = "catId",required = false) Integer categoryId,
                                                             @RequestParam(value = "type",required = false) String type,
                                                             @RequestParam(value = "order",required = false) String order,
                                                             HttpServletRequest httpServletRequest){

        boolean sortFlag = false;

        if(name!=null && !name.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*$"))
        {
            name=null;
        }
        if(ownerName!=null && !ownerName.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*$"))
        {
            ownerName=null;
        }
        if(localization!=null && !localization.matches("^[a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]{1}([a-zA-ZążśźęćńółĄŻŚŹĘĆŃÓŁ0-9]+|\\s{1,2})*$"))
        {
            localization=null;
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
        Map<String,Object> map = this.servService.getServicesAdvancedSearch(name,ownerName,localization,categoryId,sortFlag,type,order,pageNumber);


        map.put("token",token);
        if(map.get("listOfServices")=="empty"){
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
        }else{
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
        }


    }



    //Delete Service
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @RequestMapping(value = "/services/{id}",method = RequestMethod.DELETE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> deleteService(@PathVariable int id,
                                                            HttpServletRequest httpServletRequest){

        Map<String,Object> map = new HashMap<String,Object>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        if(this.servService.getServicesById(id)!=null) {
            this.servService.deleteService(id);
            map.put("message","Service has been deleted");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        }
        else{
            map.put("message","Service does not exist!");
            return new ResponseEntity<Map<String, Object>>(map, HttpStatus.OK);
        }

    }




    //Get All Services in Category
    @RequestMapping(value="/services/cat/{categoryid}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getServicesInCategory(@PathVariable int categoryid,
                                                            @RequestParam(value = "page",required = false) Integer pageNumber,
                                                            HttpServletRequest httpServletRequest)
    {

        int numberOfServicesInCategory=this.servService.getNumberOfServicesInCategory(categoryid);
        int numberOfPagesInCategory=this.servService.getNumberOfPages(numberOfServicesInCategory);

        Map<String,Object> map = new HashMap<String,Object>();
        List<Serv> serviceList;

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        map.put("sumOfServices",numberOfServicesInCategory);
        map.put("sumOfPages",numberOfPagesInCategory);

        if(numberOfPagesInCategory!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                serviceList = this.servService.getAllServicesInCategory(categoryid, PageRequest.of(pageNumber.intValue() - 1, 9));
                map.put("listOfServices",serviceList);
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } else {
                serviceList = this.servService.getAllServicesInCategory(categoryid, PageRequest.of(0, 9));

                map.put("listOfServices",serviceList);
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            }
        }
        else{
            map.put("listOfServices","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
        }
    }



    //Get Services Ordered By Name Or Raiting In Category
    @RequestMapping(value="/services/sort/{categoryid}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getAllSortedServicesInCategory(@PathVariable int categoryid,
                                                                     @RequestParam(value = "page",required = false) Integer pageNumber,
                                                                     @RequestParam(value="type",required = true) String type,
                                                                     @RequestParam(value="order",required=true) String order,
                                                                     HttpServletRequest httpServletRequest){

        int numberOfServicesInCategory=this.servService.getNumberOfServicesInCategory(categoryid);
        int numberOfPagesInCategory=this.servService.getNumberOfPages(numberOfServicesInCategory);

        Map<String,Object> map = new HashMap<String,Object>();
        List<Serv> serviceList;

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        map.put("sumOfProducts",numberOfServicesInCategory);
        map.put("sumOfPages",numberOfPagesInCategory);
        if(numberOfPagesInCategory!=0) {
            if (type.contentEquals("name")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {

                        serviceList = this.servService.getServicesOrderByNameInCategoryAsc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfServices",serviceList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {

                        serviceList = this.servService.getServicesOrderByNameInCategoryAsc(categoryid, PageRequest.of(0, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {
                        serviceList = this.servService.getServicesOrderByNameInCategoryDesc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfServices",serviceList);

                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {
                        serviceList = this.servService.getServicesOrderByNameInCategoryDesc(categoryid, PageRequest.of(0, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                }

            } else if (type.contentEquals("rating")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {
                        serviceList = this.servService.getServicesOrderByRaitingInCategoryAsc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {
                        serviceList = this.servService.getServicesOrderByRaitingInCategoryAsc(categoryid, PageRequest.of(0, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPagesInCategory) {
                        serviceList = this.servService.getServicesOrderByRaitingInCategoryDesc(categoryid, PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {
                        serviceList = this.servService.getServicesOrderByRaitingInCategoryDesc(categoryid, PageRequest.of(0, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                }

            }

        }

        map.put("listOfServices","empty");
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);

    }


    //Get Service By Name
    @RequestMapping(value="/services/name/{name}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getServicesByName(@PathVariable String name,
                                                                @RequestParam(value = "page",required = false) Integer pageNumber,
                                                                HttpServletRequest httpServletRequest){

        int numberOfServices = this.servService.getNumberOfServicesWithName(name);
        int numberOfPages = this.servService.getNumberOfPages(numberOfServices);

        Map<String,Object> map = new HashMap<String,Object>();
        List<Serv> servicesList;
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        map.put("sumOfServices",numberOfServices);
        map.put("sumOfPages",numberOfPages);

        if(numberOfPages!=0) {
            if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {

                servicesList = this.servService.getServicesByName(name, PageRequest.of(pageNumber.intValue() - 1, 9));
                map.put("listOfServices",servicesList);
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            } else {
                servicesList = this.servService.getServicesByName(name, PageRequest.of(0, 9));
                map.put("listOfServices",servicesList);
                return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
            }
        }else{
            map.put("listOfServices","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
        }
    }


    //Get Service By Id
    @RequestMapping(value="/services/{id}",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getServicesById(@PathVariable int id,
                                                HttpServletRequest httpServletRequest){

        Map<String,Object> map = new HashMap<String,Object>();
        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        Serv service = this.servService.getServicesById(id);
        if(service!=null){
            map.put("service",service);
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.OK);
        }
        else{
            map.put("service","empty");
            return new ResponseEntity<Map<String,Object>>(map,HttpStatus.BAD_REQUEST);
        }
    }



    //Get Services Ordered By Name
    @RequestMapping(value="/services/sort",method=RequestMethod.GET,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Map<String,Object>> getAllSortedServices(@RequestParam(value = "page",required = false) Integer pageNumber,
                                                           @RequestParam(value="type",required = true) String type,
                                                           @RequestParam(value="order",required=true) String order,
                                                           HttpServletRequest httpServletRequest){

        int numberOfServices = this.servService.getNumberOfServices();
        int numberOfPages = this.servService.getNumberOfPages(numberOfServices);

        Map<String,Object> map = new HashMap<String,Object>();
        List<Serv> serviceList;

        Object token = httpServletRequest.getAttribute("token");
        map.put("token",token);

        map.put("sumOfServices",numberOfServices);
        map.put("sumOfPages",numberOfPages);
        if(numberOfPages!=0) {
            if (type.contentEquals("name")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                        serviceList = this.servService.getServicesOrderByNameAsc(PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {
                        serviceList = this.servService.getServicesOrderByNameAsc(PageRequest.of(0, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                        serviceList = this.servService.getServicesOrderByNameDesc(PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {
                        serviceList = this.servService.getServicesOrderByNameDesc(PageRequest.of(0, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                }

            } else if (type.contentEquals("rating")) {

                if (order.contentEquals("asc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                        serviceList = this.servService.getServicesOrderByRaitingAsc(PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {
                        serviceList = this.servService.getServicesOrderByRaitingAsc(PageRequest.of(0, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                } else if (order.contentEquals("desc")) {
                    if (pageNumber != null && pageNumber.intValue() >= 1 && pageNumber.intValue() <= numberOfPages) {
                        serviceList = this.servService.getServicesOrderByRaitingDesc(PageRequest.of(pageNumber.intValue() - 1, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    } else {
                        serviceList = this.servService.getServicesOrderByRaitingDesc(PageRequest.of(0, 9));
                        map.put("listOfServices",serviceList);
                        return new ResponseEntity<Map<String,Object>>(map, HttpStatus.OK);
                    }
                }

            }
        }
        map.put("listOfServices","empty");
        return new ResponseEntity<Map<String,Object>>(map,HttpStatus.NOT_FOUND);
    }




}
