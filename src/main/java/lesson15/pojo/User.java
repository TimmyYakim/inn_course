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

    public User(String name, Date birthday, int login_ID, String city, String email, String description) {
        this.name = name;
        this.birthday = birthday;
        this.login_ID = login_ID;
        this.city = city;
        this.email = email;
        this.description = description;
    }

    public User(Integer id, String name, Date birthday, int login_ID, String city, String email, String description) {
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
        checkValue(id);
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        checkValue(name);
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        checkValue(birthday);
        this.birthday = birthday;
    }

    public int getLoginId() {
        return login_ID;
    }

    public void setLoginId(int login_ID) {
        checkValue(login_ID);
        this.login_ID = login_ID;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        checkValue(city);
        this.city = city;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        checkValue(email);
        this.email = email;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        checkValue(description);
        this.description = description;
    }


    private void checkValue(Object value) {
        if (Objects.isNull(value))
            throw new IllegalArgumentException("Null is not allowed");
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
        return login_ID == user.login_ID && name.equals(user.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(login_ID, name);
    }
}
