package com.example.lenovo.always;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import java.util.Random;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    Button[][] buttons = new Button[3][3];
    TextView textView,computer,player;
    Switch aSwitch;

    private boolean turn = true,status = true,hard = false;

    private int counter = 0,restart = 1,score_computer = 0,score_player = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = (TextView) findViewById(R.id.text);
        computer = (TextView) findViewById(R.id.computer);
        player = (TextView) findViewById(R.id.player);
        for(int i=0;i<3;i++)
        {
            for(int j=0;j<3;j++)
            {
                String buttonID = "b_" + i + j;
                int value = getResources().getIdentifier(buttonID,"id",getPackageName());
                buttons[i][j] = (Button) findViewById(value);
                buttons[i][j].setOnClickListener(this);
            }
        }
        change_background();

        /* Switch for hard and easy mode */

        aSwitch = (Switch) findViewById(R.id.switch1);
        aSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {

                if(b)
                {
                    hard = true;
                    Toast.makeText(MainActivity.this, "Hard Mode", Toast.LENGTH_SHORT).show();
                    if(counter == 0)
                    {
                        hard_mode_genrate();
                    }


                }
                else
                {
                    hard = false;
                    Toast.makeText(MainActivity.this, "Easy Mode", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



    public void hard_mode_genrate()
    {
        if(counter == 0) {
            Random dice = new Random();
            int n = dice.nextInt(100);
            n += 6;
            n = n % 6;
            if(n==0)
                buttons[1][1].setText("X");
            else if(n==2 || n == 1)
                buttons[0][0].setText("X");
            else if(n==3)
                buttons[0][2].setText("X");
            else if(n==4)
                buttons[2][0].setText("X");
            else if(n==5)
                buttons[2][2].setText("X");
            counter++;
        }
    }
    /* Method for reset play board */

    public void reset(View v){

        for(int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++) {
                buttons[i][j].setText("");
            }
        }
        textView.setText("Play..");
        status = true;
        turn = true;
        counter = 0;
        if(hard)
            hard_mode_genrate();
        if(restart == 2)
        {
         score_player = 0;
         score_computer = 0;
         computer.setText("Computer = "+score_computer);
         player.setText("Player = "+score_player);
            Toast.makeText(this, "Fully Reset", Toast.LENGTH_SHORT).show();
        }
        restart++;
        change_background();
    }
    public void change_background()
    {
        int x;
        LinearLayout change = (LinearLayout) findViewById(R.id.back);
        Random dice = new Random();
        x = dice.nextInt(500);
        x +=5;
        x = x % 5;
        if(x == 0)
            change.setBackgroundResource(R.drawable.b1);
        else if(x == 1)
            change.setBackgroundResource(R.drawable.b2);
        else if(x == 2)
            change.setBackgroundResource(R.drawable.b3);
        else if (x == 3)
            change.setBackgroundResource(R.drawable.b4);
        else if(x==4)
            change.setBackgroundResource(R.drawable.b5);
    }
    @Override
    public void onClick(View v) {
        restart = 1;
        if(!((Button) v).getText().toString().equals("")){
            return;
        }
        if(status)
        {
            ((Button) v).setText("0");
            counter++;
            win();
            if(!status)
                return;
            turn=!turn;
            if(counter<9)
            {
                create();
                counter++;
                win();
                if(!status)
                    return;
            }
            turn = !turn;
        }
        else
        {
            textView.setText("Play Again");
        }

    }
    public void win()
    {
        if(check()==1)
        {
            if(!turn)
            {
                textView.setText("Sorry you lose");
                score_computer++;
                computer.setText("Computer = "+ score_computer);
            }
            else
            {
                textView.setText("You WON Hooray");
                score_player++;
                player.setText("Player = "+score_player);

            }
            status = false;
            return;
        }
        else if(counter == 9){
            textView.setText("Draw");
            status = false;
            return;
        }

    }
    public void create(){
        int i,j,cnt=0,y=0,t=15,o=15;
        String p = "X",q="0";
        String[][] play = new String[3][3];
        for(i=0;i<3;i++)
        {
            for(j=0;j<3;j++)
            {
                play[i][j] = buttons[i][j].getText().toString();
            }
        }
        while(y<=1)
        {
            cnt = 0;
            for(i=0;i<3;i++)
            {
                if(play[i][i].equals(p))
                    cnt++;
                else if(play[i][i].equals(""))
                    t = i;
                else if(play[i][i].equals(q))
                {
                    cnt = 3;
                    break;
                }
            }
            if(cnt == 2) {
                buttons[t][t].setText("X");
                return;
            }
            cnt=0;
            j=2;
            for(i=0;i<3;i++)
            {
                if(play[i][j].equals(p))
                    cnt++;
                else if(play[i][j].equals(""))
                {
                    t = i;
                    o = j;
                }
                else if(play[i][j].equals(q))
                {
                    cnt = 3;
                    break;
                }
                j--;
            }
            if(cnt == 2)
            {
                buttons[t][o].setText("X");
                return;
            }
            for(i=0;i<3;i++)
            {
                cnt = 0;
                for(j=0;j<3;j++)
                {
                    if(play[i][j].equals(p))
                        cnt++;
                    else if(play[i][j].equals(""))
                        t = j;
                    else if(play[i][j].equals(q))
                    {
                        cnt = 3;
                        break;
                    }
                }
                if(cnt==2)
                {
                    buttons[i][t].setText("X");
                    return;
                }
            }
            for(i=0;i<3;i++)
            {
                cnt=0;
                for(j=0;j<3;j++)
                {
                    if(play[j][i].equals(p))
                        cnt++;
                    else if (play[j][i].equals(""))
                        t = j;
                    else if(play[j][i].equals(q))
                    {
                        cnt = 3;
                        break;
                    }
                }
                if(cnt==2){
                    buttons[t][i].setText("X");
                    return;
                }

            }
            y++;
            p = "0";
            q = "X";
        }

        if(hard)
        {
            if(think(play)==0)
                regenrate(play);
        }
        else
            regenrate(play);

        return;

    }

    public int think(String[][] play)
    {
        int i,j,o=0,t=0,cnt=0,space=0,y=0;

        String p = "X",q = "0";

        if(play[1][1].equals(""))
        {
            buttons[1][1].setText("X");
            return 1;
        }

        while(y<=1) {


            /* to check row */
            for (i = 0; i < 3; i++) {
                cnt = 0;
                space = 0;

                for (j = 0; j < 3; j++) {
                    if (play[i][j].equals(p))
                        cnt++;
                    else if (play[i][j].equals(q)) {
                        cnt = 2;
                        break;
                    } else if (play[i][j].equals("")) {
                        space++;
                        t = j;
                    }

                }
                if (cnt == 1 && space == 2) {
                    buttons[i][t].setText("X");
                    return 1;
                }
            }
            /* To check column*/
            for (i = 0; i < 3; i++) {
                cnt = 0;
                space = 0;
                for (j = 0; j < 3; j++) {
                    if (play[j][i].equals(p))
                        cnt++;
                    else if (play[j][i].equals(q)) {
                        cnt = 2;
                        break;
                    } else if (play[j][i].equals("")) {
                        space++;
                        t = j;
                    }
                }
                if (cnt == 1 && space == 2) {
                    buttons[t][i].setText("X");
                    return 1;
                }
            }

            /* To check diagonal left to right*/
            for (i = 0; i < 3; i++) {
                if (play[i][i].equals(p))
                    cnt++;
                else if (play[i][i].equals(q)) {
                    cnt = 2;
                    break;
                } else if (play[i][i].equals("")) {
                    space++;
                    t = i;
                }
            }
            if (cnt == 1 && space == 2) {
                buttons[t][t].setText("X");
                return 1;
            }

            /* to check diagonal right to left*/
            cnt = 0;
            space = 0;
            j = 2;

            for (i = 0; i < 3; i++) {
                if (play[i][j].equals(p))
                    cnt++;
                else if (play[i][j].equals(q)) {
                    cnt = 2;
                    break;
                } else if (play[i][j].equals("")) {
                    space++;
                    t = i;
                    o = j;
                }
                j--;
            }
            if (cnt == 1 && space == 2) {
                buttons[t][o].setText("X");
                return 1;
            }

            p = "0";
            q = "X";
            y++;
        }
        return 0;
    }

    public void regenrate(String[][] play)
    {

        int i,j=0,number;
        if(hard)
        {
            if(play[1][1].equals(""))
                buttons[1][1].setText("X");
        }
        Random dice = new Random();
        number = dice.nextInt(500);
        number+=4;
        number = number % 4;

        if(number == 0)
        {
            for (i=0;i<3;i++)
            {
                for (j=0;j<3;j++)
                {
                    if (play[i][j].equals(""))
                    {
                        buttons[i][j].setText("X");
                        return;
                    }
                }
            }
        }
        else if(number==1)
        {
            for (i=2;i>=0;i--)
            {
                for (j=2;j>=0;j--)
                {
                    if(play[i][j].equals(""))
                    {
                        buttons[i][j].setText("X");
                        return;
                    }
                }
            }
        }
        else if(number==2)
        {
            for(i=0;i<3;i++)
            {
                for (j=2;j>=0;j--)
                {
                    if(play[i][j].equals(""))
                    {
                        buttons[i][j].setText("X");
                        return;
                    }
                }
            }
        }
        else if (number==3)
        {

            for (i=2;i>=0;i--)
            {
                for (j=0;j<3;j++)
                {
                    if (play[i][j].equals(""))
                    {
                        buttons[i][j].setText("X");
                        return;
                    }
                }
            }
        }

    }

    public int check()
    {
        String[][] play = new String[3][3];
        for(int i=0;i<3;i++)
        {
            for (int j=0;j<3;j++)
            {
                play[i][j] = buttons[i][j].getText().toString();
            }
        }

        for(int i=0;i<3;i++)
        {
            if((play[i][0].equals(play[i][1])) && (play[i][0].equals(play[i][2])) && !(play[i][0].equals("")))
                return 1;
            if(play[0][i].equals(play[1][i]) && play[0][i].equals(play[2][i]) && !play[0][i].equals(""))
                return 1;
            if((play[0][0].equals(play[1][1])) && (play[0][0].equals(play[2][2])) && !(play[0][0].equals("")))
                return 1;
            if((play[0][2].equals(play[1][1])) && (play[0][2].equals(play[2][0])) && !(play[0][2].equals("")))
                return 1;
        }
        return 0;
    }

}