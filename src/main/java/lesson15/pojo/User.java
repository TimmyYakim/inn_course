package lesson15.pojo;

import java.util.Date;
import java.util.Objects;

/**
 * Модель пользователя
 *
 * @author Timofey Yakimov
 */
public class User {

    private int id;
    private String name;
    private Date birthday;
    private int login_ID;
    private String city;
    private String email;
    private String description;

    public User(){}

    public User(int id, String name, Date birthday, int login_ID, String city, String email, String description) {
        this.id = id;
        this.name = name;
        this.birthday = birthday;
        this.login_ID = login_ID;
        this.city = city;
        this.email = email;
        this.description = description;
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

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public int getLogin_ID() {
        return login_ID;
    }

    public void setLogin_ID(int login_ID) {
        this.login_ID = login_ID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", birthday=" + birthday +
                ", login_ID=" + login_ID +
                ", city='" + city + '\'' +
                ", email='" + email + '\'' +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return id == user.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
