package test.spring_tx.Dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import test.spring_tx.entry.Account;

import java.math.BigDecimal;

/**
 * @author Leon
 */
public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Modifying
    @Query(value = "UPDATE account SET MONEY = MONEY+?2 WHERE id = ?1", nativeQuery = true)
    void addMoney(Integer id, BigDecimal money);

    @Modifying
    @Query(value = "UPDATE account SET MONEY = MONEY-?2 WHERE id = ?1", nativeQuery = true)
    void decreateMoney(Integer id, BigDecimal money);

}
