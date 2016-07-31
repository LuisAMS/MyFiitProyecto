package myfiit.hackaldea.com.myfiit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Intro extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        Button btnFacebook = (Button) findViewById(R.id.btnFacebook);
        btnFacebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnEresMenor(view);
            }
        });

        Button btnEresMenor = (Button) findViewById(R.id.btnMenor);
        btnEresMenor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eresmenorLogin(view);
            }
        });
    }

    void btnEresMenor(View v){
        Intent i = new Intent(getBaseContext(), Login.class);
        startActivity(i);
        //finish();
    }

    void eresmenorLogin(View v){
        Intent i = new Intent(getBaseContext(), CharacterSelect.class);
        startActivity(i);
        //finish();
    }
}
