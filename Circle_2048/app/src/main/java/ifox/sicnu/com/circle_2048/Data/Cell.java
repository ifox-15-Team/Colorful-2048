package ifox.sicnu.com.circle_2048.Data;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class Cell {
    private int id;                    //代表当前Cell实际的id
    private int data;                  //代表当前Cell实际存的数据
    private int display;               //代表当前Cell展示时的数据
    private int offset;                 //代表当前Cell每一格在移对的偏移量，偏移量的的大小是位移的角度 可见，根据cell的id不同，offset的进位也是不同的。
    private int last_steps;            //代表当前Cell还需要行进多少格 >0:push positive  <0:pop negetive
    private int movetype;               //type在GameBoard中进行定义
    public static final int ROTATE_SURFACE = 30;
    public static final int ROTATE_MIDDLE = 60;                 //在内(外)圈的时候，offset达到这(上面)个数值就会进行进位
    public static final int PUSH_MAX = Const.RADIUS;            //在进行滑动的时候，如果offset达到了这个数值，就会进行进位

    //一共十一个等级，如果加上 空 就是十二种状态

    public Cell(int id) {
        this.id = id;
        this.data = 0;
        this.display = 0;
        this.offset = 0;
        this.last_steps = 0;
        this.movetype = 0;
    }

    public boolean isStatic() {
        if (this.last_steps != 0 || this.offset != 0) {
            return false;
        } else
            return true;
    }

    /**
     * 返还需要被绘制的数
     */
    public int getDisplay() {
        return display;
    }

    public void setDisplay(int display) {
        this.display = display;
    }

    //在同步后，会将自己的其他的绘制相关的成员变量统统置为 默认
    public void syncDisplay() {
        this.display = this.data;
        this.offset = 0;
        this.last_steps = 0;
        this.movetype = 0;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void increaseData() {
        this.data++;
    }

    public void clearDataandSetoffset(int type) {
        this.last_steps = 1;
        this.offset = 0;
        this.data = 0;
        this.movetype = type;
    }

    public void clearData() {
        this.data = 0;
    }

    //增加自己的offset 如果增加到了极点到可进位的时候，会返还true
    public boolean increaseOffset() {
        this.offset *= 1.1;
        this.offset += 2;
        if (this.movetype == GameBoard.ROTATE_POSITIVE || this.movetype == GameBoard.ROTATE_NEGETIVE) {
            if (id > 0 && id < 7) {
                if (this.offset > Cell.ROTATE_MIDDLE * this.last_steps) {
                    this.offset = Cell.ROTATE_MIDDLE * this.last_steps;
                    return true;
                } else
                    return false;
            }   //内圈_旋转
            else if (id > 6) {
                if (this.offset > Cell.ROTATE_SURFACE * this.last_steps) {
                    this.offset = Cell.ROTATE_SURFACE * this.last_steps;
                    return true;
                } else
                    return false;
            }   //外圈_旋转
            else
                return false;
        }           //如果它的运动方式是旋转系 再判定它的范围是内圈还是外圈
        else {
            if (this.offset > Cell.PUSH_MAX) {
                return true;
            } else
                return false;
        }           //如果它的运动方式是收放系
    }

    public int getOffset() {
        return offset;
    }

    public int getMovetype() {
        return movetype;
    }

    //传入会进行绘制图案的步数
    public void settimes(int times) {
        this.last_steps = times;
    }

    public void clearDisplay() {
        this.display = 0;
    }

    public void setMoveType(int type) {
        this.movetype = type;
    }

    //清除自身除了id 和 data 之外的所有数据
    public void clearExceptData() {
        this.display = 0;
        this.movetype = 0;
        this.last_steps = 0;
        this.offset = 0;
    }

    public int gettimes() {
        return this.last_steps;
    }

    @Override
    public String toString() {
        return String.format("id:%d data:%d display:%d offset:%d times:%d", this.id, this.data, this.display, this.offset, this.last_steps);
    }

    public boolean isEmpty() {
        if (this.data == 0)
            return true;
        else
            return false;
    }
}
