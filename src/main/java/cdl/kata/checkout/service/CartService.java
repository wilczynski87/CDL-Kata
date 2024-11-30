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

    // cart for products
    private Map<String, List<ProductEntity>> cart = new HashMap<>();

    //clear cart and create a map for new one
    public void cleanCart() {
        this.cart.clear();
        productService.getAllProducts().stream()
            .map(ProductEntity::getSku)
            .forEach(sku -> cart.put(sku, new ArrayList<>()));
    }

    // adding product to the cart
    public Map<String, List<ProductEntity>> addToCart(ProductEntity product) {
        this.cart.get(product.getSku()).add(product);
        return this.cart;
    }

    // calculating price for all products, with these same SKU
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
        // checking special price quatity
        if(productList.getFirst().getSpecialOffer().getSpecialQuantity() == null 
            || productList.getFirst().getSpecialOffer().getSpecialQuantity() == 0l
        ) return BigDecimal.ZERO;

        denominator = productList.getFirst().getSpecialOffer().getSpecialQuantity();

        var specialSumMeter = productCount / denominator;

        // checking special price, price
        if(productList.getFirst().getSpecialOffer().getSpecialPrice() == null) {
            specialPrice = productList.getFirst().getPrice();
        } else specialPrice = productList.getFirst().getSpecialOffer().getSpecialPrice();

        var divisionLeftouverMeter = productCount % denominator;

        // calculating all products for special price discount
        var specialSum = specialPrice.multiply(BigDecimal.valueOf(specialSumMeter));
        // calculating products for regular price
        var divisionLeftouver = regularPrice.multiply(BigDecimal.valueOf(divisionLeftouverMeter));

        // returing results
        return specialSum.add(divisionLeftouver);
    }

    // calculate the sum of all product
    public static BigDecimal calculateWhole(Map<String, List<ProductEntity>> cart) {
        
        return cart.keySet().stream()
            .map(cart::get)
            .map(CartService::calculateProductList)
            .reduce(BigDecimal.ZERO, BigDecimal::add)
            ;
    }

}
