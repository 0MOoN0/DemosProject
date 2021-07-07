package test.spring_tx;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.spring_tx.entry.Account;
import test.spring_tx.service.AccountService;

import java.math.BigDecimal;

@SpringBootTest
class SpringtxApplicationTests {

    @Autowired
    private AccountService accountService;

    @Test
    void contextLoads() {
        // 模拟转账过程
        Account increateAccount = new Account();
        increateAccount.setId(1);
        increateAccount.setMoney(new BigDecimal(10));
        accountService.addMoney(increateAccount);
//        int i = 1/0;      如果这里报错，则不会回滚事务，因为这超出了addMoney方法的事务边界
//        Account decreateAccount = new Account();
//        decreateAccount.setId(2);
//        decreateAccount.setMoney(new BigDecimal(10));
//        accountService.decreateMoney(decreateAccount);
    }

}
