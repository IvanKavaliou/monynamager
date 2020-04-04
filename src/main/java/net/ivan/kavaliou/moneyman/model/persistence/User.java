package net.ivan.kavaliou.moneyman.model.persistence;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.moneyman.utils.enums.CurrencyType;
import net.ivan.kavaliou.moneyman.utils.enums.TransactionType;
import net.ivan.kavaliou.moneyman.utils.enums.UserRoles;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User{
    @Id
    @Column(name = "id", unique = true, nullable = false, columnDefinition = "integer default nextval('global_seq')")
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank
    @Size(max = 100)
    private String email;
    @Column(name = "password", nullable = false)
    @NotBlank
    @Size(min = 4, max = 100)
    private String password;

    @Column(name = "registred", nullable = false, columnDefinition = "timestamp default now()")
    @NotNull
    private Date registred = new Date();

    @Column(name = "enabled", nullable = false, columnDefinition = "bool default true")
    private boolean enabled = true;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_currency", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @NotNull
    private Currency currency;

    @ElementCollection(targetClass = UserRoles.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_roles", joinColumns = @JoinColumn(name = "id_users"))
    @Enumerated(EnumType.STRING)
    private Set<UserRoles> roles;

    @ManyToMany(cascade = { CascadeType.ALL })
    @JoinTable(
            name = "users_currency",
            joinColumns = { @JoinColumn(name = "id_users") },
            inverseJoinColumns = { @JoinColumn(name = "id_currency") }
    )
    private Set<Currency> userCurrencys;
}
