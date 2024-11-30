package cdl.kata.checkout.service;

import cdl.kata.checkout.model.entity.SpecialOfferEntity;
import cdl.kata.checkout.service.SpecialOfferService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class SpecialOfferServiceTest {

    private SpecialOfferService specialOfferService;

    @BeforeEach
    void setUp() {
        specialOfferService = new SpecialOfferService();
    }

    @Test
    void testCreateSpecialOffer_ValidInputs() {
        Long quantity = 3L;
        BigDecimal price = BigDecimal.valueOf(99.99);

        SpecialOfferEntity result = specialOfferService.createSpecialOffer(quantity, price);

        assertNotNull(result);
        assertNull(result.getId());
        assertEquals(quantity, result.getSpecialQuantity());
        assertEquals(price, result.getSpecialPrice());
    }

    @Test
    void testCreateSpecialOffer_NullQuantity() {
        BigDecimal price = BigDecimal.valueOf(50.00);

        SpecialOfferEntity result = specialOfferService.createSpecialOffer(null, price);

        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getSpecialQuantity());
        assertEquals(price, result.getSpecialPrice());
    }

    @Test
    void testCreateSpecialOffer_NullPrice() {
        Long quantity = 2L;

        SpecialOfferEntity result = specialOfferService.createSpecialOffer(quantity, null);

        assertNotNull(result);
        assertNull(result.getId());
        assertEquals(quantity, result.getSpecialQuantity());
        assertNull(result.getSpecialPrice());
    }

    @Test
    void testCreateSpecialOffer_NullInputs() {
        SpecialOfferEntity result = specialOfferService.createSpecialOffer(null, null);

        assertNotNull(result);
        assertNull(result.getId());
        assertNull(result.getSpecialQuantity());
        assertNull(result.getSpecialPrice());
    }
}

