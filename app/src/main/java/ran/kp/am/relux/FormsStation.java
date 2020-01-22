package ran.kp.am.relux;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.net.Uri;
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
import java.util.List;
import java.util.regex.Pattern;

//import com.obsez.android.lib.filechooser.ChooserDialog;

public class FormsStation extends AppCompatActivity implements MultiSelectionSpinner.OnMultipleItemsSelectedListener {
    Spinner spstate, spddist;
    MultiSelectionSpinner spchargingcapacity, spadapter, spmode;
    ArrayList<String> listState;
    ArrayList<String> listCity;
    ArrayList<String> listAdapter;
    ArrayList<String> listMode;

    ArrayAdapter stateadapter;
    ArrayAdapter distadapter;
    ArrayAdapter Adapadapter;

    String ststate,stdist,make,model,adap,rcbook,stinv;
    EditText uploadrcbook,name,mobile,email,LocTag,Invoice;
    StateVO selCC= new StateVO();
    String globalpath="";
    String globalINV="";


   // TextView tv;
    StringBuilder stf;
    //PrefManager  prefManager;
    String selmake="", selmodel="",seladap="", selststate="", selstdist="",selrcbook="";


    ArrayList<String> n;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signupevstation);
        spstate = findViewById(R.id.edstate);
        StrictMode.ThreadPolicy policy = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        spddist = findViewById(R.id.eddist);

        n= new ArrayList<>();
        spchargingcapacity = findViewById(R.id.edmake);
        spadapter = findViewById(R.id.edadapter);
        spmode = findViewById(R.id.edmodel);
        Tools.setSystemBarColor(this, R.color.green_600);
        Tools.setSystemBarLight(this);
        stf=new StringBuilder();
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.drawable.relwhite);
        getSupportActionBar().setDisplayUseLogoEnabled(true);
        uploadrcbook = findViewById(R.id.rcbook);
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        LocTag = findViewById(R.id.vehicle);
        email = findViewById(R.id.email);
        Invoice = findViewById(R.id.inv);
        rcbook="select";
        stinv="select";
        uploadrcbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(0);
            }
        });
        Invoice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser(1);
            }
        });

        listState = new ArrayList<String>();
        listCity = new ArrayList<String>();


        String[] select_qualification = {
                "3X3kW", "3.3kW", "7kW", "8kW",
                "15kW", "25kW", "50kW", "100kW"};


        spchargingcapacity.setItems(select_qualification);
        spchargingcapacity.setListener(this, 1);

        ArrayList<StateVO> listVOs = new ArrayList<>();

        for (int i = 0; i < select_qualification.length; i++) {
            StateVO stateVO = new StateVO();
            stateVO.setTitle(select_qualification[i]);
            stateVO.setSelected(false);
            listVOs.add(stateVO);
        }
        MyAdapter MyAD = new MyAdapter(FormsStation.this, 0, listVOs);
        Adapadapter = new ArrayAdapter(FormsStation.this, android.R.layout.simple_list_item_1, listVOs);


        final String[] adapterA = {
                  "GB/T", "CHadeMO", "CCS"};
        spadapter.setItems(adapterA);
        spadapter.setListener(this, 2);


        final String[] adaptermode = {
                  "A C", "D C"};
        spmode.setItems(adaptermode);
        spmode.setListener(this, 3);


        listMode = new ArrayList<String>();

        obj_list();
        stateadapter = new ArrayAdapter(FormsStation.this, android.R.layout.simple_list_item_1, listState);
        spstate.setAdapter(stateadapter);
        spstate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ststate = "select";

                if (position != 0) {
                    Toast.makeText(FormsStation.this, "Selected " + listState.get(position), Toast.LENGTH_SHORT).show();
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
                        distadapter = new ArrayAdapter(FormsStation.this, android.R.layout.simple_list_item_1, listCity);
                        spddist.setAdapter(distadapter);
                        spddist.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                Toast.makeText(FormsStation.this, "Selected " + listCity.get(position), Toast.LENGTH_SHORT).show();
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


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mene,menu);
        return super.onCreateOptionsMenu(menu);
    }

    public void settings(MenuItem item) {
        //Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }public void Help(MenuItem item) {
        //Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }public void Support(MenuItem item) {
        //Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
    }public void Feedback(MenuItem item) {
        //Toast.makeText(this, "Coming Soon", Toast.LENGTH_SHORT).show();
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
    public void gotosignin(View view) {
        startActivity(new Intent(FormsStation.this, LoginSimpleGreen.class));
    }

    private boolean isValidMobile(String phone) {
        if(!Pattern.matches("[a-zA-Z]+", phone)) {
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
        String nam,mpb,emai,vech;
        nam=name.getText().toString();
        mpb=mobile.getText().toString();
        emai=email.getText().toString();
        vech=LocTag.getText().toString();


        if (nam.equals("")&&mpb.equals("")&&emai.equals("")&&vech.equals("")){
            Toast.makeText(this, "Please fill all the details correctly.", Toast.LENGTH_SHORT).show();
        } else {
            if (isValidMobile(mpb)) {
                if (isValidMail(emai)) {

                    Log.d("fqwgjh",ststate+stdist+rcbook+stinv);
                    if (ststate.equals("done")&&stinv.equals("done")&&stinv.equals("done")&&stdist.equals("done")){
                        String a = spchargingcapacity.getSelectedItemsAsString();
                        String b = spadapter.getSelectedItemsAsString();
                        String c = spmode.getSelectedItemsAsString();
                        final Dialog dialog = new Dialog(this);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
                        dialog.setContentView(R.layout.dialog_info);
                        dialog.setCancelable(true);
                        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                        lp.copyFrom(dialog.getWindow().getAttributes());
                        lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
                        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;

                        String bodyd = "Name :" + nam + "\n"
                                + "Mobile :" + mpb + "\n"
                                + "Mail Id : " + emai + "\n"
                                + "Vehicle Number :" + vech + "\n"
                                + "State :" + selststate + "\n"
                                + "District :" + selstdist + "\n"
                                + "Charging Capacity :" + a + "\n"
                                + "Adapters :" + b + "\n" + "Modes :" + c + "\n";

                        try {
                            //sendEmailMulipleFiles(FormsStation.this, "reluxgroups01@gmail.com", "New registration from "+nam, bodyd, n);
                            GMailSender gMailSender = new GMailSender("reluxelectric@gmail.com", "100%relux");
                            try {
                                gMailSender.addAttachment(n.get(0));
                                gMailSender.addAttachment(n.get(1));
                                gMailSender.sendMail("New registration from " + nam, bodyd, "reluxelectric@gmail.com", "reluxgroups01@gmail.com");
                                dialog.show();

                                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                                    @Override
                                    public void onDismiss(DialogInterface dialog) {

                                    }
                                });
                            } catch (Exception e) {
                                Toast.makeText(this, "Error! Please contact us...", Toast.LENGTH_SHORT).show();

                                e.printStackTrace();
                            }

                            ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    //  Toast.makeText(getApplicationContext(), ((AppCompatButton) v).getText().toString() + " Clicked", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    finish();

                                }
                            });
                        } catch (Exception e) {
                            e.printStackTrace();
                            Toast.makeText(this, "Error! Please contact us.", Toast.LENGTH_SHORT).show();
                        }

                        dialog.getWindow().setAttributes(lp);
                    }else {
                        Toast.makeText(this, "Please fill all the details correctly...", Toast.LENGTH_SHORT).show();

                    }

                } else  {
                    Toast.makeText(this, "Please Enter a Valid Mail ID", Toast.LENGTH_SHORT).show();
                }
            }
            else
            {
                Toast.makeText(this, "Enter Mobile Number Correctly", Toast.LENGTH_SHORT).show();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    /*private void showFileChooser(int i) {
        new ChooserDialog(FormsStation.this)
                .withStartFile(Environment.getExternalStorageDirectory().getPath())
                .withFilter(false, false, "jpeg", "jpg",
                        "png", "pdf")

                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(String path, File pathFile) {
                        Toast.makeText(FormsStation.this, "->" + path, Toast.LENGTH_SHORT).show();
                        //chosefile=path;
                        n.add(i,path);
                        String h = n.get(i);
                        switch (i){
                            case 0:
                                if (!h.equals("")){
                                    rcbook="done";
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                                        uploadrcbook.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_assig, 0);
                                    }
                                    uploadrcbook.setHint("File Attached");
                            uploadrcbook.setHintTextColor(getResources().getColor(R.color.green_400));
                                }
                                break;
                            case 1:
                                if (!h.equals("")){
                                    stinv="done";
                                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                                        Invoice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_assig, 0);
                                    }
                                    Invoice.setHint("File Attached");
                            Invoice.setHintTextColor(getResources().getColor(R.color.green_400));
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
    }*/

    private void showFileChooser(int i) {
        if (Build.VERSION.SDK_INT >= 29) {
            //only api 28 above
        //    Toast.makeText(this, "above 28", Toast.LENGTH_SHORT).show();
            new ChooserDialog(FormsStation.this)
                   // .withStartFile(getExternalFilesDir(null).getAbsolutePath())
                    .withStartFile(Environment.getExternalStorageDirectory().getPath())
                    //.withStartFile(FormsStation.this.getExternalFilesDir("/storage/emulated/0/").getPath())
                    .withFilter(false, false, "jpeg", "jpg","png", "pdf")

                    .withChosenListener(new ChooserDialog.Result() {
                        @Override
                        public void onChoosePath(String path, File pathFile) {

                            //String ok = path.replace("/storage/emulated/0", "");
                           // Toast.makeText(FormsStation.this, "->" + ok, Toast.LENGTH_SHORT).show();
                            //globalpath = ok;

                            n.add(i,path);
                            String h = n.get(i);
                            switch (i){
                                case 0:
                                    if (!h.equals("")){
                                        rcbook="done";
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                                            uploadrcbook.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_assig, 0);
                                        }
                                        uploadrcbook.setHint("File Attached");
                                        uploadrcbook.setHintTextColor(getResources().getColor(R.color.green_400));
                                    }
                                    break;
                                case 1:
                                    if (!h.equals("")){
                                        stinv="done";
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                                            Invoice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_assig, 0);
                                        }
                                        Invoice.setHint("File Attached");
                                        Invoice.setHintTextColor(getResources().getColor(R.color.green_400));
                                    }
                                    break;
                            }
                            uploadrcbook.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_assig, 0);
                            //uploadrcbook.setHint("File Loaded");
                            uploadrcbook.setHintTextColor(getResources().getColor(R.color.green_400));
                            uploadrcbook.setText(globalpath);
                            uploadrcbook.setHintTextColor(getColor(R.color.green_400));
                            rcbook = "done";
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

        } else {
            //only api 28 down
          //  Toast.makeText(this, "below 28", Toast.LENGTH_SHORT).show();

            new ChooserDialog(FormsStation.this)
                    .withStartFile(Environment.getExternalStorageDirectory().getPath())
                    .withFilter(false, false, "jpeg", "jpg","png", "pdf")

                    .withChosenListener(new ChooserDialog.Result() {
                        @Override
                        public void onChoosePath(String path, File pathFile) {
                            //uploadrcbook.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_assig, 0);
                            //uploadrcbook.setHint("File Loaded");
                            n.add(i,path);
                            String h = n.get(i);
                            switch (i){
                                case 0:
                                    if (!h.equals("")){
                                        rcbook="done";
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                                            uploadrcbook.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_assig, 0);
                                        }
                                        uploadrcbook.setHint("File Attached");
                                        uploadrcbook.setHintTextColor(getResources().getColor(R.color.green_400));
                                    }
                                    break;
                                case 1:
                                    if (!h.equals("")){
                                        stinv="done";
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.CUPCAKE) {
                                            Invoice.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_assig, 0);
                                        }
                                        Invoice.setHint("File Attached");
                                        Invoice.setHintTextColor(getResources().getColor(R.color.green_400));
                                    }
                                    break;
                            }
                         //   uploadrcbook.setHintTextColor(getResources().getColor(R.color.green_400));
                          ///  uploadrcbook.setText(globalpath);
                          //  rcbook = "done";
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
    }
    public void sendEmailMulipleFiles(final Context context, final String toAddress, final String subject, final String body, ArrayList<String> attachmentPath) throws Exception {
        try {

            //final String ipath = Environment.getExternalStorageDirectory().toString()+ "/test.txt";
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE); // before
            dialog.setContentView(R.layout.dialog_info);
            dialog.setCancelable(true);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.WRAP_CONTENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            ((AppCompatButton) dialog.findViewById(R.id.bt_close)).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialog.dismiss();
                    Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra(Intent.EXTRA_EMAIL, new String[] { toAddress });
                    intent.putExtra(Intent.EXTRA_SUBJECT, subject);
                    intent.putExtra(Intent.EXTRA_TEXT, body);
                    intent.setType("message/rfc822");
                    PackageManager pm = context.getPackageManager();
                    List<ResolveInfo> matches = pm.queryIntentActivities(intent, 0);
                    ResolveInfo best = null;
                    for (final ResolveInfo info : matches) {
                        if (info.activityInfo.packageName.contains(".gm.") || info.activityInfo.name.toLowerCase().contains("gmail"))
                            best = info;
                    }
                    ArrayList<Uri> uri = new ArrayList<Uri>();
                    for (int i = 0; i < n.size(); i++) {
                        File file = new File(n.get(i));
                        uri.add(Uri.fromFile(file));
                    }

                    intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uri);

                    if (best != null)
                        intent.setClassName(best.activityInfo.packageName, best.activityInfo.name);

                    context.startActivity(Intent.createChooser(intent, "Choose an email application..."));
                    finish();

                }});
            dialog.show();


        } catch (Exception ex) {
            ex.printStackTrace();
            throw ex;
        }
    }

    @Override
    public void selectedIndices(List<Integer> indices) {

    }

    @Override
    public void selectedStrings(List<String> strings) {
        Toast.makeText(this, strings.toString(), Toast.LENGTH_LONG).show();
    }

    public void signinn(MenuItem item) {
        startActivity(new Intent(FormsStation.this, LoginSimpleGreen.class));

    }
}
