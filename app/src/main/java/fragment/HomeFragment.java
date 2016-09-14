package fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.orhanobut.logger.Logger;

import butterknife.BindView;
import cn.pedant.SweetAlert.SweetAlertDialog;
import cqut.edu.cn.bangke.R;
import fragment.iView.IHomeView;
import mvp.home.HomePresenter;
import mvp.home.iHome.IHomePresenter;
import utils.Constants;

/**
 * Created by dhl on 2016/9/5.
 */
public class HomeFragment extends MainFragment implements IHomeView {

    @BindView(R.id.srl_home)
    SwipeRefreshLayout mSwipeRefreshLayout;
    @BindView(R.id.rv_home)
    RecyclerView mRecyclerView;
    IHomePresenter presenter;
    Handler mHandler;
    static HomeFragment homeFragment;
    SweetAlertDialog dialog;

    public static HomeFragment newInstance() {
        if (homeFragment == null)
            homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FragmentManager fragmentManager = getFragmentManager();
        if (savedInstanceState != null) {
            homeFragment = (HomeFragment) fragmentManager.findFragmentByTag(Constants.FRAG_HOME);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        findView(view);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        mHandler = new Handler();
        presenter = new HomePresenter(getContext(), this);
        //首次加载
        dialog.show();
        getDatas(true);
    }

    @Override
    public void findView(View view) {
        mSwipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.srl_home);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.rv_home);
        dialog = new SweetAlertDialog(view.getContext(),SweetAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#A5DC86"));
        dialog.setTitleText("拼命加载中...");
        dialog.setCancelable(false);
    }

    public void initView() {
        //mSwipeRefreshLayout.setProgressViewOffset(true, 0, 100);
        /*mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getDatas(false);
            }
        });*/
    }

    @Override
    public void dismissRefreshLayout(boolean is) {
        if (!(mSwipeRefreshLayout.isRefreshing()) == is) {
            mSwipeRefreshLayout.setRefreshing(is);
        }
    }

    @Override
    public void dismissLoadingLayout(boolean is) {
        if((!dialog.isShowing()) == is){
            dialog.dismiss();
        }
    }


    @Override
    public void getDatas(boolean isReload) {
        presenter.getRecruit(mRecyclerView, isReload);
    }
}
