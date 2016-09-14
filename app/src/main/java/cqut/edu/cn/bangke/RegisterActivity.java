package cqut.edu.cn.bangke;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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
import com.orhanobut.logger.Logger;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import utils.BaseUtil;
import utils.NetWorkUtils;
import utils.ToastDialog;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.et_reg_username)
    EditText et_username;
    /* @BindView(R.id.et_reg_passwd)
     EditText et_passwd;
     @BindView(R.id.et_reg_rpwd)
     EditText et_rpwd;*/
    @BindView(R.id.et_auth_code)
    EditText et_verify_code;

    SweetAlertDialog mProgress;

    AVUser user;
    String username;
    String verifyCode;

    boolean isExsit = false;
    Context mContext = RegisterActivity.this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        user = new AVUser();
        mProgress = new SweetAlertDialog(this, SweetAlertDialog.PROGRESS_TYPE);
        mProgress.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        mProgress.setTitleText("Loading");
        mProgress.setCancelable(true);
    }

    /**
     * 注册
     *
     * @param view
     */
    @OnClick(R.id.btn_register)
    public void doRegister(View view) {
        getInputContent();
        if (viabilityCheck(username))
            if (!(verifyCode.equals(""))) {
                mProgress.show();
                //注册是再次检验网络状态
                if (NetWorkUtils.isNetworkAvailable(mContext)) {
                    user.setUsername(username);
                    user.put("mobilePhoneNumber", username);
                    Logger.e("username = " + username);
                    AVUser.signUpOrLoginByMobilePhoneInBackground(username, verifyCode, new LogInCallback<AVUser>() {
                        @Override
                        public void done(AVUser avUser, AVException e) {
                            if (e == null) {
                                /*AVUser.verifyMobilePhoneInBackground(verifyCode, new AVMobilePhoneVerifyCallback() {
                                    @Override
                                    public void done(AVException e) {
                                        if (e == null) {
                                            mProgress.dismiss();
                                            Logger.i("为啥子不跳呢？？？？？");
                                            Intent intent = new Intent(mContext, MainActivity.class);
                                            startActivity(intent);
                                        } else {
                                            mProgress.dismiss();
                                            ToastDialog.toast_short(mContext, "验证码错误！");
                                            return;
                                        }
                                    }
                                });*/
                                Intent intent = new Intent(mContext, MainActivity.class);
                                startActivity(intent);
                            } else {
                                mProgress.dismiss();
                                mProgress = new SweetAlertDialog(mContext, SweetAlertDialog.ERROR_TYPE)
                                        .setTitleText("注册失败！");
                                mProgress.dismiss();
                                e.printStackTrace();
                            }
                        }
                    });
                }
            }
    }


    /**
     * 获取用户输入的内容
     */
    public void getInputContent() {
        verifyCode = et_verify_code.getText().toString();
        username = et_username.getText().toString();
    }


    /**
     * 注册查询 查询账号是否已被注册
     *
     * @param username 手机号
     * @return boolean
     */
    public boolean doRegisterQuery(final String username) {
        final AVQuery<AVUser> query = new AVQuery<>("_User");
        query.whereEqualTo("username", username);
        query.findInBackground(new FindCallback<AVUser>() {
            @Override
            public void done(List<AVUser> list, AVException e) {
                if (list != null) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    isExsit = true;
                } else {
                    isExsit = false;
                }
            }
        });
        return isExsit;
    }

    /**
     * 获取验证码
     *
     * @param view
     */
    @OnClick(R.id.btn_auth)
    public void getVerifyCode(View view) {
        getInputContent();
        if (!username.equals("")) {
            if (BaseUtil.checkMobilePhoneNumber(username))
                requestVerifyCode(username);
            else
                new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                        .setTitleText("手机号码不合法！")
                        .setContentText("请输入正确的手机号码！")
                        .setCustomImage(R.drawable.ic_launcher)
                        .show();
        } else {
            new SweetAlertDialog(this, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                    .setTitleText("手机号码为空！")
                    .setContentText("请输入您的手机号码！")
                    .setCustomImage(R.drawable.ic_launcher)
                    .show();
        }
    }

    /**
     * 请求发送验证码
     *
     * @param username 手机号
     */
    public void requestVerifyCode(String username) {
        Logger.e("username = " + username);
        AVOSCloud.requestSMSCodeInBackground(username, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    ToastDialog.toast_short(mContext, "验证码已发送，请注意查收！");
                } else {
                    ToastDialog.toast_short(mContext, "发送失败！");
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 可行性检查 检查注册信息是否满足注册要求
     *
     * @param username 手机号
     * @return
     */
    public boolean viabilityCheck(String username) {
        boolean flag;
        if (!username.equals("") && BaseUtil.checkMobilePhoneNumber(username) && !doRegisterQuery(username)) {
            flag = true;
        } else {
            flag = false;
            if (NetWorkUtils.isNetworkAvailable(mContext)) {
                if (doRegisterQuery(username))
                    new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                            .setTitleText("该手机号已注册,请直接登录！")
                            .show();
            } else if (!BaseUtil.checkMobilePhoneNumber(username)) {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("请输入正确的手机号码！")
                        .show();
            } else {
                new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE)
                        .setTitleText("手机号码为空！")
                        .show();
            }
        }
        return flag;
    }
}
