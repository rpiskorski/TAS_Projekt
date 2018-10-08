package project.services;


import org.springframework.stereotype.Service;
import project.model.Category;
import project.model.Product;

import java.util.List;


public interface ProductService {

    public List<Product> getAllProducts();

    public List<Product> getProductsByName(String name);

    public List<Product> getAllProductsInCategory(int cat_id);


    //PROBLEM Z TYM ZAPYTANIEM
    //public List<Product> getAllByRaitingAsc();

    //public List<Product> getAllByRaitingDesc();

    public Product addProduct(Product product);

    public void deleteProduct(int productID);




}
