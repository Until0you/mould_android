package com.mouldandroid.activity.message;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.mouldandroid.R;
import com.mouldandroid.adapter.MessageAdapter;
import com.mouldandroid.base.BaseActivity;
import com.mouldandroid.entity.Message;
import com.mouldandroid.urlInterface.APIServer;
import com.mouldandroid.utils.ConstValues;
import com.mouldandroid.utils.LogUtils;
import com.mouldandroid.utils.ToastUtil;
import com.yanzhenjie.recyclerview.swipe.SwipeItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.ArrayList;

import butterknife.BindView;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrator on 2018/1/25.
 * 消息列表
 */

public class MessageActivity extends BaseActivity implements View.OnClickListener,SwipeItemClickListener {

    private Context context;

    @BindView(R.id.iv_back)
    public ImageView iv_back;
    @BindView(R.id.iv_save)
    public ImageView iv_save;
    @BindView(R.id.tv_title_name)
    public TextView tv_title_name;
    @BindView(R.id.recycler_view)
    public SwipeMenuRecyclerView recycler_view;

    private MessageAdapter adapter;
    private Message myMessage;

    @Override
    protected int setView() {
        return R.layout.message_layout;
    }

    @Override
    protected void initView() {
        context = MessageActivity.this;
        iv_back.setOnClickListener(this);
        iv_save.setVisibility(View.GONE);
        tv_title_name.setText("系统消息");

        adapter = new MessageAdapter(context);
        recycler_view.setLayoutManager(new LinearLayoutManager(context));
        // 设置菜单创建器。
        recycler_view.setSwipeMenuCreator(swipeMenuCreator);
        //设置列表样式
        recycler_view.addItemDecoration(new DefaultItemDecoration(ContextCompat.getColor(this, R.color.color_656565)));
        // 设置菜单Item点击监听。
        recycler_view.setSwipeMenuItemClickListener(mMenuItemClickListener);
        //列表点击监听
        recycler_view.setSwipeItemClickListener(this);
        //设置是否允许侧滑，默认为允许，false为允许，true为不允许
//        recycler_view.setItemViewSwipeEnabled(false);
    }

    @Override
    protected void initData() {
        postData();
    }


    private void postData(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ConstValues.CSYM)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        APIServer apiServer = retrofit.create(APIServer.class);
        apiServer.getMessageList(mouldManager.getUserAccount())
                .subscribeOn(Schedulers.newThread())
                .observeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Message>() {
                    @Override
                    public void onCompleted() {}
                    @Override
                    public void onError(Throwable e) {
                        LogUtils.e("错误信息：" + e.getMessage());
                        ToastUtil.showToast(context,"获取数据失败");
                    }
                    @Override
                    public void onNext(Message message) {
//                        ToastUtil.showToast(context,"获取数据成功");
                        myMessage = message;
                        recycler_view.setAdapter(adapter);
                        adapter.notifyDataSetChanged(addList(message));
                    }
                });
    }

    private ArrayList<Message.MessageData> addList(Message message){
        ArrayList<Message.MessageData> list = new ArrayList<>();
        for (int i = 0;i < message.getData().size();i++){
            list.add(message.getData().get(i));
        }
        return list;
    }

    /**
     * 菜单创建器。在Item要创建菜单的时候调用。
     */
    private SwipeMenuCreator swipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = getResources().getDimensionPixelSize(R.dimen.diment_70dp);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(context)
                        .setBackground(R.drawable.selector_green)
                        .setImage(R.mipmap.ic_launcher) // 图标。
                        .setText("删除") // 文字。
                        .setTextColor(Color.WHITE) // 文字颜色。
                        .setTextSize(16) // 文字大小。
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };

    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。

            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                Toast.makeText(MessageActivity.this, "list第" + adapterPosition + "; 右侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                Toast.makeText(MessageActivity.this, "list第" + adapterPosition + "; 左侧菜单第" + menuPosition, Toast.LENGTH_SHORT).show();
            }
        }
    };

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.iv_back:
                finish();
                break;
        }
    }

    /**
     * 消息项点击监听
     * @param itemView
     * @param position
     */
    @Override
    public void onItemClick(View itemView, int position) {
//        ToastUtil.showToast(context,"第"+ position +"个");
        Intent it_msg_content = new Intent(context,MessageContent.class);
        it_msg_content.putExtra("title",myMessage.getData().get(position).getTitle());
        it_msg_content.putExtra("content",myMessage.getData().get(position).getSummary());
        startActivity(it_msg_content);
    }
}
