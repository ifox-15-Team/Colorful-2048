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

    private ArrayList<Cell> cells_needDraw_first;
    private ArrayList<Cell> cells_needDraw_last;
    private ArrayList<Integer> bufferlines;
    private ArrayList<Integer> recordlines;

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
        cells_needDraw_last = new ArrayList<>();
        cells_needDraw_first = new ArrayList<>();
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
        initialRecords();
        for (int i = 0; i < 6; i++) {
            ArrayList<Cell> cells = new ArrayList<>();
            cells.add(getCell(0));
            cells.add(getCell(1 + i));
            cells.add(getCell(7 + 2 * i));
            combine(cells, GameBoard.PUSH);
        }
    }

    public void operatePop() {
        operateAllowed(GameBoard.POP);
        initialRecords();
        for (int i = 0; i < 6; i++) {
            ArrayList<Cell> cells = new ArrayList<>();
            cells.add(getCell(7 + 2 * i));
            cells.add(getCell(1 + i));
            combine(cells, GameBoard.POP);
        }
    }

    public void operateNegetive() {
        operateAllowed(GameBoard.ROTATE_NEGETIVE);
        initialRecords();
        ArrayList<Cell> cells = new ArrayList<>();
        for (int i = 0; i < 6; i++) {
            int x = getMiddleBoarder() - i;
            if (x < 1) {
                x += 6;
            }                       //得到内圆环里的每一个
            cells.add(getCell(x));
        }
        combine(cells, GameBoard.ROTATE_NEGETIVE);
        ArrayList<Cell> cells2 = new ArrayList<>();
        for (int i = 0; i < 12; i++) {
            int x = getSurfaceBoarder() - i;
            if (x < 7) {
                x += 12;
            }
            cells2.add(getCell(x));
        }
        combine(cells2, GameBoard.ROTATE_NEGETIVE);
    }

    //顺时针旋转
    public void operatePositive() {
        operateAllowed(GameBoard.ROTATE_POSITIVE);
        initialRecords();
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

    private void initialRecords() {
        this.recordlines = new ArrayList<>();
        this.bufferlines = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            getCell(i).setStatic(true);
        }
        this.cells_needDraw_last.removeAll(this.cells_needDraw_last);
        this.cells_needDraw_first.removeAll(this.cells_needDraw_first);
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
                if (a.getData() == b.getData()) {               //它俩相等，并且都不等于0  (等于零已经被排除)      此时j - i 即为 b所带表的元素需要行进的格数
                    b.setFirststep(j - i);
                    a.increaseData();
                    b.clearData();
                    b.setStatic(false);
                    pushintoFirstDrawList(b);
                    record(a.getId(), b.getId());
                    Log.i(TAG, String.format("combine:  %d %d were Combine Their data is %d", a.getId(), b.getId(), a.getData()));
                    break;                                      //跳过对当前a 的判定。
                } else
                    break;
            }
        }
        cells.get(cells.size() - 1).setMoveType(type);              //将最后一个元素的movetype 也进行设置
        //完成对一个数据表的先归并
        int spannum = 0;                //这个是将要进行位移的个数
        int position = 0;               //这个是一个位移下标，通过这个下标来表示会位移到此处的细胞
        for (int i = 0; i < cells.size(); i++) {
            Cell cell = cells.get(i);
            if (cell.getData() == 0) {
                spannum++;                          //已经经过的空格数会进行自动加一
            } else {
//                cell.syncDisplay();                 //同步即将要显示的数据
                cell.setLaststep(spannum);             //这是会进行位移的个数
                if (position != i) {
                    cells.get(position).setData(cell.getData());            //将当前这个点的实际数据，平移到这条线的最左边去
                    cell.clearData();                   //再将自己的实际的数据进行清空
                }
                if (cell.gettimes() != 0) {
                    Log.i(TAG, String.format("move: %s has been pushed into the list offsets ", cell.toString()));
                    cell.setStatic(false);
                    pushintoLastDrawList(cell);
                }
                position++;                         //指向位移地址的下标会进行自动加一
            }
        }           //再完成对归并数据表后的位移
    }

    //将一个细胞放入 第一波动画节奏中去
    private void pushintoFirstDrawList(Cell b) {
        this.cells_needDraw_first.add(b);
    }


    private void record(int a_id, int b_id) {
        Const.Tool.putIntegerIntoList_nosame(this.bufferlines, a_id);          //往bufferlines 里添加 a
        Const.Tool.removeIntegerIntheList(this.recordlines, a_id);             //添加完a 了后，把recordlines 里的 a 进行删除
        if (!Const.Tool.checkIntegerIntheList(this.bufferlines, b_id))         //添加b 之前检查 bufferlines 里是否存在 b
            Const.Tool.putIntegerIntoList_nosame(this.recordlines, b_id);      //不存在，再把 b 添加进recordlines
        else
            Const.Tool.removeIntegerIntheList(this.recordlines, b_id);         //存在，recordlines 删除 b ;
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
        if (size_alive(Cell.COMBINE) == 0 && size_alive(Cell.MOVE) == 0)
            return false;
        else
            return true;
    }

    public Cell getLastNeedDraw(int index) {
        return this.cells_needDraw_last.get(index);
    }

    public void removefromFirstDrawList(Cell cell) {
        this.cells_needDraw_first.remove(cell);
    }

    public void pushintoLastDrawList(Cell cell2) {
        this.cells_needDraw_last.add(cell2);
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
        for (int i = 0; i < this.cells_needDraw_last.size(); i++) {
            Log.i(TAG, String.format("Need %s", this.cells_needDraw_last.get(i).toString()));
        }
    }

    //当传入的这个cell已经完成了它的动画同步后，在调用此方法，此细胞的display清空。目标Cell 的数据同步让最后统一进行调用
    public void synctheMovingCell(Cell cell) {
        cell.clearExceptData();
//        Log.i(TAG, String.format("synctheMovingCell: %d has Finished", cell.getId()));
//        if (cell.getId() > 6) {
//            int x = cell.getId();
//            if (cell.getMovetype() == GameBoard.ROTATE_POSITIVE) {
//                x -= cell.gettimes();
//            } else if (cell.getMovetype() == GameBoard.ROTATE_NEGETIVE) {
//                x += cell.gettimes();
//            }
//            if (x < 7)
//                x += 12;
//            else if (x > 18)
//                x -= 12;
//            cell.clearExceptData();
//        }//此时的细胞是外圈细胞
//        else {
//            if (cell.getMovetype() == GameBoard.POP)
//        }//此时的细胞为内圈细胞
    }

    public void debugsyncAll() {
        for (int i = 0; i < size(); i++) {
            getCell(i).clearExceptData();
            getCell(i).syncDisplayandClearOther();
        }
    }

    public int size_firstneedDraw() {
        return this.cells_needDraw_first.size();
    }

    public int size_lastneedDraw() {
        return this.cells_needDraw_last.size();
    }

    public Cell getFirstNeedDraw(int index) {
        return this.cells_needDraw_first.get(index);
    }

    public int size_alive(int type) {
        int num = 0;
        if (type == Cell.COMBINE) {
            for (int i = 0; i < this.cells_needDraw_first.size(); i++) {
                if (cells_needDraw_first.get(i).isAlive())
                    num++;
            }
        } else {
            for (int i = 0; i < this.cells_needDraw_last.size(); i++) {
                if (cells_needDraw_last.get(i).isAlive())
                    num++;
            }
        }
        return num;
    }

}
