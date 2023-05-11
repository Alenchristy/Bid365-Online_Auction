package com.example.auction;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.os.StrictMode.ThreadPolicy;
import android.preference.PreferenceManager;
import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class Test extends Activity implements View.OnClickListener {

	EditText e1;
	Button send;
	ListView chatview;

	String namespace1="http://tempuri.org/";
	
	String lastid="0";

	String method1="chatview";
	String soapaction1=namespace1+method1;
	
	//String [] chat_id,message,date,type;
	
	MessagesAdapter adapterMessages;
	ListView listMessages;
	Button   bt1;
	EditText edtxttosent;
	Handler hnd;
	Runnable ad;

	TextView tvs;
	
	String []date,msg,fid,mid,ty,time;
	String lid="";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		
		
		SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor ed=sh.edit();
		ed.putString("idd","0");
		ed.commit();

		lid=sh.getString("uid", "");
		
		hnd=new Handler();




		listMessages= (ListView)findViewById(R.id.list_chat);
		bt1= (Button) findViewById(R.id.button_chat_send);
		adapterMessages = new MessagesAdapter(Test.this);
		edtxttosent=(EditText)findViewById(R.id.input_chat_message);

		// Enable auto scroll
		listMessages.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		listMessages.setStackFromBottom(true);
		edtxttosent.setOnClickListener(this);

		tvs=findViewById(R.id.tvs);


		String url1 = "http://" + sh.getString("ip", "") + ":5000/name";
		RequestQueue queue = Volley.newRequestQueue(Test.this);
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				// Display the response string.
				Log.d("+++++++++++++++++",response);
				try {
					JSONArray ar=new JSONArray(response);

					{
						JSONObject jo=ar.getJSONObject(0);
						tvs.setText(jo.getString("pname"));


					}
					if (android.os.Build.VERSION.SDK_INT > 9) {
						StrictMode.ThreadPolicy policy =
								new StrictMode.ThreadPolicy.Builder().permitAll().build();
						StrictMode.setThreadPolicy(policy);
					}




				} catch (JSONException e) {
//                    e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {


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




		bt1.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View arg0) {
			String nam = edtxttosent.getText().toString();

			if (nam.equalsIgnoreCase("")) {
				edtxttosent.setError("Enter a message");
			}

			else {

				if (arg0 == bt1) {
					{

						SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
						String ip = sh.getString("ip", "");
						final String sid1 = sh.getString("user", "");


//					String url = "http://" + ip + ":5000/in_message2";

						String url = "http://" + ip + ":5000/chats";

						RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

						StringRequest postRequest = new StringRequest(Request.Method.POST, url,
								new Response.Listener<String>() {
									@Override
									public void onResponse(String response) {

//		                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

										// response
										try {
											JSONObject jsonObj = new JSONObject(response);
											String sucs = jsonObj.getString("status");
											if (sucs.equalsIgnoreCase("send")) {
												Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();

												edtxttosent.setText("");
											}


										} catch (Exception e) {

										}
									}
								},
								new Response.ErrorListener() {
									@Override
									public void onErrorResponse(VolleyError error) {
										// error
										Toast.makeText(getApplicationContext(), "eeeee" + error.toString(), Toast.LENGTH_SHORT).show();
									}
								}
						) {
							@Override
							protected Map<String, String> getParams() {


								SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
								String uid = sh.getString("lid", "");
								String toid = sh.getString("uid", "");

								Map<String, String> params = new HashMap<String, String>();
								params.put("fid", uid);
								params.put("toid", toid);
								params.put("msg", edtxttosent.getText().toString());
								params.put("pid", getIntent().getStringExtra("pid"));


								return params;
							}
						};

						requestQueue.add(postRequest);

					}
				}
			}
		}
		});
		
		
		
		ad=new Runnable() {
			@Override
			public void run() {
				SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				String ip=sh.getString("ip", "");
				final String use=sh.getString("user","");
				String url = "http://" + ip + ":5000/view_message2";
//				Toast.makeText(getApplicationContext(), lastid, Toast.LENGTH_LONG).show();
				RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
			    //    Toast.makeText(getApplicationContext(),"hai",Toast.LENGTH_SHORT).show();
			        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
			                new Response.Listener<String>()
			                {
			                    @Override
			                    public void onResponse(String response) {



			                        // response
			                        try {
			                            JSONObject jsonObj = new JSONObject(response);
			                            String sucs=   jsonObj.getString("status");
			                            if(sucs.equalsIgnoreCase("ok"))
			                            {
			                            	JSONArray jsa=new JSONArray(jsonObj.getString("res1"));
			                            	date=new String[jsa.length()];
			                            	msg=new String[jsa.length()];
			                            	fid=new String[jsa.length()];
											mid=new String[jsa.length()];
											ty=new String[jsa.length()];
											time=new String[jsa.length()];

			                            	for(int i=0;i<jsa.length();i++)
			                            	{
			                            		JSONObject jsob=jsa.getJSONObject(i);
												time[i]=jsob.getString("time");
												date[i]=jsob.getString("date");
			                            		msg[i]=jsob.getString("message");
			                            		fid[i]=jsob.getString("fromid");
												mid[i]=jsob.getString("msgid");
												ty[i]=jsob.getString("ty");
			                            		lastid=mid[i];

			                            		if(ty[i].equalsIgnoreCase("o"))
			    					    		{
													ChatMessage	message1 = new ChatMessage();
													message1.setUsername("Other");
													message1.setMessage(msg[i]);
													message1.setTime(time[i]);
													message1.setIncomingMessage(true);
													adapterMessages.add(message1);
			    					    		}
			    					    		else
			    					    		{
													ChatMessage	message = new ChatMessage();
													message.setUsername("Me");
													message.setMessage(msg[i]);
													message.setTime(time[i]);
													message.setIncomingMessage(false);
													adapterMessages.add(message);
			    					    		}
			    								listMessages.setAdapter(adapterMessages);

			                            	}

			                                 }
			                        } catch (Exception e) {
			                        	 Toast.makeText(getApplicationContext(),"eeeee"+e.toString(),Toast.LENGTH_LONG).show();
			                       }
			                    }
			                },
			                new Response.ErrorListener()
			                {
			                    @Override
			                    public void onErrorResponse(VolleyError error) {
			                        // error
//			                        Toast.makeText(getApplicationContext(),"eeeee"+error.toString(),Toast.LENGTH_SHORT).show();
			                    }
			                }
			        ) {
			            @Override
			            protected Map<String, String> getParams()
			            {
			                SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
			                Map<String, String>  params = new HashMap<String, String>();
			              String s=sh.getString("lid","");
			                params.put("fid", s);
			                params.put("toid",  sh.getString("uid",""));
			                params.put("lastmsgid", lastid);
							params.put("pid",getIntent().getStringExtra("pid"));
			                return params;
			            }
			        };
			        requestQueue.add(postRequest);
				hnd.postDelayed(ad, 4000);
			}
		};
		hnd.post(ad);
	}
	
	@SuppressLint("NewApi")
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		hnd.removeCallbacks(ad);
		SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		Editor edt= sh.edit();
    	edt.putString("lastid", "0");
    	edt.commit();
    	lastid="0";
		super.onBackPressed();
	}

	@Override
	public void onClick(View view) {
		edtxttosent.setText("");
		edtxttosent.getText().clear();
		edtxttosent.clearFocus();
	}
}
