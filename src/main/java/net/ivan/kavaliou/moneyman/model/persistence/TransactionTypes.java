package net.ivan.kavaliou.moneyman.model.persistence;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import net.ivan.kavaliou.moneyman.utils.serializer.TransactionTypeSerializer;

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
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 100000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @Column(name = "code", nullable = false)
    @NotBlank
    @Size(min = 1, max = 10)
    @Enumerated(EnumType.STRING)
    @JsonSerialize(using = TransactionTypeSerializer.class)
    private TransactionType transactionType;
}
