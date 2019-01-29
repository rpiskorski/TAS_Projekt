package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.model.Category;
import project.model.Serv;
import project.model.User;
import project.repositories.ServiceRepository;
import project.repositories.UserRepository;


import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ServServiceImpl implements ServService {

    @Autowired
    private ServiceRepository serviceRepository;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Override
    public Map<String,Object> getServicesAdvancedSearch(String name, String ownerName,String localization, Integer categoryId,
                                                        boolean Sort, String type, String order, Integer page){
        List<Serv> fullProductList;
        List<Serv> pageProductList;
        int numberOfProducts;
        int numberOfPages;

        Pageable pageable;


        String catIdString = null;
        int catId = 0;
        if(categoryId!=null){
            catId = categoryId.intValue();
            catIdString = categoryId.toString();
        }

        boolean flagFirst = true;


        String[] stringTable = {name,ownerName,localization,catIdString};


        String[] stringTableQueries = {"p.name COLLATE UTF8_GENERAL_CI LIKE :name ",
                "p.owner_name COLLATE UTF8_GENERAL_CI LIKE :ownerName ",
                "p.localization COLLATE UTF8_GENERAL_CI LIKE :localization ",
                "p.category_id=:catId "};

        String Q="SELECT * FROM services p ";

        if(name!=null || ownerName!=null || localization!=null || catId!=0){
            Q=Q.concat("WHERE ");
            for(int i=0;i<4;i++){
                if(stringTable[i]!=null && flagFirst){
                    flagFirst=false;

                    Q=Q.concat(stringTableQueries[i]);
                }else if(stringTable[i]!=null){
                    Q=Q.concat("AND "+stringTableQueries[i]);

                }
            }
        }
        if(Sort){


            Q=Q.concat("ORDER BY p."+type+" "+order);

        }

        //System.out.println(Q);
        Query q = entityManager.createNativeQuery(Q,Serv.class);
        for(int i=0;i<4;i++){
            if(stringTable[i]!=null && i==0){
                q.setParameter("name","%"+name+"%");
            }else if(stringTable[i]!=null && i==1){
                q.setParameter("ownerName", "%"+ownerName+"%");
            }else if(stringTable[i]!=null && i==2){
                q.setParameter("localization", "%"+localization+"%");
            }
            else if(stringTable[i]!=null && i==3){
                q.setParameter("catId", catId);
            }


        }


        fullProductList = q.getResultList();
        numberOfProducts = fullProductList.size();
        numberOfPages = getNumberOfPages(numberOfProducts);
        if(page!=null && page.intValue()>=1 && page.intValue()<=numberOfPages) {

            pageable = PageRequest.of(page.intValue()-1,9);
        }else{

            pageable = PageRequest.of(0,9);
        }
        long start = pageable.getOffset();
        long end = (start + pageable.getPageSize()) > numberOfProducts ? numberOfProducts : (start + pageable.getPageSize());

        pageProductList = new PageImpl<Serv>(fullProductList.subList((int)start,(int)end), pageable, numberOfProducts).getContent();

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("sumOfServices",numberOfProducts);
        map.put("sumOfPages",numberOfPages);
        if(numberOfProducts!=0){
            map.put("listOfServices",pageProductList);
        }else{
            map.put("listOfServices","empty");
        }

        return map;
    }


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
    public int getNumberOfPages(int numberOfServices){

        int numberOfPages = 0;
        if(numberOfServices%9==0){
            numberOfPages = numberOfServices/9;
        }
        else{
            numberOfPages = (int)(numberOfServices/9)+1;
        }
        return numberOfPages;
    }

    @Override
    public int getNumberOfServicesInCategory(int cat_id){
        return this.serviceRepository.getNumberOfSerivcesInCategory(cat_id);
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
    public Serv editService(String name, String ownerName, String localization, Category cat, int id){
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser=userRepository.findOneByName(userDetails.getUsername());

        Serv s = this.getServicesById(id);
        int catId = cat.getId();

        if(currentUser.getRole().getName().contentEquals("ADMIN")){
            this.serviceRepository.updateService(name,ownerName,localization,catId,id);
            s.setName(name);
            s.setOwner_name(ownerName);
            s.setLocalization(localization);
            s.setCat(cat);
            return s;
        }
        return null;
    }

    @Override
    public void deleteService(int serviceID) {

        this.serviceRepository.deleteById(serviceID);
    }
}
