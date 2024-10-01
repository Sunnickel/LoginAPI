package at.sunnickel.loginapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * The type User.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @JsonProperty(value = "id")
    private Long id = 0L;

    @JsonProperty(value = "name")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonProperty(value = "password")
    @Column(name = "hashed_password", nullable = false)
    private String password;


    @JsonProperty(value = "ottoken")
    @Column(name = "ottoken")
    private String ottoken = null;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPassword() {
        return password;
    }

    /**
     * Sets password
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * Tests if One Time Token exists
     *
     * @return true if One Time Token is there
     */
    public boolean testOTToken() {
        return ottoken != null;
    }

    /**
     * Gets and deletes the One Time token
     */
    public String getOTToken() {
        String ottoken = this.ottoken;
        this.ottoken = null;
        return ottoken;
    }

    /**
     * Sets the One Time Token
     *
     * @param ottoken the One Time Token
     */
    public void setOTToken(String ottoken) {
        this.ottoken = ottoken;
    }
}

