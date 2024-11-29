package cdl.kata.checkout.service;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;

import cdl.kata.checkout.model.entity.ProductEntity;
import lombok.AllArgsConstructor;

@Component
@AllArgsConstructor
public class PrintingOutput {

    public void printProducts(List<ProductEntity> products){
        System.out.println("Product to choose from:");
        products.forEach(product -> {
            System.out.println(printProduct(product));
        });

    }

    private String printProduct(ProductEntity product) {
        String productOutput = "SKU: %s, Unit Price (pence): %s, Special Price (pence): %s";
        return String.format(productOutput, product.getSku(), product.getPrice().toString(), product.getSpecialOffer());
    }

    public void printRecip(Map<String, List<ProductEntity>> cart) {
        System.out.println("Current receipt: ");
        cart.forEach((key, products) -> {
            System.out.println(
                String.format("%s x %s = %s", products.size(), key, CartService.calculateProductList(products))
            );
        });
        System.out.println("Whole sum: " + CartService.calculateWhole(cart));
    }

}
