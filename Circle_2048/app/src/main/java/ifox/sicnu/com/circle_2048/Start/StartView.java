package ifox.sicnu.com.circle_2048.Start;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

import ifox.sicnu.com.circle_2048.Data.Const;
import ifox.sicnu.com.circle_2048.R;
import ifox.sicnu.com.circle_2048.StartActivity;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class StartView extends SurfaceView implements SurfaceHolder.Callback {
    private static final String TAG = "StartView";
    private int offset = 0;
    SurfaceHolder surfaceHolder;
    StartThread st;
    boolean flag = false;               //为了防止线程冲突，如果已经 点击过屏幕，则flag 为ture


    public StartView(Context context) {
        super(context);
        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this);
    }


    public void doDraw(Canvas canvas) {
        canvas.rotate(offset, Const.WIDTH_SC / 2, Const.HEIGHT_SC / 2);
        canvas.scale(1 - ((float) offset / 360), 1 - ((float) offset / 360), Const.WIDTH_SC / 2, Const.HEIGHT_SC / 2);
        canvas.drawColor(Color.WHITE);
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.start_background);
        bitmap = Bitmap.createScaledBitmap(bitmap, Const.WIDTH_SC, Const.HEIGHT_SC, true);
        canvas.drawBitmap(bitmap, 0, 0, null);
        if (this.offset > 360 && this.st != null)
            this.st.flag = false;
    }

    @Override
    public void surfaceCreated(SurfaceHolder surfaceHolder) {
        Canvas canvas = surfaceHolder.lockCanvas();
        doDraw(canvas);
        surfaceHolder.unlockCanvasAndPost(canvas);
        if (flag) {
            st = new StartThread(this);
            st.flag = true;
            st.start();
        }
    }

    @Override
    public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
        if (st != null) {
            st.flag = false;
            st = null;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (!flag) {
                flag = true;
                st = new StartThread(this);
                st.flag = true;
                st.start();
            }
        }
        return true;
    }

    public int getOffset() {
        return offset;
    }

    public void increaseOffset() {
        this.offset += 30;
    }

    public void gotonextView() {
        if (this.st != null) {
            this.st.flag = false;
            this.st = null;
        }
        ((StartActivity) getContext()).start_game();
    }
}
