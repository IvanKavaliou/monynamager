package net.ivan.kavaliou.moneyman.repository;

import net.ivan.kavaliou.moneyman.model.persistence.TransactionTypes;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionTypesRepository extends JpaRepository<TransactionTypes, Integer> {

    /*@Query("SELECT tt FROM transaction_types tt WHERE tt.code = :code")
    public TransactionTypes get(@Param("code")String code);*/

    public TransactionTypes findByTransactionType(TransactionType type);
}
