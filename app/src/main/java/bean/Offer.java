package bean;

import java.util.Date;

/**
 * Created by dhl on 2016/9/7.
 * 职位类
 */
public class Offer {
    int recr_id;
    int offer_id;
    int offer_pub_uId;//发布者id
    String offer_name;//招聘标题
    String offer_wage;//薪资
    String avatar;//头像
    String recr_requ;//应职要求
    String exp_limit;//工作经验
    String edu_limit;//教育经历
    String position;//城市
    String comp_name;//公司名称
    String comp_type;//公司类型
    String finance;//融资
    String need;//招聘人数
    Date createdAt;//创建时间
    Date updatedAt;//更新时间

    public int getRecr_id() {
        return recr_id;
    }

    public void setRecr_id(int recr_id) {
        this.recr_id = recr_id;
    }

    public int getOffer_id() {
        return offer_id;
    }

    public void setOffer_id(int offer_id) {
        this.offer_id = offer_id;
    }

    public int getOffer_pub_uId() {
        return offer_pub_uId;
    }

    public void setOffer_pub_uId(int offer_pub_uId) {
        this.offer_pub_uId = offer_pub_uId;
    }

    public String getOffer_name() {
        return offer_name;
    }

    public void setOffer_name(String offer_name) {
        this.offer_name = offer_name;
    }

    public String getOffer_wage() {
        return offer_wage;
    }

    public void setOffer_wage(String offer_wage) {
        this.offer_wage = offer_wage;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getRecr_requ() {
        return recr_requ;
    }

    public void setRecr_requ(String recr_requ) {
        this.recr_requ = recr_requ;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getComp_name() {
        return comp_name;
    }

    public void setComp_name(String comp_name) {
        this.comp_name = comp_name;
    }

    public String getComp_type() {
        return comp_type;
    }

    public void setComp_type(String comp_type) {
        this.comp_type = comp_type;
    }

    public String getFinance() {
        return finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
    }

    public String getNeed() {
        return need;
    }

    public void setNeed(String need) {
        this.need = need;
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

    public String getExp_limit() {
        return exp_limit;
    }

    public void setExp_limit(String exp_limit) {
        this.exp_limit = exp_limit;
    }

    public String getEdu_limit() {
        return edu_limit;
    }

    public void setEdu_limit(String edu_limit) {
        this.edu_limit = edu_limit;
    }
}
