package com.example.auction;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class adproduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner sp;
    String url, ip, lid, title, url1;
    int hour, minute;

    String PathHolder = "";
    byte[] filedt = null;
    SharedPreferences sh;
    ArrayList<String> cid, cname;
    String fileName = "", path = "";
    private static final int FILE_SELECT_CODE = 0;
    Button bt, bt1;

    EditText name, details, price, rprice, place, image;

    TextView dateee,tm;

    String nam, dtls, prc,rprc, plc, tim, dat, im;
    String eeid;
    final Calendar myCalendar= Calendar.getInstance();
    DatePickerDialog.OnDateSetListener setListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adproduct);
        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        url1 = "http://" + sh.getString("ip", "") + ":5000/adproduct";

        sp = findViewById(R.id.s);
        sp.setOnItemSelectedListener(this);
        name = findViewById(R.id.pnam);
        image = findViewById(R.id.img);
        price = findViewById(R.id.price);
        rprice = findViewById(R.id.price2);
        place = findViewById(R.id.place);

        details = findViewById(R.id.details);

        tm = findViewById(R.id.tim);

        bt = findViewById(R.id.b1);
        bt1 = findViewById(R.id.b2);
        dateee = findViewById(R.id.date1);




//        datepicker
        final Calendar currentDate = Calendar.getInstance();
        dateee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        adproduct.this,  new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        dateee.setText(String.format("%d/%d/%d", dayOfMonth, month+1, year));

                    }
                },currentDate.get(Calendar.YEAR),
                        currentDate.get(Calendar.MONTH),
                        currentDate.get(Calendar.DAY_OF_MONTH)
                );
                currentDate.add(Calendar.DAY_OF_MONTH,1);
                datePickerDialog.getDatePicker().setMinDate(currentDate.getTimeInMillis());
                datePickerDialog.show();

            }
        });


        tm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar calendar = Calendar.getInstance();
                int hour = calendar.get(Calendar.HOUR_OF_DAY);
                int minute = calendar.get(Calendar.MINUTE);

                int limitHourStart = 8;
                int limitMinuteStart = 0;
                int limitHourEnd = 17;
                int limitMinuteEnd = 0;

                TimePickerDialog timePickerDialog = new TimePickerDialog(adproduct.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                                String am_pm = "";
                                int hour = hourOfDay;
                                if(hour >= 12) {
                                    hour = hour - 12;
                                    am_pm = "PM";
                                } else {
                                    am_pm = "AM";
                                }
                                if(hour == 0) {
                                    hour = 12;
                                }

                                String formattedTime = String.format(Locale.getDefault(), "%02d:%02d", hourOfDay, minute, am_pm);
                                tm.setText(formattedTime);


//                                tm.setText(String.format("%d:%d", hour, minute, am_pm));
                            }
                        }, hour, minute, false);
                timePickerDialog.show();
            }
        });





        url = "http://" + sh.getString("ip", "") + ":5000/viewcatg";
        RequestQueue queue = Volley.newRequestQueue(adproduct.this);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++", response);
                try {

                    JSONArray ar = new JSONArray(response);
                    cid = new ArrayList<>();
                    cname = new ArrayList<>();


                    for (int ik = 0; ik < ar.length(); ik++) {
                        JSONObject jo = ar.getJSONObject(ik);
                        cid.add(jo.getString("id"));
                        cname.add(jo.getString("c_name"));
                    }

                    ArrayAdapter<String> ad = new ArrayAdapter<>(adproduct.this, android.R.layout.simple_spinner_dropdown_item, cname);
                    sp.setAdapter(ad);
                    sp.setOnItemSelectedListener(adproduct.this);


                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(adproduct.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(stringRequest);


        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
//            intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 7);
            }
        });


        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nam = name.getText().toString();
                dtls = details.getText().toString();
                im = image.getText().toString();
                plc = place.getText().toString();
                dat = dateee.getText().toString();
                tim = tm.getText().toString();
                prc = price.getText().toString();
                rprc = rprice.getText().toString();

                if (nam.equalsIgnoreCase("")) {
                    name.setError("Enter product name");
                }
                else if (!nam.matches("^[a-z A-Z ]*$")) {
                    name.setError("characters only allowed");
                }
                else if (dtls.equalsIgnoreCase("")) {
                    details.setError("Enter product details");
                }
                else if (prc.equalsIgnoreCase("")) {
                    price.setError("Enter price");
                }
                else if (!prc.matches("^[0-9]*$")) {
                    price.setError("Numbers only allowed");
                }
                else if (!rprc.matches("^[0-9]*$")) {
                    rprice.setError("Numbers only allowed");
                }
                else if (plc.equalsIgnoreCase("")) {
                    place.setError("Enter place");
                }
                else if (!plc.matches("^[a-z A-Z ]*$")) {
                    place.setError("characters only allowed");
                }
                else if (dat.equalsIgnoreCase("")) {
                    dateee.setError("Enter date");
                }

                else if (tim.equalsIgnoreCase("")) {
                    tm.setError("Enter time");
                }

                else if (im.equalsIgnoreCase("")) {
                    image.setError("insert product image");
                }


                else {

                    uploadBitmap(title);
                }

            }
        });


    }

