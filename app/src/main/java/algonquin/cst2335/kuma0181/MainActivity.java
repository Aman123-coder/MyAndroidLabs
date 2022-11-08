package algonquin.cst2335.kuma0181;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    Button loginButton;
    EditText emailEditText;


    private static String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.w("MainActivity", "In onCreate() - Loading Widgets");
        Log.d(TAG, "Message");

        loginButton = findViewById(R.id.loginButton);
        emailEditText = findViewById(R.id.EmailEdittext);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String emailAddress = prefs.getString("LoginName", "");
        emailEditText.setText(emailAddress);


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent nextPage = new Intent(MainActivity.this, SecondActivity.class);
                nextPage.putExtra("EmailAddress", emailEditText.getText().toString());
                SharedPreferences.Editor editor = prefs.edit();
                editor.putString("LoginName", emailEditText.getText().toString());
                editor.apply();
                startActivity(nextPage);
            }
        });
    }


    @Override
    protected void onStart() {
        super.onStart();
        Log.w(TAG, "The application is now visible on the screen");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w(TAG, "The application is now responding to user input");
    }


    @Override
    protected void onPause() {
        super.onPause();
        Log.w(TAG, "The application no longer responds to user input");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w(TAG, "The application is no longer visible");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w(TAG, "Any memory used by the application is freed");
    }


}