package ifox.sicnu.com.circle_2048.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ifox.sicnu.com.circle_2048.Data.Const;
import ifox.sicnu.com.circle_2048.Data.GameBoard;
import ifox.sicnu.com.circle_2048.Thread.StaticDrawThread;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {
    public GameBoard gb;
    public GameDrawer gd;
    public GameTouchListener gtl;
    public StaticDrawThread sdt;

    public GameView(Context context) {
        super(context);
        Const.gameView = this;
        getHolder().addCallback(this);
        gb = new GameBoard();
        gd = new GameDrawer(gb);
        gtl = new GameTouchListener(gb);
        Const.gameDrawer = gd;
    }

    public void doDraw(Canvas canvas) {
        gd.doDraw(canvas);
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Canvas canvas = getHolder().lockCanvas(null);
        doDraw(canvas);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        gd.clearScrolOffset();
        if (sdt != null) {
            sdt.flag = false;
            sdt = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean flag = false;
        if (event.getPointerCount() == 1) {
            if (!gtl.operateforbid_static && !gtl.operateforbit_action) {
                gtl.scrollClick(event);
                gtl.workscrollClick(event);
                flag = true;
            }
        } else if (event.getPointerCount() == 2) {
            if (!gtl.operateforbid_static && !gtl.operateforbit_action) {
                gtl.scaleClick(event);
                flag = true;
            }
        }

        if (flag) {
            Canvas canvas = getHolder().lockCanvas();
            doDraw(canvas);
            getHolder().unlockCanvasAndPost(canvas);
        }
        return true;
    }
}
