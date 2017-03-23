package zlfffzhh.ifox.com.learn2028;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

    GameView gameView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gameView = new GameView(this);
        setContentView(gameView);

    }
}
