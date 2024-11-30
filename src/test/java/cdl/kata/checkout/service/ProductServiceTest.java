package cdl.kata.checkout.service;

import cdl.kata.checkout.model.entity.ProductEntity;
import cdl.kata.checkout.repository.ProductEntityRepo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductServiceTest {

    @Mock
    private ProductEntityRepo productEntityRepo;

    private ProductService productService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productService = new ProductService(productEntityRepo);
    }

    @Test
    void testCheckProduct_Exists() {
        when(productEntityRepo.existsBySku("SKU1")).thenReturn(true);

        Boolean result = productService.checkProduct("SKU1");

        assertTrue(result);
        verify(productEntityRepo).existsBySku("SKU1");
    }

    @Test
    void testCheckProduct_NotExists() {
        when(productEntityRepo.existsBySku("SKU1")).thenReturn(false);

        Boolean result = productService.checkProduct("SKU1");

        assertFalse(result);
        verify(productEntityRepo).existsBySku("SKU1");
    }

    @Test
    void testFindProduct_Found() {
        ProductEntity product = new ProductEntity(1L, "SKU1", BigDecimal.TEN, null);
        when(productEntityRepo.findBySku("SKU1")).thenReturn(List.of(product));

        ProductEntity result = productService.findProduct("SKU1");

        assertNotNull(result);
        assertEquals(product, result);
        verify(productEntityRepo).findBySku("SKU1");
    }

    @Test
    void testFindProduct_NotFound() {
        when(productEntityRepo.findBySku("SKU1")).thenReturn(List.of());

        ProductEntity result = productService.findProduct("SKU1");

        assertNull(result);
        verify(productEntityRepo).findBySku("SKU1");
    }

    @Test
    void testUpdateSpecialOffer_Success() {
        ProductEntity product = new ProductEntity(1L, "SKU1", BigDecimal.TEN, null);
        ProductEntity updatedProduct = new ProductEntity(1L, "SKU1", BigDecimal.TEN, null);

        when(productEntityRepo.findBySku("SKU1")).thenReturn(List.of(product));
        when(productEntityRepo.save(product)).thenReturn(updatedProduct);

        ProductEntity result = productService.updateSpecialOffer(product);

        assertNotNull(result);
        assertEquals(updatedProduct, result);
        verify(productEntityRepo).findBySku("SKU1");
        verify(productEntityRepo).save(product);
    }

    @Test
    void testUpdateSpecialOffer_ProductNotFound() {
        ProductEntity product = new ProductEntity(1L, "SKU1", BigDecimal.TEN, null);

        when(productEntityRepo.findBySku("SKU1")).thenReturn(List.of());

        ProductEntity result = productService.updateSpecialOffer(product);

        assertNull(result);
        verify(productEntityRepo).findBySku("SKU1");
        verify(productEntityRepo, never()).save(any());
    }

    @Test
    void testGetAllProducts() {
        List<ProductEntity> products = List.of(
            new ProductEntity(1L, "SKU1", BigDecimal.TEN, null),
            new ProductEntity(2L, "SKU2", BigDecimal.valueOf(20), null)
        );
        when(productEntityRepo.findAll()).thenReturn(products);

        List<ProductEntity> result = productService.getAllProducts();

        assertEquals(products, result);
        verify(productEntityRepo).findAll();
    }
}
