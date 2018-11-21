package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import project.model.ServUser;

public interface ServUserRepository extends JpaRepository<ServUser,Integer> {

    @Query(value="SELECT * FROM serv_user su WHERE su.service_id=:serviceID AND su.user_id=:userID",nativeQuery = true)
    public ServUser checkIfExists(@Param("serviceID") int serviceID, @Param("userID") int userID);


    @Transactional
    @Modifying
    @Query(value="UPDATE serv_user su SET su.comment=:comment, su.rating=:rating, su.date=:date WHERE su.id=:id",nativeQuery = true)
    public void updateComment(@Param("comment") String comment,@Param("rating") int rating,@Param("date") long date,@Param("id") int id);




}
