package by.park.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString
@EqualsAndHashCode
@Entity
@Table(name = "m_bank_account")
public class BankAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    @Column
    private String iban;

    @Column
    private Long amount;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_user")
    private User idUser;

    @ManyToOne
    @JsonBackReference
    @JoinColumn(name = "id_bank")
    private Bank idBank;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

    @Column(name = "is_deleted")
    private Boolean deleted;

    @OneToMany(mappedBy = "idBankAccount")
    private Set<Card> cards = new HashSet<>();

    @OneToMany(mappedBy = "idBankAccount")
    private Set<Transaction> transactions = new HashSet<>();
}
