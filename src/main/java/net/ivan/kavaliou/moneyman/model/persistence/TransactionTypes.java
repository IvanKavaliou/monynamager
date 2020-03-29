package net.ivan.kavaliou.moneyman.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Entity
@Table(name = "transaction_types")
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TransactionTypes {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Integer id;

    @Column(name = "name", nullable = false)
    @NotBlank
    @Size(min = 1, max = 100)
    @Enumerated(EnumType.STRING)
    private TransactionType transactionType;
}
