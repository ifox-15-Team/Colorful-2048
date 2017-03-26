package ifox.sicnu.com.circle_2048.Data;

import android.content.Context;

import java.util.ArrayList;

import ifox.sicnu.com.circle_2048.Game.GameDrawer;
import ifox.sicnu.com.circle_2048.Game.GameView;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class Const {
    private static final String TAG = "Const";
    public static int WIDTH_SC;
    public static int HEIGHT_SC;            //屏幕的宽高
    public static int CELL_SMALL;
    public static int CELL_BIG;             //Cell的半径
    public static int RADIUS;          //内外层CELL直接的长度
    public static int BASESPAN;             //绘制图片的基本段长
    public static Context context;
    public static GameDrawer gameDrawer;
    public static GameView gameView;
    public static GameBoard gameBoard;
    public static int MIDDLELINE;

    public static class Tool {
        public static float PI = 3.1415926535f;

        public static ToolPoint createPoint(int cx, int cy, int radius, int angle) {
            float realangle = 2 * PI / 360 * angle;
            int x = (int) (Math.cos(realangle) * radius + cx);
            int y = (int) (-Math.sin(realangle) * radius + cy);
            return new ToolPoint(x, y);
        }

        /**
         * @param i :通过这个传入的参数，代入createPoint 来返还Point
         */
        public static ToolPoint getPoint(int cx, int cy, int i, int scrolloffset, int scaleoffset) {
            if (i == 0)
                return createPoint(cx, cy, 0, 0 + scrolloffset);
            else if (i == 1) {
                ToolPoint tp = createPoint(cx, cy, Const.RADIUS + scaleoffset, 90 + scrolloffset);
                return tp;
            } else if (i == 2)
                return createPoint(cx, cy, Const.RADIUS + scaleoffset, 30 + scrolloffset);
            else if (i == 3)
                return createPoint(cx, cy, Const.RADIUS + scaleoffset, -30 + scrolloffset);
            else if (i == 4)
                return createPoint(cx, cy, Const.RADIUS + scaleoffset, -90 + scrolloffset);
            else if (i == 5)
                return createPoint(cx, cy, Const.RADIUS + scaleoffset, -150 + scrolloffset);
            else if (i == 6)
                return createPoint(cx, cy, Const.RADIUS + scaleoffset, 150 + scrolloffset);
            else if (i == 7)
                return createPoint(cx, cy, Const.RADIUS * 2 + scaleoffset, 90 + scrolloffset);
            else if (i == 8)
                return createPoint(cx, cy, Const.RADIUS * 2 + scaleoffset, 60 + scrolloffset);
            else if (i == 9)
                return createPoint(cx, cy, Const.RADIUS * 2 + scaleoffset, 30 + scrolloffset);
            else if (i == 10)
                return createPoint(cx, cy, Const.RADIUS * 2 + scaleoffset, 0 + scrolloffset);
            else if (i == 11)
                return createPoint(cx, cy, Const.RADIUS * 2 + scaleoffset, -30 + scrolloffset);
            else if (i == 12)
                return createPoint(cx, cy, Const.RADIUS * 2 + scaleoffset, -60 + scrolloffset);
            else if (i == 13)
                return createPoint(cx, cy, Const.RADIUS * 2 + scaleoffset, -90 + scrolloffset);
            else if (i == 14)
                return createPoint(cx, cy, Const.RADIUS * 2 + scaleoffset, -120 + scrolloffset);
            else if (i == 15)
                return createPoint(cx, cy, Const.RADIUS * 2 + scaleoffset, -150 + scrolloffset);
            else if (i == 16)
                return createPoint(cx, cy, Const.RADIUS * 2 + scaleoffset, -180 + scrolloffset);
            else if (i == 17)
                return createPoint(cx, cy, Const.RADIUS * 2 + scaleoffset, 150 + scrolloffset);
            else if (i == 18)
                return createPoint(cx, cy, Const.RADIUS * 2 + scaleoffset, 120 + scrolloffset);
            return null;
        }

        /*不重复的添加整型元素*/
        public static void putIntegerIntoList_nosame(ArrayList<Integer> integers, int a) {
            boolean flag = true;
            for (int i = 0; i < integers.size(); i++) {
                if (integers.get(i) == a) {
                    flag = false;
                    break;
                }
            }
            if (flag)
                integers.add(a);
        }

        public static void removeIntegerIntheList(ArrayList<Integer> integers, int a) {
            for (int i = 0; i < integers.size(); i++) {
                if (integers.get(i) == a) {
                    integers.remove(i);
                    break;
                }
            }
        }

        //检查integers 列表里 是否有 整数 a
        public static boolean checkIntegerIntheList(ArrayList<Integer> integers, int a) {
            for (int i = 0; i < integers.size(); i++) {
                if (integers.get(i) == a) {
                    return true;
                }
            }
            return false;
        }
    }           //工具类

    public static class ToolPoint {
        public int x;
        public int y;

        public ToolPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            // TODO Auto-generated method stub
            return String.format("x:%d  y:%d", x, y);
        }
    }
}
