package cdl.kata.checkout.service;

import java.util.List;

import org.springframework.stereotype.Service;

import cdl.kata.checkout.model.entity.ProductEntity;
import cdl.kata.checkout.repository.ProductEntityRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductEntityRepo productEntityRepo;

    // checking if product is in database
    public Boolean checkProduct(String productSKU) {
        return productEntityRepo.existsBySku(productSKU);
    }

    public ProductEntity findProduct(String sku) {
        var product = productEntityRepo.findBySku(sku);

        if(product.isEmpty()) return null;

        return product.getFirst();
    }

    // updating special dicount/price rule
    public ProductEntity updateSpecialOffer(ProductEntity product) {

        var forUpdate = findProduct(product.getSku());

        if(forUpdate == null) return null;

        forUpdate.setSpecialOffer(product.getSpecialOffer());

        return productEntityRepo.save(forUpdate);
    }

    public List<ProductEntity> getAllProducts() {
        return productEntityRepo.findAll();
    }

}
