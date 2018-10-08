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

    //PROBLEM Z TYM ZAPYTANIEM
//    public List<Product> getAllByRaitingAsc(){
//        return this.productRepository.findProductOrderByRaiting();
//    }

//    public List<Product> getAllByRaitingDesc(){
//        return this.productRepository.findEveryOrderByAvgDesc();
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
