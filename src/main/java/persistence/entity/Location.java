package persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "latitude")
    private double latitude;
    @Column(name = "longitude")
    private double longitude;

    public Location(int id, String name, User user, double latitude, double longitude) {
        this.id = id;
        this.name = name;
        this.user = user;
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Location() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }
}
