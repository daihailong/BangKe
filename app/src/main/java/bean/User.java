package bean;

import java.util.Date;

/**
 * Created by dhl on 2016/9/8.
 */
public class User {
    String email;
    String username;
    String mobilePhoneNumber;
    String u_avatar;
    int u_id;
    int u_sex;
    Date createdAt;
    Date updatedAt;
    boolean emailVerified;
    boolean mobilePhoneVerified;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobilePhoneNumber() {
        return mobilePhoneNumber;
    }

    public void setMobilePhoneNumber(String mobilePhoneNumber) {
        this.mobilePhoneNumber = mobilePhoneNumber;
    }

    public String getU_avatar(){
        return u_avatar;
    }

    public void setU_avatar(String u_avatar){
        this.u_avatar = u_avatar;
    }

    public int getU_id() {
        return u_id;
    }

    public void setU_id(int u_id) {
        this.u_id = u_id;
    }

    public int getU_sex() {
        return u_sex;
    }

    public void setU_sex(int u_sex) {
        this.u_sex = u_sex;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public boolean isEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(boolean emailVerified) {
        this.emailVerified = emailVerified;
    }

    public boolean isMobilePhoneVerified() {
        return mobilePhoneVerified;
    }

    public void setMobilePhoneVerified(boolean mobilePhoneVerified) {
        this.mobilePhoneVerified = mobilePhoneVerified;
    }

    @Override
    public String toString() {
        return "User{" +
                "email='" + email + '\'' +
                ", username='" + username + '\'' +
                ", mobilePhoneNumber='" + mobilePhoneNumber + '\'' +
                ", u_id=" + u_id +
                ", u_sex=" + u_sex +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                ", emailVerified=" + emailVerified +
                ", mobilePhoneVerified=" + mobilePhoneVerified +
                '}';
    }
}
