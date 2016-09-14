package utils;

import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVUser;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import bean.Offer;
import bean.User;

/**
 * Created by dhl on 2016/9/7.
 * 将AVObject格式对象转换成自定义的格式 ,如: AVObject --> Offer
 */
public class AVObjectTransform {

    /**
     * 将AVObject对象转换成Offer对象
     * @param object
     * @return
     */
    public static Offer avObjectToOffer(AVObject object) {
        Offer offer = new Offer();
        offer.setRecr_id(object.getInt("recr_id"));
        offer.setOffer_id(object.getInt("offer_id"));
        offer.setOffer_pub_uId(object.getInt("offer_pub_uId"));
        offer.setOffer_name(object.getString("offer_name"));
        offer.setOffer_wage(object.getString("offer_wage"));
        offer.setAvatar(object.getString("avatar"));
        offer.setComp_name(object.getString("comp_name"));
        offer.setComp_type(object.getString("comp_type"));
        offer.setExp_limit(object.getString("exp_limit"));
        offer.setEdu_limit(object.getString("edu_limit"));
        offer.setRecr_requ(object.getString("recr_requ"));
        offer.setFinance(object.getString("finance"));
        offer.setNeed(object.getString("need"));
        offer.setPosition(object.getString("position"));
        offer.setCreatedAt((Date) object.get("createdAt"));
        offer.setUpdatedAt((Date) object.get("updatedAt"));
        return offer;
    }

    /**
     * 将 AVObject 列表转换成 Offer 列表
     * @param objects
     * @return
     */
    public static List<Offer> toOfferList(List<AVObject> objects) {
        List<Offer> offers = new ArrayList<>();
        for (int i = 0; i < objects.size(); i++) {
            offers.add(avObjectToOffer(objects.get(i)));
        }
        return offers;
    }


    /**
     * AVObject 对象转换成 User对象
     * @param avUser
     * @return
     */
    public static User avUserToUser(AVUser avUser){
        User user = new User();
        user.setUsername(avUser.getUsername());
        user.setEmail(avUser.getEmail());
        user.setMobilePhoneNumber(avUser.getMobilePhoneNumber());
        user.setMobilePhoneVerified(avUser.isMobilePhoneVerified());
        user.setEmailVerified(avUser.getBoolean("emailVerified"));
        user.setU_avatar(avUser.getString("u_avatar"));
        user.setU_id(avUser.getInt("u_id"));
        user.setU_sex(avUser.getInt("u_sex"));
        user.setCreatedAt(avUser.getCreatedAt());
        user.setUpdatedAt(avUser.getUpdatedAt());
        return user;
    }

}
