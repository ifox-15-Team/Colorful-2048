package zlfffzhh.ifox.com.learn2028;

import android.content.Context;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.WindowManager;

/**
 * Created by 41988 on 2017/3/18.
 */
public class GameView extends SurfaceView implements SurfaceHolder.Callback {


    DrawGame drawGame;
    TouchLogic touchLogic;
    static Context mcontext;
    Canvas canvas;
    public GameView(Context context) {
        super(context);
        mcontext = context;
        touchLogic = new TouchLogic();
        getHolder().addCallback(this);

    }

    public void DrawView(){
        canvas = getHolder().lockCanvas(null);
        DrawGame.draw_game_map(canvas);
        DrawGame.draw_game_cards(canvas);
        getHolder().unlockCanvasAndPost(canvas);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

//        if(event.getAction()==MotionEvent.ACTION_MOVE){
//            DrawGame.draw_game_map(canvas);
//            DrawGame.draw_game_cards(canvas);
//        }

         touchLogic.GameTouch(event);
        if(touchLogic.aws!=0){
            DrawView();
        }

        return true;
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        canvas = getHolder().lockCanvas(null);
        drawGame = new DrawGame();
        DrawGame.draw_game_map(canvas);
        DrawGame.draw_game_cards(canvas);
        getHolder().unlockCanvasAndPost(canvas);



    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {

    }
    public static int getScreenHeight() {
        WindowManager wm = (WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.heightPixels;
    }

    public static int getScreenWith(){
        WindowManager wm = (WindowManager) mcontext.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics metrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(metrics);
        return metrics.widthPixels;
    }
}
