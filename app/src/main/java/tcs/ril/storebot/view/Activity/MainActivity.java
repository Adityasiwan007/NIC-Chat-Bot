package tcs.ril.storebot.view.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ActivityOptions;
import android.content.ActivityNotFoundException;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.media.session.MediaSession;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

import android.support.constraint.ConstraintLayout;
import android.support.constraint.ConstraintSet;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.util.Log;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import android.widget.TextView;
import android.widget.Toast;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import tcs.ril.storebot.Network.Util;
import tcs.ril.storebot.R;
import tcs.ril.storebot.model.Control;
import tcs.ril.storebot.model.Object;
import tcs.ril.storebot.model.OrderHistory;
import tcs.ril.storebot.model.PreferenceManager;
import tcs.ril.storebot.model.Response;
import tcs.ril.storebot.model.SendQuery;
import tcs.ril.storebot.model.UserMessage;
import tcs.ril.storebot.model.UsersWithPendingPickup;
import tcs.ril.storebot.view.Adapter.HideButtonsInterface;
import tcs.ril.storebot.view.Adapter.MessageListAdapter;
import tcs.ril.storebot.view.Adapter.ReceivedBtnHolder;
import tcs.ril.storebot.view.Fragment.MessageFragment;
import tcs.ril.storebot.view.Fragment.VoiceRecognizerDialogFragment;
import tcs.ril.storebot.view.Fragment.VoiceRecognizerInterface;
import tcs.ril.storebot.view.Other.RealPathUtil;
import tcs.ril.storebot.view.Other.Time;

import static android.support.constraint.Constraints.TAG;
import static tcs.ril.storebot.view.Adapter.ListButtonAdapter.buttons;

import static tcs.ril.storebot.view.Adapter.ViewListButtonAdapter.viewList;


public  class MainActivity extends AppCompatActivity implements VoiceRecognizerInterface {
    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private static final int REQUEST_CAMERA_CODE = 777;
    private static final int EXTERNAL_READ_REQUEST = 666;
    private static final java.lang.Object TAG = "Main Activity";
    private int WRITE_STATUS = 0;
    ImageView chatbotImage;
    ByteArrayOutputStream bytes;
    private String LOGTAG = "Main Activity";
    private String userInput, profileURL;
    static final int CAM_REQUEST = 1;
    public static final int QR_REQUEST = 0x0000c0de;
    public static String url = "";
    private ImageButton qrButton, cameraButton, galleryBtn;

    private RecyclerView mRecyclerView;
    private LinearLayoutManager mLayoutManager;
    private Button mSpeakBtn;
    Button send;
    Button popup;
    EditText userInputText;
    static String receivedMessage;
    List<Control> controlList;
    List<UserMessage> mylist;
    MessageListAdapter messageListAdapter = null;
    private final int PICK_IMAGE_REQUEST = 2;
    Time time = new Time();
    LinearLayout mRevealView;
    boolean hidden = true;
    private PreferenceManager preferenceManager;
    MediaSession _mediaSession;
    MediaSession.Token _mediaSessionToken;
    private ReceivedBtnHolder receivedButtonHolder;
    MessageFragment messageFragment;
    public static TextToSpeech t1;
    LinearLayout messageLayout;
    TextView messageTextView;
    static boolean messageHidden=true;
    ConstraintLayout mainActivityLayout;
    static int unicode = 0x1F60A;
    static String smily= new String(Character.toChars(unicode));
    static String boldText="<b>Nick"+"</b>";

