package cdl.kata.checkout.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import cdl.kata.checkout.model.entity.SpecialOfferEntity;
import cdl.kata.checkout.repository.SpecialOfferRepo;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecialOfferService {

    private final SpecialOfferRepo specialOfferRepo;

    public SpecialOfferEntity createSpecialOffer(Long quantity, BigDecimal price) {
        return new SpecialOfferEntity(null, quantity, price);
    }

}
