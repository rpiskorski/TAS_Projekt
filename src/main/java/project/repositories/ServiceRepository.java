package project.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.model.Product;
import project.model.Serv;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Serv,Integer> {

    //Chceck If Service Already Exists
    @Query(value = "SELECT * FROM services s WHERE s.name=:name AND s.owner_name=:owner_name AND s.localization=:localization",nativeQuery = true)
    List<Serv> checkIfExists(@Param("name")String name,@Param("owner_name")String owner_name,@Param("localization")String localization);

    //Find All Services
    @Query(value="SELECT * FROM services",nativeQuery = true)
    List<Serv> findAllServices(Pageable pageable);

    //Count number of services
    @Query(value = "SELECT COUNT(s.id) FROM services s",nativeQuery = true)
    int getNumberOfServices();


    @Query(value="SELECT * FROM services s WHERE s.name LIKE %:name%", nativeQuery = true)
    List<Serv> findByName(@Param("name") String name,Pageable pageable);

    //Count number of services with name
    @Query(value = "SELECT COUNT(s.id) FROM services s WHERE s.name LIKE %:name%",nativeQuery = true)
    int getNumberOfServicesWithName(@Param("name")String name);

    @Query(value="SELECT * FROM services s WHERE s.category_id=:cat_id", nativeQuery = true)
    List<Serv> findByCat(@Param("cat_id") int cat_id,Pageable pageable);

    //Count number of services in category
    @Query(value = "SELECT COUNT(s.id) FROM services s WHERE s.category_id=:cat_id",nativeQuery = true)
    int getNumberOfSerivcesInCategory(@Param("cat_id") int cat_id);

    @Query(value="SELECT * FROM services s WHERE s.id=:id", nativeQuery = true)
    Serv findById(@Param("id") int id);


    @Query(value="SELECT * FROM services s WHERE s.category_id=:cat_id ORDER BY s.name ASC", nativeQuery = true)
    List<Serv> findByCatOrderedByNameAsc(@Param("cat_id") int cat_id,Pageable pageable);

    @Query(value="SELECT * FROM services s WHERE s.category_id=:cat_id ORDER BY s.name DESC", nativeQuery = true)
    List<Serv> findByCatOrderedByNameDesc(@Param("cat_id") int cat_id,Pageable pageable);

    @Query(value="SELECT * FROM services s WHERE s.category_id=:cat_id ORDER BY s.avg_raiting ", nativeQuery = true)
    List<Serv> findByCatOrderedByRaitingAsc(@Param("cat_id") int cat_id,Pageable pageable);

    @Query(value="SELECT * FROM services s WHERE s.category_id=:cat_id ORDER BY s.avg_raiting ", nativeQuery = true)
    List<Serv> findByCatOrderedByRaitingDesc(@Param("cat_id") int cat_id,Pageable pageable);


    @Query(value="SELECT * FROM services s ORDER BY s.name ASC",nativeQuery = true)
    List<Serv> findServiceOrderByNameAsc(Pageable pageable);
    @Query(value="SELECT * FROM services s ORDER BY s.name DESC",nativeQuery = true)
    List<Serv> findServiceOrderByNameDesc(Pageable pageable);

    @Query(value="SELECT * FROM services s ORDER BY s.avg_rating ASC",nativeQuery = true)
    List<Serv> findServiceOrderByRaitingAsc(Pageable pageable);
    @Query(value="SELECT * FROM services s ORDER BY s.avg_rating DESC",nativeQuery = true)
    List<Serv> findServiceOrderByRaitingDesc(Pageable pageable);
}
