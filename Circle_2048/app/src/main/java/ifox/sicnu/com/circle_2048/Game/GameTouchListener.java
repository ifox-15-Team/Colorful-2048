package ifox.sicnu.com.circle_2048.Game;

import android.util.Log;
import android.view.MotionEvent;

import ifox.sicnu.com.circle_2048.Data.Const;
import ifox.sicnu.com.circle_2048.Data.GameBoard;
import ifox.sicnu.com.circle_2048.Thread.StaticDrawThread;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class GameTouchListener {
    private static final String TAG = "GameTouchListener";
    GameBoard gameBoard;
    int tx;
    int ty;             //Down时的x坐标，y坐标
    int x;
    int y;              //Move时的x坐标，y坐标
    //右边界面占比0.75
    public boolean operateforbid_static = false;
    public boolean operateforbit_action = false;               // 两个变量任意一个为true 时，玩家的操作被进制，由外部调用，而非自身调用

    public GameTouchListener(GameBoard gameBoard) {
        this.gameBoard = gameBoard;
    }

    public void scrollClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.x = (int) event.getX();
            this.y = (int) event.getY();
        }
        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            int x = (int) event.getX();
            int y = (int) event.getY();
//            Log.i(TAG, String.format("scrollClick: x:%d y:%d   nx:%d ny:%d    左滑%d  右滑%d", this.x, this.y, x, y, (this.y - y) / 2, (y - this.y) / 2));
            if (x > Const.MIDDLELINE && x < Const.WIDTH_SC * 0.625)
                Const.gameDrawer.addOffset((y - this.y) / 2);           //让滑动不那么迅速
            else if (x > Const.WIDTH_SC * 0.625)
                Const.gameDrawer.addOffset((this.y - y) / 2);
            this.x = x;
            this.y = y;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (Const.gameView.sdt == null) {
                Const.gameView.sdt = new StaticDrawThread();
                Const.gameView.sdt.start();
            } else if (!Const.gameView.sdt.flag) {
                Const.gameView.sdt = new StaticDrawThread();
                Const.gameView.sdt.start();
            } else {
                Const.gameDrawer.clearOffset();
                Const.gameView.sdt.flag = false;
                Const.gameView.sdt = null;
            }
        }
    }

    public void workClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.tx = (int) event.getX();
            this.ty = (int) event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            if (this.tx > Const.MIDDLELINE && this.tx < Const.WIDTH_SC * 0.625) {

            }
        }
    }

}
