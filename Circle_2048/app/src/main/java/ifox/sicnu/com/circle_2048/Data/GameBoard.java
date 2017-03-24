package ifox.sicnu.com.circle_2048.Data;

import java.util.ArrayList;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class GameBoard {
    private Cell center;
    private Cell[] middle;
    private Cell[] surface;                         //分别代表 最里面的Cell id:0， 中间的6个 Cell id:1~6, 最外围的Cell id:7~18;
    private int boardertype;                        //总共有六个取值，分别代表各个边界会出现的位置

    public static final int ROTATE_POSITIVE = 0;
    public static final int ROTATE_NEGETIVE = 1;
    public static final int PUSH = 2;
    public static final int POP = 3;

    public GameBoard() {
        center = new Cell(0);
        middle = new Cell[6];
        surface = new Cell[12];
        for (int i = 0; i < 6; i++) {
            middle[i] = new Cell(i + 1);
        }
        for (int i = 0; i < 12; i++) {
            surface[i] = new Cell(i + 7);
        }                                           //初始化各种细胞
    }

    public Cell getCell(int index) {
        if (index == 0)
            return center;
        else if (index < 7) {
            return middle[index - 1];
        } else if (index < 19) {
            return surface[index - 7];
        } else
            return null;
    }

    public int size() {
        return 19;
    }

    public void operatePush() {

    }

    public void operatePop() {

    }

    public void operateNegetive() {

    }

    public void operatePositive() {

    }

    //传入一个cell数组，从而对它们进行合并化简
    private void combine(ArrayList<Cell> cells, int type) {
        for (int i = 0; i < cells.size() - 1; i++) {
            Cell a = cells.get(i);
            if (a.getData() == 0)
                continue;               //如果当前的判定的 a 数值为空，则判定下一个元素
            for (int j = 0; j < cells.size(); j++) {
                Cell b = cells.get(j);
                if (b.getData() == 0)
                    continue;                                   //如果当前判定的 b 的数值为空，则判定下一个元素
                if (a.getData() == b.getData()) {               //它俩相等，并且都不等于0  (等于零已经被排除)
                    a.increaseData();
                    b.clearDataandSetoffset(type);
                    break;                                      //跳过对当前a 的判定。
                }
            }
        }
    }

    public void bordtypeincrease() {
        this.boardertype += 1;
        this.boardertype %= 6;
    }

    public int getBoardertype() {
        return boardertype;
    }

    public int getMiddleBoarder() {
        return boardertype + 1;
    }

    public int getSurfaceBoarder() {
        return boardertype * 2 + 7;
    }
}
