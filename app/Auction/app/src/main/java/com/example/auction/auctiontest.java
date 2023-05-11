package com.example.auction;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.speech.tts.TextToSpeech;
import android.speech.tts.Voice;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

@TargetApi(Build.VERSION_CODES.GINGERBREAD)
public class auctiontest extends Activity implements View.OnClickListener {
	private TextToSpeech tts;
	private EditText e1;
	private Button bt;
	Button b1,b2,b3;
	TextView pronam,sprice,cbid,timer;
	String url1,url;
	Button send;
	ListView chatview;

	String namespace1="http://tempuri.org/";

	String lastid="0";
	float camt=0;

	String method1="chatview";
	String soapaction1=namespace1+method1;

	//String [] chat_id,message,date,type;

	MessagesAdapter1 adapterMessages;
	ListView listMessages;
	Button   bt1;
	EditText edtxttosent;
	Handler hnd;
	Runnable ad;

	String []date,msg,fid,mid,ty,time;
	String lid="";
	SharedPreferences sh;
	int compflag=0,sflag=0,compflg=-1,final_status=0;
	int tc=60;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_auction_test);

		pronam = findViewById(R.id.pronam);
		sprice = findViewById(R.id.sprice);
		cbid = findViewById(R.id.cbid);
		timer = findViewById(R.id.timer);


		ImageView img = findViewById(R.id.im4);
		Glide.with(this)
				.load(R.drawable.ac4)
				.into(img);

		b1 = findViewById(R.id.bt1);
		b2 = findViewById(R.id.bt2);
		b3 = findViewById(R.id.bt3);


		b1.setVisibility(View.INVISIBLE);
		b2.setVisibility(View.INVISIBLE);
		b3.setVisibility(View.INVISIBLE);
		img.setVisibility(View.VISIBLE);




		sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
		String pid = getIntent().getStringExtra("pid");
		Editor ed = sh.edit();
		ed.putString("idd", pid);
		ed.commit();

		lid = sh.getString("lid", "");

		hnd = new Handler();

		listMessages = (ListView) findViewById(R.id.list_chat);
		bt1 = (Button) findViewById(R.id.button_chat_send);

		adapterMessages = new MessagesAdapter1(auctiontest.this);
		edtxttosent = (EditText) findViewById(R.id.input_chat_message);

		// Enable auto scroll
		listMessages.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
		listMessages.setStackFromBottom(true);
		edtxttosent.setOnClickListener(this);




		sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());


		url1 = "http://" + sh.getString("ip", "") + ":5000/prod";
		RequestQueue queue = Volley.newRequestQueue(auctiontest.this);
		StringRequest stringRequest = new StringRequest(Request.Method.POST, url1, new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				// Display the response string.
				Log.d("+++++++++++++++++",response);
				try {

					JSONObject jo=new JSONObject(response);
					pronam.setText(jo.getString("pname"));
					sprice.setText(jo.getString("price"));
					cbid.setText(jo.getString("cprice"));
					camt=Float.parseFloat(jo.getString("cprice"));

				} catch (JSONException e) {
					e.printStackTrace();
				}

			}
		}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(getApplicationContext(),"error",Toast.LENGTH_LONG).show();

			}
		}){
			@Override
			protected Map<String, String> getParams()
			{
				Map<String, String>  params = new HashMap<String, String>();
				params.put("pid", sh.getString("idd", ""));
				return params;
			}
		};

		// Add the request to the RequestQueue.
		queue.add(stringRequest);



		tts = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
			@Override
			public void onInit(int status) {
				tts.setSpeechRate(.6f);


				if (status == TextToSpeech.SUCCESS) {
					int res = tts.setLanguage(Locale.ENGLISH);
					if (res == TextToSpeech.LANG_MISSING_DATA ||
							res == TextToSpeech.LANG_NOT_SUPPORTED) {
						Log.e("tts", "language not supported");
					} else {
						bt1.setEnabled(true);
					}
				} else {
					Log.e("tts", "failed");
				}

			}
		});



		bt1.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View arg0) {

				String amt=edtxttosent.getText().toString();

				SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				if (amt.equalsIgnoreCase("")) {
					edtxttosent.setError("Enter a bid ammount");
					speak2();
				}
				else if (!amt.matches("^[0-9]*$")) {
					edtxttosent.setError("Numbers only allowed");
				}

				else {

					if (arg0 == bt1) {
						if (compflag == 0) {
							if (camt < Float.parseFloat(edtxttosent.getText().toString())) {
								speak();

								String ip = sh.getString("ip", "");
								final String sid1 = sh.getString("user", "");

								String url = "http://" + ip + ":5000/insertbid";

								RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());

								StringRequest postRequest = new StringRequest(Request.Method.POST, url,
										new Response.Listener<String>() {
											@Override
											public void onResponse(String response) {

//		                    Toast.makeText(getApplicationContext(),response,Toast.LENGTH_SHORT).show();

												// response
												try {
													JSONObject jsonObj = new JSONObject(response);
													String sucs = jsonObj.getString("task");
													if (sucs.equalsIgnoreCase("success")) {
														Toast.makeText(getApplicationContext(), "success", Toast.LENGTH_SHORT).show();
														cbid.setText(edtxttosent.getText().toString());
														camt = Float.parseFloat(edtxttosent.getText().toString());
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
//		                    Toast.makeText(getApplicationContext(),"eeeee"+error.toString(),Toast.LENGTH_SHORT).show();
											}
										}
								) {
									@Override
									protected Map<String, String> getParams() {


										SharedPreferences sh = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
										String lid = sh.getString("lid", "");
//		            String pid=sh.getString("pid","");

										Map<String, String> params = new HashMap<String, String>();
										params.put("pid", pid);
										params.put("lid", lid);
										params.put("amt", edtxttosent.getText().toString());
										params.put("pid", getIntent().getStringExtra("pid"));


										return params;
									}
								};

								requestQueue.add(postRequest);

							} else {

								Toast.makeText(getApplicationContext(), "Invalid Amount", Toast.LENGTH_LONG).show();
								speak1();
							}
						} else {
							Toast.makeText(getApplicationContext(), "auction completed", Toast.LENGTH_LONG).show();
						}

					}
				}
			}

			private void speak2() {
				String txt = " you must enter an amount null values are not acceptable";

				tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);


			}

			private void speak1() {
				String txt = " you enterd an Invalid bid amount. Bid amount must be greater than the previous amount make sure while sending amount";

				tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);


			}

			private void speak() {
				String txt = edtxttosent.getText().toString();
				tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);
			}

