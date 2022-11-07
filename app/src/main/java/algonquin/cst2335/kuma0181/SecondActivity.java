package algonquin.cst2335.kuma0181;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class SecondActivity extends AppCompatActivity {

    TextView textView;
    TextView phoneNumber;
    Button callButton;
    Button changePic;
    ImageView profPicture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        textView = findViewById(R.id.textView);
        phoneNumber = findViewById(R.id.editTextPhone);
        callButton = findViewById(R.id.callButton);
        profPicture = findViewById(R.id.imageView);
        changePic = findViewById(R.id.button2);

        Intent fromPrevious = getIntent();
        String emailAddress = fromPrevious.getStringExtra("EmailAddress");
        textView = findViewById(R.id.textView);
        textView.setText("Welcome back " + emailAddress);

        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);
        String phoneNo = prefs.getString("PhoneNumber", "");
        phoneNumber.setText(phoneNo);

        callButton.setOnClickListener( clk-> {
            Intent call = new Intent(Intent.ACTION_DIAL);
            String callNumber = phoneNumber.getText().toString();

            call.setData(Uri.parse("tel:" + callNumber));
            startActivity(call);

        });

        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        ActivityResultLauncher<Intent> cameraResult = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {

                        if (result.getResultCode() == Activity.RESULT_OK) {

                            Intent data = result.getData();
                            Bitmap thumbnail = data.getParcelableExtra("data");
                            profPicture.setImageBitmap(thumbnail);

                            FileOutputStream fOut;
                            try {
                                fOut = openFileOutput("Picture.png", Context.MODE_PRIVATE);
                                thumbnail.compress(Bitmap.CompressFormat.PNG, 100, fOut);
                                fOut.flush();
                                fOut.close();
                            }
                            catch (FileNotFoundException e) {
                                e.printStackTrace();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }

                });

        changePic.setOnClickListener( clk-> {

            cameraResult.launch(cameraIntent);

        });

        File file = new File( getFilesDir(), "Picture.png");

        if((file.exists())){
            String path = getFilesDir().getAbsolutePath() + "/Picture.png";
            Bitmap theImage = BitmapFactory.decodeFile(path);
            profPicture.setImageBitmap(theImage);
        }
    }

    protected void onPause(){
        super.onPause();
        phoneNumber = findViewById(R.id.editTextPhone);

        //creating a SharedPreference object
        SharedPreferences prefs = getSharedPreferences("MyData", Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = prefs.edit();
        editor.putString("PhoneNumber", phoneNumber.getText().toString());
        editor.apply();
    }
}