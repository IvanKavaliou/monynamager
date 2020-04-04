package net.ivan.kavaliou.moneyman.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;


@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Currency{
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 100000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @Column(name = "code", nullable = false)
    @Size(min = 3, max = 3)
    @NotBlank
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;
}
