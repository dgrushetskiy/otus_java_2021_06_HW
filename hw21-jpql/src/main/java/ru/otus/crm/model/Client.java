package ru.otus.crm.model;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "client", schema = "scheme_otus")
public class Client implements Cloneable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private Long id;

    @Column(name = "name", length = 50)
    private String name;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Phone> phones = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "address_id", nullable = false, foreignKey = @ForeignKey(name = "fk_client_address"))
    private Address address;

    public Client() {
    }

    public Client(String name) {
        this.id = null;
        this.name = name;
    }

    public Client(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public Client(Long id, String name,  Address address, List<Phone> phones) {
        this.id = id;
        this.name = name;
        this.phones = phones;
        this.address = address;
    }

    @Override
    public Client clone() {
        Client clonedClient = new Client(this.id, this.name);
        if (this.address != null) {
            Address newAddress = this.address.clone();
            clonedClient.setAddress(newAddress);
        }
        List<Phone> clonedPhones = phones.stream().map(p -> {
            Phone clonedPhone = p.clone();
            clonedPhone.setClient(clonedClient);
            return clonedPhone;
        }).collect(Collectors.toList());
        clonedClient.setPhones(clonedPhones);
        return clonedClient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Phone> getPhones() {
        return phones;
    }

    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
