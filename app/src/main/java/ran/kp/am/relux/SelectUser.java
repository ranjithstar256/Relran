package ran.kp.am.relux;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class SelectUser extends AppCompatActivity {

    LinearLayout l1,l2;
    Button l11,l21;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_user);
        Tools.setSystemBarColor(this, R.color.green_600);
        Tools.setSystemBarLight(this);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.relwhite);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        l1=findViewById(R.id.l1);
        l2=findViewById(R.id.l2);
        l11=findViewById(R.id.textView);
        l21=findViewById(R.id.textView2);



        l1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectUser.this, "Selected EV Users", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SelectUser.this, FormSignupCard.class);
                startActivity(i);
            }
        });

        l2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectUser.this, "Selected EV Stations", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SelectUser.this, FormsStation.class);
                startActivity(i);
            }
        });
        l11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectUser.this, "Selected EV Users", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SelectUser.this, FormSignupCard.class);
                startActivity(i);
            }
        });

        l21.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SelectUser.this, "Selected EV Stations", Toast.LENGTH_SHORT).show();
                Intent i = new Intent(SelectUser.this, FormsStation.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed() {
        ///super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mene,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void settings(MenuItem item) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }public void Help(MenuItem item) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }public void Support(MenuItem item) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }public void Feedback(MenuItem item) {
        Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }

    public void signinn(MenuItem item) {
        startActivity(new Intent(SelectUser.this, LoginSimpleGreen.class));

    }
}
