package mvp.search;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import adapter.SearchRVAdapter;
import bean.Offer;
import cqut.edu.cn.bangke.OfferDetailActivity;
import mvp.search.iSearch.ISearchModel;
import mvp.search.iSearch.ISearchPresenter;
import mvp.search.iSearch.ISearchView;
import utils.AVObjectTransform;
import utils.Constants;

/**
 * Created by dhl on 2016/9/11.
 */
public class SearchPresenter implements ISearchPresenter {

    ISearchModel model;
    ISearchView view;
    Context mContext;
    List<Offer> dataList = new ArrayList<>();
    SearchRVAdapter adapter;
    boolean isNoMoreDatas = false;
    int curPage = 0;

    public SearchPresenter(Context context, ISearchView view) {
        this.mContext = context;
        this.view = view;
        this.model = new SearchModel(mContext, this);
    }


    @Override
    public void getSearchList(final RecyclerView recyclerView, boolean isReload, String key, String location) {
        if (isReload) {
            //重置
            isNoMoreDatas = false;
        }
        if (isNoMoreDatas) {
            return;
        }
        getSearchResult(recyclerView, isReload, curPage++, key, location);
    }

    public void getSearchResult(final RecyclerView recyclerView, boolean isReload, int curPage, String key, String location) {
        if (isReload) {
            curPage = 0;
            dataList.clear();
        }
        AVQuery<AVObject> key_query = new AVQuery<>("offer");
        key_query.whereContains("offer_name", key);
        AVQuery<AVObject> location_query = new AVQuery<>("offer");
        location_query.whereEqualTo("position", location);
        AVQuery<AVObject> query = AVQuery.and(Arrays.asList(key_query, location_query));
        query.limit(Constants.LIMIT);
        query.skip(Constants.SKIP * (curPage - 1));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                //将获取的数据加到dataList中
                if (list != null) {
                    Logger.i("list = " + list.get(0).getString("offer_name"));
                    dataList.addAll(AVObjectTransform.toOfferList(list));
                }
                if (dataList != null) {
                    adapter = new SearchRVAdapter(dataList);
                    initRecyclerView(recyclerView, adapter);
                    adapter.notifyDataSetChanged();
                    if (dataList.size() < Constants.LIMIT) {
                        isNoMoreDatas = true;
                        ((SearchRVAdapter) recyclerView.getAdapter()).isNoMoreDatas(isNoMoreDatas);
                    }
                }
            }
        });
    }

    public void initRecyclerView(RecyclerView recyclerView, SearchRVAdapter adapter) {
        LinearLayoutManager manager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(manager);
        adapter.setListener(new SearchRVAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                Intent intent = new Intent(mContext, OfferDetailActivity.class);

                intent.putExtra(Constants.OFFER_ID, dataList.get(position).getOffer_id());
                Logger.i("id_name = " + dataList.get(position).toString());
                mContext.startActivity(intent);
            }
        });
        recyclerView.setAdapter(adapter);
    }
}
