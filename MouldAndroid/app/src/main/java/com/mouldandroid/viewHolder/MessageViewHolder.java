package com.mouldandroid.viewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.mouldandroid.R;
import com.mouldandroid.entity.Message;

/**
 * Created by Administrator on 2018/1/25.
 */

public class MessageViewHolder extends RecyclerView.ViewHolder{

    public TextView message_item_title;
    public TextView message_item_content;

    public MessageViewHolder(View itemView) {
        super(itemView);
        message_item_title = itemView.findViewById(R.id.message_item_title);
        message_item_content = itemView.findViewById(R.id.message_item_content);
    }

    public void setData(Message.MessageData data){

        message_item_title.setText(data.getTitle());
        message_item_content.setText(data.getSummary());

    }
}
