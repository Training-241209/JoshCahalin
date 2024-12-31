package project1.p1Back.entity;
import jakarta.persistence.*;

/**
 * This is a class that models a Role.
 */
@Entity
@Table(name = "role")
public class Role {
    /**
     * An id for this Role. You should use this as the Entity's ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "roleId")
    private Integer roleId;

    /**
     * The name of the role.
     */
    private String name;

    /**
     * Default no-args constructor needed for Jackson ObjectMapper.
     */
    public Role() {
    }

    /**
     * Constructor without roleId for creating a new Role.
     */
    public Role(String name) {
        this.name = name;
    }

    /**
     * Constructor with all fields.
     */
    public Role(Integer roleId, String name) {
        this.roleId = roleId;
        this.name = name;
    }

    // Getters and Setters
    public Integer getRoleId() {
        return roleId;
    }

    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Role other = (Role) obj;
        return roleId != null && roleId.equals(other.roleId);
    }

    // toString
    @Override
    public String toString() {
        return "Role{" +
                "roleId=" + roleId +
                ", name='" + name + '\'' +
                '}';
    }
}
