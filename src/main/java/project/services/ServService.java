package project.services;

import org.springframework.data.domain.Pageable;
import project.model.Category;
import project.model.Serv;

import java.util.List;
import java.util.Map;

public interface ServService {


    public boolean checkIfExists(String name,String owner_name,String localization);

    public List<Serv> getAllServices(Pageable pageable);

    public int getNumberOfServices();
    public int getNumberOfPages(int numberOfServices);

    public int getNumberOfServicesInCategory(int cat_id);


    public List<Serv> getServicesByName(String name,Pageable pageable);

    public int getNumberOfServicesWithName(String name);


    public Serv getServicesById(int id);

    public List<Serv> getAllServicesInCategory(int cat_id,Pageable pageable);

    public List<Serv> getServicesOrderByNameInCategoryAsc(int cat_id,Pageable pageable);
    public List<Serv> getServicesOrderByNameInCategoryDesc(int cat_id,Pageable pageable);
    public List<Serv> getServicesOrderByRaitingInCategoryAsc(int cat_id,Pageable pageable);
    public List<Serv> getServicesOrderByRaitingInCategoryDesc(int cat_id,Pageable pageable);


    public List<Serv> getServicesOrderByNameAsc(Pageable pageable);
    public List<Serv> getServicesOrderByNameDesc(Pageable pageable);
    public List<Serv> getServicesOrderByRaitingAsc(Pageable pageable);
    public List<Serv> getServicesOrderByRaitingDesc(Pageable pageable);


    Map<String,Object> getServicesAdvancedSearch(String name, String ownerName,String localization, Integer categoryId,
                                                 boolean Sort, String type, String order, Integer page);

    public Serv addService(Serv service);

    public Serv editService(String name, String ownerName,String localization, Category cat, int id);

    public void deleteService(int serviceID);
}
