package com.mobiletrain.letterindexview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by idea on 2016/9/30.
 */
public class FriendAdapter extends BaseAdapter {

    private final LayoutInflater inflater;
    Context context;
    public List<Friend> friends;
    public Map<String,Integer> firstLetterPositions = new HashMap<>();

    public FriendAdapter(Context context, List<Friend> friends) {
        this.context = context;
        this.friends = friends;//送进来的朋友是已经按拼音排好序的
        inflater = LayoutInflater.from(context);

        //遍历所有朋友，记录第一个A、B、C、D姓朋友的位置，将来让该位置显示tvFirstLetter，其余位置均不显示
        for (int i = 0; i < friends.size(); i++) {
            Friend friend = friends.get(i);
            String firstLetter = friend.getFirstLetter();
            if(!firstLetterPositions.containsKey(firstLetter)){
                firstLetterPositions.put(firstLetter,i);
            }
        }
    }

    @Override
    public int getCount() {
        return friends.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_friend, parent, false);
            TextView tvFirstLetter = (TextView) convertView.findViewById(R.id.tvFirstLetter);
            ImageView iv = (ImageView) convertView.findViewById(R.id.iv);
            TextView tvName = (TextView) convertView.findViewById(R.id.tvName);
            holder = new Holder(tvFirstLetter, iv, tvName);
            convertView.setTag(holder);
        } else {
            holder = ((Holder) convertView.getTag());
        }

        Friend friend = friends.get(position);
        holder.tvName.setText(friend.getName());

        //判断tvFirstLetter要不要显示
        Integer firstLetterPosition = firstLetterPositions.get(friend.getFirstLetter());//A姓第一人的position
        if(firstLetterPosition == position){
            holder.tvFirstLetter.setVisibility(View.VISIBLE);
            holder.tvFirstLetter.setText(friend.getFirstLetter());
        }else {
            holder.tvFirstLetter.setVisibility(View.GONE);
        }

        return convertView;
    }

    class Holder {
        TextView tvFirstLetter;
        ImageView iv;
        TextView tvName;

        public Holder(TextView tvFirstLetter, ImageView iv, TextView tvName) {
            this.tvFirstLetter = tvFirstLetter;
            this.iv = iv;
            this.tvName = tvName;
        }
    }
}
