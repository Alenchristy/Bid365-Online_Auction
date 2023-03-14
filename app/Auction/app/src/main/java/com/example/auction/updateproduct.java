package com.example.auction;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
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
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class updateproduct extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    Spinner spi;
    String url, ip, lid, title, url1,url2;
    int hour, minute;

    String PathHolder = "";
    byte[] filedt = null;
    SharedPreferences sh;
    ArrayList<String> cid, cname;
    String fileName = "", path = "";
    private static final int FILE_SELECT_CODE = 0;
    Button btn1, btn2;
    EditText name1, details1, price1, place1, date1, tm1, image1;

    int pos;
    String cd;

    String nam, dtls, prc, plc, tim, dat, im;
    String eeid;
    final Calendar myCalendar= Calendar.getInstance();
    DatePickerDialog.OnDateSetListener setListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_updateproduct);

        sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());



        spi = findViewById(R.id.spin);
        spi.setOnItemSelectedListener(this);
        name1 = findViewById(R.id.pnam1);
        image1 = findViewById(R.id.img2);
        price1 = findViewById(R.id.price1);
        place1 = findViewById(R.id.place1);

        details1 = findViewById(R.id.details1);
        tm1 = findViewById(R.id.tim1);
        btn1 = findViewById(R.id.bt1);
        btn2 = findViewById(R.id.bt2);
        date1 = (EditText) findViewById(R.id.date2);




//        datepicker
        final Calendar currentDate = Calendar.getInstance();
        date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        updateproduct.this,  new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                        date1.setText(String.format("%d/%d/%d", dayOfMonth, month+1, year));

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


        tm1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Calendar c = Calendar.getInstance();
                hour = c.get(Calendar.HOUR_OF_DAY);
                minute = c.get(Calendar.MINUTE);

                int limitHourStart = 8;
                int limitMinuteStart = 0;
                int limitHourEnd = 17;
                int limitMinuteEnd = 0;

                TimePickerDialog timePickerDialog = new TimePickerDialog(updateproduct.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                                hour = selectedHour;
                                minute = selectedMinute;

//                                Toast.makeText(updateproduct.this, "Selected time is " + hour + ":" + minute, Toast.LENGTH_SHORT).show();
                                tm1.setText(String.format("%d:%d", hour, minute));
                            }
                        }, hour, minute, true);
                timePickerDialog.show();
            }
        });


        url1 = "http://" + sh.getString("ip", "") + ":5000/updateview";
        RequestQueue queue = Volley.newRequestQueue(updateproduct.this);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Display the response string.
                Log.d("+++++++++++++++++",response);
                try {
//                    Toast.makeText(updateproduct.this, ""+response, Toast.LENGTH_SHORT).show();
                    JSONArray ar=new JSONArray(response);

                    {
                        JSONObject jo=ar.getJSONObject(0);
                        name1.setText(jo.getString("pname"));
                        details1.setText(jo.getString("details"));
                        price1.setText(jo.getString("price"));
                        place1.setText(jo.getString("place"));
                        date1.setText(jo.getString("date"));
                        tm1.setText(jo.getString("time"));
                        image1.setText(jo.getString("image"));
                        cd=jo.getString("cat");
//                        spi.setSelection(cd);
//


                    }


                } catch (JSONException e) {
//                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
//                Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();

            }
        }){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("pid",getIntent().getStringExtra("pid"));
                return params;
            }
        };

        // Add the request to the RequestQueue.
        queue.add(stringRequest);





        url2 = "http://" + sh.getString("ip", "") + ":5000/viewcatg";
        RequestQueue queue1 = Volley.newRequestQueue(updateproduct.this);

        StringRequest stringRequest2 = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
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

                    ArrayAdapter<String> ad = new ArrayAdapter<>(updateproduct.this, android.R.layout.simple_spinner_dropdown_item, cname);
                    spi.setAdapter(ad);

                    pos = ad.getPosition(cd);
                    spi.setSelection(pos);

                    spi.setOnItemSelectedListener(updateproduct.this);


                } catch (Exception e) {
                    Log.d("=========", e.toString());
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

//                Toast.makeText(updateproduct.this, "err" + error, Toast.LENGTH_SHORT).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<>();
                return params;
            }
        };
        queue.add(stringRequest2);


        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
