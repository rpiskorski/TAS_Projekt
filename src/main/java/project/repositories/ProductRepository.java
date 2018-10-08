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



    List<Product> findByName(String name);

    @Query(value="SELECT * FROM products p WHERE p.category_id=:cat_id", nativeQuery = true)
    List<Product> findByCat(@Param("cat_id") int cat_id);


    //PROBLEM Z TYM ZAPYTANIEM
  //  List<Product> findProductOrderByRaiting();
    //List<Product> findEveryOrderByAvgDesc();
}


