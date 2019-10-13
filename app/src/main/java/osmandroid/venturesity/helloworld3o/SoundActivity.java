package osmandroid.venturesity.helloworld3o;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SoundActivity extends AppCompatActivity {

    private Button button1;
    private Button button2;
    private Button button3;
    private Button button4;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sound);

        ActionBar actionBar = getSupportActionBar();
        if(actionBar!=null)actionBar.setTitle("Sounds");

        button1=findViewById(R.id.button1);
        button2=findViewById(R.id.button2);
        button3=findViewById(R.id.button3);
        button4=findViewById(R.id.button4);


        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.youtube.com/watch?v=I1R-r-VBySY"); // missing 'http://' will cause crashed
                startSound(uri);
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.youtube.com/watch?v=JLJqUipWRWk&list=RDJLJqUipWRWk&index=1"); // missing 'http://' will cause crashed
                startSound(uri);

            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.youtube.com/watch?v=FrsOco9Hbu0&list=RDJLJqUipWRWk&index=3\""); // missing 'http://' will cause crashed
                startSound(uri);

            }
        });
        button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Uri uri = Uri.parse("https://www.youtube.com/watch?v=sSgPDKG6bB0&list=RDJLJqUipWRWk&index=4"); // missing 'http://' will cause crashed
                startSound(uri);
            }
        });


    }

    void startSound(Uri uri)
    {
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

}
