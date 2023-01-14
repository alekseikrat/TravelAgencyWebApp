package ua.edu.znu.travelagencyweb.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.Set;

@Data
@Entity
@Table(name = "tours")
public class Tour {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "departure", nullable = false, length = 20)
    private String departure;
    @Column(name = "arrival", nullable = false, unique = true, length = 20)
    private String arrival;
    @Column(name = "transport", nullable = false, length = 20)
    private String transport;
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @ManyToMany(mappedBy = "tours", cascade = CascadeType.PERSIST)
    private Set<Client> clients = new LinkedHashSet<>();
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "tour", cascade = CascadeType.PERSIST, orphanRemoval = true)
    private Set<Employee> employees = new LinkedHashSet<>();
}