package ifox.sicnu.com.circle_2048.Game;

import android.view.MotionEvent;
import android.widget.Toast;

import ifox.sicnu.com.circle_2048.Data.Const;
import ifox.sicnu.com.circle_2048.Data.GameBoard;
import ifox.sicnu.com.circle_2048.Thread.ActionDrawThread;
import ifox.sicnu.com.circle_2048.Thread.StaticDrawThread;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class GameTouchListener {
    private static final String TAG = "GameTouchListener";
    GameBoard gameBoard;
    int tx;
    int ty;             //ScrollDown时的x坐标，y坐标
    int x;
    int y;              //ScrollMove时的x坐标，y坐标
    float lastdistance;     //Scale 时的偏移量
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
                Const.gameDrawer.addScrolOffset((y - this.y) / 2);           //让滑动不那么迅速
            else if (x > Const.WIDTH_SC * 0.625)
                Const.gameDrawer.addScrolOffset((this.y - y) / 2);
            this.x = x;
            this.y = y;
        } else if (event.getAction() == MotionEvent.ACTION_UP) {                //这个代表滑动起手的时候，产生的回滚的特效的处理
            CreateStaticThread();
        }
    }

    private void CreateStaticThread() {
        if (Const.gameView.sdt == null) {
            Const.gameView.sdt = new StaticDrawThread();
            Const.gameView.sdt.start();
        } else if (!Const.gameView.sdt.flag) {
            Const.gameView.sdt = new StaticDrawThread();
            Const.gameView.sdt.start();
        } else {
            Const.gameDrawer.clearStaticOffset();
            Const.gameView.sdt.flag = false;
            Const.gameView.sdt = null;
        }
    }

    private void CreateActionThread() {
        if (Const.gameView.adt == null) {
            Const.gameView.adt = new ActionDrawThread();
            Const.gameView.adt.start();
        } else if (!Const.gameView.adt.flag) {
            Const.gameView.adt = new ActionDrawThread();
            Const.gameView.adt.start();
        } else {
            Const.gameDrawer.clearOffsetCells();
            Const.gameView.adt.flag = false;
            Const.gameView.adt = null;
        }
    }

    public void workscrollClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            this.tx = (int) event.getX();
            this.ty = (int) event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            boolean flag = false;                           //成功行动的标志
            if (Const.gameDrawer.getScaleoffset() > 5) {
//                flag = true;
                gameBoard.operatePush();
            } else if (Const.gameDrawer.getScaleoffset() < -5) {
//                flag = true;
                gameBoard.operatePop();
            } else if (Const.gameDrawer.getScrolloffset() > 5) {
                flag = true;
                gameBoard.operateNegetive();
            } else if (Const.gameDrawer.getScrolloffset() < -5) {
                flag = true;
                gameBoard.operatePositive();
            }
            if (flag) {
                CreateActionThread();
//                gameBoard.debug();
                gameBoard.bordtypeincrease();                       //每次处理完逻辑后，都会进行bordertype 的更改
                if (!gameBoard.createNewCell()) {
                    Toast.makeText(Const.context, "游戏结束", Toast.LENGTH_SHORT).show();
                }                                                   //如果产生新细胞失败，那么会
//                gameBoard.debugsyncAll();
            }
        }
    }


    public void scaleClick(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            float ofx = event.getX(0) - event.getX(1);
            float ofy = event.getY(0) - event.getY(1);
            this.lastdistance = (float) Math.sqrt(ofx * ofx + ofy * ofy);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE) {
            float ofx = event.getX(0) - event.getX(1);
            float ofy = event.getY(0) - event.getY(1);
            float currentdistance = (float) Math.sqrt(ofx * ofx + ofy * ofy);
            Const.gameDrawer.addScaleOffset(currentdistance - lastdistance);
            this.lastdistance = currentdistance;
        }                       //回滚特效的线程交由单点击事件处理。
    }
}
