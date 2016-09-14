package mvp.home;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.orhanobut.logger.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import adapter.RecruitAdapter;
import bean.Offer;
import cqut.edu.cn.bangke.OfferDetailActivity;
import fragment.HomeFragment;
import fragment.iView.IHomeView;
import mvp.home.iHome.IHomeModel;
import mvp.home.iHome.IHomePresenter;
import utils.AVObjectTransform;
import utils.Constants;

/**
 * Created by dhl on 2016/9/6.
 */
public class HomePresenter implements IHomePresenter {
    IHomeModel model;
    IHomeView view;
    Context mContext;
    Handler mHandler;
    int curPage = 1;
    RecruitAdapter adapter = null;
    List<Offer> dataList = new ArrayList<>();

    public HomePresenter(Context mContext, HomeFragment view) {
        this.mContext = mContext;
        this.view = view;
        this.model = new HomeModel(mContext, this);
        mHandler = new Handler(Looper.getMainLooper());
    }

    boolean isNoMoreDatas = false;

    @Override
    public void getRecruit(final RecyclerView recyclerView, boolean isReload) {

        if (isNoMoreDatas) {
            return;
        }
        if (isReload) {
            curPage = 0;
            dataList.clear();
            view.dismissRefreshLayout(true);
        }
        getRecruitList(recyclerView, isReload, curPage++);
    }


    /**
     * 获取当前时间之前发布的招聘信息
     *
     * @param recyclerView
     * @param isReload
     * @param curPage
     */
    public void getRecruitList(final RecyclerView recyclerView, boolean isReload, int curPage) {
        Date now = new Date();
        final AVQuery<AVObject> query = new AVQuery<>("offer");
        query.whereLessThanOrEqualTo("createdAt", now);
        query.limit(Constants.LIMIT);
        query.skip(Constants.SKIP * (curPage - 1));
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                //将获取的数据加到dataList中
                if (list != null) {
                    dataList.addAll(AVObjectTransform.toOfferList(list));
                }
                final int from = dataList.size();
                if (dataList != null) {
                    Logger.i("datalist = " + dataList.size());
                    adapter = (RecruitAdapter) recyclerView.getAdapter();
                    if (adapter != null) {
                        if (from == 0) {
                            adapter.notifyDataSetChanged();
                        } else {
                            //将新获取的数据插入最顶上
                            adapter.notifyItemChanged(from, dataList.size());
                        }
                    } else {
                        Logger.i("datalist = " + dataList.size());
                        adapter = new RecruitAdapter(dataList);
                        initRecyclerView(recyclerView, adapter);
                    }
                    if (dataList.size() < 10) {
                        isNoMoreDatas = true;
                        ((RecruitAdapter) recyclerView.getAdapter()).isNoMoreDatas(isNoMoreDatas);
                    }
                }
                view.dismissRefreshLayout(false);
                view.dismissLoadingLayout(false);
            }
        });
    }

    /**
     * 获取数据，填充到recyclerview中
     *
     * @param recyclerView
     * @param data
     */

    public void fillDatas(RecyclerView recyclerView, List<AVObject> data) {

    }

    public void getData() {
    }

    /**
     * 初始化recyclerview
     *
     * @param recyclerView
     * @param adapter
     */
    public void initRecyclerView(RecyclerView recyclerView, final RecruitAdapter adapter) {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerView.setLayoutManager(layoutManager);

        adapter.setListener(new RecruitAdapter.OnItemClickListener() {
            @Override
            public void itemClick(int position) {
                Intent intent = new Intent(mContext, OfferDetailActivity.class);

                intent.putExtra(Constants.OFFER_ID, dataList.get(position).getOffer_id());
                Logger.i("id_name = " + dataList.get(position).toString());
                mContext.startActivity(intent);
            }
        });

        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            boolean isScrollToBottom = false;

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);
                LinearLayoutManager llm = (LinearLayoutManager) recyclerView.getLayoutManager();

                int lastVisibleItem = llm.findLastCompletelyVisibleItemPosition();
                int allItem = llm.getItemCount() - 2;

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (lastVisibleItem >= allItem) {
                        ((RecruitAdapter) recyclerView.getAdapter()).isNoMoreDatas(true);
                    } else {
                        getRecruit(recyclerView, false);
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    isScrollToBottom = true;
                } else {
                    isScrollToBottom = false;
                }
            }
        });
    }
}
