package com.codecool.elproyectegrande1.entity;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

@Entity
@DiscriminatorValue("admin")
public class Admin extends User {

    public Admin() {
    }

    public Admin(String username, String email, String password) {
        super(username, email, password);
    }
}
