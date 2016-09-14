package cqut.edu.cn.bangke;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.UpdatePasswordCallback;

import bean.User;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import fragment.MeFragment;
import utils.AVObjectTransform;
import utils.Constants;
import utils.ToastDialog;

public class UserInfoActivity extends AppCompatActivity {

    @BindView(R.id.btn_log_out)
    TextView btn_logOut;
    @BindView(R.id.ly_mobile_phone_verified)
    LinearLayout ly_mobile_phone_verified;
    @BindView(R.id.ly_email_verified)
    LinearLayout ly_email_verified;
    @BindView(R.id.tv_email_verified)
    TextView tv_email_verified;
    @BindView(R.id.tv_mobile_phone_verified)
    TextView tv_mobile_phone_verified;
    User user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);
        ButterKnife.bind(this);
    }


    @Override
    protected void onResume() {
        super.onResume();
        AVUser avUser = AVUser.getCurrentUser();
        user = AVObjectTransform.avUserToUser(avUser);
        initView(avUser);
    }


    public void initView(AVUser user) {
        if (user.isMobilePhoneVerified())
            tv_mobile_phone_verified.setText(user.getMobilePhoneNumber());
        else {
            tv_mobile_phone_verified.setText("未验证");
            ly_mobile_phone_verified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastDialog.toast_short(UserInfoActivity.this,"尚未验证手机号");
                }
            });
        }
        if (user.getBoolean("emailVerified") == true)
            tv_email_verified.setText(user.getEmail());
        else {
            tv_email_verified.setText("未验证");
            ly_email_verified.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ToastDialog.toast_short(UserInfoActivity.this, "尚未验证邮箱");
                }
            });
        }
    }

    @OnClick(R.id.tv_modify_password)
    public void doModifyPassword(View view) {
        Intent intent = new Intent(UserInfoActivity.this, ModifyPwdActivity.class);
        startActivityForResult(intent, Constants.REQUEST_CODE_LOGOUT);
        //overridePendingTransition(R.anim.push_top_in2,R.anim.push_top_out2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Constants.REQUEST_CODE_LOGOUT) {
            AVUser.logOut();
            MeFragment.avUser = AVUser.getCurrentUser();
            MeFragment.user = null;
        }
    }

    @OnClick(R.id.btn_log_out)
    public void logOut(View view) {
        AVUser.logOut();// 清除缓存用户对象
        Intent intent = new Intent(UserInfoActivity.this, MainActivity.class);
        setResult(Constants.REQUEST_CODE_LOGOUT, intent);
        UserInfoActivity.this.finish();
    }
}
