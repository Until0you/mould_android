package com.mouldandroid.fragments;

import android.graphics.Color;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.mouldandroid.R;
import com.mouldandroid.base.BaseFragments;
import com.mouldandroid.entity.TheArticle;
import com.mouldandroid.urlInterface.APIServer;
import com.mouldandroid.utils.ConstValues;
import com.mouldandroid.utils.ConversionUtil;
import com.mouldandroid.utils.LogUtils;
import com.mouldandroid.utils.ToastUtil;
import com.mouldandroid.viewHolder.RecyclerViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/30.
 */

public class ContentFragment extends BaseFragments implements RecyclerArrayAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnItemClickListener{

    @BindView(R.id.recyclerView)
    public EasyRecyclerView recyclerView;

    private RecyclerArrayAdapter adapter;
    private Handler handler_thread = new Handler();
    private final int SUCCESS = 1;
    private ArrayList<TheArticle.TheArticleData> arrayList;

    @Override
    public int getLayoutResId() {
        return R.layout.fragment_item;
    }

    @Override
    protected void loadData() {
        onRefresh();
    }

    @Override
    protected void initViewsAndEvents(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setRefreshingColorResources(R.color.color_54a2f8);  //设置SwipeRefreshLayout的进度条颜色
        recyclerView.setProgressView(R.layout.view_progress);
        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, ConversionUtil.dip2px(getActivity(),0.5f),ConversionUtil.dip2px(getActivity(),72),0);
        itemDecoration.setDrawLastItem(false);
        //设置项分割线
//        recyclerView.addItemDecoration(itemDecoration);

        recyclerView.setAdapterWithProgress(adapter = new RecyclerArrayAdapter(getActivity()) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new RecyclerViewHolder(parent);
            }
        });
        //设置正在加载更多数据
        adapter.setMore(R.layout.view_more,this);
        //设置没有更多数据显示和回调
        adapter.setNoMore(R.layout.view_nomore,onNoMoreListener);
        //设置网络错误layout和回调
        adapter.setError(R.layout.view_error,errorListener);
        //设置项点击事件
        adapter.setOnItemClickListener(this);
        //设置下拉刷新
        recyclerView.setRefreshListener(this);

    }

    @Override
    protected void initData() {

    }

    /**
     * 下拉刷新
     */
    @Override
    public void onRefresh() {
        handler_thread.postDelayed(new Runnable() {
            @Override
            public void run() {
                adapter.clear();
                postData();
            }
        }, 2000);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case SUCCESS:
                    adapter.addAll(arrayList);
                    break;
            }
        }
    };

    /**
     * 加载方法
     */
    @Override
    public void onLoadMore() {

    }

    /**
     * 项点击方法
     * @param position
     */
    @Override
    public void onItemClick(int position) {
        adapter.getItem(position);
    }


    private void postData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);
        apiServer.getPulldown("1","1")
                .subscribeOn(Schedulers.newThread())          //请求在新的线程中执行
                .observeOn(Schedulers.io())                     //请求完成后在io线程中执行
                .doOnNext(new Action1<TheArticle>() {
                    @Override
                    public void call(TheArticle theArticle) {            //添加数据
                        arrayList = new ArrayList<>();
                        for (int i = 0;i<theArticle.getData().size();i++){
                            arrayList.add(theArticle.getData().get(i));
                        }
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())      //最后在主线程中执行
                .subscribe(new Subscriber<TheArticle>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {  //请求失败
                        LogUtils.e(e.getMessage());
                        ToastUtil.showToast(getActivity(),"请求失败！！！");
                    }
                    @Override
                    public void onNext(TheArticle theArticle) {
                        handler.sendEmptyMessage(SUCCESS);
                    }
                });
    }


    //网络错误回调
    private RecyclerArrayAdapter.OnErrorListener errorListener = new RecyclerArrayAdapter.OnErrorListener(){
        @Override
        public void onErrorShow() {
            adapter.resumeMore();
        }
        @Override
        public void onErrorClick() {
            adapter.resumeMore();
        }
    };

    //没有更多回调
    private RecyclerArrayAdapter.OnNoMoreListener onNoMoreListener = new RecyclerArrayAdapter.OnNoMoreListener(){
        @Override
        public void onNoMoreShow() {
            adapter.resumeMore();
        }
        @Override
        public void onNoMoreClick() {
            adapter.resumeMore();
        }
    };

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
