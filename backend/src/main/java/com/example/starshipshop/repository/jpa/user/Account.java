package com.example.starshipshop.repository.jpa.user;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_account", updatable = false, columnDefinition = "BIGINT")
    private Long id;
    @Column(name = "username", nullable = false)
    @Email(message = "Email must respect a valid email")
    private String username;
    @Column(name = "password", nullable = false)
    @Size(min = 8)
    private String password;
    @Column(name = "roles", nullable = false)
    private String roles;

    @Size(max = 5)
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "account")
    private List<User> users;
}
