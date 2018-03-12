package com.mouldandroid.fragments;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.base.BaseFragments;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Administrator on 2018/1/17.
 */

public class MoreFragment extends BaseFragments{

    @BindView(R.id.tablayout_title)
    public TabLayout tablayout_title;
    @BindView(R.id.vp_fragment)
    public ViewPager vp_fragment;
    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;

    private List<String> mTitleList = new ArrayList<>();//页卡标题集合
    private List<View> mViewList = new ArrayList<>();//页卡视图集合
    private List<String> listTitles;
    private List<Fragment> fragments;
    private List<TextView> listTextViews;

    @Override
    public int getLayoutResId() {
        return R.layout.more_fragment;
    }

    @Override
    protected void initViewsAndEvents(View view) {
        iv_back.setVisibility(View.GONE);
        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("内容");

    }

    @Override
    protected void initData() {
        listTitles = new ArrayList<>();
        fragments = new ArrayList<>();
        listTextViews = new ArrayList<>();

        listTitles.add("推荐");
        listTitles.add("欧美");
        listTitles.add("国内");
        listTitles.add("日韩");
        ContentFragment fragment_one = new ContentFragment();
        ContentFragment fragment_two = new ContentFragment();
        ContentFragment fragment_three = new ContentFragment();
        ContentFragment fragment_four = new ContentFragment();
        fragments.add(fragment_one);
        fragments.add(fragment_two);
        fragments.add(fragment_three);
        fragments.add(fragment_four);


        for (int i = 0;i<listTitles.size();i++){
            tablayout_title.addTab(tablayout_title.newTab().setText(listTitles.get(i)));//添加tab选项
        }

        FragmentPagerAdapter mAdapter = new FragmentPagerAdapter(getActivity().getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragments.get(position);
            }

            @Override
            public int getCount() {
                return fragments.size();
            }
            //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
            @Override
            public CharSequence getPageTitle(int position) {
              return listTitles.get(position);
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
//                super.destroyItem(container, position, object);
            }
        };
        vp_fragment.setAdapter(mAdapter);
        tablayout_title.setTabMode(TabLayout.MODE_SCROLLABLE);
        tablayout_title.setupWithViewPager(vp_fragment);//将TabLayout和ViewPager关联起来。
        tablayout_title.setTabsFromPagerAdapter(mAdapter);//给Tabs设置适配器
    }
}
