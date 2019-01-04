package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import project.model.Category;
import project.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    //Chceck If Product Already Exists
    @Query(value = "SELECT * FROM products p WHERE p.name=:name AND p.manufacturer_name=:manufacturer_name",nativeQuery = true)
    List<Product> checkIfExists(@Param("name")String name,@Param("manufacturer_name")String manu_name);

    //Find All Products
    @Query(value="SELECT * FROM products",nativeQuery = true)
    List<Product> findAllProducts(Pageable pageable);

    //Count number of products
    @Query(value = "SELECT COUNT(p.id) FROM products p",nativeQuery = true)
    int getNumberOfProducts();

    //Find Products By Name
    @Query(value="SELECT * FROM products p WHERE p.name LIKE %:name%", nativeQuery = true)
    List<Product> findByName(@Param("name") String name,Pageable pageable);

    //Find Products By Name In Category
    @Query(value="SELECT * FROM products p WHERE p.name LIKE %:name% AND p.category_id=:cat_id", nativeQuery = true)
    List<Product> findByNameInCategory(@Param("cat_id") int cat_id,@Param("name") String name,Pageable pageable);

    //Count number of products with name in category
    @Query(value = "SELECT COUNT(p.id) FROM products p WHERE p.name LIKE %:name% AND p.category_id=:cat_id",nativeQuery = true)
    int getNumberOfProductsWithNameInCategory(@Param("cat_id")int cat_id,@Param("name")String name);


    //Count number of products with name
    @Query(value = "SELECT COUNT(p.id) FROM products p WHERE p.name LIKE %:name%",nativeQuery = true)
    int getNumberOfProductsWithName(@Param("name")String name);


    //Find Product By ID
    @Query(value="SELECT * FROM products p WHERE p.id=:id", nativeQuery = true)
    Product findById(@Param("id") int id);

    //Find All Products In Category
    @Query(value="SELECT * FROM products p WHERE p.category_id=:cat_id", nativeQuery = true)
    List<Product> findByCat(@Param("cat_id") int cat_id,Pageable pageable);

    //Count number of products in category
    @Query(value = "SELECT COUNT(p.id) FROM products p WHERE p.category_id=:cat_id",nativeQuery = true)
    int getNumberOfProductsInCategory(@Param("cat_id") int cat_id);

    //Find All Products In Category Order BY Name Ascending
    @Query(value="SELECT * FROM products p WHERE p.category_id=:cat_id ORDER BY p.name ASC", nativeQuery = true)
    List<Product> findByCatOrderedByNameAsc(@Param("cat_id") int cat_id,Pageable pageable);

    //Find All Products In Category Order BY Name Descending
    @Query(value="SELECT * FROM products p WHERE p.category_id=:cat_id ORDER BY p.name DESC", nativeQuery = true)
    List<Product> findByCatOrderedByNameDesc(@Param("cat_id") int cat_id,Pageable pageable);

    //Find All Products In Category Order BY Raiting Ascending
    @Query(value="SELECT * FROM products p WHERE p.category_id=:cat_id ORDER BY p.avg_raiting ASC", nativeQuery = true)
    List<Product> findByCatOrderedByRaitingAsc(@Param("cat_id") int cat_id,Pageable pageable);

    //Find All Products In Category Order By Raiting Descending
    @Query(value="SELECT * FROM products p WHERE p.category_id=:cat_id ORDER BY p.avg_raiting DESC", nativeQuery = true)
    List<Product> findByCatOrderedByRaitingDesc(@Param("cat_id") int cat_id,Pageable pageable);


    //Find All Products Order BY Name Ascending
    @Query(value="SELECT * FROM products p ORDER BY p.name ASC",nativeQuery = true)
    List<Product> findProductOrderByNameAsc(Pageable pageable);

    //Find All Products Order BY Name Descending
    @Query(value="SELECT * FROM products p ORDER BY p.name DESC",nativeQuery = true)
    List<Product> findProductOrderByNameDesc(Pageable pageable);

    //Find All Products Order BY Raiting Ascending
    @Query(value="SELECT * FROM products p ORDER BY p.avg_rating ASC",nativeQuery = true)
    List<Product> findProductOrderByRaitingAsc(Pageable pageable);

    //Find All Products Order BY Raiting Descending
    @Query(value="SELECT * FROM products p ORDER BY p.avg_rating DESC",nativeQuery = true)
    List<Product> findProductOrderByRaitingDesc(Pageable pageable);

    //***************************************

    //Count number of products by manufacturer
    @Query(value = "SELECT COUNT(p.id) FROM products p WHERE p.manufacturer_name=:manufacturer_name",nativeQuery = true)
    int getNumberOfProductsByManufacturer(@Param("manufacturer_name") String manufacturer_name);

    //Find All Products By Manufacturer
    @Query(value="SELECT * FROM products p WHERE p.manufacturer_name=:manufacturer_name", nativeQuery = true)
    List<Product> findByManufacturer(@Param("manufacturer_name") String manufacturer_name,Pageable pageable);

    //Find All Products By Manufacturer Order BY Name Ascending
    @Query(value="SELECT * FROM products p WHERE p.manufacturer_name=:manufacturer_name ORDER BY p.name ASC", nativeQuery = true)
    List<Product> findByManufacturerOrderedByNameAsc(@Param("manufacturer_name") String manufacturer_name,Pageable pageable);

    //Find All Products By Manufacturer Order BY Name Descending
    @Query(value="SELECT * FROM products p WHERE p.manufacturer_name=:manufacturer_name ORDER BY p.name DESC", nativeQuery = true)
    List<Product> findByManufacturerOrderedByNameDesc(@Param("manufacturer_name") String manufacturer_name,Pageable pageable);

    //Find All Products By Manufacturer Order BY Raiting Ascending
    @Query(value="SELECT * FROM products p WHERE p.manufacturer_name=:manufacturer_name ORDER BY p.avg_raiting ASC", nativeQuery = true)
    List<Product> findByManufacturerOrderedByRaitingAsc(@Param("manufacturer_name") String manufacturer_name,Pageable pageable);

    //Find All Products By Manufacturer Order By Raiting Descending
    @Query(value="SELECT * FROM products p WHERE p.manufacturer_name=:manufacturer_name ORDER BY p.avg_raiting DESC", nativeQuery = true)
    List<Product> findByManufacturerOrderedByRaitingDesc(@Param("manufacturer_name") String manufacturer_name,Pageable pageable);

}


