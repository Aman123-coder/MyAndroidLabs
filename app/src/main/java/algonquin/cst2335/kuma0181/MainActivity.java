package algonquin.cst2335.kuma0181;
import static android.widget.Toast.*;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity {
    /* this hold the text at the center of screen*/
    private TextView tv=null;
    /* this holds and let the user to change the input*/
    private EditText et=null;
    /* this lets the user to click it and change the activity*/
    private Button btn = null;
@Override

protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    //loads xml file on screen:
    setContentView(R.layout.activity_main);
     tv = findViewById(R.id.textView);
    et = findViewById(R.id.editTextTextPassword);
     btn = findViewById(R.id.button);
    btn.setOnClickListener(clk -> {
        String password = et.getText().toString();
        if(checkPasswordComplexity(password)){
            tv.setText("Password meets all requirements");
        }
        else{
            tv.setText("you shall not pass");
        }

        checkPasswordComplexity(password);

    });
}

/**
 *
 * @param password The string object that we are checking
 * @return returns true if all conditions are present and false if not
 */
    public boolean checkPasswordComplexity(String password) {


        boolean foundUppercase;
        boolean foundLowercase;
        boolean foundNumber;
        boolean foundSpecial;
        foundUppercase = foundLowercase = foundNumber = foundSpecial = false;


        for (int i = 0; !foundLowercase && i < password.length(); i++) {
            foundLowercase = Character.isLowerCase(password.charAt(i));
        }

        for (int i = 0; !foundUppercase && i < password.length(); i++) {
            foundUppercase = Character.isUpperCase(password.charAt(i));
        }

        for (int i = 0; !foundNumber && i < password.length(); i++) {
            foundNumber = Character.isDigit(password.charAt(i));
        }
        for (int i = 0; !foundSpecial && i < password.length(); i++) {
            foundSpecial = isspecialcharacter(password.charAt(i));
        }


/**
 * toast messages for all the errors that might happen to
 */

        Toast nouppercase = Toast.makeText(getApplicationContext(), "missing an uppercase letter", LENGTH_SHORT);
        Toast nolowercase = Toast.makeText(getApplicationContext(), "missing a lowercase letter", LENGTH_SHORT);
        Toast nodigit = Toast.makeText(getApplicationContext(), "missing a digit", LENGTH_SHORT);
        Toast nospecial = Toast.makeText(getApplicationContext(), "missing special characters", LENGTH_SHORT);

        if (!foundLowercase) {
            nolowercase.show();
        }
        if (!foundUppercase) {
            nouppercase.show();
        }
        if (!foundNumber) {
            nodigit.show();
        }

        if(!foundSpecial){
            nospecial.show();
        }
        return foundLowercase && foundUppercase && foundNumber && foundSpecial;

    }

    private boolean isspecialcharacter(char c) {
        {
            switch(c){
                case'!':
                case'@':
                case'#':
                case'$':
                case'%':
                case'^':
                case'&':
                case'*':
                case'?':
                    return true;
                default:
                    return false;
            }
        }
    }


}