package mvp.search;

import android.content.Context;

import mvp.search.iSearch.ISearchModel;
import mvp.search.iSearch.ISearchPresenter;

/**
 * Created by dhl on 2016/9/11.
 */
public class SearchModel implements ISearchModel {

    ISearchPresenter presenter;
    Context context;

    public SearchModel(Context context, ISearchPresenter presenter) {
        this.presenter = presenter;
        this.context = context;
    }


}
