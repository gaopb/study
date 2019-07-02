package com.eenet.androidbase.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.Random;

/**
 * //                            _ooOoo_
 * //                           o8888888o
 * //                           88" . "88
 * //                           (| -_- |)
 * //                            O\ = /O
 * //                        ____/`---'\____
 * //                      .   ' \\| |// `.
 * //                      / \\||| : |||// \
 * //                     / _||||| -:- |||||- \
 * //                       | | \\\ - /// | |
 * //                     | \_| ''\---/'' | |
 * //                     \ .-\__ `-` ___/-. /
 * //                   ___`. .' /--.--\ `. . __
 * //               ."" '< `.___\_<|>_/___.' >'"".
 * //               | | : `- \`.;`\ _ /`;.`/ - ` : | |
 * //                \ \ `-. \_ __\ /__ _/ .-` / /
 * //         ======`-.____`-.___\_____/___.-`____.-'======
 * //                           `=---='
 * //
 * //        .............................................
 * //                 佛祖保佑       永无BUG
 * Created by mei on 2017/7/11.
 * 用于图形验证码的工具类
 */

public class CodeUtils {

    private static final char[] CHARS = {
            '0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
            'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M',
            'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'
    };

    private static CodeUtils mCodeUtils;
    private int mPaddingLeft, mPaddingTop;
    private StringBuilder mBuilder = new StringBuilder();
    private Random mRandom = new Random();
    private String mCode;

    private static final int DEFAULT_CODE_LENGTH = 4;//验证码的长度
    private static final int DEFAULT_FONT_SIZE = 40;//字体大小
    private static final int DEFAULT_LINE_NUMBER = 3;//干扰线条数
    private static final int DEFAULT_WIDTH = 150;//默认宽度、图片的总宽
    private static final int DEFAULT_HEIGHT = 80;//默认高度、图片的总高
    private static final int DEFAULT_COLOR = 0xDF;//默认背景颜色值
    private static final int PADDING_LEFT = 15;//左边距
    private static final int PADDING_TOP = 55;//上边距
    private static final int RANGE_PADDING_LEFT = 15;//左边距范围值
    private static final int RANGE_PADDING_TOP = 15;//上边距范围值

    public static CodeUtils getInstance() {
        if (mCodeUtils == null) {
            mCodeUtils = new CodeUtils();
        }
        return mCodeUtils;
    }

    public Bitmap createBitmap() {
        mPaddingLeft = 0;
        mPaddingTop = 0;
        mCode = createCode();

        Bitmap bitmap = Bitmap.createBitmap(DEFAULT_WIDTH, DEFAULT_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        canvas.drawColor(Color.rgb(DEFAULT_COLOR, DEFAULT_COLOR, DEFAULT_COLOR));
        Paint paint = new Paint();
        paint.setTextSize(DEFAULT_FONT_SIZE);
        for(int i = 0; i < mCode.length(); i ++){
            randomTextStyle(paint);
            randomPadding();
            canvas.drawText(mCode.charAt(i) + "",mPaddingLeft,mPaddingTop,paint);
        }
        
        for (int i = 0; i < DEFAULT_LINE_NUMBER; i++) {
            drawLine(canvas, paint);
        }

        canvas.save(Canvas.ALL_SAVE_FLAG);
        canvas.restore();
        return bitmap;
    }

    //生成验证码
    private String createCode() {
        mBuilder.delete(0, mBuilder.length());//使用前首先清空内容
        for (int i = 0; i < DEFAULT_CODE_LENGTH; i++) {
            mBuilder.append(CHARS[mRandom.nextInt(CHARS.length)]);
        }
        return mBuilder.toString();
    }

    //随机文本样式
    private void randomTextStyle(Paint paint) {
        int color = randomColor();
        paint.setColor(color);
        paint.setFakeBoldText(mRandom.nextBoolean());//true为粗体，false为非粗体
        float skewX = mRandom.nextInt(11) / 10;
        skewX = mRandom.nextBoolean() ? skewX : -skewX;
        paint.setTextSkewX(skewX);//float 类型参数，负数表示右斜，整数表左斜
//        paint.setUnderlineText(false);//true为下划线，false为非下划线
//        paint.setStrikeThruText(false);//true为删除线，false为非删除线
    }

    //随机颜色
    private int randomColor() {
        mBuilder.delete(0, mBuilder.length());//使用前清空内容
        String haxString;
        for (int i = 0; i < 3; i++) {
            haxString = Integer.toHexString(mRandom.nextInt(0xFF));
            if (haxString.length() == 1) {
                haxString = "0" + haxString;
            }
            mBuilder.append(haxString);
        }

        return Color.parseColor("#" + mBuilder.toString());
    }

    //生成干扰线
    private void drawLine(Canvas canvas, Paint paint) {
        int color = randomColor();
        int startX = mRandom.nextInt(DEFAULT_WIDTH);
        int startY = mRandom.nextInt(DEFAULT_HEIGHT);
        int stopX = mRandom.nextInt(DEFAULT_WIDTH);
        int stopY = mRandom.nextInt(DEFAULT_HEIGHT);
        paint.setStrokeWidth(1);
        paint.setColor(color);
        canvas.drawLine(startX,startY,stopX,stopY,paint);
    }

    //随机间距
    private void randomPadding() {
        mPaddingLeft += PADDING_LEFT + mRandom.nextInt(RANGE_PADDING_LEFT);
        mPaddingTop = PADDING_TOP + mRandom.nextInt(RANGE_PADDING_TOP);
    }

    /**
     * 得到图片中的验证码字符串
     * @return
     */
    public String getCode() {
        return mCode;
    }

}
