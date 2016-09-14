package cqut.edu.cn.bangke;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.avos.avoscloud.AVUser;
import com.orhanobut.logger.Logger;

import butterknife.BindView;
import butterknife.ButterKnife;
import fragment.CircleFragment;
import fragment.HomeFragment;
import fragment.MainFragment;
import fragment.MeFragment;
import fragment.NewsFragment;
import utils.AVObjectTransform;
import utils.Constants;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.panel_home)
    LinearLayout panel_home;
    @BindView(R.id.panel_news)
    LinearLayout panel_news;
    @BindView(R.id.panel_circle)
    LinearLayout panel_circle;
    @BindView(R.id.panel_me)
    LinearLayout panel_me;

    @BindView(R.id.tv_home)
    TextView tv_home;
    @BindView(R.id.iv_home)
    ImageView iv_home;
    @BindView(R.id.tv_news)
    TextView tv_news;
    @BindView(R.id.iv_news)
    ImageView iv_news;
    @BindView(R.id.tv_circle)
    TextView tv_circle;
    @BindView(R.id.iv_circle)
    ImageView iv_circle;
    @BindView(R.id.tv_me)
    TextView tv_me;
    @BindView(R.id.iv_me)
    ImageView iv_me;


    FragmentManager fragmentManager;
    MainFragment fragment = null;
    int mPreviousItem = 0;
    int mCurrentItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        fragmentManager = getSupportFragmentManager();
        Logger.i("fragmentManager = null ?　" + (fragmentManager == null));
        if (savedInstanceState == null) {
            fragment = MainFragment.newInstance();
        } else {
            fragment = (MainFragment) fragmentManager.findFragmentByTag(Constants.FRAG_MAIN);
        }
        replaceFragment(fragment);
        panel_home.setOnClickListener(this);
        panel_news.setOnClickListener(this);
        panel_circle.setOnClickListener(this);
        panel_me.setOnClickListener(this);

      /*  FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .se tAction("Action", null).show();
            }
        });*/
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.i("resultCode = " + resultCode);
        Logger.i("resultCode = " + RESULT_OK);
        if (resultCode == Constants.REQUEST_CODE_LOGOUT) {
            MeFragment.avUser = AVUser.getCurrentUser();
            MeFragment.user = null;
        } else if (resultCode == Constants.REQUEST_COED_LOGIN) {
            MeFragment.avUser = AVUser.getCurrentUser();
            MeFragment.user = AVObjectTransform.avUserToUser(MeFragment.avUser);
        }
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void initFragment() {
        replaceFragment(fragment);
    }


    public void replaceFragment(Fragment fragment) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        if (fragment instanceof MainFragment) {
            ft.replace(R.id.main_frame, fragment, Constants.FRAG_MAIN);
        } else if (fragment instanceof HomeFragment) {
            ft.replace(R.id.main_frame, fragment, Constants.FRAG_HOME);
        } else if (fragment instanceof NewsFragment) {

            ft.replace(R.id.main_frame, fragment, Constants.FRAG_NEWS);
        } else if (fragment instanceof CircleFragment) {
            ft.replace(R.id.main_frame, fragment, Constants.FRAG_CIRCLE);
        } else if (fragment instanceof MeFragment) {
            ft.replace(R.id.main_frame, fragment, Constants.FRAG_ME);
        }
        ft.show(fragment);
        ft.commit();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //unregisterReceiver(mRefreshBroadcastReceiver);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        int currentPosition = fragment.setCurrentPager(mPreviousItem);
        switch (id) {
            case R.id.panel_home:
                int disPos = Constants.HOME - mPreviousItem;
                mCurrentItem = currentPosition + disPos;
                fragment.setCurrentPager(mCurrentItem);
                mPreviousItem = mCurrentItem;
                break;
            case R.id.panel_news:
                disPos = Constants.NEWS - mPreviousItem;
                mCurrentItem = currentPosition + disPos;
                fragment.setCurrentPager(mCurrentItem);
                mPreviousItem = mCurrentItem;
                break;
            case R.id.panel_circle:
                disPos = Constants.CIRCLE - mPreviousItem;
                mCurrentItem = currentPosition + disPos;
                fragment.setCurrentPager(mCurrentItem);
                mPreviousItem = mCurrentItem;
                break;
            case R.id.panel_me:
                disPos = Constants.ME - mPreviousItem;
                mCurrentItem = currentPosition + disPos;
                fragment.setCurrentPager(mCurrentItem);
                mPreviousItem = mCurrentItem;
                break;
        }
    }


}
