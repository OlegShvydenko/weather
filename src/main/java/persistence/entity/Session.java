package persistence.entity;

import jakarta.persistence.*;
import java.sql.Timestamp;

@Entity
@Table(name = "sessions")
public class Session {
    @Id
    @Column(name = "id")
    private String id;
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "expires_at")
    private Timestamp expires_at;


    public Session(String id, User user, Timestamp expires_at) {
        this.id = id;
        this.user = user;
        this.expires_at = expires_at;
    }
    public Session() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Timestamp getExpires_at() {
        return expires_at;
    }

    public void setExpires_at(Timestamp expires_at) {
        this.expires_at = expires_at;
    }
}
