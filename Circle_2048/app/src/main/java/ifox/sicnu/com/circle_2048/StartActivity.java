package ifox.sicnu.com.circle_2048;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;

import ifox.sicnu.com.circle_2048.Data.Const;
import ifox.sicnu.com.circle_2048.Start.StartView;

public class StartActivity extends Activity {

    private static final String TAG = "StartActivity";

    StartView startView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);

        Const.WIDTH_SC = getResources().getDisplayMetrics().widthPixels;
        Const.HEIGHT_SC = getResources().getDisplayMetrics().heightPixels;
        Const.CELL_BIG = (int) (Const.HEIGHT_SC * 0.1);
        Const.CELL_SMALL = Const.CELL_BIG / 2;
        Const.RADIUS = (int) (Const.HEIGHT_SC * 0.22);
        Const.BASESPAN = Const.RADIUS / 4;
        Const.MIDDLELINE = (int) (Const.WIDTH_SC * 0.25);
        startView = new StartView(this);
        setContentView(startView);
    }

    public void start_game() {
        this.startView = null;
        startActivity(new Intent(StartActivity.this, GameActivity.class));
        finish();
    }
}
