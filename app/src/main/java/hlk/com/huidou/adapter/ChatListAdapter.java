package hlk.com.huidou.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import hlk.com.huidou.R;
import hlk.com.huidou.bean.ChatMessage;

/**
 * 聊天适配器
 * Created by hlk on 2016/11/1.
 */

public class ChatListAdapter extends BaseAdapter {

    private Context mContext;
    private ArrayList<ChatMessage> mDatas;

    public ChatListAdapter(Context context, ArrayList<ChatMessage> data) {
        this.mContext = context;
        this.mDatas = data;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        ChatMessage message = mDatas.get(position);
        return message.getType() == ChatMessage.Type.INPUT ? 1 : 0;
    }

    @Override
    public View getView(int position, View contentView, ViewGroup viewGroup) {
        int itemViewType = getItemViewType(position);
        ViewHolder holder;
        if (contentView == null) {
            holder = new ViewHolder();
            switch (itemViewType) {
                case 0://发送
                    contentView = View.inflate(mContext, R.layout.chat_right, null);
                    holder.name = (TextView) contentView.findViewById(R.id.chat_to_name);
                    holder.createDate = (TextView) contentView.findViewById(R.id.tv_crate_date);
                    holder.msg = (TextView) contentView.findViewById(R.id.chat_to_content);
                    break;
                case 1://返回
                    contentView = View.inflate(mContext, R.layout.chat_left, null);
                    holder.name = (TextView) contentView.findViewById(R.id.chat_from_name);
                    holder.createDate = (TextView) contentView.findViewById(R.id.tv_crate_date);
                    holder.msg = (TextView) contentView.findViewById(R.id.chat_from_content);
                    break;
            }
            contentView.setTag(holder);
        } else {
            holder = (ViewHolder) contentView.getTag();
        }

        holder.msg.setText(mDatas.get(position).getMsg());
        holder.createDate.setText(mDatas.get(position).getDateStr());
        return contentView;
    }


    class ViewHolder {
        TextView createDate;
        TextView msg;
        TextView name;
    }

}
