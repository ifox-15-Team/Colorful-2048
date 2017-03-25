package ifox.sicnu.com.circle_2048.Data;

import android.util.Log;

import java.util.ArrayList;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class GameBoard {
    private static final String TAG = "GameBoard";
    private Cell center;
    private Cell[] middle;
    private Cell[] surface;                         //分别代表 最里面的Cell id:0， 中间的6个 Cell id:1~6, 最外围的Cell id:7~18;
    private int boardertype;                        //总共有六个取值，分别代表各个边界会出现的位置

    public static final int ROTATE_POSITIVE = 0;
    public static final int ROTATE_NEGETIVE = 1;
    public static final int PUSH = 2;
    public static final int POP = 3;

    private ArrayList<Cell> cells_needDraw;

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
        cells_needDraw = new ArrayList<>();
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
        operateAllowed(GameBoard.PUSH);
    }

    public void operatePop() {
        operateAllowed(GameBoard.POP);
    }

    public void operateNegetive() {
        operateAllowed(GameBoard.ROTATE_NEGETIVE);
    }

    //顺时针旋转
    public void operatePositive() {
        operateAllowed(GameBoard.ROTATE_POSITIVE);
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int x = getMiddleBoarder() + i;
            if (x > 6) {
                x -= 6;
            }                       //得到内圆环里的每一个
            cells.add(getCell(x));
        }
        combine(cells, GameBoard.ROTATE_POSITIVE);
        ArrayList<Cell> cells2 = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int x = getSurfaceBoarder() + i;
            if (x > 18) {
                x -= 12;
            }
            cells2.add(getCell(x));
        }
        combine(cells2, GameBoard.ROTATE_POSITIVE);
    }

    //自动判定当前的行动是否合乎要求。
    private boolean operateAllowed(int type) {
        return true;
    }

    //传入一个cell数组，从而对它们进行合并化简
    private void combine(ArrayList<Cell> cells, int type) {
        for (int i = 0; i < cells.size() - 1; i++) {
            Cell a = cells.get(i);
            a.setMoveType(type);
            if (a.getData() == 0)
                continue;               //如果当前的判定的 a 数值为空，则判定下一个元素
            for (int j = i + 1; j < cells.size(); j++) {
                Cell b = cells.get(j);
                if (b.getData() == 0)
                    continue;                                   //如果当前判定的 b 的数值为空，则判定下一个元素
                if (a.getData() == b.getData()) {               //它俩相等，并且都不等于0  (等于零已经被排除)
                    Log.i(TAG, String.format("Combine %d %d", a.getId(), b.getId()));
                    a.increaseData();
                    b.clearData();
                    b.clearDisplay();
                    break;                                      //跳过对当前a 的判定。
                } else
                    break;
            }
        }
        //完成对一个数据表的先归并
        int spannum = 0;                //这个是将要进行位移的个数
        int position = 0;               //这个是一个位移下标，通过这个下标来表示会位移到此处的细胞
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if (cell.getData() == 0) {
                spannum++;                          //已经经过的空格数会进行自动加一
            } else {
                cell.syncDisplay();                 //同步即将要显示的数据
                cell.settimes(spannum);             //这是会进行位移的个数
                if (position != i) {
                    cells.get(position).setData(cell.getData());            //将当前这个点的实际数据，平移到这条线的最左边去
                    cell.clearData();                   //再将自己的实际的数据进行清空
                }
                if (cell.gettimes() != 0) {
                    Log.i(TAG, String.format("combine: %d has been pushed into the list offsets is : %d", cell.getId(), cell.gettimes()));
                    pushintoDrawList(cell);
                }
                position++;                         //指向位移地址的下标会进行自动加一
            }
        }           //再完成对归并数据表后的位移
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

    //将所有细胞绘图有关的成员变量全部置为初始值
    public void clearOffsetCells() {
        for (int i = 0; i < middle.length; i++) {
            middle[i].clearExceptData();
        }
        for (int i = 0; i < surface.length; i++) {
            surface[i].clearExceptData();
        }
    }

    public boolean isAction() {
        if (cells_needDraw.size() == 0)
            return false;
        else
            return true;
    }

    public Cell getNeedDraw(int index) {
        return this.cells_needDraw.get(index);
    }

    public int size_needDraw() {
        return this.cells_needDraw.size();
    }

    /**
     * 当前细胞发出了它的进位信号，我们需要对它做进一步的处理
     * 首先根据细胞的id 和 movetype 得到 它的下一个 继承者是谁，并将继承者cell 放入自己的 drawcell list中
     * 第二步，清除自身的绘制的数据。
     * 第三步，将自身从 drawcell list 中进行抹去
     */
    public void carrySignal(Cell cell) {
        int id = cell.getId();
        Cell cell2 = null;
        if (cell.isStatic()) {
            removefromDrawList(cell);
            return;
        }
        if (id > 0 && id < 7) {
            if (cell.getMovetype() == GameBoard.ROTATE_POSITIVE) {
                int x = id - 1;
                if (x < 1) {
                    x += 6;
                }
                cell2 = getCell(x);            //这个是继承者细胞
            }                                       //顺时针移动的时候，所有的点应该朝向比它下标更小的地方进行移动
            else if (cell.getMovetype() == GameBoard.ROTATE_NEGETIVE) {
                int x = id + 1;
                if (x > 6) {
                    x -= 6;
                }
                cell2 = getCell(x);
            }
        }               //当前的细胞的属于内圈细胞
        else if (id > 6) {
            if (cell.getMovetype() == GameBoard.ROTATE_POSITIVE) {
                int x = id + 1;
                if (x > 18) {
                    x -= 12;
                }
                cell2 = getCell(x);
            } else if (cell.getMovetype() == GameBoard.ROTATE_NEGETIVE) {
                int x = id - 1;
                if (x < 7) {
                    x += 12;
                }
                cell2 = getCell(x);
            }
        }               //当前的细胞属于外圈细胞
        cell2.settimes(cell.gettimes() - 1);
        cell2.setMoveType(cell.getMovetype());
        pushintoDrawList(cell2);
        cell.clearExceptData();
        removefromDrawList(cell);
    }

    public void removefromDrawList(Cell cell) {
        this.cells_needDraw.remove(cell);
    }

    public void pushintoDrawList(Cell cell2) {
        this.cells_needDraw.add(cell2);
    }

    //每次行动完了之后调用，能够自动产生一个新的细胞
    public boolean createNewCell() {
//        //这里需要检查
//        for (int i = getSurfaceBoarder(); i < 13; i++) {
//            if (i > 18 || i < 7) {
//                i = 6;
//                continue;
//            }
//            if (getCell(i).getData() == 0) {
//                getCell(i).setData(1);
//                getCell(i).syncDisplay();
//                return;
//            }
//        }
//        //这里也需要检查
//        for (int i = getMiddleBoarder(); i < 7; i++) {
//            if (i > 6 || i < 1) {
//                i = 0;
//                continue;
//            }
//            if (getCell(i).getData() == 0) {
//                getCell(i).setData(1);
//                getCell(i).syncDisplay();
//            }
//        }
        int random_num = (int) (Math.random() * 18 + 1);
        boolean full = false;               //这是判定是否当前存在着全满状态。如果有全满状态。
        for (int i = random_num; i < size(); i++) {
            if (i < 1)
                continue;
            else if (i > 18) {
                i = 0;
                if (!full) {
                    full = true;
                } else {
                    return false;
                }
                continue;
            }
            if (getCell(i).isEmpty()) {
                getCell(i).setData((int) (Math.random() * 2) + 1);          //随机生成2 或者 4
                getCell(i).syncDisplay();
                break;
            }
        }
        return true;                        //在生成了具体元素后，会进行弹出，并返还一个true
    }

    public void debug() {
        for (int i = 0; i < this.size(); i++) {
            Log.i(TAG, String.format("All %s", this.getCell(i)));
        }
        for (int i = 0; i < this.cells_needDraw.size(); i++) {
            Log.i(TAG, String.format("Need %s", this.cells_needDraw.get(i).toString()));
        }
    }

    //当传入的这个cell已经完成了它的动画同步后，在调用此方法，此细胞的display清空。同时将目标的cell进行数据同步
    public void synctheMovingCell(Cell cell) {
        cell.clearDisplay();
        if (cell.getId() > 6) {
            int x = cell.getId();
            if (cell.getMovetype() == GameBoard.ROTATE_POSITIVE) {
                x -= cell.gettimes();
            } else if (cell.getMovetype() == GameBoard.ROTATE_NEGETIVE) {
                x += cell.gettimes();
            }
            if (x < 7)
                x += 12;
            else if (x > 18)
                x -= 12;
            getCell(x).syncDisplay();
            cell.clearExceptData();
        }//此时的细胞是外圈细胞
        else {

        }
    }

    public void debugsyncAll() {
        for (int i = 0; i < size(); i++) {
            getCell(i).syncDisplay();
        }
    }
}
