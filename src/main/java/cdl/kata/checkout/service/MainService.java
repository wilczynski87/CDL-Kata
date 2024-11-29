package cdl.kata.checkout.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import org.springframework.stereotype.Service;

import cdl.kata.checkout.model.entity.ProductEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MainService {
    private final PrintingOutput printingOutput;
    private final ReadingInput readingInput;
    private final ProductService productService;
    private final SpecialOfferService specialOfferService;
    private final CartService cartService;
    private final Scanner scanner = new Scanner(System.in);

    public void treeOfChoice() {
        String choice;
        while(true) {
            choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    cartService.cleanCart();
                    scanProducts();
                    System.out.println("Thank you for your time! Enjoy your shopping!");
                    return;

                case "2":
                    addSpecialOffer();
                    break;
                
                case "x":
                    return;

                default:
                    System.out.println("Try again, put '1' for product list, '2' for adding special offer or exit by clicking 'x'!");
                    break;
            }
        }
    }

    private void scanProducts() {
        printingOutput.printProducts(productService.getAllProducts());
        System.out.println("To choose one, write item SKU and press Enter. (for example: A)");
        System.out.println("Basket can only take 1 item at the time!");
        
        while(true) {
            String sku = readingInput.checkSKU();
            if(sku.equals("X")) return;
            ProductEntity product = readingInput.checkProduct(sku);
            var cart = cartService.addToCart(product);
            printingOutput.printRecip(cart);
            System.out.println("Add another product, or put 'x' to EXIT");
        }



    }



    private void addSpecialOffer() {

        while(true) {
            System.out.println("Please choose, to which product, you want to add special pricing, by clicking providing 'SKU.");
            printingOutput.printProducts(productService.getAllProducts());
            System.out.println("Choose product?: ");
            String productSKU = scanner.nextLine();

            System.out.println("For how many products (quantity)?: ");
            Long quantity = Long.valueOf(scanner.nextLine());

            System.out.println("For which price?: ");
            BigDecimal price = BigDecimal.valueOf(Double.valueOf(scanner.nextLine())) ;

            System.out.println("Is this correct?:");
            System.out.println(String.format("Product: %s, %s for %s ", productSKU, quantity.toString(), price.toString()));

            System.out.println("press 'y' for YES or 'n' for NO");
            String answer = scanner.nextLine();
            if(answer.equals("y")) {

                var productCheck = productService.checkProduct(productSKU);

                if(productCheck) {
                    var forUpdate = productService.findProduct(productSKU);
                    var specialOffer = specialOfferService.createSpecialOffer(quantity, price);
                    // forUpdate.setId(quantity);
                    forUpdate.setSpecialOffer(specialOffer);
                    productService.updateSpecialOffer(forUpdate);
                    printingOutput.printProducts(productService.getAllProducts());
                    System.out.println("put '1' for product list, '2' for adding special offer or exit by clicking 'x'!");
                    return;
                } else System.out.println("Sorry, there is no such product..., try again :-)");
            }
        }


    }

}
