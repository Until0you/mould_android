package com.mouldandroid.activity.contentList;

import android.content.Context;
import android.graphics.Color;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jude.easyrecyclerview.EasyRecyclerView;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;
import com.jude.easyrecyclerview.decoration.DividerDecoration;
import com.mouldandroid.R;
import com.mouldandroid.base.BaseActivity;
import com.mouldandroid.urlInterface.APIServer;
import com.mouldandroid.utils.ConstValues;
import com.mouldandroid.utils.ConversionUtil;
import com.mouldandroid.utils.LogUtils;
import com.mouldandroid.utils.ToastUtil;
import com.mouldandroid.utils.myretrofitutils.StringConverterFactory;
import com.mouldandroid.viewHolder.FinanciaListViewHolder;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/3/1.
 * 金融列表2
 */

public class FinancialListTwoActivity extends BaseActivity implements RecyclerArrayAdapter.OnLoadMoreListener,SwipeRefreshLayout.OnRefreshListener,
        RecyclerArrayAdapter.OnItemClickListener{

    private Context context;

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.recyclerView_financial_two)
    public EasyRecyclerView recyclerView_financial_two;

    private RecyclerArrayAdapter adapter;
    private Handler handler_thread = new Handler();
    private ArrayList<String> arrayList;

    @Override
    protected int setView() {
        return R.layout.financial_list_two_layout;
    }

    @Override
    protected void initView() {
        context = FinancialListTwoActivity.this;
        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("金融列表2");

        recyclerView_financial_two.setLayoutManager(new LinearLayoutManager(context));
        recyclerView_financial_two.setRefreshingColorResources(R.color.color_54a2f8);  //设置SwipeRefreshLayout的进度条颜色
        recyclerView_financial_two.setProgressView(R.layout.view_progress);
        DividerDecoration itemDecoration = new DividerDecoration(Color.GRAY, ConversionUtil.dip2px(context,0.5f),ConversionUtil.dip2px(context,72),0);
        itemDecoration.setDrawLastItem(false);
        //设置项分割线
//        recyclerView.addItemDecoration(itemDecoration);

        recyclerView_financial_two.setAdapterWithProgress(adapter = new RecyclerArrayAdapter(context) {
            @Override
            public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
                return new FinanciaListViewHolder(parent);
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
        recyclerView_financial_two.setRefreshListener(this);
        onRefresh();
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

    /**
     * 项点击
     * @param position
     */
    @Override
    public void onItemClick(int position) {

    }

    /**
     * 上拉加载
     */
    @Override
    public void onLoadMore() {

    }


    private void postData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(StringConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);
        apiServer.financialTwo("1")
                .subscribeOn(Schedulers.newThread())   //请求在新的线程执行
                .observeOn(Schedulers.io())            //请求完成在IO线程执行
                .doOnNext(new Action1<String>() {
                    @Override
                    public void call(String s) {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e(e.getMessage());
                        ToastUtil.showToast(context,"请求失败");
                    }
                    @Override
                    public void onNext(String s) {
                        LogUtils.e(s.getBytes());
                    }
                });
    }

    @OnClick({R.id.iv_back})
    public void OnClick(View v){
        switch (v.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
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


}
