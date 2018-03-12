package com.mouldandroid.adapter;

import android.content.Context;
import android.view.ViewGroup;

import com.mouldandroid.R;
import com.mouldandroid.entity.Message;
import com.mouldandroid.viewHolder.BaseAdapter;
import com.mouldandroid.viewHolder.MessageViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2018/1/25.
 */

public class MessageAdapter extends BaseAdapter<MessageViewHolder>{

    private List<Message.MessageData> data;

    public MessageAdapter(Context context) {
        super(context);
    }

    @Override
    public void notifyDataSetChanged(List<Message.MessageData> dataList) {
        this.data = dataList;
    }

    @Override
    public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MessageViewHolder(getInflater().inflate(R.layout.message_item_layout,parent,false));
    }

    @Override
    public void onBindViewHolder(MessageViewHolder holder, int position) {
        holder.setData(data.get(position));
    }

    @Override
    public int getItemCount() {
        return data == null ? 0 : data.size();
    }
}
