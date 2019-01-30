package project.services;


import org.springframework.stereotype.Service;
import project.model.Category;
import project.model.Product;
import org.springframework.data.domain.Pageable;



import java.util.List;
import java.util.Map;


public interface ProductService {

    public boolean checkIfExists(String name,String manu_name);

    public int getNumberOfPages(int numberOfProducts);

    public Product getProductsById(int id);


    Map<String,Object> getProductsAdvancedSearch(String name, String manuName,Integer categoryId,
                                                 boolean Sort, String type, String order, Integer page);

    public Product addProduct(Product product);

    public Product editProduct(String name,String manuName,Category cat,int id);

    public void deleteProduct(int productID);






}
