package cqut.edu.cn.bangke;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVMobilePhoneVerifyCallback;
import com.avos.avoscloud.AVOSCloud;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.FindCallback;
import com.avos.avoscloud.LogInCallback;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.SignUpCallback;
import com.dd.processbutton.iml.ActionProcessButton;
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import utils.Constants;
import utils.NetWorkUtils;
import utils.ToastDialog;

public class LoginActivity extends AppCompatActivity{

    @BindView(R.id.et_username)
    EditText et_username;
    @BindView(R.id.et_password)
    EditText et_passwd;
    SweetAlertDialog mProgress;
    @BindView(R.id.btn_login_In)
    ActionProcessButton btn_login;

    boolean verifyFlag = false;
    //判断手机号码是否已注册过了
    boolean isExsit = false;

    Context mContext = LoginActivity.this;
    AVUser avUser;

    InputMethodManager inputMethodManager =
            (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);

        avUser = AVUser.getCurrentUser();
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        mProgress = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mProgress.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mProgress.setTitleText("Loading");
        mProgress.setCancelable(true);
    }

    /**
     * 登录
     *
     * @param view
     */
    @OnClick(R.id.btn_login_In)
    public void doLogin(View view) {
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        btn_login.setMode(ActionProcessButton.Mode.PROGRESS);
        final String username = et_username.getText().toString();
        final String password = et_passwd.getText().toString();
        Logger.i("username = " + username);
        Logger.i("password = " + password);
        final AVQuery<AVUser> query = new AVQuery<>("_User");
        query.whereEqualTo("username", username);
        query.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (list == null) {
                    SweetAlertDialog dialog = new SweetAlertDialog(mContext, SweetAlertDialog.PROGRESS_TYPE)
                            .setTitleText("手机号不存在！");
                    dialog.show();
                    if (e != null) {
                        e.printStackTrace();
                    }
                    isExsit = false;
                } else {
                    //登录前验证网络状态
                    if (NetWorkUtils.isNetworkAvailable(mContext)) {
                        //mProgress.show();
                        Logger.i("come here ....");
                        AVUser.loginByMobilePhoneNumberInBackground(username, password, new LogInCallback<AVUser>() {
                            @Override
                            public void done(AVUser avUser, AVException e) {
                                if (e == null) {
                                    //mProgress.dismiss();
                                    btn_login.setMode(ActionProcessButton.Mode.ENDLESS);
                                    Intent intent = new Intent(mContext, MainActivity.class);
                                    setResult(Constants.REQUEST_COED_LOGIN,intent);
                                    LoginActivity.this.finish();
                                } else {
                                    mProgress.dismiss();
                                    Logger.e("这里");
                                    e.printStackTrace();
                                }
                            }
                        });
                    }
                }
            }
        });

    }

    @OnClick(R.id.tv_do_register)
    public void toRegisterPage(View view) {
        Intent intent = new Intent(mContext, RegisterActivity.class);
        startActivity(intent);
    }


}
