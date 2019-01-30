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

    @Query(value="SELECT * FROM services s WHERE s.id=:id", nativeQuery = true)
    Serv findById(@Param("id") int id);





}
