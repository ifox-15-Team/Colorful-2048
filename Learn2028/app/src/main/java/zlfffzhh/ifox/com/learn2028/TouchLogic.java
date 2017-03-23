package zlfffzhh.ifox.com.learn2028;

import android.graphics.Canvas;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by 41988 on 2017/3/18.
 */
public class TouchLogic {

    int down_x;
    int down_y;
    int up_x;
    int up_y;


    public static final int RIGHT = 1;
    public static final int LEFT = 2;
    public static final int UP = 3;
    public static final int DOWN = 4;
    public static int aws;
    public static Canvas canvas;

    public int GameTouch(MotionEvent event){
        aws = 0;
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            down_x = (int) event.getX();
            down_y = (int) event.getY();
        }
        if(event.getAction() == MotionEvent.ACTION_MOVE){

        }
        if(event.getAction()== MotionEvent.ACTION_UP){
            up_x = (int) event.getX();
            up_y = (int) event.getY();
            aws = judgeOffset();

            if(aws !=0){
                if(aws == RIGHT){
                    Gamelogic.rightChange();
                }
                if(aws == LEFT){
                    Gamelogic.leftChange();
                }
                if(aws == UP){
                   Gamelogic.upChange();
                }
                if (aws == DOWN){
                    Gamelogic.downChange();
                }
            }
        }
        return aws;

    }

    private int judgeOffset(){
        //LEFT
        if(down_x>up_x+50&&(down_y<up_y+5||down_y>up_y+5)){
            return LEFT;
        }
        //RIGHT
        if(down_x<up_x-50&&(down_y<up_y+5||down_y>up_y+5)){
            return RIGHT;
        }
        //UP
        if((down_x>up_x+5||down_x<up_x-5)&&down_y>up_y+50){
            return UP;
        }
        //DOWN
        if((down_x>up_x+5||down_x<up_x-5)&&down_y<up_y-50){
            return DOWN;
        }
        return 0;
    }
}
