package lesson15.pojo;

import java.util.Objects;

/**
 * Модель роли
 *
 * @author Timofey Yakimov
 */
public class Role {

    private int id;
    private RoleName name;
    private String description;

    public enum RoleName {Administration, Clients, Billing}

    public Role(RoleName name, String description) {
        checkValue(name);
        checkValue(description);
        this.name = name;
        this.description = description;
    }

    public Role(Integer id, String name, String description) {
        checkValue(id);
        checkValue(name);
        checkValue(description);
        this.id = id;
        this.name = RoleName.valueOf(name);
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
        return name.toString();
    }

    public void setName(RoleName name) {
        checkValue(name);
        this.name = name;
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
        return "Role{" +
                "id=" + id +
                ", name=" + name +
                ", description='" + description + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return name.equals(role.name) && description.equals(role.description);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, description);
    }
}
