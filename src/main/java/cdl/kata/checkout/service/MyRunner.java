package cdl.kata.checkout.service;

import java.math.BigDecimal;
import java.util.Scanner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import cdl.kata.checkout.model.entity.ProductEntity;
import cdl.kata.checkout.repository.ProductEntityRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class MyRunner implements CommandLineRunner {
    private final ProductEntityRepo productEntityRepo;

    @Override
    public void run(String... args) throws Exception {
        productEntityRepo.save(new ProductEntity(null, "string", BigDecimal.ONE, null));

        System.out.println(productEntityRepo.findAll().get(0));

        System.out.println("Enter word!");
        Scanner scanner = new Scanner(System.in);
        String line = scanner.nextLine();
        System.out.println(line);
     }
}
