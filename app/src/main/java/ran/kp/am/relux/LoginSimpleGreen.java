package ran.kp.am.relux;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginSimpleGreen extends AppCompatActivity {

    private View parent_view;
    TextInputEditText edusername,edpassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_simple_green);
        parent_view = findViewById(android.R.id.content);
        edusername = findViewById(R.id.idusername);
        edpassword = findViewById(R.id.idpassword);

        Tools.setSystemBarColor(this, R.color.green_600);
        Tools.setSystemBarLight(this);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.relwhite);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        ((View) findViewById(R.id.sign_up_for_account)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(parent_view, "Sign up for an account", Snackbar.LENGTH_SHORT).show();
                //Intent i = new Intent(LoginSimpleGreen.this, SelectUser.class);
               /// startActivity(i);

            }
        });
    }

    public void signinbtn(View view) {
        String stusername,stpassword;
        stusername=edusername.getText().toString();
        stpassword=edpassword.getText().toString();
        if (stusername.equals("")&&stpassword.equals("")){
            Toast.makeText(this, "Please enter username and password", Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(this, "Invalid credentials", Toast.LENGTH_SHORT).show();
        }
    }

    public void signupbtn(View view) {
        Intent i = new Intent(LoginSimpleGreen.this, SelectUser.class);
        startActivity(i);
        finish();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
