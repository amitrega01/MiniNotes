package com.dev.insu.mininotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class AddNote extends AppCompatActivity {
    SharedPreferences sp;
    SharedPreferences.Editor ed;
    private EditText edTitle;
    private EditText edContent;
    private FloatingActionButton fab;
    private FloatingActionButton remove;
    private TextView dateText;
    int ID;
    int idFromIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        edTitle = (EditText) findViewById(R.id.edTitle);
        edContent = (EditText) findViewById(R.id.edContent);
        dateText = (TextView) findViewById(R.id.tvDate); 
        fab = (FloatingActionButton) findViewById(R.id.floatingActionButton);
        remove = (FloatingActionButton) findViewById(R.id.remove);
        
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();

        idFromIntent = getIntent().getIntExtra("ID", 0);
        //Toast.makeText(this, Integer.toString(idFromIntent), Toast.LENGTH_SHORT).show();
        if (idFromIntent != 0) {
            edTitle.setText(sp.getString(idFromIntent + "TITLE", getString(R.string.noTitle)));
            edContent.setText(sp.getString(idFromIntent + "CONTENT", " "));
            dateText.setText(sp.getString(idFromIntent+"DATE",""));
        }
        remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeNote();
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!edContent.getText().toString().isEmpty()) {

                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String date = df.format(new Date());

                    ID = sp.getInt("maxID", 0);
                    ID++;
                    if (idFromIntent == 0)
                        ed.putInt("maxID", ID);
                    if (idFromIntent != 0) ID = idFromIntent;

                    String IDS = Integer.toString(ID);

                    if (edTitle.getText().toString().isEmpty())
                        ed.putString(IDS + "TITLE", getString(R.string.noTitle));
                    else
                        ed.putString(IDS + "TITLE", edTitle.getText().toString());

                    ed.putString(IDS + "DATE", date);
                    ed.putString(IDS + "CONTENT", edContent.getText().toString());

                    ed.commit();
                    Intent i = new Intent(AddNote.this, MainActivity.class);
                    startActivity(i);
                } else
                    Toast.makeText(AddNote.this, getString(R.string.noContent), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void removeNote() {
        ID= sp.getInt("maxID", 0);
        for (int i = idFromIntent;i<=ID ;i++) {
            ed.putString(Integer.toString(i)+"TITLE",sp.getString(Integer.toString(i+1)+"TITLE",getString(R.string.noTitle)));
            ed.putString(Integer.toString(i)+"DATE",sp.getString(Integer.toString(i+1)+"DATE",getString(R.string.noTitle)));
            ed.putString(Integer.toString(i)+"CONTENT",sp.getString(Integer.toString(i+1)+"CONTENT",getString(R.string.noTitle)));
            ed.commit();
            
        }
        ID--;
        ed.putInt("maxID",ID);
        ed.commit();
        Intent c = new Intent(AddNote.this, MainActivity.class);
        startActivity(c);
        
    }
}
