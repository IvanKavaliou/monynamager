package net.ivan.kavaliou.moneyman.repository;

import net.ivan.kavaliou.moneyman.model.persistence.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction, Integer> {

    @Query("SELECT t FROM transaction t WHERE t.user.id = :id_users")
    public List<Transaction> getAllByIdUsers(@Param("id_users")Integer id_users);
}
