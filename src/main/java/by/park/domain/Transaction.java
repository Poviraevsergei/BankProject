package by.park.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
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

    @ManyToOne
    @JoinColumn(name = "id_bank_account")
    private BankAccount idBankAccount;

    @Column(name = "transaction_time")
    private Timestamp transactionTime;
}
