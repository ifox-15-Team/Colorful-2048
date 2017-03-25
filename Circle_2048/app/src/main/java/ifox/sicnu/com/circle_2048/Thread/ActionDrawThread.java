package ifox.sicnu.com.circle_2048.Thread;

import android.graphics.Canvas;
import android.util.Log;
import android.view.SurfaceHolder;

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
            Log.i(TAG, String.format("ActionDrawThread: OKOK"));
        sh = Const.gameView.getHolder();
    }

    @Override
    public void run() {
        Log.i(TAG, String.format("run1: %d", Const.gameBoard.size_needDraw()));
        while (flag) {
            Const.gameView.gtl.operateforbit_action = true;
            Log.i(TAG, String.format("run2: %d", Const.gameBoard.size_needDraw()));
            while (Const.gameBoard.isAction()) {
                Canvas canvas = sh.lockCanvas();
                Log.i(TAG, String.format("run3: %d", Const.gameBoard.size_needDraw()));
                for (int i = 0; i < Const.gameBoard.size_needDraw(); i++) {
                    Cell cell = Const.gameBoard.getNeedDraw(i);
                    if (cell.increaseOffset()) {
                        Const.gameBoard.synctheMovingCell(cell);
                        Const.gameBoard.removefromDrawList(cell);
                    }
                }
                Const.gameDrawer.doDraw(canvas);
                sh.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(span);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            flag = false;
        }
        Const.gameView.gtl.operateforbit_action = false;
    }
}
