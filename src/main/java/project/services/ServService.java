package project.services;

import project.model.Serv;

import java.util.List;

public interface ServService {

    public List<Serv> getAllServices();

    public List<Serv> getServicesByName(String name);

    public List<Serv> getAllServicesInCategory(int cat_id);

    public List<Serv> getServicesOrderByNameInCategoryAsc(int cat_id);
    public List<Serv> getServicesOrderByNameInCategoryDesc(int cat_id);
    public List<Serv> getServicesOrderByRaitingInCategoryAsc(int cat_id);
    public List<Serv> getServicesOrderByRaitingInCategoryDesc(int cat_id);


    public List<Serv> getServicesOrderByNameAsc();
    public List<Serv> getServicesOrderByNameDesc();
    public List<Serv> getServicesOrderByRaitingAsc();
    public List<Serv> getServicesOrderByRaitingDesc();

    public Serv addService(Serv service);

    public void deleteService(int serviceID);
}
