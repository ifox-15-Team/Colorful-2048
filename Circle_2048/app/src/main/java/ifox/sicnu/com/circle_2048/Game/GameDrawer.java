package ifox.sicnu.com.circle_2048.Game;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import ifox.sicnu.com.circle_2048.Data.Const;
import ifox.sicnu.com.circle_2048.Data.Cell;
import ifox.sicnu.com.circle_2048.Data.GameBoard;
import ifox.sicnu.com.circle_2048.R;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class GameDrawer {
    private static Paint paint;
    GameBoard gameBoard;
    private Bitmap[] bm_datas;
    private int scrolloffset = 0;           //手势滑动的时候，控制的偏移量
    private int scaleoffset = 0;            //手势缩放的时候，控制的缩放量

    private static final String TAG = "GameDrawer";

    static {
        paint = new Paint();
        paint.setTextSize(10);
    }

    public GameDrawer(GameBoard gb) {
        gameBoard = gb;
        bm_datas = new Bitmap[12];
        bm_datas[0] = BitmapFactory.decodeResource(Const.context.getResources(), R.drawable.pic_2);
        bm_datas[0] = Bitmap.createScaledBitmap(bm_datas[0], Const.BASESPAN, Const.BASESPAN, true);

        bm_datas[1] = BitmapFactory.decodeResource(Const.context.getResources(), R.drawable.pic_4);
        bm_datas[1] = Bitmap.createScaledBitmap(bm_datas[1], Const.BASESPAN, Const.BASESPAN, true);

        bm_datas[2] = BitmapFactory.decodeResource(Const.context.getResources(), R.drawable.pic_8);
        bm_datas[2] = Bitmap.createScaledBitmap(bm_datas[2], Const.BASESPAN, Const.BASESPAN, true);

        bm_datas[3] = BitmapFactory.decodeResource(Const.context.getResources(), R.drawable.pic_16);
        bm_datas[3] = Bitmap.createScaledBitmap(bm_datas[3], Const.BASESPAN, Const.BASESPAN, true);

        bm_datas[4] = BitmapFactory.decodeResource(Const.context.getResources(), R.drawable.pic_32);
        bm_datas[4] = Bitmap.createScaledBitmap(bm_datas[4], Const.BASESPAN, Const.BASESPAN, true);

        bm_datas[5] = BitmapFactory.decodeResource(Const.context.getResources(), R.drawable.pic_64);
        bm_datas[5] = Bitmap.createScaledBitmap(bm_datas[5], Const.BASESPAN, Const.BASESPAN, true);

        bm_datas[6] = BitmapFactory.decodeResource(Const.context.getResources(), R.drawable.pic_128);
        bm_datas[6] = Bitmap.createScaledBitmap(bm_datas[6], Const.BASESPAN, Const.BASESPAN, true);

        bm_datas[7] = BitmapFactory.decodeResource(Const.context.getResources(), R.drawable.pic_256);
        bm_datas[7] = Bitmap.createScaledBitmap(bm_datas[7], Const.BASESPAN, Const.BASESPAN, true);

        bm_datas[8] = BitmapFactory.decodeResource(Const.context.getResources(), R.drawable.pic_512);
        bm_datas[8] = Bitmap.createScaledBitmap(bm_datas[8], (int) (Const.BASESPAN * 1.1), (int) (Const.BASESPAN * 1.05), true);

        bm_datas[9] = BitmapFactory.decodeResource(Const.context.getResources(), R.drawable.pic_1024);
        bm_datas[9] = Bitmap.createScaledBitmap(bm_datas[9], (int) (Const.BASESPAN * 1.2), (int) (Const.BASESPAN * 1.1), true);

        bm_datas[10] = BitmapFactory.decodeResource(Const.context.getResources(), R.drawable.pic_2048);
        bm_datas[10] = Bitmap.createScaledBitmap(bm_datas[10], (int) (Const.BASESPAN * 1.3), (int) (Const.BASESPAN * 1.15), true);

        bm_datas[11] = BitmapFactory.decodeResource(Const.context.getResources(), R.drawable.pic_4096);
        bm_datas[11] = Bitmap.createScaledBitmap(bm_datas[11], (int) (Const.BASESPAN * 1.4), (int) (Const.BASESPAN * 1.2), true);
    }

    public void doDraw(Canvas canvas) {
        DrawLeft(Const.MIDDLELINE, Const.HEIGHT_SC, canvas);                                                                      // 传入给绘制左边的逻辑的宽高
        DrawRight(Const.MIDDLELINE, 0, (int) (Const.WIDTH_SC * 0.75), Const.HEIGHT_SC, canvas);                                   // 传入给绘制右边逻辑的宽高
    }

    //DrawRight 会调用 绘制静态细胞的方法和绘制动态细胞的方法
    private void DrawRight(int ofx, int ofy, int width, int height, Canvas canvas) {
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(ofx, ofy, width + ofx, height, paint);
        DrawStaticCell(ofx, ofy, width, height, canvas);
        if (Const.gameView.adt != null && Const.gameView.adt.flag)
            for (int i = 0; i < gameBoard.size_needDraw(); i++) {
                DrawEachActioncell(gameBoard.getNeedDraw(i), ofx, ofy, width, height, canvas);
            }
    }


    //绘制细胞的简单逻辑，由 绘制静态细胞 DrawStaticCell 调用
    private void DrawCell(int x, int y, int r, int level, Canvas canvas, int id, boolean flag) {
        if (flag) {
            canvas.drawCircle(x, y, r, paint);
            int drx = x - Const.BASESPAN / 2;
            int dry = y - Const.BASESPAN / 2;
            paint.setColor(Color.RED);
            canvas.drawText(String.valueOf(id), drx, dry, paint);
            Bitmap bitmap = getBitmapByLevel(level);
            if (bitmap != null)
                canvas.drawBitmap(bitmap, drx, dry, null);
        }
    }

    private void DrawLeft(int width, int height, Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, width, height, paint);
    }

    private Bitmap getBitmapByLevel(int level) {
        if (level == 0)
            return null;
        else
            return this.bm_datas[level - 1];
    }

    public void addScrolOffset(int index) {
        this.scrolloffset += index;
    }

    public void clearStaticOffset() {
        this.scrolloffset = 0;
        this.scaleoffset = 0;
    }

    //如果偏移量不为0，说明正在偏移
    public boolean isoffseting() {
        if (this.scrolloffset != 0 || this.scaleoffset != 0)
            return true;
        else
            return false;
    }

    //对两个offset进行削减
    public void suboffset() {
        this.scrolloffset *= 0.95;
        this.scaleoffset *= 0.95;
        if (scrolloffset > 0) {
            this.scrolloffset -= 3;
            if (scrolloffset < 0)
                clearStaticOffset();
        } else if (scrolloffset < 0) {
            this.scrolloffset += 3;
            if (scrolloffset > 0)
                clearStaticOffset();
        }
        if (scaleoffset > 0) {
            this.scaleoffset -= 3;
            if (scaleoffset < 0)
                clearScaleOffset();
        } else if (scaleoffset < 0) {
            this.scaleoffset += 3;
            if (scaleoffset > 0)
                clearScaleOffset();
        }
    }

    private void clearScaleOffset() {
        this.scaleoffset = 0;
    }

    public void addScaleOffset(float v) {
        if (v > 20)
            v = 20;
        else if (v < -20)
            v = -20;
        this.scaleoffset += v;
        if (this.scaleoffset > 200)
            this.scaleoffset = 200;
        else if (this.scaleoffset < -200)
            this.scaleoffset = -200;
    }

    public int getScaleoffset() {
        return scaleoffset;
    }

    public int getScrolloffset() {
        return scrolloffset;
    }

    public void clearOffsetCells() {
        gameBoard.clearOffsetCells();
    }

    //绘制所有静态细胞的方法-->静态细胞会根据当前手势的变化而变化
    private void DrawStaticCell(int ofx, int ofy, int width, int height, Canvas canvas) {
//        Log.i(TAG, String.format("getPoint: %d", scrolloffset));
        for (int i = 0; i < gameBoard.size(); i++) {
            boolean isboarder = false;
            Cell cell = gameBoard.getCell(i);
            if (i == gameBoard.getMiddleBoarder() || i == gameBoard.getSurfaceBoarder()) {
                isboarder = true;
            }
            Const.ToolPoint point = Const.Tool.getPoint(ofx + width / 2, ofy + height / 2, i, scrolloffset, scaleoffset);
            if (isboarder)
                paint.setColor(Color.YELLOW);
            else
                paint.setColor(Color.DKGRAY);                       //如果当前的点是边界，那么对当前点的背景绘制就变为黄色，否则是灰色
            if (i == 0)
                DrawCell(point.x, point.y, Const.CELL_BIG, cell.getDisplay(), canvas, cell.getId(), true);
            else
                DrawCell(point.x, point.y, Const.CELL_SMALL, cell.getDisplay(), canvas, cell.getId(), cell.isStatic());             //只有静态的细胞，才会绘制里面的display
        }
    }

    /**
     * 传入正在移动的Cell，从而根据该cell自身的偏移量，再调用Const里的Tool 类的getPoint方法进行获取横纵坐标
     */
    private void DrawEachActioncell(Cell cell, int ofx, int ofy, int width, int height, Canvas canvas) {
        Const.ToolPoint point;
        if (cell.getMovetype() == GameBoard.ROTATE_POSITIVE) {
            point = Const.Tool.getPoint(ofx + width / 2, ofy + height / 2, cell.getId(), scrolloffset + cell.getOffset(), scaleoffset);
        } else if (cell.getMovetype() == GameBoard.ROTATE_NEGETIVE) {
            point = Const.Tool.getPoint(ofx + width / 2, ofy + height / 2, cell.getId(), scrolloffset + cell.getOffset(), scaleoffset);
        } else if (cell.getMovetype() == GameBoard.POP) {
            point = Const.Tool.getPoint(ofx + width / 2, ofy + height / 2, cell.getId(), scrolloffset, scaleoffset + cell.getOffset());
        } else {
            point = Const.Tool.getPoint(ofx + width / 2, ofy + height / 2, cell.getId(), scrolloffset, scaleoffset - cell.getOffset());
        }
        paint.setColor(Color.DKGRAY);
        DrawCell(point.x, point.y, Const.CELL_SMALL, cell.getDisplay(), canvas, cell.getId(), true);
    }
}
