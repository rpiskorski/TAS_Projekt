package project.services;

import org.springframework.data.domain.Pageable;
import project.model.ServUser;

import java.util.List;

public interface ServUserService {

    public ServUser add(ServUser servUser);

    public void delete(int id);

    public boolean checkIfExists(int servID,int userID);

    public boolean checkIfExists(int commentID);

    public ServUser getServUser(int id);

    public boolean editComment(String comment,Integer rating,int id);

    public boolean isOwner(int id);

    public List<ServUser> getAllServiceUsers(int serviceID, Pageable pageable);

    public int getNumberOfServiceUsersForService(int id);

    public int getNumberOfPagesForService(int id);

    public List<ServUser> getAllServiceUsersForUser(int userID,Pageable pageable);

    public int getNumberOfServiceUsersForUsers(int id);

    public int getNumberOfPagesForUsers(int id);
}
