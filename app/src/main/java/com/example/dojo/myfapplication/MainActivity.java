package com.example.dojo.myfapplication;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Set;

public class MainActivity extends AppCompatActivity {

    TextView status;
    ArrayList previousPosition;
    ArrayList activatedItems;
    ArrayList idList;
    int test;
    private Set<String> syncedUniqueRfidSet;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        previousPosition = new ArrayList<String>();
        activatedItems = new ArrayList<String>();
        idList = new ArrayList<String>();



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
        status = (TextView)findViewById(R.id.status);
        status.setText(this.syncedUniqueRfidSet.size());
    }

    private void createClientThread () {
        ClientThread clientThread = new ClientThread(this);
        clientThread.start();
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


    public void handOverRefreshedSet(Set<String> syncedUniqueRfidSet) {
        this.syncedUniqueRfidSet = syncedUniqueRfidSet;
    }

}
