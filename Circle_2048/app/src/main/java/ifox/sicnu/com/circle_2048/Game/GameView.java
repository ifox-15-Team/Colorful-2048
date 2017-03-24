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
    GameBoard gb;
    GameDrawer gd;
    GameTouchListener gtl;
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
        gd.clearOffset();
        if (sdt != null) {
            sdt.flag = false;
            sdt = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        gtl.scrollClick(event);
        Canvas canvas = getHolder().lockCanvas();
        doDraw(canvas);
        getHolder().unlockCanvasAndPost(canvas);
        return true;
    }
}
