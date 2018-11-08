package fr.ynov.dap.dap.data;

import javax.persistence.*;

@Entity
public class GoogleAccount {
    @Id
    @GeneratedValue
    Integer id;


    @Column
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @ManyToOne
    AppUser owner;

    public void setOwner(AppUser owner){
        this.owner = owner;
    }
}
