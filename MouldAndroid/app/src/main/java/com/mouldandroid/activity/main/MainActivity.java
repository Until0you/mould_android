package com.mouldandroid.activity.main;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.mouldandroid.R;
import com.mouldandroid.base.BaseActivity;
import com.mouldandroid.custom.view.CustomizeTabLayout;
import com.mouldandroid.custom.view.TabBean;
import com.mouldandroid.fragments.HomeFragment;
import com.mouldandroid.fragments.MoreFragment;
import com.mouldandroid.fragments.MyFragment;
import com.mouldandroid.fragments.ProblemFragment;

import java.util.ArrayList;

import butterknife.BindView;

public class MainActivity extends BaseActivity {


    @BindView(R.id.vp_container)
    public ViewPager mViewPager;
    @BindView(R.id.tabLayout)
    public CustomizeTabLayout mTabLayout;
    @BindView(R.id.bottomNavigationBar)
    public BottomNavigationBar bottomNavigationBar;

    @BindView(R.id.rg_page)
    public RadioGroup rg_page;
    @BindView(R.id.rb_home)
    public RadioButton rb_home;
    @BindView(R.id.rb_component)
    public RadioButton rb_component;
    @BindView(R.id.rb_content)
    public RadioButton rb_content;
    @BindView(R.id.rb_my)
    public RadioButton rb_my;
    @BindView(R.id.fl_tabview)
    public FrameLayout fl_tabview;

    private int mUnSelectColor = Color.BLACK;
    private Context context;
    private ArrayList<TabBean> mTabbeans = new ArrayList<>();
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private String[] mTitles = {"首页", "组件", "更多", "我的"};
    private int[] mSelectIcons = {R.mipmap.icon_red_home_24dp,R.mipmap.icon_red_wallet_24dp,R.mipmap.icon_red_more_24dp,R.mipmap.icon_red_user_24dp};
    private int[] mUnSelectIcons = {R.mipmap.icon_grey_home_24dp,R.mipmap.icon_grey_wallet,R.mipmap.icon_grey_more,R.mipmap.icon_grey_user};

    private HomeFragment homeFragment;
    private MoreFragment moreFragment;
    private MyFragment myFragment;
    private ProblemFragment problemFragment;


    @Override
    protected int setView() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {
        context = MainActivity.this;

        homeFragment = new HomeFragment();
        moreFragment = new MoreFragment();
        myFragment = new MyFragment();
        problemFragment = new ProblemFragment();

        rg_page.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int checkedId) {
                switch (checkedId){
                    case R.id.rb_home:
                        one();
                        break;
                    case R.id.rb_component:
                        two();
                        break;
                    case R.id.rb_content:
                        three();
                        break;
                    case R.id.rb_my:
                        four();
                        break;
                }
            }
        });
    }

    @Override
    protected void initData() {
//        setBottom();
        one();
    }


    /**
     * 首页
     */
    private void one(){
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();

        if (!homeFragment.isAdded()){
            transaction1.add(R.id.fl_tabview,homeFragment);
            hideFragment(transaction1);
            transaction1.show(homeFragment);
        }else {
            hideFragment(transaction1);
            transaction1.show(homeFragment);
        }
        transaction1.commit();
    }

    private void two(){
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();

        if (!problemFragment.isAdded()){
            transaction1.add(R.id.fl_tabview,problemFragment);
            hideFragment(transaction1);
            transaction1.show(problemFragment);
        }else {
            hideFragment(transaction1);
            transaction1.show(problemFragment);
        }
        transaction1.commit();
    }

    private void three(){
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();

        if (!moreFragment.isAdded()){
            transaction1.add(R.id.fl_tabview,moreFragment);
            hideFragment(transaction1);
            transaction1.show(moreFragment);
        }else {
            hideFragment(transaction1);
            transaction1.show(moreFragment);
        }
        transaction1.commit();
    }

    private void four(){
        FragmentTransaction transaction1 = getSupportFragmentManager().beginTransaction();

        if (!myFragment.isAdded()){
            transaction1.add(R.id.fl_tabview,myFragment);
            hideFragment(transaction1);
            transaction1.show(myFragment);
        }else {
            hideFragment(transaction1);
            transaction1.show(myFragment);
        }
        transaction1.commit();
    }

    //隐藏所有的fragment
    private void hideFragment(FragmentTransaction transaction){
        if(homeFragment != null){
            transaction.hide(homeFragment);
        }
        if(moreFragment != null){
            transaction.hide(moreFragment);
        }
        if(myFragment != null){
            transaction.hide(myFragment);
        }
        if (problemFragment != null){
            transaction.hide(problemFragment);
        }
    }



    private void setBottom(){
        for (int i = 0; i < mTitles.length; i++) {
//            mTabbeans.add(new TabBean(mTitles[i], mSelectUrls[i],mUnSelectUrls[i], mSelectColor, mUnSelectColor,mSelectIcons[i], mUnSelectIcons[i]));
            mTabbeans.add(new TabBean(mTitles[i], null,null, getResources().getColor(R.color.color_ea4455), mUnSelectColor,mSelectIcons[i], mUnSelectIcons[i]));
        }

        mFragments.add(new HomeFragment());
        mFragments.add(new ProblemFragment());
        mFragments.add(new MoreFragment());
        mFragments.add(new MyFragment());
//        mFragments.add(inid(1));
//        mFragments.add(inid(2));
//        mFragments.add(inid(3));
//        mFragments.add(inid(4));

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        initFragmentViewpager();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }
    }

    private void initFragmentViewpager() {
        mTabLayout.setTabDate(mTabbeans);
        mTabLayout.setmListener(new CustomizeTabLayout.OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position, false);
            }
            /**
             * 连续点击调用此方法
             */
            @Override
            public void onTabReselect(int position) {

            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                mTabLayout.setCurrentTab(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    /**
     * 底部导航栏
     */
    private void setBottomNavigationBar(){
        bottomNavigationBar.setTabSelectedListener(new BottomNavigationBar.OnTabSelectedListener() {
            @Override
            public void onTabSelected(int position) {}
            @Override
            public void onTabUnselected(int position) {}
            @Override
            public void onTabReselected(int position) {}
        });
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_STATIC);
        bottomNavigationBar.addItem(
                new BottomNavigationItem(R.mipmap.icon_red_home_24dp, "首页")         //选中
                        .setInactiveIcon(ContextCompat.getDrawable(this,R.mipmap.icon_grey_home_24dp)))  //未选中
                .addItem(new BottomNavigationItem(R.mipmap.icon_back, "组件"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_red_user_24dp, "我的"))
                .addItem(new BottomNavigationItem(R.mipmap.icon_red_more_24dp, "更多"))
                .setFirstSelectedPosition(0)
                .initialise(); //所有的设置需在调用该方法前完成
    }
}
