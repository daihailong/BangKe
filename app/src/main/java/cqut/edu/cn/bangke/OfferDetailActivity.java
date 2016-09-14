package cqut.edu.cn.bangke;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.Date;
import java.util.List;

import bean.Offer;
import bean.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import utils.AVObjectTransform;
import utils.Constants;

public class OfferDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.tv_offer_name)
    TextView tv_offer_name;
    @BindView(R.id.tv_offer_wage)
    TextView tv_offer_wage;
    @BindView(R.id.tv_offer_position)
    TextView tv_offer_position;
    @BindView(R.id.tv_offer_exp_limit)
    TextView tv_offer_exp_limit;
    @BindView(R.id.tv_offer_edu_limit)
    TextView tv_offer_edu_limit;
    @BindView(R.id.tv_recr_requ)
    TextView tv_recr_requ;
    @BindView(R.id.user_avatar)
    SimpleDraweeView user_avatar;
    @BindView(R.id.tv_offer_pub_user)
    TextView tv_offer_pub_user;
    @BindView(R.id.tv_offer_pub_time)
    TextView tv_offer_pub_time;
    @BindView(R.id.btn_send_brief)
    Button btn_send_brief;
    @BindView(R.id.btn_chat)
    Button btn_chat;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_detail);
        ButterKnife.bind(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            int id = bundle.getInt(Constants.OFFER_ID);
            getOfferDetail(id);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    public void getOfferDetail(int offer_id) {
        AVQuery<AVObject> query = new AVQuery<>("offer");
        query.whereEqualTo("offer_id", offer_id);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                if (list != null) {
                    Offer offer = AVObjectTransform.avObjectToOffer(list.get(0));
                    bindDatas(offer);
                    AVQuery<AVUser> avQuery = new AVQuery<>("_User");
                    avQuery.whereEqualTo("u_id", offer.getOffer_pub_uId());
                    avQuery.findInBackground(new FindCallback<AVUser>() {
                        @Override
                        public void done(List<AVUser> list, AVException e) {
                            if (list != null) {
                                User user = AVObjectTransform.avUserToUser(list.get(0));
                                tv_offer_pub_user.setText(user.getUsername());
                                //继续在这处理点击后的事件和chat、发送简历
                            }
                        }
                    });
                }
            }
        });
    }

    /**
     * 为控件绑定数据
     *
     * @param offer
     */
    public void bindDatas(Offer offer) {
        tv_offer_name.setText(offer.getOffer_name());
        tv_offer_wage.setText("【" + offer.getOffer_wage() + "】");
        tv_offer_position.setText(offer.getPosition());
        tv_offer_exp_limit.setText(offer.getExp_limit());
        tv_offer_edu_limit.setText(offer.getEdu_limit());
        tv_recr_requ.setText(offer.getRecr_requ());
        Uri path = Uri.parse(offer.getAvatar());
        user_avatar.setImageURI(path);
        Date date = offer.getCreatedAt();
        tv_offer_pub_time.setText(date.toLocaleString());
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.btn_send_brief:
                break;
            case R.id.btn_chat:
                break;
        }
    }
}
