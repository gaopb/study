package com.eenet.androidbase.utils.event;

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
 * Created by chenxiaozhou on 2016/11/17.
 */

public class EENetNewMessageEvent {

    private int msgNum;

    public int getMsgNum() {
        return msgNum;
    }

    public void setMsgNum(int msgNum) {
        this.msgNum = msgNum;
    }

    public EENetNewMessageEvent(int msgNum) {
        this.msgNum = msgNum;
    }

}
