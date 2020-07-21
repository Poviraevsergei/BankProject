package by.park.repository;

import by.park.domain.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface TransactionRepository extends JpaRepository<Transaction, Long> {
    @Modifying
    @Query("update Transaction t set t.deleted = true where t.id=:id")
    void deleteTransactionById(@Param("id") Long id);
}
