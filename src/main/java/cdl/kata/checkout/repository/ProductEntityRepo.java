package cdl.kata.checkout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cdl.kata.checkout.model.entity.ProductEntity;
import java.util.List;


public interface ProductEntityRepo extends JpaRepository<ProductEntity, Long> {
    boolean existsBySku(String sku);

    List<ProductEntity> findBySku(String sku);
}
