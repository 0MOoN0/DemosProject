package test.spring_tx.entry;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

/**
 * @author Leon
 */
@Entity
@Table(name = "account")
@Data
public class Account {

    @Id
    private Integer id;
    private BigDecimal money;

}
