package at.sunnickel.loginapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "server")
public class Server {
    @Id
    private Long id = 0L;

    @JsonProperty(value = "password")
    @Column(name = "password", nullable = false, unique = true)
    private String password;

    @JsonProperty(value = "token")
    @Column(name = "token")
    private String token;

    /**
     * Gets the Login Token
     *
     * @return the token
     */
    public String getToken() {
        return this.token;
    }

    /**
     * Sets the Login Token
     *
     * @param token the new token
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * Gets the plain password
     *
     * @return password
     */
    public String getPassword() {
        return this.password;
    }

    /**
     * Sets the password
     *
     * @param password the new password
     */
    public void setPassword(String password) {
        this.password = password;
    }
}
