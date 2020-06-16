package net.ivan.kavaliou.moneyman.repository;

import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CurrencyRepository extends JpaRepository<Currency, Integer> {
    Optional<Currency> findByCurrencyType(CurrencyType type);
}
