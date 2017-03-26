package ifox.sicnu.com.circle_2048.Data;

import android.util.Log;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class Cell {
    private static final String TAG = "Cell";
    private int id;                    //代表当前Cell实际的id
    private int data;                  //代表当前Cell实际存的数据
    private int display;               //代表当前Cell展示时的数据
    private int offset;                 //代表当前Cell每一格在移对的偏移量，偏移量的的大小是位移的角度 可见，根据cell的id不同，offset的进位也是不同的。
    private int last_steps;             //代表当前Cell 移动时 需要行进多少格
    private int first_steps;            //代表当前Cell 归并时 需要行进多少格
    private int movetype;               //type在GameBoard中进行定义
    private boolean isstatic = true;           //true 则代表该cell是static
    private boolean isalive = true;             //true 代表该cell 仍然可以被动态绘制 每次重新归零的时候，会再赋值true， 达到offset 峰值的时候，会变成false

    public static final int ROTATE_SURFACE = 30;
    public static final int ROTATE_MIDDLE = 60;                 //在内(外)圈的时候，offset达到这(上面)个数值就会进行进位
    public static final int PUSH_MAX = Const.RADIUS;            //在进行滑动的时候，如果offset达到了这个数值，就会进行进位

    public static final int COMBINE = 11;
    public static final int MOVE = 22;                          //COMBINE 和 MOVE 作为判定 increaseoffset的返回值是否为true的时候，判定的上限究竟是以 first_steps 还是 last_steps 为准
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
        return isstatic;
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

    //增加自己的offset 如果增加到了极点到可进位的时候，会返还true,并清空对应的step
    public boolean increaseOffset(int type) {
        this.offset += 2;
        int steps = 0;
        if (type == Cell.COMBINE)
            steps = this.first_steps;
        else
            steps = this.last_steps;
        if (type == Cell.MOVE)
            Log.i(TAG, String.format("increaseOffset: %d", steps));
        if (this.movetype == GameBoard.ROTATE_POSITIVE || this.movetype == GameBoard.ROTATE_NEGETIVE) {
            if (id > 0 && id < 7) {
                if (this.offset > Cell.ROTATE_MIDDLE * steps) {
                    this.offset = Cell.ROTATE_MIDDLE * steps;
                    clearsteps(type);
                    return true;
                }
            }   //内圈_旋转
            else if (id > 6) {
                if (this.offset > Cell.ROTATE_SURFACE * steps) {
                    this.offset = Cell.ROTATE_SURFACE * steps;
                    clearsteps(type);
                    return true;
                }
            }   //外圈_旋转
        }           //如果它的运动方式是旋转系 再判定它的范围是内圈还是外圈
        else {
            if (this.offset > Cell.PUSH_MAX * steps) {
                this.offset = Cell.PUSH_MAX * steps;
                clearsteps(type);
                return true;
            }
        }           //如果它的运动方式是收放系
        return false;
    }

    private void clearsteps(int type) {
        if (type == Cell.COMBINE)
            this.first_steps = 0;
        else
            this.last_steps = 0;
    }

    public int getOffset() {
        return offset;
    }

    public int getMovetype() {
        return movetype;
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
        this.first_steps = 0;
        this.last_steps = 0;
        this.offset = 0;
        this.isstatic = true;
        this.isalive = true;
    }

    public int gettimes() {
        return this.last_steps;
    }

    @Override
    public String toString() {
        return String.format("id:%d data:%d display:%d offset:%d firsttimes:%d lasttimes:%d isstatic:%b isAlive:%b ", this.id, this.data, this.display, this.offset, this.first_steps, this.last_steps, this.isstatic, this.isalive);
    }

    public boolean isEmpty() {
        if (this.data == 0)
            return true;
        else
            return false;
    }

    public int getFirst_steps() {
        return first_steps;
    }

    public void setFirststep(int firststep) {
        this.first_steps = firststep;
    }

    public void setLaststep(int laststep) {
        this.last_steps = laststep;
    }

    public void setStatic(boolean st) {
        this.isstatic = st;
    }

    public void syncDisplayandClearOther() {
        this.display = this.data;
        this.first_steps = 0;
        this.last_steps = 0;
        this.offset = 0;
        this.isstatic = true;
    }

    public void syncDisplay() {
        this.display = this.data;
    }

    public void setIsalive(boolean isalive) {
        this.isalive = isalive;
    }

    public boolean isAlive() {
        return this.isalive;
    }

    public void die() {
        this.isalive = false;
    }

    public void syncTargetDisplay() {
        int x = 0;
        if (this.id < 7) {
            if (this.movetype == GameBoard.ROTATE_NEGETIVE) {
                x = this.id + this.last_steps;
                if (x > 6)
                    x -= 6;
            } else if (this.movetype == GameBoard.ROTATE_POSITIVE) {
                x = this.id - this.last_steps;
                if (x < 1)
                    x += 6;
            } else {
                if (this.movetype == GameBoard.POP) {
                    x = this.id * 2 + 5;
                } else {
                    x = 0;
                }           //PUSH 如果 step == 2 则默认为0
            }
        }//内圈
        else {
            if (this.movetype == GameBoard.ROTATE_NEGETIVE) {
                x = this.id + this.last_steps;
                if (x > 18)
                    x -= 12;
            } else if (this.movetype == GameBoard.ROTATE_POSITIVE) {
                x = this.id - this.last_steps;
                if (x < 7)
                    x += 12;
            } else {
                if (last_steps == 2) {
                    x = 0;
                } else {
                    x = (this.id - 5) / 2;
                }
                //PUSH 如果 step == 2 则默认为0
            }
        }
        Const.gameBoard.getCell(x).syncDisplay();
    }
}
