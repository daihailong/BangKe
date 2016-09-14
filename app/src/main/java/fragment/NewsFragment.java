package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import basemvp.BaseFragment;
import cqut.edu.cn.bangke.R;
import fragment.iView.INewsView;
import mvp.home.HomePresenter;
import utils.Constants;

/**
 * Created by dhl on 2016/9/5.
 */
public class NewsFragment extends MainFragment implements INewsView {

    FragmentManager fragmentManager;
    static NewsFragment newsFragment;

    public static NewsFragment newInstance() {
        if (newsFragment == null)
            newsFragment = new NewsFragment();
        return newsFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        if (savedInstanceState != null) {
            newsFragment = (NewsFragment) fragmentManager.findFragmentByTag(Constants.FRAG_NEWS);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, container, false);
        findView(view);
        initView();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        //首次加载
        getDatas();
    }

    @Override
    public void findView(View view) {

    }

    public void initView() {
        //下拉刷新
        /*mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

            }
        });*/
    }

    @Override
    public void dismissRefreshLayout(boolean is) {
    }

    @Override
    public void getDatas() {
    }
}
