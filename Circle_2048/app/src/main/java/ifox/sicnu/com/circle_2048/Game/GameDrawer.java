package ifox.sicnu.com.circle_2048.Game;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;

import ifox.sicnu.com.circle_2048.Const;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class GameDrawer {
    private static Paint paint;
    static int MiddleLine;
    static boolean datachange = true;

    private static final String TAG = "GameDrawer";

    static {
        paint = new Paint();
        MiddleLine = (int) (Const.WIDTH_SC * 0.25);
        datachange = true;
    }

    public static void doDraw(Canvas canvas) {
        Log.i(TAG, "doDraw: ");
        DrawLeft(MiddleLine, Const.HEIGHT_SC, canvas);                                                                      // 传入给绘制左边的逻辑的宽高
        DrawRight(MiddleLine, 0, (int) (Const.WIDTH_SC * 0.75), Const.HEIGHT_SC, canvas);                                   // 传入给绘制右边逻辑的宽高
    }

    private static void DrawRight(int ofx, int ofy, int width, int height, Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(ofx, ofy, width + ofx, height, paint);
    }

    private static void DrawLeft(int width, int height, Canvas canvas) {
//        if (datachange) {
            Log.i(TAG, "DrawLeft: "+ datachange);
            datachange = false;                 //如果数据发生了改变，那么将执行该绘图操作
            Log.i(TAG, "DrawLeft: "+ datachange);
            paint.setColor(Color.RED);
            paint.setStyle(Paint.Style.FILL);
            canvas.drawRect(0, 0, width, height, paint);
//        }
    }

    /**如果得分发生了变化，记得调用该方法*/
    public static void dataChange() {
        datachange = true;
    }
}
