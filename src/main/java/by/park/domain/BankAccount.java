package by.park.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

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
    private String amount;

    @Column(name = "id_user")
    private String idUser;

    @Column(name = "id_bank")
    private String idBank;

    @Column
    private String created;

    @Column
    private String changed;

    @Column(name = "is_deleted")
    private String deleted;
}
