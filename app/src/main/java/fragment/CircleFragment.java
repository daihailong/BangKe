package fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import cqut.edu.cn.bangke.R;
import fragment.iView.ICircleView;
import utils.Constants;

/**
 * Created by dhl on 2016/9/5.
 */
public class CircleFragment extends MainFragment implements ICircleView {

    static CircleFragment circleFragment;
    FragmentManager fragmentManager;


    public static CircleFragment newInstance() {
        if (circleFragment == null) {
            circleFragment = new CircleFragment();
        }
        return circleFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        if (savedInstanceState != null) {
            circleFragment = (CircleFragment) fragmentManager.findFragmentByTag(Constants.FRAG_CIRCLE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_circle, container, false);
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
