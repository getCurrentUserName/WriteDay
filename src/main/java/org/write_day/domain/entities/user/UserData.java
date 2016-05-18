package org.write_day.domain.entities.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.UUID;

/**
 * Created by LucidMinds on 15.05.16.
 * package org.write_day.domain.entities.user;
 */
@Entity
@Table(name = "user_data_table")
@DynamicUpdate
@DynamicInsert
public class UserData {

    public static final String ID = "id";
    public static final String FIRST_NAME = "firstName";
    public static final String LAST_NAME = "lastName";
    public static final String EMAIL = "email";
    public static final String PHONE = "phone";
    public static final String ABOUT = "about";

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = ID)
    @Type(type="pg-uuid")
    @JsonIgnore
    private UUID id;

    /** Имя */
    @Column(name = FIRST_NAME, length = 32)
    private String firstName;

    /** Фамилия */
    @Column(name = LAST_NAME, length = 32)
    private String lastName;

    /** Телефон */
    @Column(name = PHONE, length = 32)
    private String phone;

    /** Email */
    @Column(name = EMAIL, length = 32)
    private String email;

    /** О пользователе */
    @Column(name = ABOUT, length = 500)
    private String about;

    @JsonBackReference
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "userData", optional = true)
    private User user;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }
}
