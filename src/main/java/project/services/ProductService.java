package project.services;


import org.springframework.stereotype.Service;
import project.model.Category;
import project.model.Product;
import org.springframework.data.domain.Pageable;



import java.util.List;


public interface ProductService {

    public boolean checkIfExists(String name,String manu_name);

    public List<Product> getAllProducts(Pageable pageable);

    public int getNumberOfProducts();
    public int getNumberOfPages();


    public int getNumberOfProductsInCategory(int cat_id);
    public int getNumberOfPagesInCategory(int cat_id);

    public List<Product> getProductsByName(String name, Pageable pageable);
    public int getNumberOfProductsWithName(String name);
    public int getNumberOfPagesWithName(String name);

    public List<Product> getAllProductsInCategory(int cat_id,Pageable pageable);

    public Product getProductsById(int id);

    public List<Product> getProductsOrderByNameInCategoryAsc(int cat_id,Pageable pageable);
    public List<Product> getProductsOrderByNameInCategoryDesc(int cat_id,Pageable pageable);
    public List<Product> getProductsOrderByRaitingInCategoryAsc(int cat_id,Pageable pageable);
    public List<Product> getProductsOrderByRaitingInCategoryDesc(int cat_id,Pageable pageable);


    public List<Product> getProductsOrderByRaitingAsc(Pageable pageable);
    public List<Product> getProductsOrderByRaitingDesc(Pageable pageable);
    public List<Product> getProductsOrderByNameAsc(Pageable pageable);
    public List<Product> getProductsOrderByNameDesc(Pageable pageable);
//
//    public List<Product> getProductsOrderByName(String asc);
//    public List<Product> getProductsOrderByRaiting(String asc);
    public Product addProduct(Product product);

    public void deleteProduct(int productID);






}
