package cdl.kata.checkout.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

import cdl.kata.checkout.model.entity.ProductEntity;
import cdl.kata.checkout.model.entity.SpecialOfferEntity;
import cdl.kata.checkout.repository.ProductEntityRepo;
import cdl.kata.checkout.service.MainService;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyRunner implements CommandLineRunner {
    private final ProductEntityRepo productEntityRepo;
    private final MainService mainService;

    @Override
    public void run(String... args) throws Exception {
        // entering sample products
        List<ProductEntity> sampleProducts = List.of(
            new ProductEntity(null, "A", BigDecimal.valueOf(50), new SpecialOfferEntity(null, 3l, BigDecimal.valueOf(130))),
            new ProductEntity(null, "B", BigDecimal.valueOf(30), new SpecialOfferEntity(null, 2l, BigDecimal.valueOf(45))),
            new ProductEntity(null, "C", BigDecimal.valueOf(20), null),
            new ProductEntity(null, "D", BigDecimal.valueOf(15), null)
        );
        productEntityRepo.saveAll(sampleProducts);

        // initializing sacanner
        // Scanner scanner = new Scanner(System.in);

        System.out.println("Hello in Checkout: ");
        System.out.println("If you wish for: begin adding products to basket, press '1'");
        System.out.println("If you wish for: adding pricing rules, press '2'");
        System.out.println("For exit put 'x'");

        // String choice = scanner.nextLi1ne();

        mainService.treeOfChoice();
        
    }
}
