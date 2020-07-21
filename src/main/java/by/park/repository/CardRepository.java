package by.park.repository;

import by.park.domain.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface CardRepository extends JpaRepository<Card, Long> {
    @Modifying
    @Query("update Card c set c.deleted = true where c.id=:id")
    void deleteCardById(@Param("id") Long id);
}
