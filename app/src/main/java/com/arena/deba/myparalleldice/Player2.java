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


public class Player2 extends ActionBarActivity {

    private FrameLayout die1, die2;
    private Button roll, hold;
    static long scorePlayer2;
    static long scoreMain;
    private long currentPlayer2Score;
    boolean gotOne = false;
    TextView roundTextView;
    TextView usTextView;
    TextView themTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player2);

        //Initialize
        currentPlayer2Score = 0l;
        gotOne = false;

        Intent intent = getIntent();
        scoreMain = intent.getExtras().getLong("scoreMain");

        //Toast.makeText(this, "The score is: " +scoreMain, Toast.LENGTH_LONG).show();

        roundTextView = (TextView)findViewById(R.id.round);
        usTextView = (TextView)findViewById(R.id.us);
        themTextView = (TextView)findViewById(R.id.them);

        usTextView.setText("US: " +scorePlayer2);
        themTextView.setText("THEM: " +scoreMain);

        roll = (Button)findViewById(R.id.button);
        roll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rollDice();
            }
        });

        die1 = (FrameLayout)findViewById(R.id.die1);
        die2 = (FrameLayout)findViewById(R.id.die2);

        hold = (Button)findViewById(R.id.hold);
        hold.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view){
                if(gotOne == false) {
                    scorePlayer2 += currentPlayer2Score;
                }

                Intent resultIntent = new Intent();
                // TODO Add extras or a data URI to this intent as appropriate.
                resultIntent.putExtra("scorePlayer2", scorePlayer2);
                setResult(Player2.RESULT_OK, resultIntent);
                finish();
            }
        });

    }

    //get two random ints
    public void rollDice(){
        int val1 = 1 + (int)(6 * Math.random());
        int val2 = 1 + (int)(6 * Math.random());
        setDie(val1, die1);
        setDie(val2, die2);
        long tempScore = val1 + val2 + scorePlayer2;
        if(tempScore >= 100){
            new AlertDialog.Builder(Player2.this)
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
                            Intent in = new Intent(Player2.this,MainActivity.class);
                            startActivity(in);
                            finish();
                        }
                    }).create().show();
        }
        else if(val1 == 1 || val2 == 1) {
            gotOne = true;
            new AlertDialog.Builder(Player2.this)
                    .setTitle("OOPS U Got 1")
                    .setMessage("Turn for the next player!!!")
                    .setCancelable(false)
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            hold.performClick();
                        }
                    }).create().show();
        }
        else {
            currentPlayer2Score = val1 + val2 + currentPlayer2Score;
            roundTextView.setText("Round: " +currentPlayer2Score);
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
