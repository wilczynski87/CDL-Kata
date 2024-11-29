package cdl.kata.checkout.service;

import java.util.Scanner;

import org.springframework.stereotype.Service;

import cdl.kata.checkout.model.entity.ProductEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ReadingInput {

    private final ProductService productService;
    private final Scanner scanner = new Scanner(System.in);

    public String checkSKU() {
        while(true) {
            String productSku = scanner.nextLine();
            if(productService.checkProduct(productSku.trim().toUpperCase())) {
                return productSku.trim().toUpperCase();
            } else if(productSku.trim().toUpperCase().equals("X")) {
                return "X";
            } else {
                System.out.println("I do not have these product: " + productSku);
                System.out.println("Please, try again...");
            }
        }
    }

    public ProductEntity checkProduct(String sku) {
        while(true) {
            var check = productService.checkProduct(sku);
            if(check) {
                return productService.findProduct(sku);
            } else {
                System.out.println("Sorry, product with given name, do not exist :-(");
            }
                
        }
    }

}
