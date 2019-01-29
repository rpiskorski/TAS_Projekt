package project.services;


import org.springframework.stereotype.Service;
import project.model.Category;
import project.model.Product;
import org.springframework.data.domain.Pageable;



import java.util.List;
import java.util.Map;


public interface ProductService {

    public boolean checkIfExists(String name,String manu_name);

    public List<Product> getAllProducts(Pageable pageable);

    public int getNumberOfProducts();
    public int getNumberOfPages(int numberOfProducts);


    public int getNumberOfProductsInCategory(int cat_id);

    public int getNumberOfProductsByManufacturer(String manufacturer_name);

    public List<Product> getProductsByName(String name, Pageable pageable);
    public List<Product> getProductsByNameInCategory(int cat_id,String name, Pageable pageable);


    public int getNumberOfProductsWithName(String name);


    public int getNumberOfProductsWithNameInCategory(int cat_id,String name);


    public List<Product> getAllProductsInCategory(int cat_id,Pageable pageable);

    public List<Product> getAllProductsByManufacturer(String manufacturer_name,Pageable pageable);

    public List<Product> getProductsOrderByNameByManufacturerAsc(String manufacturer_name,Pageable pageable);
    public List<Product> getProductsOrderByNameByManufacturerDesc(String manufacturer_name,Pageable pageable);
    public List<Product> getProductsOrderByRaitingByManufacturerAsc(String manufacturer_name,Pageable pageable);
    public List<Product> getProductsOrderByRaitingByManufacturerDesc(String manufacturer_name,Pageable pageable);

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


    Map<String,Object> getProductsAdvancedSearch(String name, String manuName,Integer categoryId,
                                                 boolean Sort, String type, String order, Integer page);

    public Product addProduct(Product product);

    public Product editProduct(String name,String manuName,Category cat,int id);

    public void deleteProduct(int productID);






}
