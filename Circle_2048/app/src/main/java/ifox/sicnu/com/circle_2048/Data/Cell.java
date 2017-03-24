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
    private boolean rotate;             //如果rotate为true，那么当前的所有绘制行为都变为绕着边界旋转。


    //一共十一个等级，如果加上 空 就是十二种状态

    public Cell(int id) {
        this.id = id;
        this.data = 0;
        this.display = 0;
    this.offset = 0;
    this.last_steps = 0;
    this.rotate = false;
}

    public boolean isStatic() {
        if (this.last_steps != 0) {
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
        this.rotate = false;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }

    public int getId(){
        return id;
    }
}
