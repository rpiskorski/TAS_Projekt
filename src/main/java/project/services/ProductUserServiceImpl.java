package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.model.ProductUser;
import project.model.User;
import project.repositories.ProductUserRespository;
import project.repositories.UserRepository;

import org.springframework.data.domain.Pageable;
import java.util.Calendar;
import java.util.List;
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
        if(pu!=null && pu.getUserP().getId()==currentUser.getId()){
            return true;
        }
        else return false;
    }


    public int getNumberOfProductUsersForProduct(int id){
        return this.productUserRespository.getNumberOfProductUsersForProduct(id);
    }

    public int getNumberOfPagesForProduct(int id){
        int numberOfProductUsers = getNumberOfProductUsersForProduct(id);
        int numberOfPages = 0;
        if(numberOfProductUsers%10==0){
            numberOfPages = numberOfProductUsers/10;
        }
        else{
            numberOfPages = (int)(numberOfProductUsers/10)+1;
        }
        return numberOfPages;
    }



    public ProductUser add(ProductUser productUser){

            return this.productUserRespository.save(productUser);
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
            int newRating = 0;
            String newComment = "";
            if(rating!=null){
                if(rating.intValue()>=0 && rating.intValue()<=6)
                {
                    newRating = rating.intValue();
                }
            }
            if(newComment != null){
                newComment = comment;
            }
//            if(rating==null) {
//                newRating=getProductUser(id).getRating();
//
//            }else{
//                newRating = rating.intValue();
//            }
//            if(comment == null){
//                newComment = getProductUser(id).getComment();
//            }else{
//                newComment=comment;
//            }
            long date = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw")).getTimeInMillis();

            this.productUserRespository.updateComment(newComment, newRating, date, id);
            return true;

        }
        return false;
    }

    public List<ProductUser> getAllProductUsers(int productID,Pageable pageable){
        return this.productUserRespository.findAllProductUsers(productID,pageable);
    }


}
