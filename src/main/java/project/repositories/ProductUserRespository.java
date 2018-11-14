package project.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import project.model.ProductUser;

@Repository
public interface ProductUserRespository extends JpaRepository<ProductUser,Integer> {

    @Query(value="SELECT * FROM product_user pu WHERE pu.product_id=:productID AND pu.user_id=:userID",nativeQuery = true)
    public ProductUser checkIfExists(@Param("productID") int productID, @Param("userID") int userID);




}
