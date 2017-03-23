package ifox.sicnu.com.circle_2048.Start;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class StartThread extends Thread {
    private static final int span = 20;           //休息时间
    private static final String TAG = "StartThread";
    boolean flag;           //开启线程的标志位
    StartView startView;

    public StartThread(StartView startView) {
        this.startView = startView;
    }

    @Override
    public void run() {
        while (flag) {
            while (startView.getOffset() <= 360) {
                startView.increaseOffset();
                SurfaceHolder sh = startView.getHolder();
                synchronized (sh) {
                    Canvas canvas = sh.lockCanvas();
                    if (canvas != null) {
                        startView.doDraw(canvas);
                        startView.getHolder().unlockCanvasAndPost(canvas);
                    }
                }
                try {
                    Thread.sleep(span);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
        if (startView.getOffset() >= 360)
            startView.gotonextView();
    }
}
