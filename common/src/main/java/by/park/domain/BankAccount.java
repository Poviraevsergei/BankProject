package by.park.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.Set;

@Setter
@Getter
@ToString(exclude = {
        "userId", "idBank"
})
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(exclude = {
        "userId", "idBank"
})
@Entity
@Table(name = "m_bank_account")
public class BankAccount implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String iban;

    @Column
    private Long amount;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_user")
    private User userId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "id_bank")
    private Bank idBank;

    @Column
    private Timestamp created;

    @Column
    private Timestamp changed;

    @JsonManagedReference
    @OneToMany(mappedBy = "idBankAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Card> cards = new HashSet<>();

    @JsonManagedReference
    @OneToMany(mappedBy = "idBankAccount", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private Set<Transaction> transactions = new HashSet<>();
}