    public static String mes;
    VoiceRecognizerDialogFragment languageDialogFragment;
    private ConstraintLayout bottomLayout;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //this line sets receiver priority
        SpannableStringBuilder str = new SpannableStringBuilder("Nick");
        str.setSpan(new android.text.style.StyleSpan(android.graphics.Typeface.BOLD), 0, str.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        mes="Hi "+smily+" My name is <b>Nick</b> (Nucleus Intelligent Chatbot Kit). I'm a chatbot built by Nucleus team to demonstrate the power of chatbots for store associates.";
        messageLayout=findViewById(R.id.fragment_container);
        messageLayout.setVisibility(View.GONE);
        languageDialogFragment    = new VoiceRecognizerDialogFragment(MainActivity.this,MainActivity.this);

        _mediaSession = new MediaSession(getApplicationContext(), Settings.Global.NAME + "." + TAG);

        if (_mediaSession == null) {
            Log.d((String) TAG, "initMediaSession: _mediaSession = null");
            return;
        }


        _mediaSessionToken = _mediaSession.getSessionToken();

        _mediaSession.setCallback(new MediaSession.Callback() {
            @Override
            public void onPlay() {
                Log.d(LOGTAG, "onPlay called (media button pressed)");

            startVoiceRecog();
               // languageDialogFragment.startListening();
                super.onPlay();

            }

            @Override
            public void onPause() {
                Log.d(LOGTAG, "onPause called (media button pressed)");
                super.onPause();
            }

            @Override
            public void onStop() {

                startVoiceRecog();
                Log.d(LOGTAG, "onStop called (media button pressed)");

            }

            @Override
            public boolean onMediaButtonEvent( @NonNull Intent mediaButtonIntent) {
                return super.onMediaButtonEvent(mediaButtonIntent);
            }



        });

        PlaybackState state = new PlaybackState.Builder()
                .setActions(PlaybackState.ACTION_PLAY)
                .setState(PlaybackState.STATE_STOPPED, PlaybackState.PLAYBACK_POSITION_UNKNOWN, 0)
                .build();

        _mediaSession.setFlags(MediaSession.FLAG_HANDLES_MEDIA_BUTTONS | MediaSession.FLAG_HANDLES_TRANSPORT_CONTROLS);
        _mediaSession.setPlaybackState(state);
        _mediaSession.setActive(true);


        // Create a MediaSessionCompat
        messageTextView=findViewById(R.id.message_textview);
        mainActivityLayout=findViewById(R.id.activity_main);
        final Toolbar toolbar = findViewById(R.id.chat_toolbar);
        setSupportActionBar(toolbar);
        preferenceManager = new PreferenceManager(MainActivity.this);
        profileURL = preferenceManager.getProfileUrl(PreferenceManager.PROFILE_URL);
        LoginActivity.ROOT_URL = "http://" + preferenceManager.getIp(PreferenceManager.IP) ;
        Util.setAccessToken(preferenceManager.getAccessToken(PreferenceManager.ACCESS_TOKEN));
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        send = findViewById(R.id.send);
        popup = findViewById(R.id.btn_popup);
        userInputText = findViewById(R.id.et_search_box);
        mRecyclerView = findViewById(R.id.rv_chat);
        mSpeakBtn = findViewById(R.id.mike_chat);
        mylist = new ArrayList<>();
        bottomLayout=findViewById(R.id.bottom_layout);
        mRevealView = findViewById(R.id.reveal_items);
        chatbotImage=findViewById(R.id.chatbot_main);
        chatbotImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                messageFragment =new MessageFragment();
//                FragmentManager fragmentManager=getSupportFragmentManager();
//                fragmentManager.beginTransaction()
//                        .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
//                        .replace(R.id.fragment_container,messageFragment)
//                        .addToBackStack(null)
//                        .commit();

                if(messageHidden){
                    messageTextView.setText(Html.fromHtml(mes));
                    messageHidden=false;
                    ConstraintSet set=new ConstraintSet();
                    set.clone(mainActivityLayout);
                    set.connect(messageLayout.getId(),ConstraintSet.TOP,toolbar.getId(),ConstraintSet.BOTTOM);
                    set.connect(mRecyclerView.getId(),ConstraintSet.TOP,messageLayout.getId(),ConstraintSet.BOTTOM,12);
                    set.connect(mRecyclerView.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT);
                    set.connect(mRecyclerView.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT);
                    set.connect(mRecyclerView.getId(),ConstraintSet.BOTTOM,bottomLayout.getId(),ConstraintSet.TOP);
                    set.applyTo(mainActivityLayout);
                    messageLayout.setVisibility(View.VISIBLE);
                    t1.stop();
                    Log.d(LOGTAG, "onClick: "+messageLayout.getVisibility());

                }else{
                    ConstraintSet set=new ConstraintSet();
                    set.clone(mainActivityLayout);
                    set.connect(messageLayout.getId(),ConstraintSet.TOP,toolbar.getId(),ConstraintSet.BOTTOM);
                    set.connect(mRecyclerView.getId(),ConstraintSet.TOP,toolbar.getId(),ConstraintSet.BOTTOM,10);
                    set.connect(mRecyclerView.getId(),ConstraintSet.LEFT,ConstraintSet.PARENT_ID,ConstraintSet.LEFT);
                    set.connect(mRecyclerView.getId(),ConstraintSet.RIGHT,ConstraintSet.PARENT_ID,ConstraintSet.RIGHT);
                    set.connect(mRecyclerView.getId(),ConstraintSet.BOTTOM,bottomLayout.getId(),ConstraintSet.TOP);
                    set.applyTo(mainActivityLayout);
                    messageLayout.setVisibility(View.GONE);
                    messageHidden=true;
                    t1.stop();
                }

            }
        });
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               // startVoiceInput();
//                if (!hidden) {
//                    mRevealView.setVisibility(View.GONE);
//                    hidden = true;
//                }
            startVoiceRecog();

            }
        });
        mRecyclerView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hidden) {
                    mRevealView.setVisibility(View.GONE);
                    hidden = true;
                }
            }
        });


        mRevealView.setVisibility(View.GONE);
        // use this setting to improve performance if you know that changes
        // in content do not change the layout size of the RecyclerView
        mRecyclerView.setHasFixedSize(true);

        // use a linear layout manager
        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        messageListAdapter = new MessageListAdapter(MainActivity.this, mylist);
        mRecyclerView.setAdapter(messageListAdapter);
        final ViewGroup viewGroup = (ViewGroup) ((ViewGroup) this
                .findViewById(android.R.id.content)).getChildAt(0);
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hidden) {
                    mRevealView.setVisibility(View.GONE);
                    hidden = true;
                }
            }
        });
        /*On click of send button the utterance is get from the the edit text box and send to the server,
         * at the same time the UI is being refreshed using the message list adapter which the adapter for the recyler view rv_chat*/
        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                send();
                if (!hidden) {
                    mRevealView.setVisibility(View.GONE);
                    hidden = true;
                }
            }
        });
       // messageListAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(messageListAdapter.getItemCount() - 1);
        userInputText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!hidden) {
                    mRevealView.setVisibility(View.GONE);
                    hidden = true;
                }
            }
        });
        /*Getting the popup to select options like gallery,camera and QR code scanner*/
        popup.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if (hidden) {

                    // to show the layout when icon is tapped
                    mRevealView.setVisibility(View.VISIBLE);
                    hidden = false;

                } else {
                    mRevealView.setVisibility(View.GONE);
                    hidden = true;
                }
            }
        });
        qrButton = findViewById(R.id.btn_qr);
        cameraButton = findViewById(R.id.btn_cam);
        galleryBtn = findViewById(R.id.galley_btn);

        /*Call for different fucnctions from the popup*/
        qrButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                QRScanIntent(view);
            }
        });

        cameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED
                        ) {
                    requestCameraPermission();
                } else {
                    Intent camera_intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(camera_intent, CAM_REQUEST);
                }
            }
        });

        galleryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this,
                        Manifest.permission.CAMERA)
                        != PackageManager.PERMISSION_GRANTED) {
                    requestGalleryPermission();
                } else {
                    galleryIntent();
                }
            }
        });

        UserMessage firstMessage = new UserMessage();
        /*This call is used for getting the initial message  in the chat from the server*/
        getDataFromServer("vengariamleviosaspectropetronussectumseprastupify");
       // messageListAdapter.notifyDataSetChanged();
        mRecyclerView.smoothScrollToPosition(messageListAdapter.getItemCount());


        mRecyclerView.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (bottom < oldBottom){ scrollToBottom();}
            }
        });


    }


    public void startVoiceRecog()
    {
        if(languageDialogFragment.isVisible())
        {
            languageDialogFragment.dismiss();
        }
        FragmentManager fragmentManager = getSupportFragmentManager();
        if (fragmentManager != null && fragmentManager.findFragmentByTag("dialogVoiceRecognizer") == null && !isFinishing()) {

            languageDialogFragment.show(fragmentManager, "dialogVoiceRecognizer");
        }

    }

    private void scrollToBottom() {
        mLayoutManager.smoothScrollToPosition(mRecyclerView, null, messageListAdapter.getItemCount());
        t1.stop();
        t1.shutdown();
    }

    HideButtonsInterface ci;

    public HideButtonsInterface getCi() {
        return ci;
    }

    public void setCi(HideButtonsInterface ci) {
        this.ci = ci;
    }

    @Override
    public void onBackPressed() {
        if (!hidden) {
            mRevealView.setVisibility(View.GONE);
            hidden = true;
            return;
        }
        if (hidden) {
            finish();
        }

    }

    /*Inflating the triple dots menu to have the options to go the profile section*/
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    /*Handling the events of the triple dots menu in the activity*/
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.profile:
                Intent i = new Intent(MainActivity.this, ProfileActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(i, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                }
                return true;
            case R.id.help:
                Intent intent = new Intent(MainActivity.this, HelpActivity.class);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    //Request for reading the external storage in context of the selecting the image form
    public void requestGalleryPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {

        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE}, EXTERNAL_READ_REQUEST);
        }
    }

    //Getting Permission for cameraButton if not granted
    public void requestCameraPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.CAMERA)) {

        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]
                    {Manifest.permission.CAMERA,
                            Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_CAMERA_CODE);
        }
    }

    /*Handling the grant of the required permissions*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        boolean cameraAccepted, writeAccepted;

        if (requestCode == REQUEST_CAMERA_CODE) {
            cameraAccepted = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
            writeAccepted = grantResults.length > 0 && grantResults[1] == PackageManager.PERMISSION_GRANTED;

            if (cameraAccepted && writeAccepted) {
                Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(cameraIntent, CAM_REQUEST);
            }
        }

        if (requestCode == EXTERNAL_READ_REQUEST) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                galleryIntent();
            }
        }
    }

    /*This send function handles the sending of the utterance to the server and updating the UI at the same time*/
    public void send() {
        t1.stop();
        t1.shutdown();
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.UK);
                }
            }
        });

        Log.d(LOGTAG, "onClick: " + userInput);
        String value = userInputText.getText().toString();

        buttons.clear();
        viewList.clear();
        messageListAdapter.notifyDataSetChanged();
        if (value.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter text", Toast.LENGTH_SHORT).show();
            userInputText.getText().clear();
        } else {
            userInput = userInputText.getText().toString();
            /*Getting the response for the utterance using the function getDataFromServer*/
            getDataFromServer(userInput);
            UserMessage sendMessage = new UserMessage();
            sendMessage.setTime(time.getTime());
            sendMessage.setTag(0);
            sendMessage.setProfileURL(profileURL);
            sendMessage.setMessage(userInput);
            mylist.add(sendMessage);
           // messageListAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(messageListAdapter.getItemCount());
            userInputText.getText().clear();
        }
    }

    public void send1(String value, String position) {
        Log.d(LOGTAG, "send: " + value);
        //messageListAdapter.getCi().Hide();
        t1.stop();
        t1.shutdown();
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }


        });

        if (value.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter text", Toast.LENGTH_SHORT).show();
        } else {
            userInput = value;
            Log.d(LOGTAG, "send1: "+userInput);
            String query1 = position;
            buttons.clear();
            viewList.clear();
            messageListAdapter.notifyDataSetChanged();
            /*Getting the response for the utterance using the function getDataFromServer*/
            getDataFromServer(query1);
            UserMessage sendMessage = new UserMessage();
            sendMessage.setTime(time.getTime());
            sendMessage.setTag(0);
            sendMessage.setProfileURL(profileURL);
            sendMessage.setMessage(userInput);
            mylist.add(sendMessage);
           // messageListAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(messageListAdapter.getItemCount());
        }
    }

    public void sendFromVoice(String value) {
        Log.d(LOGTAG, "send: " + value);
        //messageListAdapter.getCi().Hide();

        if (value.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Enter text", Toast.LENGTH_SHORT).show();
        } else {
            userInput = value;
            Log.d(LOGTAG, "send1: "+userInput);
            buttons.clear();
            viewList.clear();
            messageListAdapter.notifyDataSetChanged();
            /*Getting the response for the utterance using the function getDataFromServer*/
            getDataFromServer(value);
            UserMessage sendMessage = new UserMessage();
            sendMessage.setTime(time.getTime());
            sendMessage.setTag(0);
            sendMessage.setProfileURL(profileURL);
            sendMessage.setMessage(userInput);
            mylist.add(sendMessage);
            // messageListAdapter.notifyDataSetChanged();
            mRecyclerView.smoothScrollToPosition(messageListAdapter.getItemCount());
        }
    }

    //   Function to get data from the server
    private void getDataFromServer(String query) {
        t1=new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if(status != TextToSpeech.ERROR) {
                    t1.setLanguage(Locale.ENGLISH);
                }
            }


        });
        Log.d(LOGTAG, "getDataFromServer: ");
        SendQuery sendQuery = new SendQuery(query);
        Log.d(LOGTAG, "getDataFromServer: " + sendQuery.getUtterance());
        Util.getUserService().chatResponse(Util.getAccessToken(), sendQuery).enqueue(new Callback<Response>() {
            @Override
            public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {
                if (response.isSuccessful()) {
                    Response response1 = response.body();
                    if (response1.isSuccess()) {
                        Object chatResponse;
                        chatResponse = response1.getObject();
                        receivedMessage = chatResponse.getDisplayText();
                        UserMessage obj = new UserMessage();
                        if(chatResponse.getViewBtn().getText().equals("") && chatResponse.getViewBtn().getUsersWithPendingPickup().isEmpty()){
                        if (chatResponse.getImages().size() == 0) {
                            controlList = chatResponse.getControls();
                            if (controlList.size() != 0) {
                                List<String> tempList = new ArrayList<>();
                                List<String> tempListValue = new ArrayList<>();
                                Log.d(LOGTAG, "onResponse: " + controlList.size());
                                for (int i = 0; i < controlList.size(); i++) {
                                    tempList.add(controlList.get(i).getText());
                                    tempListValue.add(controlList.get(i).getValue());
                                }
                                obj.setMessage(receivedMessage);
                                obj.setTime(time.getTime());
                                obj.setBtnText(tempList);
                                obj.setBtnValue(tempListValue);
                                obj.setBtnTag(1);
                                obj.setTag(1);
                                obj.setImageTag(9999);
                                mylist.add(obj);
                                mRecyclerView.scrollToPosition(messageListAdapter.getItemCount() - 1);
                            } else {
                                obj.setMessage(receivedMessage);
                                obj.setTag(1);
                                obj.setImageTag(9999);
                                obj.setBtnTag(9999);
                                obj.setTime(time.getTime());
                                mylist.add(obj);
                               // messageListAdapter.notifyDataSetChanged();
                                t1.speak(receivedMessage,TextToSpeech.QUEUE_FLUSH,null);
                                mRecyclerView.scrollToPosition(messageListAdapter.getItemCount() - 1);
                            }
                        } else if (chatResponse.getImages().size() != 0) {
                            obj.setURL(tcs.ril.storebot.view.Activity.LoginActivity.ROOT_URL + "/" + chatResponse.getImages().get(0).getUrl());
                            obj.setTag(9999);
                            obj.setImageTag(1);
                            obj.setBtnTag(9999);
                            obj.setMessage(receivedMessage);
                            obj.setTime(time.getTime());
                            mylist.add(obj);
                            t1.speak(receivedMessage,TextToSpeech.QUEUE_FLUSH,null);
                           // messageListAdapter.notifyDataSetChanged();
                            mRecyclerView.scrollToPosition(messageListAdapter.getItemCount() - 1);
                        }
                        }
                        else{
                            if(chatResponse.getAuxiliary().equals("")){
                                if(chatResponse.getControls()!=null){
                                    List<String> tempList = new ArrayList<>();
                                    List<String> tempListValue = new ArrayList<>();
                                    Log.d(LOGTAG, "onResponse: " + controlList.size());
                                    for (int i = 0; i < controlList.size(); i++) {
                                        tempList.add(controlList.get(i).getText());
                                        tempListValue.add(controlList.get(i).getValue());
                                    }
                                    obj.setAuxTag(0);
                                    obj.setAuxBtnTag(1);
                                    obj.setBtnText(tempList);
                                    obj.setBtnValue(tempListValue);
                                }
                            }
                            if(!chatResponse.getAuxiliary().equals("")){
                                if(chatResponse.getControls()!=null){
                                    List<String> tempList = new ArrayList<>();
                                    List<String> tempListValue = new ArrayList<>();
                                    Log.d(LOGTAG, "onResponse: " + controlList.size());
                                    for (int i = 0; i < controlList.size(); i++) {
                                        tempList.add(controlList.get(i).getText());
                                        tempListValue.add(controlList.get(i).getValue());
                                    }
                                    obj.setAuxMessage(chatResponse.getAuxiliary());
                                    obj.setBtnValue(tempListValue);
                                    obj.setBtnText(tempList);
                                    obj.setAuxTag(1);
                                    obj.setAuxBtnTag(1);
                                }
                            }
                            if(!chatResponse.getViewBtn().getText().equals(""))
                            {
                                if(chatResponse.getImages().size()!=0)
                                {
                                    obj.setURL(tcs.ril.storebot.view.Activity.LoginActivity.ROOT_URL + "/" + chatResponse.getImages().get(0).getUrl());
                                }
                                obj.setBtnMessageText(chatResponse.getDisplayText());
                                obj.setMessage(chatResponse.getViewBtn().getText());
                                obj.setViewTag(1);
                                obj.setTime(time.getTime());
                                obj.setTag(9999);
                                obj.setImageTag(9999);
                                mylist.add(obj);
                                mRecyclerView.scrollToPosition(messageListAdapter.getItemCount() -1);
                            }
                            else{
                                obj.setBtnMessageText(chatResponse.getDisplayText());
                                Log.d("MainActivity", "onResponse: 1"+chatResponse.getViewBtn().getUsersWithPendingPickup());
                                obj.setPendingPickups(chatResponse.getViewBtn().getUsersWithPendingPickup());
                                Map<String , List<OrderHistory>> pendingPickup=new HashMap<>();
                                List<String> name=new ArrayList<>();
                                List<List<OrderHistory>> orderHistoryList =new ArrayList<>();
                                int pos=0;
                                String tempName;
                                for(UsersWithPendingPickup p : chatResponse.getViewBtn().getUsersWithPendingPickup()){
                                    tempName=p.getFirstName()+" "+p.getLastName();
                                    pendingPickup.put(tempName,p.getOrderHistory());
                                }
                                Log.d(LOGTAG, "onResponse: "+pendingPickup);
                                List<String> viewButtons=new ArrayList<>();
                                for ( Map.Entry<String,List<OrderHistory>> e :pendingPickup.entrySet() ) {
                                    name.add(e.getKey());
                                    orderHistoryList.add(e.getValue());
                                    viewButtons.add("#"+orderHistoryList.get(pos).get(0).getTranscationID()+" - "+name.get(pos)+" ");
                                    pos++;
                                }
                                Log.d(LOGTAG, "onResponse: "+viewButtons);
                                obj.setViewBtnValue(pendingPickup);
                                obj.setViewBtnTex(viewButtons);
                                obj.setViewTag(1);
                                obj.setTime(time.getTime());
                                obj.setTag(9999);
                                obj.setImageTag(9999);
                                mylist.add(obj);
                                mRecyclerView.scrollToPosition(messageListAdapter.getItemCount() -1);
                            }
                        }
                    } else {
                        Log.d(LOGTAG, "onResponse: Nothing received");
                    }
                } else
                    Log.d(LOGTAG, "onResponse: Error Occurred");
            }

            @Override
            public void onFailure(Call<Response> call, Throwable t) {
                Toast.makeText(MainActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    /*Handling the voice input*/
    public void startVoiceInput() {
        Log.d(LOGTAG, "startVoiceInput: ");
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }

    //    Function to call the respective intents for audio to text,camera and barcode scan and selecting image from gallery, the intents are being selected based on the request code
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case REQ_CODE_SPEECH_INPUT:
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    userInputText.setText(result.get(0));
                    send();
                    userInputText.setText(null);
                    break;
                case QR_REQUEST:
                    onQRResult(requestCode, resultCode, data);
                    break;
                case CAM_REQUEST:
                    onCaptureImageResult(data);
                    break;
                case PICK_IMAGE_REQUEST:
                    onSelectFromGalleryResult(data);
                    break;
            }
        }
    }

    public void QRScanIntent(View v) {
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
        integrator.setPrompt("Scan");
        integrator.setCameraId(0);
        integrator.setBeepEnabled(false);
        integrator.setBarcodeImageEnabled(false);
        integrator.initiateScan();

    }

    private void galleryIntent() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    /*Handling the data in the camera intent*/
    private void onCaptureImageResult(Intent data) {
        int permissionCheck = ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA);
//        Log.d(TAG, "onCaptureImageResult: Permission for camera= " + permissionCheck);
//        Log.d(TAG, "onCaptureImageResult: Permission for access storage= " + WRITE_STATUS);

        saveImage(data);


    }

    /*Saving the image clicked by the Camera*/
    public void saveImage(Intent data) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        File destination = new File(Environment.getExternalStorageDirectory() + "", System.currentTimeMillis() + ".jpeg");
        Toast.makeText(this, "" + destination.toURI().toString(), Toast.LENGTH_SHORT).show();
        FileOutputStream fo;
        try {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       // messageListAdapter.getCi().Hide();
        UserMessage userMessage = new UserMessage();
        userMessage.setURL(destination.toURI().toString());
        userMessage.setTag(9999);
        userMessage.setBtnTag(9999);
        userMessage.setProfileURL(profileURL);
        userMessage.setImageTag(0);
        userMessage.setTime(time.getTime());

        mRevealView.setVisibility(View.GONE);
        mylist.add(userMessage);
        messageListAdapter.notifyDataSetChanged();
        mRecyclerView.scrollToPosition(messageListAdapter.getItemCount() - 1);
    }


    //handling the qrButton CODE Scanner, Toasting the data stored in the qrButton
    private void onQRResult(int requestCode, int resultCode, Intent data) {
        Log.d(LOGTAG, "onQR: Inside Function");
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Log.d(LOGTAG, "onQR: " + result.getContents());
                Toast.makeText(this, "You cancelled the Scanning", Toast.LENGTH_LONG).show();
            } else {
                Log.d(LOGTAG, "onQR: " + result.getContents());
                Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
            }
        } else {
            Log.d(LOGTAG, "onQR: in Else part");
            super.onActivityResult(requestCode, resultCode, data);
        }
    }


    //Getting the image from gallery and displaying it in the Recycler View
    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data) {
        Uri imageUri = null;
        Bitmap bitmap = null;
        try {
            imageUri = data.getData();
            bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), data.getData());
        } catch (IOException e) {
            e.printStackTrace();
        }
        String path = imageUri.getEncodedPath();
        String realPath;
        // SDK < API11
        if (Build.VERSION.SDK_INT < 11)
            realPath = RealPathUtil.getRealPathFromURI_BelowAPI11(this, data.getData());

            // SDK >= 11 && SDK < 19
        else if (Build.VERSION.SDK_INT < 19)
            realPath = RealPathUtil.getRealPathFromURI_API11to18(this, data.getData());

            // SDK > 19 (Android 4.4)
        else
            realPath = RealPathUtil.getRealPathFromURI_API19(this, data.getData());
        File imgFile = new File(realPath);
        UserMessage message = new UserMessage();
        message.setURL(Uri.fromFile(imgFile).toString());
        message.setImageTag(0);
        message.setTag(9999);
        message.setProfileURL(profileURL);
        message.setTime(time.getTime());
        mRevealView.setVisibility(View.GONE);

        mylist.add(message);
        messageListAdapter.notifyDataSetChanged();
        mRecyclerView.smoothScrollToPosition(messageListAdapter.getItemCount() - 1);

    }

    @Override
    protected void onPause() {
        super.onPause();
        t1.stop();
        t1.shutdown();
    }


    @Override
    public void spokenText(String spokenText) {
            sendFromVoice(spokenText);
    }
}