package cdl.kata.checkout.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cdl.kata.checkout.model.entity.ProductEntity;
import lombok.Data;

@Component
@Data
public class CartService {

    private final ProductService productService;

    private Map<String, List<ProductEntity>> cart = new HashMap<>();

    public void cleanCart() {
        this.cart.clear();
        productService.getAllProducts().stream()
            .map(ProductEntity::getSku)
            .forEach(sku -> cart.put(sku, new ArrayList<>()));
    }

    public Map<String, List<ProductEntity>> addToCart(ProductEntity product) {
        this.cart.get(product.getSku()).add(product);
        return this.cart;
    }

    public static BigDecimal calculateProductList(List<ProductEntity> productList) {
        // checking if there is any product of given type
        var productCount = productList.size();
        if(productList == null || productCount == 0) return BigDecimal.ZERO;

        // calculate if there is no Special Price
        var regularPrice = productList.getFirst().getPrice();
        if(productList.getFirst().getSpecialOffer() == null) {
            return regularPrice.multiply(BigDecimal.valueOf(productCount));
        }

        Long denominator;
        BigDecimal specialPrice;
        // checking special price
        if(productList.getFirst().getSpecialOffer().getSpecialQuantity() == null 
            || productList.getFirst().getSpecialOffer().getSpecialQuantity() == 0l
        ) return BigDecimal.ZERO;

        denominator = productList.getFirst().getSpecialOffer().getSpecialQuantity();

        var specialSumMeter = productCount / denominator;

        if(productList.getFirst().getSpecialOffer().getSpecialPrice() == null) {
            specialPrice = productList.getFirst().getPrice();
        } else specialPrice = productList.getFirst().getSpecialOffer().getSpecialPrice();

        var divisionLeftouverMeter = productCount % denominator;

        var specialSum = specialPrice.multiply(BigDecimal.valueOf(specialSumMeter));
        var divisionLeftouver = regularPrice.multiply(BigDecimal.valueOf(divisionLeftouverMeter));

        return specialSum.add(divisionLeftouver);
    }

    public static BigDecimal calculateWhole(Map<String, List<ProductEntity>> cart) {
        
        return cart.keySet().stream()
            .map(cart::get)
            .map(CartService::calculateProductList)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            ;
    }

}
