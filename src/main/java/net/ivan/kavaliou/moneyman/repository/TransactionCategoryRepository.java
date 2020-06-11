package net.ivan.kavaliou.moneyman.repository;

import net.ivan.kavaliou.moneyman.model.persistence.TransactionCategory;
import net.ivan.kavaliou.moneyman.model.persistence.TransactionTypes;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Integer> {

    List<TransactionCategory> findByUserAndAndTransactionType(User user, TransactionTypes transactionType);
    Optional<TransactionCategory> findByUserAndId(User user, Integer id);
    Optional<TransactionCategory> findOneByUserAndAndTransactionTypeAndName(User user, TransactionTypes transactionType, String name);
}