//    ProgressDialog pd;

    private void uploadBitmap(final String title) {
//        pd = new ProgressDialog(adproduct.this);
//        pd.setMessage("Uploading....");
//        pd.show();
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, url1,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response1) {
//                        pd.dismiss();
                        String x = new String(response1.data);
                        try {
                            JSONObject obj = new JSONObject(new String(response1.data));
//                        Toast.makeText(Upload_agreement.this, "Report Sent Successfully", Toast.LENGTH_LONG).show();
                            if (obj.getString("task").equalsIgnoreCase("success")) {

//                                Toast.makeText(adproduct.this, "Successfully uploaded", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), payment.class);
                                i.putExtra("pname", nam);
                                startActivity(i);
                                Toast.makeText(adproduct.this, nam, Toast.LENGTH_LONG).show();
                            } else {
                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("name", nam);
                params.put("detail", dtls);
                params.put("place", plc);
                params.put("price", prc);
                params.put("rp", rprc);

                params.put("date", dat);
                params.put("time", tim);
                params.put("lid", sh.getString("lid", ""));
                params.put("ccid", eeid);
                return params;
            }

            @Override
            protected Map<String, VolleyMultipartRequest.DataPart> getByteData() {
                Map<String, VolleyMultipartRequest.DataPart> params = new HashMap<>();
                long imagename = System.currentTimeMillis();
                params.put("file", new DataPart(PathHolder, filedt));

                return params;
            }
        };


        Volley.newRequestQueue(this).add(volleyMultipartRequest);
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 7:
                if (resultCode == RESULT_OK) {
                    Uri uri = data.getData();
                    Log.d("File Uri", "File Uri: " + uri.toString());
                    // Get the path
                    try {
                        PathHolder = FileUtils.getPathFromURI(this, uri);
//                        PathHolder = data.getData().getPath();
//                        Toast.makeText(this, PathHolder, Toast.LENGTH_SHORT).show();

                        filedt = getbyteData(PathHolder);
                        Log.d("filedataaa", filedt + "");
//                        Toast.makeText(this, filedt+"", Toast.LENGTH_SHORT).show();
                        image.setText(PathHolder);
                    }
                    catch (Exception e){
                        Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                break;
        }
    }

    private byte[] getbyteData(String pathHolder) {
        Log.d("path", pathHolder);
        File fil = new File(pathHolder);
        int fln = (int) fil.length();
        byte[] byteArray = null;
        try {
            InputStream inputStream = new FileInputStream(fil);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[fln];
            int bytesRead = 0;

            while ((bytesRead = inputStream.read(b)) != -1) {
                bos.write(b, 0, bytesRead);
            }
            byteArray = bos.toByteArray();
            inputStream.close();
        } catch (Exception e) {
        }
        return byteArray;
    }


        @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

        eeid=cid.get(i);
    }
    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}


