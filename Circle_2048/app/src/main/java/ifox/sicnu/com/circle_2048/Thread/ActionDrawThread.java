package ifox.sicnu.com.circle_2048.Thread;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import ifox.sicnu.com.circle_2048.Data.Cell;
import ifox.sicnu.com.circle_2048.Data.Const;

/**
 * Created by Funchou Fu on 2017/3/24.
 */
public class ActionDrawThread extends Thread {
    public static final int span = 20;
    public boolean flag = true;
    SurfaceHolder sh;

    public ActionDrawThread() {
        sh = Const.gameView.getHolder();
    }

    @Override
    public void run() {
        while (flag) {
            Const.gameView.gtl.operateforbit_action = true;
            if (Const.gameBoard.isAction()) {
                Canvas canvas = sh.lockCanvas();
                for (int i = 0; i < Const.gameBoard.size_needDraw(); i++) {
                    Cell cell = Const.gameBoard.getNeedDraw(i);
                    if (cell.increaseOffset())
                        Const.gameBoard.carrySignal(cell);
                    Const.gameDrawer.drawActioncell(cell, canvas);
                }
                sh.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(span);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } else {
                flag = false;
            }
        }
        Const.gameView.gtl.operateforbit_action = false;
    }
}
