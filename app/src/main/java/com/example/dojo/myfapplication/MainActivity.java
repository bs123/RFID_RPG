package com.example.dojo.myfapplication;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.view.SurfaceView;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.toString() ;
    private SurfaceView surfaceView;
    TextView status;
    TextView points;
    ArrayList previousPosition;
    ArrayList activatedItems;
    ArrayList idList;
    int test;
    int pointsInt;
    private List<String> uniqueRFidPresentList = new ArrayList<String>();
    Drawable bmp;
    Drawable swordBmp;
    Drawable axeBmp;
    Drawable bowBmp;
    Drawable clubBmp;
    private  String sword = "3000E20020648118011816206C22";
    private  String hero = "3000E2002064811801200810C10D";
    Quest activeQuest = new Quest(hero, Artefacts.SWORD, "kleiner", "grosser");
    ImageView imageWrapperSword;
    ImageView imageWrapperAxe;
    ImageView imageWrapperBow;
    ImageView imageWrapperClub;
    private boolean swordBonusReceived;
    



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        pointsInt = 0;

        status = (TextView)findViewById(R.id.status);
        points = (TextView)findViewById(R.id.points);

        previousPosition = new ArrayList<String>();
        activatedItems = new ArrayList<String>();
        idList = new ArrayList<String>();
        createClientThread();
        bmp = getDrawable(R.drawable.test);
        swordBmp = getDrawable(R.drawable.sword);
        axeBmp = getDrawable(R.drawable.axe);
        clubBmp = getDrawable(R.drawable.club);
        bowBmp = getDrawable(R.drawable.bow);
        imageWrapperSword = (ImageView)findViewById(R.id.sword);
        imageWrapperBow = (ImageView)findViewById(R.id.bow);
        imageWrapperAxe = (ImageView)findViewById(R.id.axe);
        imageWrapperClub = (ImageView)findViewById(R.id.club);
        status.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //compares current and previous position and saves the item ID in ArrayList activatedItems when they were moved
    public void compare(ArrayList currentPosition) {
        for(int i = 0; i < previousPosition.size(); i++) {
            if(previousPosition.get(i).equals(currentPosition.get(i))) {
                activatedItems.add(currentPosition.get(i));
            }
        }
        previousPosition = currentPosition;
    }

    public void event (ArrayList events) {

    }

    public void refresh (View view) {
        // status.setText("" + this.uniqueRFidPresentList.size());
        Iterator<String> iterator = uniqueRFidPresentList.iterator();

       StringBuilder sb =  new StringBuilder();
        while (iterator.hasNext())
        {
            try {
                String nextMsg = iterator.next();
                sb.append(nextMsg);
                activeQuest.toggleAndEvalArtefactReserved(nextMsg);
        /*        imageWrapperSword.setBackground(swordBmp);
                imageWrapperClub.setBackground(clubBmp);
                imageWrapperBow.setBackground(bowBmp);
                imageWrapperAxe.setBackground(axeBmp);
         */       if (activeQuest.toggleAndEvalArtefactActivated(nextMsg)) {
                    imageWrapperSword.setBackground(swordBmp);
                    if(!activeQuest.getQuestBonusReceived()) {
                        pointsInt += activeQuest.rewardQuestPoints();
                    }
                }

                Log.i(TAG, "Monster killed : " + activeQuest.countAvailableMonsters(nextMsg));


            }catch(Exception ex) {
                Log.e(TAG, "mainAcCuncurrencyProblem", ex);
                break;
            }
        }
        points.setText("Points: " + pointsInt);
        status.setText("DEBUG : FoundAndReserved: " + activeQuest.isArtefactReserved() + " ActivatedQuestFullfilled: " + activeQuest.isArtefactActivated() +  " size: "+ this.uniqueRFidPresentList.size() + " #### " + sb.toString());
    
    }

    private void createClientThread () {
        try {
        ClientThread clientThread = new ClientThread(this);
        clientThread.start();
        } catch (Exception e){
                Log.e(TAG, "mainAcCreateClientThread", e);
            }
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);


    }

    private Canvas onDraw() {
        Canvas canvas = new Canvas();
      //s  canvas.drawBitmap(bmp, (float) 0.0, (float) 0.0, new Paint());
        canvas.drawColor(Color.RED);
        return canvas;
    }



    public synchronized  void handOverRefreshedList(List<String> uniqueRFidPresentList) {
        this.uniqueRFidPresentList = uniqueRFidPresentList;
     /*   final Handler handler = new Handler();
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                Looper.prepare();
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        Looper.prepare();
                    refresh(null);
                    // /    status.setText("");
                    }
                });
            }
        });
        t.start();*/
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                refresh(null);
            }
        });
        System.out.println(" +++++++++++++++++++++++++ update List "  + uniqueRFidPresentList.size());
    }
}
