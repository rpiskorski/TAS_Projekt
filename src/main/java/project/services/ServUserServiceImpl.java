package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.model.ServUser;
import project.model.User;
import project.repositories.ServUserRepository;
import project.repositories.UserRepository;

import javax.print.attribute.standard.Severity;
import java.util.Calendar;
import java.util.Optional;
import java.util.TimeZone;

@Service
public class ServUserServiceImpl implements ServUserService {

    @Autowired
    ServUserRepository servUserRepository;

    @Autowired
    UserRepository userRepository;

    public ServUser add(ServUser servUser){

        return this.servUserRepository.save(servUser);

    }

    public void delete(int id){
        this.servUserRepository.deleteById(id);
    }

    public boolean checkIfExists(int servID,int userID){
        ServUser su = this.servUserRepository.checkIfExists(servID,userID);
        if(su!=null){
            return true;
        }
        else{
            return false;
        }
    }

    public boolean checkIfExists(int commentID){
        Optional<ServUser> su = this.servUserRepository.findById(commentID);
        if(su.isPresent()){
            return true;
        }else{
            return false;
        }

    }

    public ServUser getServUser(int id){
        Optional<ServUser> su = this.servUserRepository.findById(id);
        if(su.isPresent()){
            return su.get();
        }else{
            return null;
        }
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
//                newRating=getServUser(id).getRating();
//            }else{
//                newRating = rating.intValue();
//            }
//            if(comment == null){
//                newComment = getServUser(id).getComment();
//            }else{
//                newComment=comment;
//            }
            long date = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw")).getTimeInMillis();

            this.servUserRepository.updateComment(newComment, newRating, date, id);
            return true;

        }
        return false;
    }

    public boolean isOwner(int id){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = this.userRepository.findOneByName(userDetails.getUsername());

        ServUser su = this.getServUser(id);

        if(su!=null && currentUser.getId()==su.getUserS().getId()){
            return true;
        }else{
            return false;
        }
    }

}
