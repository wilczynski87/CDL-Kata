package cdl.kata.checkout.model.entity;

import java.math.BigDecimal;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class SpecialOfferEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;
    private Long specialQuantity;
    private BigDecimal specialPrice;


    @Override
    public String toString() {
        return String.format("Special Price: %s for %s", specialQuantity, specialPrice.toString());
    }

}