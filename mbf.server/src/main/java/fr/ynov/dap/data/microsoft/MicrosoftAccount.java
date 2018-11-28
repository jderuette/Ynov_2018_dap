package fr.ynov.dap.data.microsoft;

import fr.ynov.dap.data.google.AppUser;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity
public class MicrosoftAccount {

    @Id
    @GeneratedValue
    private Integer id;

    @ManyToOne
    private AppUser owner;

    private String accountName;

    public final Integer getId() {
            return id;
        }

    public final void setId(Integer id) {
            this.id = id;
        }

    public final AppUser getOwner() {
            return owner;
        }

    public final void setOwner(AppUser owner) {
            this.owner = owner;
        }

    public final String getAccountName() {
            return accountName;
        }

    public final void setAccountName(String name) {
            this.accountName = name;
        }
}
