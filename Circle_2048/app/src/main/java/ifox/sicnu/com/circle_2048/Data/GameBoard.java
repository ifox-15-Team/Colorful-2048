package ifox.sicnu.com.circle_2048.Data;

/**
 * Created by Funchou Fu on 2017/3/23.
 */
public class GameBoard {
    private Cell center;
    private Cell[] middle;
    private Cell[] surface;                         //分别代表 最里面的Cell id:0， 中间的6个 Cell id:1~6, 最外围的Cell id:7~18;

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
}
