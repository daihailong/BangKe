package cqut.edu.cn.bangke;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.RecruitAdapter;
import adapter.SearchRVAdapter;
import bean.Offer;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import mvp.search.SearchPresenter;
import mvp.search.iSearch.ISearchPresenter;
import mvp.search.iSearch.ISearchView;
import utils.AVObjectTransform;
import utils.Constants;

public class OfferSearchActivity extends AppCompatActivity implements ISearchView {

    @BindView(R.id.rv_search_list)
    RecyclerView mRecyclerView;
    @BindView(R.id.iv_search)
    ImageView iv_search;
    @BindView(R.id.et_bar_search)
    TextView search_key;
    @BindView(R.id.tv_search_position)
    TextView search_location;


    ISearchPresenter presenter;
    String key = "";
    String location = "";
    String lastKey;
    String lastLocation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_search);
        ButterKnife.bind(this);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        presenter = new SearchPresenter(OfferSearchActivity.this, this);
    }

    @OnClick(R.id.iv_search)
    public void doSearch(View view) {
        Logger.i("到这里来了。。。。");
        lastKey = key;
        lastLocation = location;
        key = search_key.getText().toString();
        location = search_location.getText().toString();
        View mView = getWindow().peekDecorView();
        if (mView != null) {
            InputMethodManager inputmanger = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputmanger.hideSoftInputFromWindow(mView.getWindowToken(), 0);
        }
        if (lastKey != key||lastLocation != location) {
            presenter.getSearchList(mRecyclerView, true, key, location);
        }else{
            Logger.i("这tm是个啥子jb玩意儿");
        }
    }

    @Override
    public void dismissLoadingLayout(boolean is) {
    }
}
