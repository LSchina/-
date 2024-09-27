package com.example.springboot.domain.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 
 * @TableName user
 */
@TableName(value ="user")
@Data
public class SUser implements Serializable {
    /**
     * 
     */
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    /**
     * 
     */
    private String username;

    /**
     * 
     */
    private String password;

    /**
     * 
     */
    private String avater;

    /**
     * 
     */
    private String studentId;

    /**
     * 
     */
    private String className;

    /**
     * 
     */
    private String phone;

    /**
     * 
     */
    private Date loginDate;

    /**
     * 
     */
    private Date createTime;

    /**
     * 
     */
    private String remark;

    /**
     * 
     */
    private Integer communityId;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    /**
     * 所属角色
     */
    @TableField(exist = false)
    private String roles;

    /**
     * 所有角色集合
     */
    @TableField(exist = false)
    private List<Role> RoleList;

    /**
     * 确认新密码
     */
    @TableField(exist = false)
    private String newPassword;
    /**
     *
     */
    public Long getId() {
        return id;
    }

    /**
     *
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     *
     */
    public String getUsername() {
        return username;
    }

    /**
     *
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     *
     */
    public String getPassword() {
        return password;
    }

    /**
     *
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     *
     */
    public String getAvater() {
        return avater;
    }

    /**
     *
     */
    public void setAvater(String avater) {
        this.avater = avater;
    }

    /**
     *
     */
    public String getStudentId() {
        return studentId;
    }

    /**
     *
     */
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

    /**
     *
     */
    public String getClassName() {
        return className;
    }

    /**
     *
     */
    public void setClassName(String className) {
        this.className = className;
    }

    /**
     *
     */
    public String getPhone() {
        return phone;
    }

    /**
     *
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    /**
     *
     */
    public Date getLoginDate() {
        return loginDate;
    }

    /**
     *
     */
    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    /**
     *
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     *
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     *
     */
    public String getRemark() {
        return remark;
    }

    /**
     *
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /**
     *
     * @return
     */
    public Integer getCommunityId() {
        return communityId;
    }

    /**
     *
     */
    public void setCommunityId(Integer communityId) {
        this.communityId = communityId;
    }

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        SUser other = (SUser) that;
        return (this.getId() == null ? other.getId() == null : this.getId().equals(other.getId()))
            && (this.getUsername() == null ? other.getUsername() == null : this.getUsername().equals(other.getUsername()))
            && (this.getPassword() == null ? other.getPassword() == null : this.getPassword().equals(other.getPassword()))
            && (this.getAvater() == null ? other.getAvater() == null : this.getAvater().equals(other.getAvater()))
            && (this.getStudentId() == null ? other.getStudentId() == null : this.getStudentId().equals(other.getStudentId()))
            && (this.getClass() == null ? other.getClass() == null : this.getClass().equals(other.getClass()))
            && (this.getPhone() == null ? other.getPhone() == null : this.getPhone().equals(other.getPhone()))
            && (this.getLoginDate() == null ? other.getLoginDate() == null : this.getLoginDate().equals(other.getLoginDate()))
            && (this.getCreateTime() == null ? other.getCreateTime() == null : this.getCreateTime().equals(other.getCreateTime()))
            && (this.getRemark() == null ? other.getRemark() == null : this.getRemark().equals(other.getRemark()))
            && (this.getCommunityId() == null ? other.getCommunityId() == null : this.getCommunityId().equals(other.getCommunityId()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getId() == null) ? 0 : getId().hashCode());
        result = prime * result + ((getUsername() == null) ? 0 : getUsername().hashCode());
        result = prime * result + ((getPassword() == null) ? 0 : getPassword().hashCode());
        result = prime * result + ((getAvater() == null) ? 0 : getAvater().hashCode());
        result = prime * result + ((getStudentId() == null) ? 0 : getStudentId().hashCode());
        result = prime * result + ((getClass() == null) ? 0 : getClass().hashCode());
        result = prime * result + ((getPhone() == null) ? 0 : getPhone().hashCode());
        result = prime * result + ((getLoginDate() == null) ? 0 : getLoginDate().hashCode());
        result = prime * result + ((getCreateTime() == null) ? 0 : getCreateTime().hashCode());
        result = prime * result + ((getRemark() == null) ? 0 : getRemark().hashCode());
        result = prime * result + ((getCommunityId() == null) ? 0 : getCommunityId().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", username=").append(username);
        sb.append(", password=").append(password);
        sb.append(", avater=").append(avater);
        sb.append(", studentid=").append(studentId);
        sb.append(", class=").append(className);
        sb.append(", phone=").append(phone);
        sb.append(", loginDate=").append(loginDate);
        sb.append(", createTime=").append(createTime);
        sb.append(", remark=").append(remark);
        sb.append(", communityId=").append(communityId);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}