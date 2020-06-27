package com.example.parenting.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.example.parenting.R;
import com.example.parenting.db.NotesDB;
import com.example.parenting.db.NotesDao;
import com.example.parenting.models.Note;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Date;

public class EditNoteActivity extends AppCompatActivity {
    private TextInputEditText inputNote;
    private NotesDao dao;
    private Note temp;
    public static final String NOTE_EXTRA_Key = "note_id";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edite_note);
        inputNote = (TextInputEditText) findViewById(R.id.input_note);
        dao = NotesDB.getInstance(this).notesDao();
        if (getIntent().getExtras() != null) {
          //  System.out.println("BU null degil babaaaaaaaaaaaaaaaaaaaaa");
            int id = getIntent().getExtras().getInt(NOTE_EXTRA_Key, 0);
            temp = dao.getNoteById(id);

            inputNote.setText(temp.getNoteText());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edite_note_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.save_note)
            onSaveNote();
        return super.onOptionsItemSelected(item);
    }

    private void onSaveNote() {
        //TODO 20/06/2018 Save note
        String text = inputNote.getText().toString();
        if (!text.isEmpty()) {
            long date = new Date().getTime();// get current system time

            if (temp == null) {
                temp = new Note(text, date);
                dao.insertNote(temp); //create new note and inserted to database
            } else {
                temp.setNoteText(text);
                temp.setNoteDate(date);
                dao.updateNote(temp);//change text and date update note on database

            }

//            // Note note = new Note(text, date);//create new note
//            temp.setNoteDate(date);
//            temp.setNoteText(text);
//            if (temp.getId() == -1) {
//                System.out.println("SAVE EDİLİRKEN BABAAAAAAAAAAAAAAAAAAAAAAA İFİN İCİNE GİRDİK");
//                dao.insertNote(temp);//insert and save nots to database
//            } else dao.updateNote(temp);

            finish(); //returns to the main activity

        }

    }
}
