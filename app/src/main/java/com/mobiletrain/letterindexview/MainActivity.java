package com.mobiletrain.letterindexview;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LetterIndexView.OnPositionChangeListener{

    private LetterIndexView livLetters;
    private TextView tvCurrentLetter;
    private ListView lvFriends;
    private FriendAdapter adapter;
    private List<Friend> friends = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        initLivLetters();
        initData();
        initLvFriends();
    }

    private void findViews() {
        tvCurrentLetter = ((TextView) findViewById(R.id.tvCurrentLetter));
        lvFriends = ((ListView) findViewById(R.id.lvFriends));
        livLetters = ((LetterIndexView) findViewById(R.id.liv));
    }

    private void initLivLetters() {
        livLetters.setOnPositionChangeListener(this);
    }

    private void initLvFriends() {
        adapter = new FriendAdapter(this,friends);
        lvFriends.setAdapter(adapter);

        lvFriends.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                String firstLetter = adapter.friends.get(firstVisibleItem).getFirstLetter();
                livLetters.setCurrentLetter(firstLetter);
            }
        });
    }

    private void initData() {
        String[] names = getResources().getStringArray(R.array.friends);
        for (int i = 0; i < names.length; i++) {
            String name = names[i];
            String pinyin = ChineseToPinyinHelper.getInstance().getPinyin(name).toUpperCase();
            String firstLetter = pinyin.charAt(0) + "";
            if(!firstLetter.matches("[A-Z]")){
                firstLetter = "#";
            }
            Friend friend = new Friend(name, pinyin, firstLetter);
            friends.add(friend);
        }
        //给朋友排序
        Collections.sort(friends);
//        Collections.sort(friends, new Comparator<Friend>() {
//            @Override
//            public int compare(Friend lhs, Friend rhs) {
//                if(lhs.getFirstLetter().equals("#")){
//                    return -1;
//                }
//                if(rhs.getFirstLetter().equals("#")){
//                    return 1;
//                }
//                return lhs.getPinyin().compareTo(rhs.getPinyin());
//            }
//        });
    }

    @Override
    public void onPositionChanged(int position, String letter) {
        tvCurrentLetter.setText(letter);
        Integer temp = adapter.firstLetterPositions.get(letter);
        if(temp!=null){
            lvFriends.setSelection(temp);
        }
    }

    @Override
    public void onFingerDown(boolean fingerDown) {
        if(fingerDown){
            tvCurrentLetter.setVisibility(View.VISIBLE);
        }else {
            tvCurrentLetter.setVisibility(View.GONE);
        }
    }

}
