package com.example.parenting.Activities;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.parenting.R;
import com.example.parenting.adapters.NotesAdapter;
import com.example.parenting.callbacks.NoteEventListener;
import com.example.parenting.db.NotesDB;
import com.example.parenting.db.NotesDao;
import com.example.parenting.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.example.parenting.Activities.EditNoteActivity.NOTE_EXTRA_Key;

public class NoteActivity extends AppCompatActivity implements NoteEventListener {
    //Notenin main activitysi burası
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NotesAdapter adapter;
    private NotesDao dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //init recyleview
        recyclerView = findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        //init fab
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO gibi bi note 13/05/2019 add new note
                onAddNewNote();
            }
        });

        dao = NotesDB.getInstance(this).notesDao();
    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        List<Note> list = dao.getNotes(); //get All notes from Databse

        this.notes.addAll(list);
       // System.out.println("NOTLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAR:"+list.size());

        this.adapter = new NotesAdapter(this, notes);
        this.adapter.setListener(this);

        this.recyclerView.setAdapter(adapter);
        //  adapter.notifyDataSetChanged();
    }

    private void onAddNewNote() {
        //todo start EditNoteActivity
        startActivity(new Intent(this, EditNoteActivity.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadNotes();

    }

    @Override
    public void onNoteClick(Note note) {

        Intent edit = new Intent(this, EditNoteActivity.class);
        edit.putExtra(NOTE_EXTRA_Key, note.getId());
        startActivity(edit);

    }

    @Override
    public void onNoteLongClick(final Note note) {

        new AlertDialog.Builder(this).setTitle("Bu notu silmek istiyor musunuz?")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        })
                .setPositiveButton("DELETE", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dao.deleteNote(note); //notu siliyoruz
                        loadNotes();           //databasei yeniliyoruz
                    }
                }).create().show();

    }
}
