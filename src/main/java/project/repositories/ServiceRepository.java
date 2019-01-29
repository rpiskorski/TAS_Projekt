package project.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import project.model.Product;
import project.model.Serv;

import java.util.List;

@Repository
public interface ServiceRepository extends JpaRepository<Serv,Integer> {


    @Transactional
    @Modifying
    @Query(value="UPDATE services s SET s.name=:name, s.owner_name=:ownerName,s.localization=:localization,s.category_id=:catId WHERE s.id=:id",nativeQuery = true)
    public void updateService(@Param("name") String name,@Param("ownerName") String ownerName,@Param("localization") String localization,@Param("catId") int catId,@Param("id") int id);


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

    @Query(value="SELECT * FROM services s WHERE s.category_id=:cat_id ORDER BY s.avg_rating ", nativeQuery = true)
    List<Serv> findByCatOrderedByRaitingAsc(@Param("cat_id") int cat_id,Pageable pageable);

    @Query(value="SELECT * FROM services s WHERE s.category_id=:cat_id ORDER BY s.avg_rating ", nativeQuery = true)
    List<Serv> findByCatOrderedByRaitingDesc(@Param("cat_id") int cat_id,Pageable pageable);


    @Query(value="SELECT * FROM services s ORDER BY s.name ASC",nativeQuery = true)
    List<Serv> findServiceOrderByNameAsc(Pageable pageable);
    @Query(value="SELECT * FROM services s ORDER BY s.name DESC",nativeQuery = true)
    List<Serv> findServiceOrderByNameDesc(Pageable pageable);

    @Query(value="SELECT * FROM services s ORDER BY s.avg_rating ASC",nativeQuery = true)
    List<Serv> findServiceOrderByRaitingAsc(Pageable pageable);
    @Query(value="SELECT * FROM services s ORDER BY s.avg_rating DESC",nativeQuery = true)
    List<Serv> findServiceOrderByRaitingDesc(Pageable pageable);

    //*********************************************
    //Count number of products by manufacturer
//    @Query(value = "SELECT COUNT(s.id) FROM services s WHERE s.owner_name=:owner_name",nativeQuery = true)
//    int getNumberOfServicesByOwner(@Param("owner_name") String owner_name);
//
//    //Find All Products By Manufacturer
//    @Query(value="SELECT * FROM services s WHERE p.manufacturer_name=:manufacturer_name", nativeQuery = true)
//    List<Product> findByManufacturer(@Param("manufacturer_name") String manufacturer_name,Pageable pageable);
//
//    //Find All Products By Manufacturer Order BY Name Ascending
//    @Query(value="SELECT * FROM products p WHERE p.manufacturer_name=:manufacturer_name ORDER BY p.name ASC", nativeQuery = true)
//    List<Product> findByManufacturerOrderedByNameAsc(@Param("manufacturer_name") String manufacturer_name,Pageable pageable);
//
//    //Find All Products By Manufacturer Order BY Name Descending
//    @Query(value="SELECT * FROM products p WHERE p.manufacturer_name=:manufacturer_name ORDER BY p.name DESC", nativeQuery = true)
//    List<Product> findByManufacturerOrderedByNameDesc(@Param("manufacturer_name") String manufacturer_name,Pageable pageable);
//
//    //Find All Products By Manufacturer Order BY Raiting Ascending
//    @Query(value="SELECT * FROM products p WHERE p.manufacturer_name=:manufacturer_name ORDER BY p.avg_raiting ASC", nativeQuery = true)
//    List<Product> findByManufacturerOrderedByRaitingAsc(@Param("manufacturer_name") String manufacturer_name,Pageable pageable);
//
//    //Find All Products By Manufacturer Order By Raiting Descending
//    @Query(value="SELECT * FROM products p WHERE p.manufacturer_name=:manufacturer_name ORDER BY p.avg_raiting DESC", nativeQuery = true)
//    List<Product> findByManufacturerOrderedByRaitingDesc(@Param("manufacturer_name") String manufacturer_name,Pageable pageable);

}
