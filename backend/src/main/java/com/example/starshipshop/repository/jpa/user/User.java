package com.example.starshipshop.repository.jpa.user;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
@Entity
@Table(name = "user")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_user", updatable = false, columnDefinition = "BIGINT")
    private Long id;
    @Column(name = "pseudo", nullable = false)
    private String pseudo;
    
    @JsonBackReference
    @ToString.Exclude
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_account", nullable = false, foreignKey = @ForeignKey(name = "FK_user_account"))
    private Account account;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Address> addresses;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Email> emails;
    
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Telephone> telephones;
}
