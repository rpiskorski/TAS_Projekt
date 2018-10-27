package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.model.Category;
import project.model.Product;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {

    @Query(value="SELECT * FROM products p WHERE p.name LIKE %:name%", nativeQuery = true)
    List<Product> findByName(@Param("name") String name);


    @Query(value="SELECT * FROM products p WHERE p.id=:id", nativeQuery = true)
    Product findById(@Param("id") int id);

    @Query(value="SELECT * FROM products p WHERE p.category_id=:cat_id", nativeQuery = true)
    List<Product> findByCat(@Param("cat_id") int cat_id);

    @Query(value="SELECT * FROM products p WHERE p.category_id=:cat_id ORDER BY p.name ASC", nativeQuery = true)
    List<Product> findByCatOrderedByNameAsc(@Param("cat_id") int cat_id);

    @Query(value="SELECT * FROM products p WHERE p.category_id=:cat_id ORDER BY p.name DESC", nativeQuery = true)
    List<Product> findByCatOrderedByNameDesc(@Param("cat_id") int cat_id);

    @Query(value="SELECT * FROM products p WHERE p.category_id=:cat_id ORDER BY p.avg_raiting ", nativeQuery = true)
    List<Product> findByCatOrderedByRaitingAsc(@Param("cat_id") int cat_id);

    @Query(value="SELECT * FROM products p WHERE p.category_id=:cat_id ORDER BY p.avg_raiting ", nativeQuery = true)
    List<Product> findByCatOrderedByRaitingDesc(@Param("cat_id") int cat_id);



    @Query(value="SELECT * FROM products p ORDER BY p.name ASC",nativeQuery = true)
    List<Product> findProductOrderByNameAsc();
    @Query(value="SELECT * FROM products p ORDER BY p.name DESC",nativeQuery = true)
    List<Product> findProductOrderByNameDesc();

//    @Query(value="SELECT * FROM products p ORDER BY p.rating :asc",nativeQuery = true)
//    List<Product> findProductOrderByRaiting(@Param("asc") String asc);


    @Query(value="SELECT * FROM products p ORDER BY p.avg_rating ASC",nativeQuery = true)
    List<Product> findProductOrderByRaitingAsc();
    @Query(value="SELECT * FROM products p ORDER BY p.avg_rating DESC",nativeQuery = true)
    List<Product> findProductOrderByRaitingDesc();
}


