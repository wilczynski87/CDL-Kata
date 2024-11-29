package cdl.kata.checkout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cdl.kata.checkout.model.entity.ProductEntity;

public interface ProductEntityRepo extends JpaRepository<ProductEntity, Long> {

}
