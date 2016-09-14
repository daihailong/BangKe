package fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.view.SimpleDraweeView;
import com.orhanobut.logger.Logger;

import bean.User;
import cqut.edu.cn.bangke.LoginActivity;
import cqut.edu.cn.bangke.R;
import cqut.edu.cn.bangke.SettingsActivity;
import cqut.edu.cn.bangke.UserInfoActivity;
import fragment.iView.IMeView;
import utils.AVObjectTransform;
import utils.Constants;
import utils.ToastDialog;

/**
 * Created by dhl on 2016/9/5.
 */
public class MeFragment extends MainFragment implements IMeView, View.OnClickListener {


    SimpleDraweeView user_avatar;
    TextView tv_user_account;
    LinearLayout ly_brief;
    LinearLayout ly_collect;
    LinearLayout ly_feedback;
    LinearLayout ly_settings;

    FragmentManager fragmentManager;
    static MeFragment meFragment;
    public static AVUser avUser;
    public static User user;
    GenericDraweeHierarchy hierarchy;

    public static MeFragment newInstance() {
        if (meFragment == null) {
            meFragment = new MeFragment();
        }
        return meFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        if (savedInstanceState != null)
            meFragment = (MeFragment) fragmentManager.findFragmentByTag(Constants.FRAG_ME);
        Logger.i("onCreate .....");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Logger.i("onCreateView ...................");
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        findView(view);
        user_avatar.setOnClickListener(this);
        tv_user_account.setOnClickListener(this);
        ly_brief.setOnClickListener(this);
        ly_collect.setOnClickListener(this);
        ly_feedback.setOnClickListener(this);
        ly_settings.setOnClickListener(this);

        fragmentManager = getFragmentManager();
        return view;
    }


    @Override
    public void onResume() {
        super.onResume();
        avUser = AVUser.getCurrentUser();
        Logger.i("avUser = " + avUser);
        if (avUser != null) {
            user = AVObjectTransform.avUserToUser(avUser);
        } else {
            user = null;
        }
        initView();
    }

    @Override
    public void onStart() {
        super.onStart();
        //首次加载
        getDatas();
    }

    @Override
    public void findView(View view) {
        user_avatar = (SimpleDraweeView) view.findViewById(R.id.user_avatar);
        tv_user_account = (TextView) view.findViewById(R.id.tv_user_account);
        ly_brief = (LinearLayout) view.findViewById(R.id.ly_brief);
        ly_collect = (LinearLayout) view.findViewById(R.id.ly_collect);
        ly_feedback = (LinearLayout) view.findViewById(R.id.ly_feedback);
        ly_settings = (LinearLayout) view.findViewById(R.id.ly_settings);
        /*GenericDraweeHierarchyBuilder builder =
                new GenericDraweeHierarchyBuilder(getResources());
        hierarchy = builder
                .setPlaceholderImage(R.mipmap.default_face)
                .build();*/
    }

    public void initView() {

        if (user != null) {
            Logger.i("shit!!!!!");
            Uri path = Uri.parse(user.getU_avatar());
            user_avatar.setImageURI(path);
            tv_user_account.setText(user.getUsername());
        } else {
            Uri path = Uri.parse("http://ac-qugutj79.clouddn.com/4f1bac83c8b1522a.png");
            //user_avatar.setHierarchy(hierarchy);
            user_avatar.setImageURI(path);
            tv_user_account.setText("未登录");
        }
    }

    @Override
    public void dismissRefreshLayout(boolean is) {

    }

    @Override
    public void getDatas() {
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
            case R.id.user_avatar:
                //点击用户头像
                if (user != null) {
                    //跳转个人信息界面
                    startActivity(new Intent(view.getContext(), UserInfoActivity.class));
                } else {
                    //跳转登录页面
                    startActivityForResult(new Intent(view.getContext(), LoginActivity.class), Constants.REQUEST_COED_LOGIN);
                }
                break;
            case R.id.ly_brief:
                //点击简历
                ToastDialog.toast_short(view.getContext(), "点击了简历项");
                break;
            case R.id.ly_collect:
                //点击收藏
                ToastDialog.toast_short(view.getContext(), "点击了收藏项");
                break;
            case R.id.ly_settings:
                //点击设置
                ToastDialog.toast_short(view.getContext(), "点击了设置项");
                startActivity(new Intent(view.getContext(), SettingsActivity.class));
                break;
        }
    }
}
