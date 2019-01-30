package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.model.Category;
import project.model.Product;
import project.model.User;
import project.repositories.CategoryRepository;
import project.repositories.ProductRepository;
import project.repositories.UserRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CategoryRepository categoryRepository;


    @Autowired
    private EntityManager entityManager;

    @Override
    public Map<String,Object> getProductsAdvancedSearch(String name, String manuName,Integer categoryId,
                                                 boolean Sort, String type, String order, Integer page){
        List<Product> fullProductList;
        List<Product> pageProductList;
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


        String[] stringTable = {name,manuName,catIdString};


        String[] stringTableQueries = {"p.name COLLATE UTF8_GENERAL_CI LIKE :name ","p.manufacturer_name COLLATE UTF8_GENERAL_CI LIKE :manuName ","p.category_id=:catId "};

        String Q="SELECT * FROM products p ";

        if(name!=null || manuName!=null || catId!=0){
            Q=Q.concat("WHERE ");
            for(int i=0;i<3;i++){
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

        Query q = entityManager.createNativeQuery(Q,Product.class);
        for(int i=0;i<3;i++){
            if(stringTable[i]!=null && i==0){
                q.setParameter("name","%"+name+"%");
            }else if(stringTable[i]!=null && i==1){
                q.setParameter("manuName", "%"+manuName+"%");
            }
            else if(stringTable[i]!=null && i==2){
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

        pageProductList = new PageImpl<Product>(fullProductList.subList((int)start,(int)end), pageable, numberOfProducts).getContent();

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("sumOfProducts",numberOfProducts);
        map.put("sumOfPages",numberOfPages);
         if(numberOfProducts!=0){
             map.put("listOfProducts",pageProductList);
         }else{
             map.put("listOfProducts","empty");
         }

        return map;
    }


    @Override
    public boolean checkIfExists(String name,String manu_name){

        if(this.productRepository.checkIfExists(name,manu_name).isEmpty()){
            return false;
        }
        else return true;

    }



    @Override
    public int getNumberOfPages(int numberOfProducts){

        int numberOfPages = 0;
        if(numberOfProducts%9==0){
            numberOfPages = numberOfProducts/9;
        }
        else{
            numberOfPages = (int)(numberOfProducts/9)+1;
        }
        return numberOfPages;
    }



    @Override
    public Product getProductsById(int id){
        return this.productRepository.findById(id);
    }


    @Override
    public Product addProduct(Product product) {

        if(checkIfExists(product.getName(),product.getManufacturer_name())){
            return null;
        }
        else return this.productRepository.save(product);

    }
    @Override
    public Product editProduct(String name,String manuName,Category cat,int id){

        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser=userRepository.findOneByName(userDetails.getUsername());
        Product p = this.getProductsById(id);
        int catId = cat.getId();
        if(currentUser.getRole().getName().contentEquals("ADMIN")){
            this.productRepository.updateProduct(name,manuName,catId,id);
            p.setName(name);
            p.setManufacturer_name(manuName);
            p.setCat(cat);
            return p;
        }
        return null;
    }

    @Override
    public void deleteProduct(int productID) {

        this.productRepository.deleteById(productID);
    }


}
