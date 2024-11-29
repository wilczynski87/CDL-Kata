package cdl.kata.checkout.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import cdl.kata.checkout.model.entity.SpecialOfferEntity;

public interface SpecialOfferRepo extends JpaRepository<SpecialOfferEntity, Long> {

}
