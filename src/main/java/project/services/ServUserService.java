package project.services;

import project.model.ServUser;

public interface ServUserService {

    public ServUser add(ServUser servUser);

    public void delete(int id);

    public boolean checkIfExists(int servID,int userID);

    public boolean checkIfExists(int commentID);

    public ServUser getServUser(int id);

    public boolean editComment(String comment,Integer rating,int id);

    public boolean isOwner(int id);
}
