package cdl.kata.checkout.service;

import cdl.kata.checkout.model.entity.ProductEntity;
import cdl.kata.checkout.model.entity.SpecialOfferEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class CartServiceTest {

    @Mock
    private ProductService productService;

    private CartService cartService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cartService = new CartService(productService);
    }

    @Test
    void testCleanCart() {
        List<ProductEntity> products = List.of(
            new ProductEntity(1L, "SKU1", BigDecimal.TEN, null),
            new ProductEntity(2L, "SKU2", BigDecimal.valueOf(20), null)
        );
        when(productService.getAllProducts()).thenReturn(products);

        cartService.cleanCart();

        assertEquals(2, cartService.getCart().size());
        assertEquals(new ArrayList<>(), cartService.getCart().get("SKU1"));
        assertEquals(new ArrayList<>(), cartService.getCart().get("SKU2"));
    }

    @Test
    void testAddToCart() {
        cartService.cleanCart();
        ProductEntity product = new ProductEntity(1L, "SKU1", BigDecimal.TEN, null);
        cartService.getCart().put("SKU1", new ArrayList<>());

        cartService.addToCart(product);

        assertEquals(1, cartService.getCart().get("SKU1").size());
        assertEquals(product, cartService.getCart().get("SKU1").get(0));
    }

    @Test
    void testCalculateProductList_NoSpecialOffer() {
        List<ProductEntity> productList = List.of(
            new ProductEntity(1L, "SKU1", BigDecimal.TEN, null),
            new ProductEntity(1L, "SKU1", BigDecimal.TEN, null)
        );

        BigDecimal result = CartService.calculateProductList(productList);

        assertEquals(BigDecimal.valueOf(20), result);
    }

    @Test
    void testCalculateProductList_WithSpecialOffer() {
        SpecialOfferEntity specialOffer = new SpecialOfferEntity(1L, 3L, BigDecimal.valueOf(25));
        List<ProductEntity> productList = List.of(
            new ProductEntity(1L, "SKU1", BigDecimal.TEN, specialOffer),
            new ProductEntity(1L, "SKU1", BigDecimal.TEN, specialOffer),
            new ProductEntity(1L, "SKU1", BigDecimal.TEN, specialOffer),
            new ProductEntity(1L, "SKU1", BigDecimal.TEN, specialOffer)
        );

        BigDecimal result = CartService.calculateProductList(productList);

        assertEquals(BigDecimal.valueOf(35), result); // 25 for 3 + 10 for the remainder
    }

    @Test
    void testCalculateWholeCart() {
        SpecialOfferEntity specialOffer = new SpecialOfferEntity(1L, 3L, BigDecimal.valueOf(25));
        ProductEntity product1 = new ProductEntity(1L, "SKU1", BigDecimal.TEN, specialOffer);
        ProductEntity product2 = new ProductEntity(2L, "SKU2", BigDecimal.valueOf(15), null);

        cartService.cleanCart();
        cartService.getCart().put("SKU1", List.of(product1, product1, product1, product1));
        cartService.getCart().put("SKU2", List.of(product2, product2));

        BigDecimal result = CartService.calculateWhole(cartService.getCart());

        assertEquals(BigDecimal.valueOf(65), result); // 35 (SKU1) + 30 (SKU2)
    }

    @Test
    void testCalculateProductList_EmptyList() {
        BigDecimal result = CartService.calculateProductList(new ArrayList<>());

        assertEquals(BigDecimal.ZERO, result);
    }
}
