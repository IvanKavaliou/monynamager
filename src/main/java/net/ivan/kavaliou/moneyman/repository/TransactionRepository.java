package net.ivan.kavaliou.moneyman.repository;

import net.ivan.kavaliou.moneyman.model.persistence.Currency;
import net.ivan.kavaliou.moneyman.model.persistence.Transaction;
import net.ivan.kavaliou.moneyman.model.persistence.TransactionCategory;
import net.ivan.kavaliou.moneyman.model.persistence.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

     List<Transaction> findByUserAndDateBetween(User user, LocalDateTime startDate, LocalDateTime endDate);
     List<Transaction> findByUserAndCurrency(User user, Currency currency);
     Optional<Transaction> findByUserAndId(User user, Integer id);
     List<Transaction> findByUserAndTransactionCategory(User user, TransactionCategory transactionCategory);
}
