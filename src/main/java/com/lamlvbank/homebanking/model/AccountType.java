package com.lamlvbank.homebanking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.Length;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@Entity
public class AccountType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAT;

    @NotNull  @NotBlank @Size(min=8, max=40)
    @Column(unique = true,nullable=false)
    @Pattern(regexp ="^Account\\s([A-Za-z]+)$",message="Patron erroneo, debe ser Account [Palabra]")
    //@Length(min=8, max=40) combina @Notblank y @Size
    private String name;

}
