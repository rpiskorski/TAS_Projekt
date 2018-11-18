package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.model.ProductUser;
import project.model.User;
import project.repositories.ProductUserRespository;
import project.repositories.UserRepository;

import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class ProductUserServiceImpl implements ProductUserService{

    @Autowired
    private ProductUserRespository productUserRespository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean isOwner(int id) {
        //Get current logged user
        UserDetails userDetails = (UserDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser=userRepository.findOneByName(userDetails.getUsername());

        ProductUser pu = getProductUser(id);

        //Check if current user is the owner of that comment
        if(pu!=null && pu.getUserP()==currentUser){
            return true;
        }
        else return false;
    }

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

    public boolean editComment(String comment,Integer rating,int id){
        if(isOwner(id)){
            int newRaiting = 0;
            if(rating==null) {
                newRaiting=getProductUser(id).getRating();

            }else{
                newRaiting = rating.intValue();
            }
            long date = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw")).getTimeInMillis();

            this.productUserRespository.updateComment(comment, newRaiting, date, id);
            return true;

        }
        return false;
    }
}
