package ifox.sicnu.com.circle_2048.Game;

import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback{

    public GameView(Context context) {
        super(context);
        getHolder().addCallback(this);
    }

    public void doDraw(Canvas canvas){
        GameDrawer.doDraw(canvas);
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

    }
}
