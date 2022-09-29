package algonquin.cst2335.kuma0181;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;

import algonquin.cst2335.kuma0181.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        TextView textView = binding.textview;

        binding.button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                textView.setText("Welcome to Cst3463");
                String editString = binding.myedittext.getText().toString();
                textView.setText("Your edit text has:  " + editString);

            }
        });

        binding.imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Image view height : "+binding.imageView.getHeight() + ". Image view width : "+binding.imageView.getWidth(), Toast.LENGTH_SHORT).show();
            }
        });

        binding.radioButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                Toast.makeText(MainActivity.this, "Radio button status is: "+b, Toast.LENGTH_SHORT).show();
            }
        });


        binding.switchButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b==true){
                    Toast.makeText(MainActivity.this, "Switch button status is selected.", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(MainActivity.this, "Switch button status is not selected.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}