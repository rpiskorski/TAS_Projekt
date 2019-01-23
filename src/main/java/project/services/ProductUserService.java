package project.services;

import project.model.ProductUser;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface ProductUserService {

    public ProductUser add(ProductUser productUser);

    public void delete(int id);

    public boolean checkIfExists(int productID,int userID);

    public boolean checkIfExists(int commentID);

    public ProductUser getProductUser(int id);

    public boolean editComment(String comment,Integer rating,int id);

    public boolean isOwner(int id);

    public List<ProductUser> getAllProductUsers(int productID,Pageable pageable);

    public int getNumberOfProductUsersForProduct(int id);

    public int getNumberOfPagesForProduct(int id);

    public List<ProductUser> getAllProductUsersForUser(int userID,Pageable pageable);

    public int getNumberOfProductUsersForUsers(int id);

    public int getNumberOfPagesForUsers(int id);

}