//            intent.setType("application/pdf");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                startActivityForResult(intent, 7);
            }
        });





        url1 = "http://" + sh.getString("ip", "") + ":5000/updateproduct";
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                nam = name1.getText().toString();
                dtls = details1.getText().toString();
                im = image1.getText().toString();
                plc = place1.getText().toString();
                dat = date1.getText().toString();
                tim = tm1.getText().toString();
                prc = price1.getText().toString();

                if (nam.equalsIgnoreCase("")) {
                    name1.setError("Enter product name");
                }
                else if (!nam.matches("^[a-z A-Z ]*$")) {
                    name1.setError("characters only allowed");
                }
                else if (dtls.equalsIgnoreCase("")) {
                    details1.setError("Enter product details");
                }
                else if (prc.equalsIgnoreCase("")) {
                    price1.setError("Enter price");
                }
                else if (!prc.matches("^[0-9]*$")) {
                    price1.setError("characters only allowed");
                }
                else if (plc.equalsIgnoreCase("")) {
                    place1.setError("Enter place");
                }
                else if (!plc.matches("^[a-z A-Z ]*$")) {
                    place1.setError("characters only allowed");
                }
                else if (dat.equalsIgnoreCase("")) {
                    date1.setError("Enter date");
                }

                else if (tim.equalsIgnoreCase("")) {
                    tm1.setError("Enter time");
                }
                else if (im.equalsIgnoreCase("")) {
                    image1.setError("insert product image");
                }

                else {
//                    Toast.makeText(getApplicationContext(), "=="+filedt, Toast.LENGTH_SHORT).show();
                    if((filedt+"").equalsIgnoreCase("null"))
                    {
                        RequestQueue queue = Volley.newRequestQueue(updateproduct.this);
                        url = "http://" + sh.getString("ip", "") + ":5000/updateproduct2";

                        // Request a string response from the provided URL.
                        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the response string.
                                Log.d("+++++++++++++++++", response);
                                try {
                                    JSONObject json = new JSONObject(response);
                                    String res = json.getString("task");
//                                    Toast.makeText(updateproduct.this, "" + res, Toast.LENGTH_SHORT).show();

                                    if (res.equalsIgnoreCase("success")) {
//                                        Toast.makeText(updateproduct.this, "registered successfully ", Toast.LENGTH_SHORT).show();

//                                        Toast.makeText(updateproduct.this, "Successfully updated", Toast.LENGTH_LONG).show();
                                        Intent i = new Intent(getApplicationContext(), sell.class);
                                        startActivity(i);

                                    } else {

//                                        Toast.makeText(updateproduct.this, "please try again", Toast.LENGTH_SHORT).show();

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }

                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
//                                Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
                            }
                        }) {
                            @Override
                            protected Map<String, String> getParams() {
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("name", nam);
                                params.put("detail", dtls);
                                params.put("place", plc);
                                params.put("price", prc);
                                params.put("date", dat);
                                params.put("time", tim);
                                params.put("lid", sh.getString("lid", ""));
                                params.put("ccid", eeid);
                                params.put("pid",getIntent().getStringExtra("pid"));
                                return params;
                            }
                        };
                        queue.add(stringRequest);
                    }
                    else {
                        uploadBitmap(title);
                    }
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

//                                Toast.makeText(updateproduct.this, "Successfully updated", Toast.LENGTH_LONG).show();
                                Intent i = new Intent(getApplicationContext(), sell.class);
                                startActivity(i);
                            } else {
//                                Toast.makeText(getApplicationContext(), "failed", Toast.LENGTH_LONG).show();
                            }

                        } catch (Exception e) {
//                            Toast.makeText(getApplicationContext(), "Error" + e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }


                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(getApplicationContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();

                params.put("name", nam);
                params.put("detail", dtls);
                params.put("place", plc);
                params.put("price", prc);
                params.put("date", dat);
                params.put("time", tim);
                params.put("lid", sh.getString("lid", ""));
                params.put("ccid", eeid);
                params.put("pid",getIntent().getStringExtra("pid"));
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
                        image1.setText(PathHolder);
                    }
                    catch (Exception e){
//                        Toast.makeText(this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {
        super.onPointerCaptureChanged(hasCapture);
    }
}
