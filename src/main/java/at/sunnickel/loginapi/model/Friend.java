package at.sunnickel.loginapi.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

/**
 * The type Friend.
 */
@Entity
@Table(name = "friends")
public class Friend {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id = 0L;

    @ManyToOne
    @JsonProperty(value = "user_1")
    @JoinColumn(name = "user_id_1", nullable = false)
    private User user1;

    @ManyToOne
    @JsonProperty(value = "user_2")
    @JoinColumn(name = "user_id_2", nullable = false)
    private User user2;

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Gets user 1.
     *
     * @return the user 1
     */
    public User getUser1() {
        return user1;
    }

    /**
     * Sets user 1.
     *
     * @param user1 the user 1
     */
    public void setUser1(User user1) {
        this.user1 = user1;
    }

    /**
     * Gets user 2.
     *
     * @return the user 2
     */
    public User getUser2() {
        return user2;
    }

    /**
     * Sets user 2.
     *
     * @param user2 the user 2
     */
    public void setUser2(User user2) {
        this.user2 = user2;
    }
}
