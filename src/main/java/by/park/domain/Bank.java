package by.park.domain;

import lombok.*;

import javax.persistence.*;
import java.sql.Timestamp;

@Getter
@Setter
@EqualsAndHashCode
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "m_banks")

public class Bank {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private Long id;

   @Column(name = "bank_name")
   private String bankName;

   @Column(name = "phone_number")
   private String phoneNumber;

   @Column(name = "bank_code")
   private String bankCode;

   @Column
   private Timestamp created;

   @Column
   private Timestamp changed;

   @Column(name = "is_deleted")
   private Boolean deleted;
}
