package cdl.kata.checkout.service;

import java.math.BigDecimal;

import org.springframework.stereotype.Service;

import cdl.kata.checkout.model.entity.SpecialOfferEntity;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SpecialOfferService {

    public SpecialOfferEntity createSpecialOffer(Long quantity, BigDecimal price) {
        return new SpecialOfferEntity(null, quantity, price);
    }
}
