package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.model.Category;
import project.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {


    @Transactional
    @Modifying
    @Query(value="UPDATE products p SET p.name=:name, p.manufacturer_name=:manuName, p.category_id=:catId WHERE p.id=:id",nativeQuery = true)
    public void updateProduct(@Param("name") String name,@Param("manuName") String manuName,@Param("catId") int catId,@Param("id") int id);

    //Chceck If Product Already Exists
    @Query(value = "SELECT * FROM products p WHERE p.name=:name AND p.manufacturer_name=:manufacturer_name",nativeQuery = true)
    List<Product> checkIfExists(@Param("name")String name,@Param("manufacturer_name")String manu_name);

    //Find All Products
    @Query(value="SELECT * FROM products",nativeQuery = true)
    List<Product> findAllProducts(Pageable pageable);

    //Count number of products
    @Query(value = "SELECT COUNT(p.id) FROM products p",nativeQuery = true)
    int getNumberOfProducts();

    //Find Product By ID
    @Query(value="SELECT * FROM products p WHERE p.id=:id", nativeQuery = true)
    Product findById(@Param("id") int id);




}


