package fr.ynov.dap.dap.data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class GoogleAccount {
    @Id
    @GeneratedValue
    Integer id;

    @ManyToOne
    AppUser owner;

    public void setOwner(AppUser owner){
        this.owner = owner;
    }
}
