package test.spring_tx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import test.spring_tx.Dao.AccountRepository;
import test.spring_tx.entry.Account;

import java.math.BigDecimal;

/**
 * @author Leon
 */
@Service
public class AccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Transactional
    public void addMoney(Account account){
        accountRepository.addMoney(account.getId(),account.getMoney());
        Account decreateAccount = new Account();
        decreateAccount.setId(2);
        decreateAccount.setMoney(new BigDecimal(10));
        decreateMoney(decreateAccount);
//        int i = 1/0;      默认情况下，addMoney和decreateAccount两个方法的SQL语句都会回滚，因为他们在同一个事务中
    }

    @Transactional
    public void decreateMoney(Account account){
        accountRepository.decreateMoney(account.getId(),account.getMoney());
//        int i = 1/0;      默认情况下，如果addMoney调用decreateMoney，则两个方法执行的SQL语句都会回滚，因为Spring
        // 事务的传播属性默认会将decreateMoney加入addMoney事务。
    }

}
