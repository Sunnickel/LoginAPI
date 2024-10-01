package at.sunnickel.loginapi.model;

import at.sunnickel.loginapi.enums.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

/**
 * The type User.
 */
@Entity
@Table(name = "user")
public class User {
    @Id
    @JsonProperty(value = "id")
    private long id = 0L;


    @JsonProperty(value = "name")
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @JsonProperty(value = "password")
    @Column(name = "hashed_password", nullable = false)
    private String password;

    @JsonProperty(value = "status")
    @Column(name = "status")
    @Enumerated(EnumType.ORDINAL)
    private Status status = Status.OFFLINE;

    @JsonProperty(value = "token")
    @Column(name = "token", nullable = false)
    private String token;

    @JsonProperty(value = "ottoken")
    @Column(name = "ottoken")
    private String ottoken;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
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
     * Sets password and hashes it.
     */
    public void setPassword() {
        this.password = password;
    }


    /**
     * Gets status.
     *
     * @return the status
     */
    public Status getStatus() {
        return this.status;
    }

    /**
     * Sets status.
     *
     * @param status the status
     */
    public void setStatus(Status status) {
        this.status = status;
    }

    /**
     * Gets the Login Token
     *
     * @return the token
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Sets the token
     *
     * @param token the login token
     */
    public void setToken(String token) {
        this.token = token;
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
     * @Param ottoken the One Time Token
     */
    public void setOTToken(String ottoken) {
        this.ottoken = ottoken;
    }
}

