package ifox.sicnu.com.circle_2048.Thread;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;
import android.widget.Toast;

import ifox.sicnu.com.circle_2048.Data.Cell;
import ifox.sicnu.com.circle_2048.Data.Const;

/**
 * Created by Funchou Fu on 2017/3/24.
 */
public class ActionDrawThread extends Thread {
    public static final int span = 20;
    private static final String TAG = "ActionDrawThread";
    public boolean flag = true;
    SurfaceHolder sh;

    public ActionDrawThread() {
        if (flag)
            sh = Const.gameView.getHolder();
    }

    @Override
    public void run() {
        while (flag) {
            Const.gameView.gtl.operateforbit_action = true;             //禁止用户的操作
//            while (Const.gameBoard.isAction()) {
            while (Const.gameBoard.isAction()) {
                Canvas canvas = sh.lockCanvas();
                Log.i(TAG, String.format("run1: %d", Const.gameBoard.size_alive(Cell.COMBINE)));
                for (int i = 0; i < Const.gameBoard.size_firstneedDraw(); i++) {
                    Cell cell = Const.gameBoard.getFirstNeedDraw(i);
                    if (cell.isAlive() && cell.increaseOffset(Cell.COMBINE)) {
//                        cell.clearExceptData();
//                        Const.gameBoard.removefromFirstDrawList(cell);
                        cell.die();
                        cell.clearDisplay();
                    }
                }
                Log.i(TAG, String.format("run2: %d", Const.gameBoard.size_alive(Cell.MOVE)));
                if (Const.gameBoard.size_alive(Cell.COMBINE) == 0) {               //只有当第一种动画执行完毕，才会执行第二种动画
                    for (int i = 0; i < Const.gameBoard.size_lastneedDraw(); i++) {
                        Cell cell = Const.gameBoard.getLastNeedDraw(i);
                        Log.i(TAG, "run: " + cell.toString());
                        if (cell.isAlive() && cell.increaseOffset(Cell.MOVE)) {
//                            cell.clearExceptData();
//                            Const.gameBoard.removefromFirstDrawList(cell);
                            cell.die();
                            cell.syncTargetDisplay();
                        }
                    }
                }

                if (canvas != null) {
                    Const.gameDrawer.doDraw(canvas);
                    sh.unlockCanvasAndPost(canvas);
                } else
                    Log.i(TAG, "run: canvasNULL");
            }
//            while (Const.gameBoard.isAction()) {
//                Canvas canvas = sh.lockCanvas();
//                Log.i(TAG, String.format("run3: %d", Const.gameBoard.size_needDraw()));
//                for (int i = 0; i < Const.gameBoard.size_needDraw(); i++) {
//                    Cell cell = Const.gameBoard.getLastNeedDraw(i);
//                    if (cell.increaseOffset()) {
//                        Const.gameBoard.synctheMovingCell(cell);
//                        Const.gameBoard.removefromDrawList(cell);
//                    }
//                }
//                Const.gameDrawer.doDraw(canvas);
//                sh.unlockCanvasAndPost(canvas);
//                try {
//                    Thread.sleep(span);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
            try {
                Thread.sleep(5 * span);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            flag = false;
            Const.gameBoard.debugsyncAll();
            Const.gameBoard.bordtypeincrease();                       //每次处理完逻辑后，都会进行bordertype 的更改
            if (!Const.gameBoard.createNewCell()) {
                Toast.makeText(Const.context, "游戏结束", Toast.LENGTH_SHORT).show();
            }                                                   //如果产生新细胞失败，那么会
        }
        Canvas canvas = sh.lockCanvas();
        Const.gameDrawer.doDraw(canvas);
        sh.unlockCanvasAndPost(canvas);
        Const.gameView.gtl.operateforbit_action = false;
    }
}
