package fragment.iView;

import basemvp.BaseView;

/**
 * Created by dhl on 2016/9/6.
 */
public interface IMeView extends BaseView{
    void dismissRefreshLayout(boolean is);
    void getDatas();
}
