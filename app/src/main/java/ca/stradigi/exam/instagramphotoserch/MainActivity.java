package ca.stradigi.exam.instagramphotoserch;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void onSerchClick(View v) {
        EditText e = (EditText)findViewById(R.id.TagEdit);
        Intent i = new Intent(MainActivity.this, PhotoList.class);
        i.putExtra("tag", e.getText().toString());
        startActivity(i);
    }

}