//			@Override
//		protected void onDestroy() {
//			if (tts != null) {
//					tts.stop();
//				tts.shutdown();
//				}
//				super.onDestroy();
//		}
		});



		ad=new Runnable() {
			@Override
			public void run() {
				SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
				String ip=sh.getString("ip", "");
				final String use=sh.getString("user","");
				String url = "http://" + ip + ":5000/view_auction_data";
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
									String ts=   jsonObj.getString("ts");
									if(ts.equalsIgnoreCase("ok"))
									{
										String flag=jsonObj.getString("flag");
										String time=jsonObj.getString("time");
//											Toast.makeText(getApplicationContext(),time,Toast.LENGTH_LONG).show();

										timer.setText(time);
										if(flag.equalsIgnoreCase("1")) {

											b1.setVisibility(View.INVISIBLE);
											b2.setVisibility(View.INVISIBLE);
											b3.setVisibility(View.INVISIBLE);

										}
										else if(flag.equalsIgnoreCase("2")) {
											if(time.equalsIgnoreCase("30"))
											{
												first();

											}
											b1.setVisibility(View.VISIBLE);
											b2.setVisibility(View.INVISIBLE);
											b3.setVisibility(View.INVISIBLE);


										}
										else if(flag.equalsIgnoreCase("3")) {
											if(time.equalsIgnoreCase("30"))
											{
												second();

											}
											b1.setVisibility(View.VISIBLE);
											b2.setVisibility(View.VISIBLE);
											b3.setVisibility(View.INVISIBLE);
										}
										else if(flag.equalsIgnoreCase("4")) {
											String st=jsonObj.getString("s");
											if(st.equalsIgnoreCase("na")) {
												if (compflag == 0) {
													b1.setVisibility(View.VISIBLE);
													b2.setVisibility(View.VISIBLE);
													b3.setVisibility(View.VISIBLE);
													compflag = 1;
													sflag = 1;
													if (time.equalsIgnoreCase("0")) {
//														third();
													}

//													update
													String url2;
													RequestQueue queue = Volley.newRequestQueue(auctiontest.this);
													url2 = "http://" + sh.getString("ip", "") + ":5000/updatebid";

													// Request a string response from the provided URL.
													StringRequest stringRequest = new StringRequest(Request.Method.POST, url2, new Response.Listener<String>() {
														@Override
														public void onResponse(String response) {
															// Display the response string.

															Log.d("+++++++++++++++++", response);

															JSONObject obj = null;
															try {
																obj = new JSONObject(response);

																if (obj.getString("task").equalsIgnoreCase("success")) {
																	compflg = 1;
																} else {
																	compflg = 0;
																}
															} catch (JSONException e) {
																e.printStackTrace();
															}

														}
													}, new Response.ErrorListener() {
														@Override
														public void onErrorResponse(VolleyError error) {


															Toast.makeText(getApplicationContext(), "Error" + error, Toast.LENGTH_LONG).show();
														}
													}) {
														@Override
														protected Map<String, String> getParams() {
															Map<String, String> params = new HashMap<String, String>();
															params.put("lastid", lastid);
															params.put("pid", getIntent().getStringExtra("pid"));
															return params;
														}
													};
													queue.add(stringRequest);


												} else {
													b1.setVisibility(View.VISIBLE);
													b2.setVisibility(View.VISIBLE);
													b3.setVisibility(View.VISIBLE);
												}
											}
											else
											{
												compflag = 1;

												sflag = 1;
//												Toast.makeText(auctiontest.this, "+++"+st+" check status ==========", Toast.LENGTH_SHORT).show();
												if(st.equalsIgnoreCase("sold")) {compflag=1;

													compflg=1;
												}
												else
												{compflag=0;
													compflg=0;}
											}
										}

									}



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

											date[i]=jsob.getString("date");
											msg[i]=jsob.getString("message");
											fid[i]=jsob.getString("fromid");
											mid[i]=jsob.getString("msgid");

											lastid=mid[i];

											if(!fid[i].equalsIgnoreCase(sh.getString("lid","")))
											{
												ChatMessage	message1 = new ChatMessage();
												message1.setUsername(jsob.getString("name"));
												message1.setMessage(msg[i]);
												message1.setTime(date[i]);
												message1.setIncomingMessage(true);
												adapterMessages.add(message1);
											}
											else
											{
												ChatMessage	message = new ChatMessage();
												message.setUsername("Me");
												message.setMessage(msg[i]);
												message.setTime(date[i]);
												message.setIncomingMessage(false);
												adapterMessages.add(message);
											}
											listMessages.setAdapter(adapterMessages);

										}
										if(compflag==1)
										{
//											if(final_status==0){
//												final_status=1;
//												b1.setVisibility(View.VISIBLE);
//												b2.setVisibility(View.VISIBLE);
//												b3.setVisibility(View.VISIBLE);
//											sflag=0;
//											if(compflg==1) {
//												ChatMessage message = new ChatMessage();
//												message.setUsername("ADMIN");
//												message.setMessage("Auction Completed");
//												message.setTime("");
//												message.setIncomingMessage(false);
//												adapterMessages.add(message);
//												listMessages.setAdapter(adapterMessages);
//											}
//											else
//											{
//												ChatMessage message = new ChatMessage();
//												message.setUsername("ADMIN");
//												message.setMessage("Auction Canceled reserve does not match");
//												message.setTime("");
//												message.setIncomingMessage(false);
//												adapterMessages.add(message);
//												listMessages.setAdapter(adapterMessages);
//
//											}
//											}
										}
									}
									if(sflag==1)
									{
//										sflag=0;
//										ChatMessage	message = new ChatMessage();
//										message.setUsername("ADMIN");
//										message.setMessage("Auction Completed");
//										message.setTime("");
//										message.setIncomingMessage(false);
//										adapterMessages.add(message);
//										listMessages.setAdapter(adapterMessages);


										if(final_status==0) {
//											textview hide
											final_status=1;

												b1.setVisibility(View.VISIBLE);
												b2.setVisibility(View.VISIBLE);
												b3.setVisibility(View.VISIBLE);
											if (compflg == 1) {
												third();
												ChatMessage message = new ChatMessage();
												message.setUsername("ADMIN");
												message.setMessage("Auction Completed");
												message.setTime("");
												message.setIncomingMessage(false);
												adapterMessages.add(message);
												listMessages.setAdapter(adapterMessages);
											} else {
												cancel();
												ChatMessage message = new ChatMessage();
												message.setUsername("ADMIN");
												message.setMessage("Auction Canceled reserve does not match");
												message.setTime("");
												message.setIncomingMessage(false);
												adapterMessages.add(message);
												listMessages.setAdapter(adapterMessages);

											}
										}

									}
								}
								catch (Exception e) {
//			                        	 Toast.makeText(getApplicationContext(),"eeeee"+e.toString(),Toast.LENGTH_LONG).show();
								}
							}

							private void cancel() {
								String txt = "Auction canceled reserve price doesnot match";
								tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);

								edtxttosent.setVisibility(View.INVISIBLE);
								bt1.setVisibility(View.INVISIBLE);

								img.setVisibility(View.INVISIBLE);



