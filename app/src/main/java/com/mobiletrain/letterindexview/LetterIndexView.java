package com.mobiletrain.letterindexview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by idea on 2016/9/29.
 */
public class LetterIndexView extends View {

    private static final String TAG = "test";
    public static final int PADDING_TOP_BOTTOM = 20;
    String[] letters = new String[]{
            "A", "B", "C", "D", "E", "F", "G",
            "H", "I", "J", "K", "L", "M", "N",
            "O", "P", "Q", "R", "S", "T",
            "U", "V", "W", "X", "Y", "Z", "#",
    };
    private Paint paint;
    private int width;
    private int height;
    private int currentPosition = 0;

    public LetterIndexView(Context context) {
        this(context, null);
    }

    public LetterIndexView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterIndexView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //setBackgroundResourece();
        setBackgroundResource(R.drawable.shape_letterindexview_bg);

        paint = new Paint();
        paint.setAntiAlias(true);//抗锯齿
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (width == 0) {
            width = getWidth();
            height = getHeight();
        }

        //把字母画在控件上，【选中字母】用红色画笔，否则黑色
        for (int i = 0; i < letters.length; i++) {

            //计算startX
            String letter = letters[i];
            float letterSize = paint.measureText(letter);
            float startX = (width - letterSize) / 2;

            //startY
            float unitHeight = (height - 40) / 27f;
            float startY = 20 + i * unitHeight + (unitHeight + letterSize) / 2;


            if(i == currentPosition){
                paint.setColor(Color.RED);
            }else {
                paint.setColor(Color.BLACK);
            }

            paint.setTextSize(35);
            paint.setStyle(Paint.Style.FILL_AND_STROKE);
            canvas.drawText(letter, startX, startY, paint);
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float y = event.getY();
        int action = event.getAction();
//        Log.e(TAG, "onTouchEvent:action="+action);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                if(onPositionChangeListener!=null){
                    onPositionChangeListener.onFingerDown(true);
                }
                startAutoChange(y);
                break;

            case MotionEvent.ACTION_MOVE:
                startAutoChange(y);
                break;

            case MotionEvent.ACTION_UP:
                //背景恢复默认
                setBackgroundResource(R.drawable.shape_letterindexview_bg);
                if(onPositionChangeListener!=null){
                    onPositionChangeListener.onFingerDown(false);
                }
                break;
        }
        return true;//把事件消费干净
    }

    /**
     * 根据当前手指所在的高度确定当前选中字母的位置
     * @param y
     */
    private void startAutoChange(float y) {
        //背景高亮
        setBackgroundResource(R.drawable.shape_letterindexview_bg_pressed);

        //根据y的位置，动态计算当前位于哪个字母的区域
        int temp = getCurrentPosition(y);
        if(temp > -1 && temp<letters.length){
            setCurrentPosition(temp);

            //将该字母设置为【选中字母】，重绘，对外抛出字母变化接口，回调该接口
            if(onPositionChangeListener!=null){
                onPositionChangeListener.onPositionChanged(currentPosition,letters[currentPosition]);
            }
        }
    }

    private void setCurrentPosition(int temp) {
        currentPosition = temp;
        invalidate();
    }


    public void setCurrentLetter(String firstLetter) {
        for (int i = 0; i < letters.length; i++) {
            if(letters[i].equals(firstLetter)){
                setCurrentPosition(i);
                return;
            }
        }
    }

    private int getCurrentPosition(float y) {
        return  (int) ((y / (float) getHeight()) * letters.length);//故意制造误差

//        //20  + i*unitheight ~  20  + (i+1)*unitheight
//        for (int i = 0; i < letters.length; i++) {
//            if (y >= 20 + i * (height - 40) / letters.length && y <= 20 + (i + 1) * (height - 40) / letters.length) {
//                return i;
//            }
//        }
//        return -1;
    }

    OnPositionChangeListener onPositionChangeListener = null;
    public void setOnPositionChangeListener(OnPositionChangeListener onPositionChangeListener) {
        this.onPositionChangeListener = onPositionChangeListener;
    }

    interface OnPositionChangeListener {
        void onPositionChanged(int position,String letter);
        void onFingerDown(boolean fingerDown);
    }
}

