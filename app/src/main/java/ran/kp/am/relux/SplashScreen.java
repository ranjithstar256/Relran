package ran.kp.am.relux;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;


public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        Tools.setSystemBarColor(this,R.color.green_400);
        Tools.setSystemBarLight(this);
        TextView tx = findViewById(R.id.tvv);

        Typeface custom_font = Typeface.createFromAsset(getAssets(),  "timesnewroman.ttf");

        tx.setTypeface(custom_font);
        tx.setTypeface(tx.getTypeface(),Typeface.ITALIC);



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(SplashScreen.this, SelectUser.class);
                startActivity(i);
                finish();
            }
        }, 3000);
    }
}
