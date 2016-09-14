package fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.avos.avoscloud.search.SearchActivity;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.List;

import adapter.MainVPAdapter;
import basemvp.BaseFragment;
import cqut.edu.cn.bangke.OfferSearchActivity;
import cqut.edu.cn.bangke.R;
import utils.Constants;

/**
 * Created by dhl on 2016/9/5.
 */
public class MainFragment extends BaseFragment{

    ViewPager mViewPager;
    TabLayout mTabLayout;
    EditText et_search_bar;
    TextView tv_toolbar_title;


    static MainFragment mainFragment;
    FragmentManager fragmentManager;

    String title[] = {"首页", "消息", "圈子", "我"};

    public static MainFragment newInstance() {
        if (mainFragment == null)
            mainFragment = new MainFragment();
        return mainFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getFragmentManager();
        if (savedInstanceState != null) {
            mainFragment = (MainFragment) fragmentManager.findFragmentByTag(Constants.FRAG_MAIN);
        }
        Logger.i("onCreate ....");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        Logger.i("onCreateView ....");
        View view = inflater.inflate(R.layout.main_fragment, container, false);
        findView(view);
        initViews();
        return view;
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Logger.i("onViewCreated ....");
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Logger.i("onSaveInstanceState");
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Logger.i("onActivityCreated ....");
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        Logger.i("onAttach ....");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Logger.i("onDestroyView ....");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Logger.i("onDestroy ....");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        Logger.i("onDetach ....");
    }

    @Override
    public void onPause() {
        super.onPause();
        Logger.i("onPause ....");
    }

    @Override
    public void onResume() {
        super.onResume();
        Logger.i("onResume ....");
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        Logger.i("onViewStateRestored ....");
    }

    @Override
    public void onStart() {
        super.onStart();
        Logger.i("onStart ....");
    }

    @Override
    public void onStop() {
        super.onStop();
        Logger.i("onStop ....");
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        Logger.i("onAttach ....");
    }

    @Override
    public void findView(View view) {
        mTabLayout = (TabLayout) view.findViewById(R.id.main_tab_layout);
        mViewPager = (ViewPager) view.findViewById(R.id.main_view_pager);
        et_search_bar = (EditText) view.findViewById(R.id.et_search_bar);
        tv_toolbar_title = (TextView) view.findViewById(R.id.tv_toolbar_title);
    }

    public void initViews() {
        et_search_bar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(view.getContext(), OfferSearchActivity.class));
            }
        });
        List<Fragment> list = getFragmentList();
        MainVPAdapter adapter = new MainVPAdapter(getChildFragmentManager(), list, title);
        mViewPager.setCurrentItem(0);
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        et_search_bar.setVisibility(View.VISIBLE);
                        tv_toolbar_title.setVisibility(View.GONE);
                        break;
                    case 1:
                        et_search_bar.setVisibility(View.GONE);
                        tv_toolbar_title.setVisibility(View.VISIBLE);
                        tv_toolbar_title.setText("消息列表");
                        break;
                    case 2:
                        et_search_bar.setVisibility(View.GONE);
                        tv_toolbar_title.setVisibility(View.VISIBLE);
                        tv_toolbar_title.setText("圈子");
                        break;
                    case 3:
                        et_search_bar.setVisibility(View.GONE);
                        tv_toolbar_title.setVisibility(View.VISIBLE);
                        tv_toolbar_title.setText("我的信息");
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        mViewPager.setOffscreenPageLimit(4);
        mTabLayout.setupWithViewPager(mViewPager);



    }

    public int setCurrentPager(int position) {
        mViewPager.setCurrentItem(position);
        return mViewPager.getCurrentItem();
    }


    private List<Fragment> getFragmentList() {
        List<Fragment> list = new ArrayList<>();
        Fragment fragHome = HomeFragment.newInstance();
        Bundle bundleHome = new Bundle();
        bundleHome.putString(Constants.FRAG_TYPE, Constants.FRAG_HOME);
        fragHome.setArguments(bundleHome);

        Fragment fragNews = NewsFragment.newInstance();
        Bundle bundleNews = new Bundle();
        bundleNews.putString(Constants.FRAG_TYPE, Constants.FRAG_NEWS);
        fragNews.setArguments(bundleNews);

        Fragment fragCircle = CircleFragment.newInstance();
        Bundle bundleCircle = new Bundle();
        bundleCircle.putString(Constants.FRAG_TYPE, Constants.FRAG_CIRCLE);
        fragCircle.setArguments(bundleCircle);

        Fragment fragMe = MeFragment.newInstance();
        Bundle bundleMe = new Bundle();
        bundleMe.putString(Constants.FRAG_TYPE, Constants.FRAG_ME);
        fragMe.setArguments(bundleMe);

        list.add(fragHome);
        list.add(fragNews);
        list.add(fragCircle);
        list.add(fragMe);
        return list;
    }
}
