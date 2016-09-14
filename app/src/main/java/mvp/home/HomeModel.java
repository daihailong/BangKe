package mvp.home;

import android.content.Context;

import mvp.home.iHome.IHomeModel;
import mvp.home.iHome.IHomePresenter;

/**
 * Created by dhl on 2016/9/6.
 */
public class HomeModel implements IHomeModel {


    IHomePresenter presenter;
    Context context;

    public HomeModel(Context context, HomePresenter presenter) {
        this.context = context;
        this.presenter = presenter;
    }
}
