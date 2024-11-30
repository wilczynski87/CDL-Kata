package cdl.kata.checkout.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cdl.kata.checkout.model.entity.ProductEntity;
import cdl.kata.checkout.model.entity.SpecialOfferEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class MainServiceTest {

    @Mock
    private PrintingOutput printingOutput;
    @Mock
    private ReadingInput readingInput;
    @Mock
    private ProductService productService;
    @Mock
    private SpecialOfferService specialOfferService;
    @Mock
    private CartService cartService;

    private MainService mainService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mainService = new MainService(printingOutput, readingInput, productService, specialOfferService, cartService);
    }

    @Test
    void testAddSpecialOffer_ValidInput() {
        // Mock product list and inputs
        ProductEntity product = new ProductEntity(1L, "SKU1", new BigDecimal("10.00"), null);
        SpecialOfferEntity specialOffer = new SpecialOfferEntity(1L, 5L, new BigDecimal("40.00"));

        when(productService.getAllProducts()).thenReturn(List.of(product));
        when(readingInput.checkSKU()).thenReturn("SKU1", "X");
        when(productService.checkProduct("SKU1")).thenReturn(true);
        when(productService.findProduct("SKU1")).thenReturn(product);
        when(readingInput.checkQuantity()).thenReturn(5L);
        when(readingInput.checkPrice()).thenReturn(new BigDecimal("40.00"));
        when(specialOfferService.createSpecialOffer(5L, new BigDecimal("40.00"))).thenReturn(specialOffer);

        mainService.treeOfChoice();

        verify(productService).updateSpecialOffer(product);
        verify(printingOutput).printProducts(anyList());
    }

    @Test
    void testTreeOfChoice_Exit() {
        // Mock immediate exit
        when(readingInput.checkSKU()).thenReturn("X");

        mainService.treeOfChoice();

        verifyNoInteractions(productService, cartService, specialOfferService);
    }

    @Test
    void testScanProducts_ValidInput() {
        ProductEntity product = new ProductEntity(1L, "SKU1", new BigDecimal("10.00"), null);

        when(productService.getAllProducts()).thenReturn(List.of(product));
        when(readingInput.checkSKU()).thenReturn("SKU1", "X");
        when(readingInput.checkProduct("SKU1")).thenReturn(product);

        mainService.treeOfChoice();

        verify(cartService).addToCart(product);
        verify(printingOutput).printRecipe(cartService.getCart());
    }

    @Test
    void testAddSpecialOffer_InvalidSKU() {
        // Mock invalid SKU input
        when(productService.getAllProducts()).thenReturn(List.of(new ProductEntity(1L, "SKU1", new BigDecimal("10.00"), null)));
        when(readingInput.checkSKU()).thenReturn("InvalidSKU", "X");

        mainService.treeOfChoice();

        verify(printingOutput, atLeastOnce()).printProducts(anyList());
        verifyNoInteractions(specialOfferService);
    }
}
