package zlfffzhh.ifox.com.learn2028;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.Log;

/**
 * Created by 41988 on 2017/3/18.
 */
public class DrawGame {
    Paint  paint;
    Gamelogic gamelogic;
    public static int card_kuan;
    public static int card_gao;

    public static Cell[] cells;

    static int left = (int) (GameView.getScreenWith()*0.01);
    static int right = (int) (GameView.getScreenWith()*0.99);
    static int top = (int) (GameView.getScreenHeight()*0.2);
    static int bottom = (int) (GameView.getScreenHeight()*0.8);




    public DrawGame(){

        paint = new Paint();
        cells = new Cell[16];           //初始化16个格子；
        gamelogic = new Gamelogic();
        inicells();
        cells[0].card = new Card(2);
        cells[1].card = new Card(2);
        cells[12].card = new Card(2);
        cells[8].card = new Card(2);
        cells[9].card = new Card(2);


    }


    public static void draw_game_map(Canvas canvas){

        Paint paint = new Paint();
        Rect game_bg = new Rect(left,top,right,bottom);
        paint.setColor(Color.GREEN);
        canvas.drawRect(game_bg,paint); //绘制整个游戏的背景区域。

        int per_x = (right-left) / 4;
        int per_y = (bottom - top) / 4;

        card_kuan = per_y;
        card_gao = per_x;
        paint.setColor(Color.BLACK);
        //绘制游戏的线条
        for(int i=0;i<4;i++){
            canvas.drawLine((left+per_x)+per_x*i,top,(left+per_x)+per_x*i,bottom,paint); // 竖线
            canvas.drawLine(left,(top+per_y)+i*per_y,right,(top+per_y)+i*per_y,paint);//横线
        }

        Log.i("map", "draw_game_map: ----");
    }

    public static void draw_game_cards(Canvas canvas){

        //遍历每一个卡片
        Paint paint = new Paint();
       paint.setColor(Color.WHITE);

        for(int i =0;i<16;i++){
            Cell cell = cells[i];
            if(cell.card!=null){
                paint.setColor(Color.RED);
                Rect rect = new Rect((int)(left+(cell.x)*card_kuan*0.92),top+(cell.y)*card_gao+card_gao/5,left+(cell.x)*card_kuan+(int)(card_kuan*0.9),top+(cell.y)*card_gao+card_gao);
                canvas.drawRect(rect, paint);
                paint.setColor(Color.YELLOW);
                paint.setTextSize(100);
                Log.i("over", "drawgame: " + cell.card.number);
                canvas.drawText(cell.card.number + "", left/2 + (cell.x) * card_kuan, top + (cell.y) * card_gao+card_gao, paint);
            }
        }


    }
    private void inicells(){
        for(int i =0;i<16;i++){
            cells[i] = new Cell();
            cells[i].x = i%4;
            cells[i].y = i/4;

        }
    }


    private int []initCells(){
        //一开始随机产生4个卡片
        int[] start = new int[4];
        for(int i=0;i<4;i++){
            int flag = 0;
            while (true){
                start[i] = (int) (Math.random()*100/16);
                for(int j =0;j<i;j++){
                    if(start[i]!=start[j])
                        flag++;
                    if(flag == i||i==0){
                        break;
                    }
                }
            }
        }
        return start;
        }

    //获得一个随机数。
    private int getRandowNum(){
        int num = (int) (Math.random()*10);
        num = (int) Math.pow(2,num);
        return num;

    }
    private int NowCards(){
        int sum = 0;
        for(int i=0;i<16;i++){
            if(cells[i].card!=null){
                sum++;
            }
        }
        return sum;
    }


    }

//格子
class Cell{
    Card card;
    boolean haveCard = false;
    int x,y; //当前Cell坐标

    public void addCard(Card card){
        if(this.haveCard)
            return;
        else this.card = card;
    }

    public void removeCard(){
        this.card = null;
    }
}


class Card{
    int number; //卡片上的数字
    public Card(int number){
        this.number = number;
    }
}
