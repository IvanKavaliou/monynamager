package net.ivan.kavaliou.moneyman.model.persistence;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.ivan.kavaliou.moneyman.utils.enums.UserRoles;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users", uniqueConstraints = {@UniqueConstraint(columnNames = "email", name = "users_unique_email_idx")})
public class User{
    @Id
    @SequenceGenerator(name = "global_seq", sequenceName = "global_seq", allocationSize = 1, initialValue = 100000)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "global_seq")
    private Integer id;

    @Column(name = "email", nullable = false, unique = true)
    @Email
    @NotBlank (message = "error.notBlank")
    @Size(max = 100)
    private String email;

    @Column(name = "password", nullable = false)
    @NotBlank (message = "error.notBlank")
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

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "users_currency",
            joinColumns = { @JoinColumn(name = "id_users") },
            inverseJoinColumns = { @JoinColumn(name = "id_currency") }
    )
    private Set<Currency> userCurrencys;
}
