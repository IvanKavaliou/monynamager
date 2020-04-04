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
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "integer default nextval('global_seq')")
    private Integer id;

    @Column(name = "code", nullable = false)
    @NotBlank
    @Size(min = 3, max = 3)
    @Enumerated(EnumType.STRING)
    private CurrencyType currencyType;
}
