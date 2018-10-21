package project.services;


import org.springframework.stereotype.Service;
import project.model.Category;
import project.model.Product;

import java.util.List;


public interface ProductService {

    public List<Product> getAllProducts();

    public List<Product> getProductsByName(String name);

    public List<Product> getAllProductsInCategory(int cat_id);

    public List<Product> getProductsOrderByNameInCategoryAsc(int cat_id);
    public List<Product> getProductsOrderByNameInCategoryDesc(int cat_id);
    public List<Product> getProductsOrderByRaitingInCategoryAsc(int cat_id);
    public List<Product> getProductsOrderByRaitingInCategoryDesc(int cat_id);


    public List<Product> getProductsOrderByRaitingAsc();
    public List<Product> getProductsOrderByRaitingDesc();
    public List<Product> getProductsOrderByNameAsc();
    public List<Product> getProductsOrderByNameDesc();
//
//    public List<Product> getProductsOrderByName(String asc);
//    public List<Product> getProductsOrderByRaiting(String asc);
    public Product addProduct(Product product);

    public void deleteProduct(int productID);




}
