package project1.p1Back.entity;
import jakarta.persistence.*;
@Entity
@Table(name = "reimbursement")
public class Reimbursement {
    /**
     * An id for this Reimbursement. You should use this as the Entity's ID.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reimId")
    private Integer reimId;

    private String description;
    private Double amount;
    private String status;

    @ManyToOne
    @JoinColumn(name = "userId", nullable = false)
    private User user;

    /**
     * Default no-args constructor needed for Jackson ObjectMapper.
     */
    public Reimbursement() {
    }

    /**
     * Constructor without reimId for creating a new Reimbursement.
     */
    public Reimbursement(String description, Double amount, String status, User user) {
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.user = user;
    }

    /**
     * Constructor with all fields.
     */
    public Reimbursement(Integer reimId, String description, Double amount, String status, User user) {
        this.reimId = reimId;
        this.description = description;
        this.amount = amount;
        this.status = status;
        this.user = user;
    }

    // Getters and Setters
    public Integer getReimId() {
        return reimId;
    }

    public void setReimId(Integer reimId) {
        this.reimId = reimId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    // equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Reimbursement other = (Reimbursement) obj;
        return reimId != null && reimId.equals(other.reimId);
    }

    // toString
    @Override
    public String toString() {
        return "Reimbursement{" +
                "reimId=" + reimId +
                ", description='" + description + '\'' +
                ", amount=" + amount +
                ", status='" + status + '\'' +
                ", user=" + user +
                '}';
    }
}