//								ImageView img = findViewById(R.id.img1);
								if (img != null) {
									Glide.with(img.getContext())
											.load(R.drawable.ac3)
											.into(img);
								}
								new Handler().postDelayed(new Runnable() {
									@Override
									public void run() {
										img.setImageDrawable(null);
									}
								}, 3800);
							}


							private void first() {
								String txt = "First Call";
								tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);


								ImageView img = findViewById(R.id.img1);
								if (img != null) {
									Glide.with(img.getContext())
											.load(R.drawable.ac2)
											.into(img);
								}

								new Handler().postDelayed(new Runnable() {
									@Override
									public void run() {
										img.setImageDrawable(null);
									}
								}, 3000);

							}


							private void second() {
								String txt = "Second Call";
								tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);

								ImageView img = findViewById(R.id.img1);
								if (img != null) {
									Glide.with(img.getContext())
											.load(R.drawable.ac2)
											.into(img);
								}


								new Handler().postDelayed(new Runnable() {
									@Override
									public void run() {
										img.setImageDrawable(null);
									}
								}, 3000);
							}



							private void third() {
								String txt = "Sold";
								tts.speak(txt, TextToSpeech.QUEUE_FLUSH, null);

								edtxttosent.setVisibility(View.INVISIBLE);
								bt1.setVisibility(View.INVISIBLE);

								img.setVisibility(View.INVISIBLE);



								ImageView img = findViewById(R.id.img1);
								if (img != null) {
									Glide.with(img.getContext())
											.load(R.drawable.ac3)
											.into(img);
								}
								new Handler().postDelayed(new Runnable() {
									@Override
									public void run() {
										img.setImageDrawable(null);
									}
								}, 3800);
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


						params.put("lastmsgid", lastid);
						params.put("pid",getIntent().getStringExtra("pid"));
						return params;
					}
				};
				requestQueue.add(postRequest);
				hnd.postDelayed(ad, 1000);
			}
		};
		hnd.post(ad);
	}

	@SuppressLint("NewApi")
	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
//		hnd.removeCallbacks(ad);
//		SharedPreferences sh=PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//		Editor edt= sh.edit();
//    	edt.putString("lastid", "0");
//    	edt.commit();
//    	lastid="0";


//		background location service
		hnd.removeCallbacks(ad);
		hnd = null;

		super.onBackPressed();
	}

	@Override
	public void onClick(View view) {
		edtxttosent.setText("");
		edtxttosent.getText().clear();
		edtxttosent.clearFocus();
	}
}









