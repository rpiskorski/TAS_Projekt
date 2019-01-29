package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import project.model.ServUser;
import project.model.User;
import project.repositories.ServUserRepository;
import project.repositories.UserRepository;

import javax.print.attribute.standard.Severity;
import java.util.Calendar;
import java.util.List;
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



    public boolean isOwner(int id){
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        User currentUser = this.userRepository.findOneByName(userDetails.getUsername());

        ServUser su = this.getServUser(id);

        if(su!=null && (currentUser.getId()==su.getUserS().getId() || currentUser.getRole().getName().contentEquals("ADMIN"))){
            return true;
        }else{
            return false;
        }
    }

    public int getNumberOfServiceUsersForService(int id){
        return this.servUserRepository.getNumberOfServiceUsersForService(id);
    }

    public int getNumberOfPagesForService(int id){
        int numberOfServiceUsers = getNumberOfServiceUsersForService(id);
        int numberOfPages = 0;
        if(numberOfServiceUsers%10==0){
            numberOfPages = numberOfServiceUsers/10;
        }
        else{
            numberOfPages = (int)(numberOfServiceUsers/10)+1;
        }
        return numberOfPages;
    }

    public List<ServUser> getAllServiceUsers(int serviceID, Pageable pageable){
        return this.servUserRepository.findAllServiceUsers(serviceID,pageable);
    }

    public List<ServUser> getAllServiceUsersForUser(int userID,Pageable pageable){
        return this.servUserRepository.findAllServiceUsersByUser(userID,pageable);
    }


    public ServUser editComment(String comment,int rating,int id){
//TODO dokończyć edycje komentarza
        if(isOwner(id)){
//            int newRating = 0;
            String newComment = "";

            if(comment != null){
                newComment = comment;
            }
            ServUser su = getServUser(id);

            long date = Calendar.getInstance(TimeZone.getTimeZone("Europe/Warsaw")).getTimeInMillis();

            this.servUserRepository.updateComment(newComment, rating, date, id);
            su.setComment(newComment);
            su.setRating(rating);
            su.setTimestamp(date);
            return su;

        }
        return null;
    }





    public int getNumberOfServiceUsersForUsers(int id){
        return this.servUserRepository.getNumberOfServiceUsersForUser(id);
    }

    public int getNumberOfPagesForUsers(int id){
        int numberOfServiceUsers = getNumberOfServiceUsersForUsers(id);
        int numberOfPages = 0;
        if(numberOfServiceUsers%10==0){
            numberOfPages = numberOfServiceUsers/10;
        }
        else{
            numberOfPages = (int)(numberOfServiceUsers/10)+1;
        }
        return numberOfPages;

    }

}
