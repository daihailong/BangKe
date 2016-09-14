package cqut.edu.cn.bangke;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.avos.avoscloud.AVException;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVQuery;
import com.avos.avoscloud.FindCallback;
import com.orhanobut.logger.Logger;

import java.util.Date;
import java.util.List;

public class Test extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);


        Date now = new Date();
        AVQuery<AVObject> query = new AVQuery<>("offer");
        query.whereLessThanOrEqualTo("createAT", now);
        query.limit(10);
        query.findInBackground(new FindCallback<AVObject>() {
            @Override
            public void done(List<AVObject> list, AVException e) {
                Logger.i("到这里了。。。。");
                //将获取的数据加到dataList中
                for (int i = 0; i < list.size(); i++) {
                    Logger.i("第" + i + "个:" + list.get(i).getString("offer_name"));
                }
                //dataList.addAll(list);
            }

        });

    }
}
