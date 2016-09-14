package mvp.search.iSearch;

import android.support.v7.widget.RecyclerView;

import basemvp.BasePresenter;

/**
 * Created by dhl on 2016/9/11.
 */
public interface ISearchPresenter extends BasePresenter {
    void getSearchList(RecyclerView recyclerView, boolean isReload, String key, String location);
}
