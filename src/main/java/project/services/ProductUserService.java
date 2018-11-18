package project.services;

import project.model.ProductUser;

public interface ProductUserService {

    public ProductUser add(ProductUser productUser);

    public void delete(int id);

    public boolean checkIfExists(int productID,int userID);

    public boolean checkIfExists(int commentID);

    public ProductUser getProductUser(int id);

    public boolean editComment(String comment,Integer rating,int id);

    public boolean isOwner(int id);
}
