package project1.p1Back.dto;

public class ApproveRequest {
    private Integer userId;
    private Integer reimId;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getReimId() {
        return reimId;
    }

    public void setReimId(Integer reimId) {
        this.reimId = reimId;
    }
}
