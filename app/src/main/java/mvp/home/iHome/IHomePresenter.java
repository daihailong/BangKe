package mvp.home.iHome;

import android.support.v7.widget.RecyclerView;

import basemvp.BasePresenter;

/**
 * Created by dhl on 2016/9/6.
 */
public interface IHomePresenter extends BasePresenter{
    void getRecruit(RecyclerView recyclerView, boolean isReload);
}
