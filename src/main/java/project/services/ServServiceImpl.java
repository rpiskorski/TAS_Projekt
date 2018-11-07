package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import project.model.Serv;
import project.repositories.ServiceRepository;


import java.util.List;

@Service
public class ServServiceImpl implements ServService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Override
    public boolean checkIfExists(String name,String owner_name,String localization){

        if(this.serviceRepository.checkIfExists(name,owner_name,localization).isEmpty()){
            return false;
        }
        else return true;
    }

    @Override
    public int getNumberOfServices(){
        return this.serviceRepository.getNumberOfServices();
    }

    @Override
    public int getNumberOfPages(){
        int numberOfServices = getNumberOfServices();
        int numberOfPages = 0;
        if(numberOfServices%10==0){
            numberOfPages = numberOfServices/10;
        }
        else{
            numberOfPages = (int)(numberOfServices/10)+1;
        }
        return numberOfPages;
    }

    @Override
    public int getNumberOfServicesInCategory(int cat_id){
        return this.serviceRepository.getNumberOfSerivcesInCategory(cat_id);
    }

    @Override
    public int getNumberOfPagesInCategory(int cat_id){
        int numberOfProducts = getNumberOfServicesInCategory(cat_id);
        int numberOfPages = 0;
        if(numberOfProducts%10==0){
            numberOfPages = numberOfProducts/10;
        }
        else{
            numberOfPages= (int)(numberOfProducts/10)+1;
        }
        return numberOfPages;

    }

    @Override
    public List<Serv> getAllServices(Pageable pageable) {
        return this.serviceRepository.findAllServices(pageable);
    }

    @Override
    public Serv getServicesById(int id){
        return this.serviceRepository.findById(id);
    }

    @Override
    public List<Serv> getServicesByName(String name,Pageable pageable){
        return this.serviceRepository.findByName(name,pageable);
    }

    @Override
    public int getNumberOfServicesWithName(String name){
        return this.serviceRepository.getNumberOfServicesWithName(name);
    }
    @Override
    public int getNumberOfPagesWithName(String name){
        int numberOfProducts = getNumberOfServicesWithName(name);
        int numberOfPages = 0;
        if(numberOfProducts%10==0){
            numberOfPages = numberOfProducts/10;
        }
        else{
            numberOfPages = (int)(numberOfProducts/10)+1;
        }
        return numberOfPages;
    }

    @Override
    public List<Serv> getAllServicesInCategory(int cat_id,Pageable pageable){
        return this.serviceRepository.findByCat(cat_id,pageable);
    }


    @Override
    public List<Serv> getServicesOrderByNameInCategoryAsc(int cat_id,Pageable pageable){
        return this.serviceRepository.findByCatOrderedByNameAsc(cat_id,pageable);
    }
    @Override
    public List<Serv> getServicesOrderByNameInCategoryDesc(int cat_id,Pageable pageable){
        return this.serviceRepository.findByCatOrderedByNameDesc(cat_id,pageable);
    }
    @Override
    public List<Serv> getServicesOrderByRaitingInCategoryAsc(int cat_id,Pageable pageable){
        return this.serviceRepository.findByCatOrderedByRaitingAsc(cat_id,pageable);
    }
    @Override
    public List<Serv> getServicesOrderByRaitingInCategoryDesc(int cat_id,Pageable pageable){
        return this.serviceRepository.findByCatOrderedByRaitingDesc(cat_id,pageable);
    }

    @Override
    public List<Serv> getServicesOrderByNameAsc(Pageable pageable) {
        return this.serviceRepository.findServiceOrderByNameAsc(pageable);
    }

    @Override
    public List<Serv> getServicesOrderByNameDesc(Pageable pageable) {
        return this.serviceRepository.findServiceOrderByNameDesc(pageable);
    }

    @Override
    public List<Serv> getServicesOrderByRaitingAsc(Pageable pageable){

        return this.serviceRepository.findServiceOrderByRaitingAsc(pageable);
    }

    @Override
    public List<Serv> getServicesOrderByRaitingDesc(Pageable pageable){
        return this.serviceRepository.findServiceOrderByRaitingDesc(pageable);
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
