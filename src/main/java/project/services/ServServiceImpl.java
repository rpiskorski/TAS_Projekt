package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.Serv;
import project.repositories.ServiceRepository;

import java.util.List;

@Service
public class ServServiceImpl implements ServService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public List<Serv> getAllServices() {
        return this.serviceRepository.findAll();
    }


    public List<Serv> getServicesByName(String name){
        return this.serviceRepository.findByName(name);
    }

    public List<Serv> getAllServicesInCategory(int cat_id){
        return this.serviceRepository.findByCat(cat_id);
    }

    //PROBLEM Z TYM ZAPYTANIEM
//    public List<Product> getAllByRaitingAsc(){
//        return this.productRepository.findProductOrderByRaiting();
//    }

//    public List<Product> getAllByRaitingDesc(){
//        return this.productRepository.findEveryOrderByAvgDesc();
//    }

    @Override
    public List<Serv> getServicesOrderByNameInCategoryAsc(int cat_id){
        return this.serviceRepository.findByCatOrderedByNameAsc(cat_id);
    }
    @Override
    public List<Serv> getServicesOrderByNameInCategoryDesc(int cat_id){
        return this.serviceRepository.findByCatOrderedByNameDesc(cat_id);
    }
    @Override
    public List<Serv> getServicesOrderByRaitingInCategoryAsc(int cat_id){
        return this.serviceRepository.findByCatOrderedByRaitingAsc(cat_id);
    }
    @Override
    public List<Serv> getServicesOrderByRaitingInCategoryDesc(int cat_id){
        return this.serviceRepository.findByCatOrderedByRaitingDesc(cat_id);
    }

    @Override
    public List<Serv> getServicesOrderByNameAsc() {
        return this.serviceRepository.findServiceOrderByNameAsc();
    }

    @Override
    public List<Serv> getServicesOrderByNameDesc() {
        return this.serviceRepository.findServiceOrderByNameDesc();
    }

    @Override
    public List<Serv> getServicesOrderByRaitingAsc(){

        return this.serviceRepository.findServiceOrderByRaitingAsc();
    }

    @Override
    public List<Serv> getServicesOrderByRaitingDesc(){
        return this.serviceRepository.findServiceOrderByRaitingDesc();
    }

    @Override
    public Serv addService(Serv service) {
        return this.serviceRepository.save(service);
    }

    @Override
    public void deleteService(int serviceID) {

        this.serviceRepository.deleteById(serviceID);
    }
}
