package lesson15.pojo;

import java.util.Objects;

/**
 * Пользователь - Роль
 *
 * @author Timofey Yakimov
 */
public class UserRole {

    private int id;
    private Integer user_id;
    private Integer role_id;

    public UserRole(int id, Integer user_id, Integer role_id) {
        this.id = id;
        this.user_id = user_id;
        this.role_id = role_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getUser_id() {
        return user_id;
    }

    public void setUser_id(Integer user_id) {
        this.user_id = user_id;
    }

    public Integer getRole_id() {
        return role_id;
    }

    public void setRole_id(Integer role_id) {
        this.role_id = role_id;
    }

    @Override
    public String toString() {
        return "UserRole{" +
                "id=" + id +
                ", user_id=" + user_id +
                ", role_id=" + role_id +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserRole userRole = (UserRole) o;
        return id == userRole.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
