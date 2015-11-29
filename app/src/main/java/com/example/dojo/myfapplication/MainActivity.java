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
    private  String sword = "3000E20020648118011816206C22";
    private  String hero = "3000E2002064811801200810C10D";
    Quest activeQuest = new Quest(hero, sword, "kleiner", "grosser");
    ImageView imageWrapper;



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
        imageWrapper = (ImageView)findViewById(R.id.sword);
        status.setMovementMethod(new ScrollingMovementMethod());
        //  imageWrapper.draw(new Canvas());
      //  imageWrapper.setBackground(bmp);
    //    surfaceView.draw(new Canvas().drawColor(2));


    /*    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
        */
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
                activeQuest.setAndEvalArtefactReserved(nextMsg);
                if (activeQuest.setAndEvalArtefactActivated(nextMsg)) {
                    imageWrapper.setBackground(swordBmp);
                    pointsInt += 1000;
                }
            }catch(Exception ex) {
                ex.printStackTrace();
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
                Log.e(TAG, "mainAcTread", e);
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



    public void handOverRefreshedList(List<String> uniqueRFidPresentList) {
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
