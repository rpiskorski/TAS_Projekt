package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.ProductUser;
import project.repositories.ProductUserRespository;

import java.util.Optional;

@Service
public class ProductUserServiceImpl implements ProductUserService{

    @Autowired
    private ProductUserRespository productUserRespository;



    public ProductUser add(ProductUser productUser){
        if(!checkIfExists(productUser.getProduct().getId(),productUser.getUserP().getId()))
        {

            return this.productUserRespository.save(productUser);
        }
        else{
            return null;
        }
    }

    public void delete(int id){
        this.productUserRespository.deleteById(id);
    }

    public boolean checkIfExists(int productID,int userID){

        ProductUser pu =this.productUserRespository.checkIfExists(productID,userID);
        if(pu!=null){
            return true;
        }
         else{
            return false;
        }
    }
    public boolean checkIfExists(int commentID){

        if(this.productUserRespository.findById(commentID).isPresent())
        {
            return true;
        }
         else{
            return false;
        }
    }

    public ProductUser getProductUser(int id){

        Optional<ProductUser> pu =this.productUserRespository.findById(id);
        if(pu.isPresent()){
            return pu.get();
        }
        else return null;
    }
}
