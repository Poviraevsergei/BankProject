package by.park.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;

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
@ToString(exclude = {
        "idBankAccount"
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {
        "idBankAccount"
})
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

    @Column(name = "card_number")
    private String cardNumber;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_bank_account")
    private BankAccount idBankAccount;

    @Column(name = "transaction_time")
    private Timestamp transactionTime;
}