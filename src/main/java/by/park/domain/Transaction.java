package by.park.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "m_transactions")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type_of_transaction")
    private String typeOfTransaction;

    @Column
    private Long count;

    @Column(name = "id_bank_account")
    private Long idBankAccount;

    @Column(name = "transaction_time")
    private Timestamp transactionTime;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

    @Column(name = "is_deleted")
    private Boolean isDeleted;
}
