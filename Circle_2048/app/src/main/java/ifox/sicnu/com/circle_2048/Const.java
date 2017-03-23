package ifox.sicnu.com.circle_2048;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class Const {
    public static int WIDTH_SC;
    public static int HEIGHT_SC;         //屏幕的宽高

    public static class Tool {
        public static float PI = 3.1415926535f;

        public static ToolPoint createPoint(int cx, int cy, int r, int radius) {
            float realradius = 2 * PI / 360 * radius;
            int x = (int) (Math.cos(realradius) * r + cx);
            int y = (int) (Math.sin(realradius) * r + cy);
            return new ToolPoint(x, y);
        }
    }           //工具类

    public static class ToolPoint {
        public int x;
        public int y;

        public ToolPoint(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }
}
