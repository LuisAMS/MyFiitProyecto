package myfiit.hackaldea.com.myfiit;

import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

public class CharacterSelect extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_select);

        ImageButton btnCharacterOne = (ImageButton) findViewById(R.id.imgBtn_character1);
        btnCharacterOne.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funcCharacter1(view);
            }
        });

        ImageButton btnCharacterTwo = (ImageButton) findViewById(R.id.imgBtn_character2);
        btnCharacterTwo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                funcCharacter2(view);
            }
        });

    }

    void funcCharacter1(View v){

        Intent i = new Intent(getBaseContext(), PrincipalActivity.class);
        String strName = "monito";
        i.putExtra("MYCHARACTER", strName);

        startActivity(i);
    }

    void funcCharacter2(View v){

        Intent i = new Intent(getBaseContext(), PrincipalActivity.class);
        String strName = "monita";
        i.putExtra("MYCHARACTER", strName);
        startActivity(i);

    }
}
