package ran.kp.am.relux;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import com.obsez.android.lib.filechooser.ChooserDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.regex.Pattern;
public class FormSignupCard extends AppCompatActivity {

    Spinner spstate, spddist, spmake, spmodel, spadapter;
    EditText uploadrcbook, name, mobile, email, vehicle;
    ArrayList<String> listState;
    ArrayList<String> listCity;
    ArrayList<String> listMake;
    ArrayList<String> listModel;
    ArrayList<String> listAdapter;

    ArrayAdapter stateadapter;
    ArrayAdapter distadapter;
    ArrayAdapter Makeadapter;
    ArrayAdapter Modeladapter;
    ArrayAdapter Adapadapter;

    String make, model, adap, ststate, stdist, rcbook;
    String selmake = "", selmodel = "", seladap = "", selststate = "", selstdist = "", selrcbook = "";
    ArrayList<String> n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_signup_card);
        Tools.setSystemBarColor(this, R.color.green_600);
        Tools.setSystemBarLight(this);
        StrictMode.ThreadPolicy policy = null;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.relwhite);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        n = new ArrayList<>();

        spstate = findViewById(R.id.edstate);
        spddist = findViewById(R.id.eddist);
        spmake = findViewById(R.id.edmake);
        spmodel = findViewById(R.id.edmodel);
        spadapter = findViewById(R.id.edadapter);
        uploadrcbook = findViewById(R.id.eduploadrcbook);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        vehicle = findViewById(R.id.vehicle);
        email = findViewById(R.id.email);
        rcbook = "select";

        uploadrcbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showFileChooser(0);
            }
        });

        listState = new ArrayList<String>();
        listMake = new ArrayList<String>();
        listModel = new ArrayList<String>();
        listAdapter = new ArrayList<String>();

        listMake.add("Select Make");
        listMake.add("TATA");
        listMake.add("Mahindra");
        listMake.add("Hyundai");
        listMake.add("MG");


        listModel.add("Select Model");
        listModel.add("Tigor");
        listModel.add("Verito");
        listModel.add("Kona");
        listModel.add("EVZ");
        listAdapter.add("select Adapter");
        listAdapter.add("GB/T");
        listAdapter.add("CCS");
        listAdapter.add("CHadeMO");


        Makeadapter = new ArrayAdapter(FormSignupCard.this, android.R.layout.simple_list_item_1, listMake);
        Modeladapter = new ArrayAdapter(FormSignupCard.this, android.R.layout.simple_list_item_1, listModel);
        Adapadapter = new ArrayAdapter(FormSignupCard.this, android.R.layout.simple_list_item_1, listAdapter);

        spmake.setAdapter(Makeadapter);
        spmodel.setAdapter(Modeladapter);
        spadapter.setAdapter(Adapadapter);

        spmake.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                make = "select";

                if (position != 0) {
                    make = "done";
                    selmake = listMake.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spmodel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                model = "select";

                if (position != 0) {
                    model = "done";
                    selmodel = listModel.get(position);

                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spadapter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adap = "select";

                if (position != 0) {
                    adap = "done";
                    seladap = listAdapter.get(position);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        callAll();
        stateadapter = new ArrayAdapter(FormSignupCard.this, android.R.layout.simple_list_item_1, listState);
        spstate.setAdapter(stateadapter);
        spstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ststate = "select";

                if (position != 0) {
                    Toast.makeText(FormSignupCard.this, "Selected " + listState.get(position), Toast.LENGTH_SHORT).show();
                    ststate = "done";
                    selststate = listState.get(position);

                    try {
                        JSONObject jsonObject = new JSONObject(getJson());
                        JSONArray array = jsonObject.getJSONArray("states");
                        JSONObject obi = array.getJSONObject(position);
                        JSONArray disarrf = obi.getJSONArray("districts");
                        listCity = new ArrayList<String>();
                        for (int j = 0; j < disarrf.length(); j++) {
                            listCity.add(disarrf.getString(j));
                        }
                        distadapter = new ArrayAdapter(FormSignupCard.this, android.R.layout.simple_list_item_1, listCity);
                        spddist.setAdapter(distadapter);
                        spddist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(FormSignupCard.this, "Selected " + listCity.get(position), Toast.LENGTH_SHORT).show();
                                stdist = "select";


                                if (listCity.size() == 1 || position != 0) {
                                    ///Toast.makeText(FormsStation.this, ""+listCity.size(), Toast.LENGTH_SHORT).show();
                                    stdist = "done";
                                    selstdist = listCity.get(position);
                                }


                            }

                            @Override
                            public void onNothingSelected(AdapterView<?> parent) {

                            }
                        });
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void showFileChooser(int i) {
        new ChooserDialog(FormSignupCard.this)
                .withStartFile(Environment.getExternalStorageDirectory().getPath())
                .withFilter(false, false, "jpeg", "jpg",
                        "png", "pdf")

                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        Toast.makeText(FormSignupCard.this, "->" + path, Toast.LENGTH_SHORT).show();
                        //chosefile=path;
                        n.add(i, path);
                        String h = n.get(i);
                        switch (i) {
                            case 0:
                                if (!h.equals("")) {
                                    rcbook = "done";
                                    uploadrcbook.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_assig, 0);
                            uploadrcbook.setHint("File Loaded");
                            uploadrcbook.setHintTextColor(getResources().getColor(R.color.green_400));
                            uploadrcbook.setText("File Loaded");
                                }
                                break;
                        }


                    }
                })
                // to handle the back key pressed or clicked outside the dialog:
                .withOnCancelListener(new DialogInterface.OnCancelListener() {
                    public void onCancel(DialogInterface dialog) {
                        dialog.cancel(); // MUST have
                    }
                })
                .build()
                .show();
    }

    public void callAll() {
        obj_list();
    }

    public String getJson() {
        String json = null;
        try {
            // Opening cities.json file
            //InputStream is = getAssets().open("cities.json");
            InputStream is = getAssets().open("india.json");
            // is there any content in the file
            int size = is.available();
            byte[] buffer = new byte[size];
            // read values in the byte array
            is.read(buffer);
            // close the stream --- very important
            is.close();
            // convert byte to string
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return json;
        }
        return json;
    }

    void obj_list() {
        // Exceptions are returned by JSONObject when the object cannot be created
        try {
            // Convert the string returned to a JSON object
            JSONObject jsonObject = new JSONObject(getJson());
            // Get Json array
            JSONArray array = jsonObject.getJSONArray("states");
            for (int i = 0; i < array.length(); i++) {
                JSONObject obi = array.getJSONObject(i);
                String statef = obi.getString("state");
                listState.add(statef);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    // hide keyboard on selecting a suggestion
    public void hideKeyBoard() {
       /* act.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
            {
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
            }
        });*/
    }

    private boolean isValidMobile(String phone) {
        if (!Pattern.matches("[a-zA-Z]+", phone)) {
            return phone.length() == 10;
        }
        return false;
    }

    private boolean isValidMail(String email) {

        String EMAIL_STRING = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        return Pattern.compile(EMAIL_STRING).matcher(email).matches();

    }

    public void signupbtn(View view) {
        String nam, mpb, emai, vech;
        nam = name.getText().toString();
        mpb = mobile.getText().toString();

        emai = email.getText().toString();
        vech = vehicle.getText().toString();
        Log.i("vbnbnbn", ststate + stdist + make + model + adap + rcbook);

        if (nam.equals("") && mpb.equals("") && emai.equals("") && vech.equals("")) {

            Toast.makeText(this, "Please fill all the details correctly..", Toast.LENGTH_SHORT).show();

        } else {
            if (isValidMobile(mpb)) {

                if (isValidMail(emai)) {

                    if (ststate.equals("done") && stdist.equals("done") && make.equals("done") && model.equals("done") && adap.equals("done") && rcbook.equals("done")) {
                        Log.i("vbnbnbn", ststate + stdist + make + model + adap + rcbook);

                        final Dialog dialog = new Dialog(this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                        dialog.setContentView(R.layout.dialog_info);
                        dialog.setCancelable(false);
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                        String bodyd = "Name :" + nam + "\n"
                                + "Mobile :" + mpb + "\n"
                                + "Mail id : " + emai + "\n"
                                + "Vehicle Number : " + vech + "\n"
                                + "Adapter :" + seladap + "\n"
                                + "Make :" + selmake + "\n"
                                + "State :" + selststate + "\n"
                                + "District :" + selstdist + "\n"
                                + "Model :" + selmodel + "\n";

                        try {
                            //sendEmailMulipleFiles(FormSignupCard.this, "reluxgroups01@gmail.com", "New registration from "+nam, bodyd, n);
                            GMailSender gMailSender = new GMailSender("reluxelectric@gmail.com", "100%relux");
                            try {

                                ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        //  Toast.makeText(getApplicationContext(), ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                                        dialog.dismiss();
                                        finish();
                                    }
                                });

                                gMailSender.addAttachment(n.get(0));
                                gMailSender.sendMail("New registration from " + nam, bodyd, "reluxelectric@gmail.com", "reluxgroups01@gmail.com");
                                dialog.show();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Error! Please contact us.", Toast.LENGTH_SHORT).show();
                        }



                    } else {
                        Toast.makeText(this, "Please fill all the details correctly.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please Enter a Valid Mail ID", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, "Enter Mobile Number Correctly", Toast.LENGTH_SHORT).show();
            }

        }
    }

    public void gotosignin(View view) {
        startActivity(new Intent(FormSignupCard.this, LoginSimpleGreen.class));
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mene, menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void settings(MenuItem item) {
       // Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }

    public void Help(MenuItem item) {
       // Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }

    public void Support(MenuItem item) {
        //Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }

    public void Feedback(MenuItem item) {
        //Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }
    public void signinn(MenuItem item) {
        startActivity(new Intent(FormSignupCard.this, LoginSimpleGreen.class));

    }

}
