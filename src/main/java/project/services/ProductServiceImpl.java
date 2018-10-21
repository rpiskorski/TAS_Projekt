package project.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import project.model.Category;
import project.model.Product;
import project.repositories.ProductRepository;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public List<Product> getAllProducts() {
        return this.productRepository.findAll();
    }


    public List<Product> getProductsByName(String name){
        return this.productRepository.findByName(name);
    }

    public List<Product> getAllProductsInCategory(int cat_id){
        return this.productRepository.findByCat(cat_id);
    }

    public List<Product> getProductsOrderByNameInCategoryAsc(int cat_id){
        return this.productRepository.findByCatOrderedByNameAsc(cat_id);
    }
    public List<Product> getProductsOrderByRaitingInCategoryAsc(int cat_id){
        return this.productRepository.findByCatOrderedByRaitingAsc(cat_id);
    }
    public List<Product> getProductsOrderByNameInCategoryDesc(int cat_id){
        return this.productRepository.findByCatOrderedByNameDesc(cat_id);
    }
    public List<Product> getProductsOrderByRaitingInCategoryDesc(int cat_id){
        return this.productRepository.findByCatOrderedByRaitingDesc(cat_id);
    }


    public List<Product> getProductsOrderByRaitingAsc(){
        return this.productRepository.findProductOrderByRaitingAsc();
    }

    public List<Product> getProductsOrderByRaitingDesc(){
        return this.productRepository.findProductOrderByRaitingDesc();
    }

    @Override
    public List<Product> getProductsOrderByNameAsc(){
        return this.productRepository.findProductOrderByNameAsc();
    }

    @Override
    public List<Product> getProductsOrderByNameDesc(){
        return this.productRepository.findProductOrderByNameDesc();
    }

//    @Override
//    public List<Product> getProductsOrderByName(String asc){
//        return this.productRepository.findProductOrderByName(asc);
//    }
//
//    @Override
//    public List<Product> getProductsOrderByRaiting(String asc){
//        return this.productRepository.findProductOrderByRaiting(asc);
//    }

    @Override
    public Product addProduct(Product product) {
        return this.productRepository.save(product);
    }

    @Override
    public void deleteProduct(int productID) {

        this.productRepository.deleteById(productID);
    }


}
