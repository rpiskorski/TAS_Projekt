package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.model.Product;
import project.model.Serv;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Serv,Integer> {

    @Query(value="SELECT * FROM services s WHERE s.name LIKE %:name%", nativeQuery = true)
    List<Serv> findByName(@Param("name") String name);

    @Query(value="SELECT * FROM services s WHERE s.category_id=:cat_id", nativeQuery = true)
    List<Serv> findByCat(@Param("cat_id") int cat_id);


    @Query(value="SELECT * FROM services s WHERE s.category_id=:cat_id ORDER BY s.name ASC", nativeQuery = true)
    List<Serv> findByCatOrderedByNameAsc(@Param("cat_id") int cat_id);

    @Query(value="SELECT * FROM services s WHERE s.category_id=:cat_id ORDER BY s.name DESC", nativeQuery = true)
    List<Serv> findByCatOrderedByNameDesc(@Param("cat_id") int cat_id);

    @Query(value="SELECT * FROM services s WHERE s.category_id=:cat_id ORDER BY s.avg_raiting ", nativeQuery = true)
    List<Serv> findByCatOrderedByRaitingAsc(@Param("cat_id") int cat_id);

    @Query(value="SELECT * FROM services s WHERE s.category_id=:cat_id ORDER BY s.avg_raiting ", nativeQuery = true)
    List<Serv> findByCatOrderedByRaitingDesc(@Param("cat_id") int cat_id);


    @Query(value="SELECT * FROM services s ORDER BY s.name ASC",nativeQuery = true)
    List<Serv> findServiceOrderByNameAsc();
    @Query(value="SELECT * FROM services s ORDER BY s.name DESC",nativeQuery = true)
    List<Serv> findServiceOrderByNameDesc();

    @Query(value="SELECT * FROM services s ORDER BY s.avg_rating ASC",nativeQuery = true)
    List<Serv> findServiceOrderByRaitingAsc();
    @Query(value="SELECT * FROM services s ORDER BY s.avg_rating DESC",nativeQuery = true)
    List<Serv> findServiceOrderByRaitingDesc();
}
