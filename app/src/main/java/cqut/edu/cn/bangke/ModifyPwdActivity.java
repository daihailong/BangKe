package cqut.edu.cn.bangke;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVUser;
import com.avos.avoscloud.RequestMobileCodeCallback;
import com.avos.avoscloud.UpdatePasswordCallback;
import com.orhanobut.logger.Logger;

import java.util.Timer;
import java.util.TimerTask;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.pedant.SweetAlert.SweetAlertDialog;
import utils.Constants;

public class ModifyPwdActivity extends AppCompatActivity {

    @BindView(R.id.et_new_pwd)
    EditText et_new_pwd;
    @BindView(R.id.et_auth_code)
    EditText et_auth_code;
    @BindView(R.id.btn_auth_rpwd)
    Button btn_auth_rpwd;
    @BindView(R.id.btn_do_modify)
    Button btn_do_modify;
    @BindView(R.id.ly_error_rpwd)
    LinearLayout ly_error_rpwd;
    @BindView(R.id.ly_error_code)
    LinearLayout ly_error_code;
    Timer timer = new Timer();
    TimerTask timerTask = new TimerTask() {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    timer.cancel();
                    dialog.dismiss();
                }
            });
        }
    };
    AlertDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_pwd);
        ButterKnife.bind(this);
    }


    @OnClick(R.id.btn_auth_rpwd)
    public void getAuthCode(View view) {
        String username = AVUser.getCurrentUser().getUsername();
        AVUser.requestPasswordResetBySmsCodeInBackground(username, new RequestMobileCodeCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {

                } else {
                    e.printStackTrace();
                }
            }
        });
    }

    public void doCheck(String newPassword, String verifyCode) {
        if (newPassword == null) {
            ly_error_rpwd.setVisibility(View.VISIBLE);
        }
        if (verifyCode == null) {
            ly_error_code.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.btn_do_modify)
    public void doModify(View view) {
        final String verifyCode = et_auth_code.getText().toString();
        final String newPassword = et_new_pwd.getText().toString();
        doCheck(newPassword,verifyCode);
        AVUser.resetPasswordBySmsCodeInBackground(verifyCode, newPassword, new UpdatePasswordCallback() {
            @Override
            public void done(AVException e) {
                if (e == null) {
                    dialog = new AlertDialog.Builder(ModifyPwdActivity.this)
                            .setMessage("修改成功")
                            .setIcon(R.mipmap.ic_success)
                            .create();
                    dialog.show();
                    timer.schedule(timerTask,2000);
                    Intent intent = new Intent(ModifyPwdActivity.this, MainActivity.class);
                    setResult(Constants.REQUEST_CODE_LOGOUT, intent);
                    ModifyPwdActivity.this.finish();
                } else {
                    e.printStackTrace();
                }
            }
        });
    }
}
