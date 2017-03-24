package ifox.sicnu.com.circle_2048.Thread;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

import ifox.sicnu.com.circle_2048.Data.Const;

/**
 * Created by Funchou Fu on 2017/3/24.
 */
public class StaticDrawThread extends Thread {
    private static final int span = 20;
    public boolean flag = true;
    SurfaceHolder sh;

    public StaticDrawThread() {
        sh = Const.gameView.getHolder();
    }

    @Override
    public void run() {
        while (flag) {
            if (Const.gameDrawer.isoffseting()) {
                Const.gameDrawer.suboffset();
                Canvas canvas = sh.lockCanvas();
                Const.gameView.doDraw(canvas);
                sh.unlockCanvasAndPost(canvas);
                try {
                    Thread.sleep(span);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
