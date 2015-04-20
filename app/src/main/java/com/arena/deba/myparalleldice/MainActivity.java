package com.arena.deba.myparalleldice;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private FrameLayout die1, die2;
    private Button roll, hold;
    static long scoreMain;
    static long scorePlayer2;
    private long currentMainScore;
    private int reqCode = 1;
    boolean gotOne = false;
    TextView roundTextView;
    TextView usTextView;
    TextView themTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        roll = (Button)findViewById(R.id.button);

        roundTextView = (TextView)findViewById(R.id.round);
        usTextView = (TextView)findViewById(R.id.us);
        themTextView = (TextView)findViewById(R.id.them);

        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });

        //Initialize
        currentMainScore = 0l;
        gotOne = false;

        usTextView.setText("US: " +scoreMain);
        themTextView.setText("THEM: " +scorePlayer2);

        die1 = (FrameLayout)findViewById(R.id.die1);
        die2 = (FrameLayout)findViewById(R.id.die2);

        hold = (Button)findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(gotOne == false) {
                    scoreMain += currentMainScore;
                }

                Intent intent = new Intent(MainActivity.this, Player2.class);
                intent.putExtra("scoreMain", scoreMain);

                //startActivity(intent);
                startActivityForResult(intent, reqCode);
            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == reqCode && resultCode == RESULT_OK && data != null) {
            scorePlayer2 = data.getLongExtra("scorePlayer2", 0l);
            //Toast.makeText(this, "The 2nd score is: " + scorePlayer2, Toast.LENGTH_LONG).show();
            themTextView.setText("THEM: " +scorePlayer2);
            Intent intentOrg = getIntent();
            finish();
            startActivity(intentOrg);
        }
    }

    //get two random ints
    public void rollDice(){
        int val1 = 1 + (int)(6 * Math.random());
        int val2 = 1 + (int)(6 * Math.random());
        setDie(val1, die1);
        setDie(val2, die2);
        long tempScore = val1 + val2 + scoreMain;
        if(tempScore >= 100){
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("Wohooooo!!!")
                    .setMessage("YOU WON!!!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MainActivity.scorePlayer2 = 0;
                            MainActivity.scoreMain = 0;
                            Player2.scoreMain = 0;
                            Player2.scorePlayer2 = 0;
                            Intent intentOrg = getIntent();
                            finish();
                            startActivity(intentOrg);
                        }
                    }).create().show();
        }
        else if(val1 == 1 || val2 == 1) {
            gotOne = true;
            new AlertDialog.Builder(MainActivity.this)
                    .setTitle("OOPS U Got 1")
                    .setMessage("Round Score: 0; Turn for the next player!!!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hold.performClick();
                        }
                    }).create().show();
        }
        else {
            currentMainScore = val1 + val2 + currentMainScore;
            roundTextView.setText("Round: " +currentMainScore);
        }
    }
    //set appropriate pics
    public void setDie(int value, FrameLayout layout){
        Drawable pic = null;
        switch(value){
            case 1:
                pic = getResources().getDrawable(R.drawable.die_face_1);
                break;
            case 2:
                pic = getResources().getDrawable(R.drawable.die_face_2);
                break;
            case 3:
                pic = getResources().getDrawable(R.drawable.die_face_3);
                break;
            case 4:
                pic = getResources().getDrawable(R.drawable.die_face_4);
                break;
            case 5:
                pic = getResources().getDrawable(R.drawable.die_face_5);
                break;
            case 6:
                pic = getResources().getDrawable(R.drawable.die_face_6);
                break;
        }
        layout.setBackground(pic);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
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
}
