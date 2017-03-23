package zlfffzhh.ifox.com.learn2028;


import android.graphics.Canvas;
import android.util.Log;

/**
 * Created by 41988 on 2017/3/18.
 */
public class Gamelogic {

    public static final int GAMEBORD_NUM = 4;


    /***
     * 判断是否一排的最后一位， 不然跳出循环；
     * 不是则进行判定当前是否有Card
     * 有则判断其后一位  否则后移
     * 是否相同，
     * 同则将当前归并  否则 后移
     * */
    public static void rightChange(){
        //从右边开始判断

        for(int i=GAMEBORD_NUM-1;i<=GAMEBORD_NUM*GAMEBORD_NUM-1;i+=GAMEBORD_NUM){

            for(int j = i;j>i-GAMEBORD_NUM+1;j--){
                Cell nowcell = DrawGame.cells[j];

                if(nowcell.card != null){
                    Log.i("--------------", "rightChange: "+j);
                    Cell nextcell = DrawGame.cells[j-1];
                    //和后面的card归并,后将nextcell 的Card 消除。
                    if(nextcell.card!=null&&nowcell.card.number == nextcell.card.number){
                        nowcell.card.number += nowcell.card.number;
                        nextcell.removeCard();
                    }
                }
            }
        }

        //移位的操作
        for(int i=GAMEBORD_NUM-1;i<=GAMEBORD_NUM*GAMEBORD_NUM-1;i+=GAMEBORD_NUM){
            int kong = 0;
            for(int j = i;j>=i-GAMEBORD_NUM+1;j--){
                Cell nowcell = DrawGame.cells[j];
                if(nowcell.card == null){
                    kong++;
                }else {
                    DrawGame.cells[j+kong].card = new Card(nowcell.card.number);
                    Log.i("move card", "rightChange: "+""+DrawGame.cells[j+kong].card.number);
                    if(kong!=0){
                        nowcell.removeCard();
                    }
                }
            }
        }



    }
    public static void leftChange(){
        for(int i=0;i<=GAMEBORD_NUM*GAMEBORD_NUM-1;i+=GAMEBORD_NUM){

            for(int j = i;j<i+GAMEBORD_NUM-1;j++){
                Cell nowcell = DrawGame.cells[j];

                if(nowcell.card != null){
                    Cell nextcell = DrawGame.cells[j+1];
                    //和后面的card归并,后将nextcell 的Card 消除。
                    if(nextcell.card!=null&&nowcell.card.number == nextcell.card.number){
                        nowcell.card.number += nowcell.card.number;
                        nextcell.removeCard();
                    }
                }
            }
        }

        //移位的操作
        for(int i=0;i<=GAMEBORD_NUM*GAMEBORD_NUM-1;i+=GAMEBORD_NUM){
            int kong = 0;
            for(int j = i;j<i+GAMEBORD_NUM;j++){
                Cell nowcell = DrawGame.cells[j];
                if(nowcell.card == null){
                    kong++;
                }else {
                    DrawGame.cells[j-kong].card = new Card(nowcell.card.number);
                    Log.i("move card", "rightChange: "+""+DrawGame.cells[j-kong].card.number);
                    if(kong!=0){
                        nowcell.removeCard();
                    }
                }
            }
        }

    }
    public static void upChange(){
        for(int i=0;i<=GAMEBORD_NUM-1;i++){

            for(int j = i;j<GAMEBORD_NUM*(GAMEBORD_NUM-1);j+=GAMEBORD_NUM){
                Cell nowcell = DrawGame.cells[j];

                if(nowcell.card != null){
                    Cell nextcell = DrawGame.cells[j+4];
                    //和后面的card归并,后将nextcell 的Card 消除。
                    if(nextcell.card!=null&&nowcell.card.number == nextcell.card.number){
                        nowcell.card.number += nowcell.card.number;
                        nextcell.removeCard();
                    }
                }
            }
        }

        //移位的操作
        for(int i=0;i<=GAMEBORD_NUM-1;i++){
            int kong = 0;
            for(int j = i;j<=i+GAMEBORD_NUM*(GAMEBORD_NUM-1);j+=GAMEBORD_NUM){
                Cell nowcell = DrawGame.cells[j];
                if(nowcell.card == null){
                    kong++;
                }else {
                    DrawGame.cells[j-kong*4].card = new Card(nowcell.card.number);
                    if(kong!=0){
                        nowcell.removeCard();
                    }
                }
            }
        }


    }
    public static void downChange(){
        for(int i=GAMEBORD_NUM*(GAMEBORD_NUM-1);i<=GAMEBORD_NUM*GAMEBORD_NUM-1;i++){

            for(int j = i;j>i-GAMEBORD_NUM*(GAMEBORD_NUM-1);j-=GAMEBORD_NUM){
                Cell nowcell = DrawGame.cells[j];

                if(nowcell.card != null){
                    Cell nextcell = DrawGame.cells[j-4];
                    //和后面的card归并,后将nextcell 的Card 消除。
                    if(nextcell.card!=null&&nowcell.card.number == nextcell.card.number){
                        nowcell.card.number += nowcell.card.number;
                        nextcell.removeCard();
                    }
                }
            }

        }
        for(int i=GAMEBORD_NUM*(GAMEBORD_NUM-1);i<=GAMEBORD_NUM*GAMEBORD_NUM-1;i++){
            int kong = 0;
            for(int j = i;j>=i-GAMEBORD_NUM*(GAMEBORD_NUM-1);j-=GAMEBORD_NUM){
                Cell nowcell = DrawGame.cells[j];
                if(nowcell.card == null){
                    kong++;
                }else {
                    DrawGame.cells[j+kong*4].card = new Card(nowcell.card.number);
                    if(kong!=0){
                        nowcell.removeCard();
                    }
                }
            }
        }


    }

}
