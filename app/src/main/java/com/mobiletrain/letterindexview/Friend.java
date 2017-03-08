package com.mobiletrain.letterindexview;

/**
 * Created by idea on 2016/9/30.
 */
public class Friend implements Comparable<Friend> {

    String name;
    String pinyin;
    String firstLetter;

    public Friend(String name, String pinyin, String firstLetter) {
        this.name = name;
        this.pinyin = pinyin;
        this.firstLetter = firstLetter;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPinyin() {
        return pinyin;
    }

    public void setPinyin(String pinyin) {
        this.pinyin = pinyin;
    }

    public String getFirstLetter() {
        return firstLetter;
    }

    public void setFirstLetter(String firstLetter) {
        this.firstLetter = firstLetter;
    }

    @Override
    public String toString() {
        return "Friend{" +
                "name='" + name + '\'' +
                ", pinyin='" + pinyin + '\'' +
                ", firstLetter='" + firstLetter + '\'' +
                '}';
    }

    @Override
    public int compareTo(Friend another) {
        if(another.getFirstLetter().equals("#")){
            return -1;
        }
        if(getFirstLetter().equals("#")){
            return 1;
        }
        return getPinyin().compareTo(another.getPinyin());
    }

}
