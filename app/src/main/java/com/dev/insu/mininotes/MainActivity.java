package com.dev.insu.mininotes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    public SharedPreferences sp;
    public SharedPreferences.Editor ed;
    public RecyclerView rvNotes;
    ArrayList<NoteModel> notes;
    private int maxId;
    private EditText quickNoteText;
    private ImageButton moreBtn;
    private ImageButton doneBtn;
    private ImageButton settingsBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        sp = PreferenceManager.getDefaultSharedPreferences(this);
        ed = sp.edit();
        quickNoteText = (EditText) findViewById(R.id.quickNoteText);
        moreBtn = (ImageButton) findViewById(R.id.moreBtn);
        doneBtn = (ImageButton) findViewById(R.id.doneBtn);
        settingsBtn = (ImageButton) findViewById(R.id.settingsBtn); 
        rvNotes = (RecyclerView) findViewById(R.id.notesList);
        notes = new ArrayList<NoteModel>();

        maxId = sp.getInt("maxID", 0);

        updateList(maxId);

      
        
        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!quickNoteText.getText().toString().isEmpty()) {


                    DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                    String date = df.format(new Date());

                    maxId = sp.getInt("maxID", 0);
                    maxId++;

                    ed.putInt("maxID", maxId);

                    String IDS = Integer.toString(maxId);


                    ed.putString(IDS + "TITLE", "");


                    ed.putString(IDS + "DATE", date);
                    ed.putString(IDS + "CONTENT", quickNoteText.getText().toString());
                    quickNoteText.setText("");
                    ed.commit();

                    Intent i = new Intent(MainActivity.this, AddNote.class);
                    i.putExtra("ID", maxId);
                    startActivity(i);
                } else
                    Toast.makeText(MainActivity.this, getString(R.string.noContent), Toast.LENGTH_SHORT).show();

            }

        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!quickNoteText.getText().toString().isEmpty()) {
                    DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
                    String date = df.format(new Date());
                        
                    maxId = sp.getInt("maxID", 0);
                    maxId++;

                    ed.putInt("maxID", maxId);

                    String IDS = Integer.toString(maxId);


                    ed.putString(IDS + "TITLE", getString(R.string.noTitle));


                    ed.putString(IDS + "DATE", date);
                    ed.putString(IDS + "CONTENT", quickNoteText.getText().toString());
                    quickNoteText.setText("");
                    ed.commit();
                    updateList(maxId);

                } else
                    Toast.makeText(MainActivity.this, getString(R.string.noContent), Toast.LENGTH_SHORT).show();

            }

        });

    }


    public void fabOnClick(View view) {
        Intent i = new Intent(this, AddNote.class);
        startActivity(i);
    }

    public void updateList(int maxId) {
        if (maxId > 0) {
            notes.clear();
            //dodawanie istniejacych notatek do listy
            for (int i = maxId; i >= 1; i--) {
                String idS = Integer.toString(i);
                String title = sp.getString(idS + "TITLE", getString(R.string.noTitle));
                String content = sp.getString(idS + "CONTENT", " ");
                Boolean isActive = sp.getBoolean(idS + "ACTIVE", true);
                String date = sp.getString(idS + "DATE", "1000-0-0");
                notes.add(new NoteModel(title, content, date, isActive));
            }

            NotesAdapter adapter = new NotesAdapter(this, notes);
            // Attach the adapter to the recyclerview to populate items
            rvNotes.setAdapter(adapter);
            // Set layout manager to position the items
            rvNotes.setLayoutManager(new LinearLayoutManager(this));
            // Create adapter passing in the sample user data
        }


    }

}
