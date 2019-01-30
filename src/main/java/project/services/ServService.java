package project.services;

import org.springframework.data.domain.Pageable;
import project.model.Category;
import project.model.Serv;

import java.util.List;
import java.util.Map;

public interface ServService {


    public boolean checkIfExists(String name,String owner_name,String localization);

    public int getNumberOfPages(int numberOfServices);


    public Serv getServicesById(int id);


    Map<String,Object> getServicesAdvancedSearch(String name, String ownerName,String localization, Integer categoryId,
                                                 boolean Sort, String type, String order, Integer page);

    public Serv addService(Serv service);

    public Serv editService(String name, String ownerName,String localization, Category cat, int id);

    public void deleteService(int serviceID);
}
