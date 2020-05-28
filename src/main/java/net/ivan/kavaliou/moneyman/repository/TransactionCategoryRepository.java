package net.ivan.kavaliou.moneyman.repository;

import net.ivan.kavaliou.moneyman.model.persistence.TransactionCategory;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionCategoryRepository extends JpaRepository<TransactionCategory, Integer> {

   // @Query("SELECT tc FROM transaction_category tc WHERE tc.user.id = :id_users AND tc.transactionType.id = :id_transaction_types")
  //  List<TransactionCategory> findByIdTransactionTypesAndIdUsers(@Param("id_transaction_types")Integer id_transaction_types, @Param("id_users")Integer id_users);
}
