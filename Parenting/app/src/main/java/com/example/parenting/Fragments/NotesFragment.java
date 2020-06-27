package com.example.parenting.Fragments;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.parenting.Activities.EditNoteActivity;
import com.example.parenting.R;
import com.example.parenting.adapters.NotesAdapter;
import com.example.parenting.callbacks.NoteEventListener;
import com.example.parenting.db.NotesDB;
import com.example.parenting.db.NotesDao;
import com.example.parenting.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import static com.example.parenting.Activities.EditNoteActivity.NOTE_EXTRA_Key;

public class NotesFragment extends Fragment implements NoteEventListener {

    private View NotesView;

    //Notenin main activitysi burası
    private RecyclerView recyclerView;
    private ArrayList<Note> notes;
    private NotesAdapter adapter;
    private NotesDao dao;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        NotesView = inflater.inflate(R.layout.fragment_notes, container, false);

        recyclerView = NotesView.findViewById(R.id.notes_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        //init fab
        FloatingActionButton fab = NotesView.findViewById(R.id.fab_notes);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO gibi bi note 13/05/2019 add new note
                onAddNewNote();
            }
        });

        dao = NotesDB.getInstance(getContext()).notesDao();

        return NotesView;
    }

    private void loadNotes() {
        this.notes = new ArrayList<>();
        List<Note> list = dao.getNotes(); //get All notes from Databse

        this.notes.addAll(list);
        // System.out.println("NOTLAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAR:"+list.size());

        this.adapter = new NotesAdapter(getContext(), notes);
        this.adapter.setListener(this);

        this.recyclerView.setAdapter(adapter);
        //  adapter.notifyDataSetChanged();
    }

    private void onAddNewNote() {
        //todo start EditNoteActivity
        startActivity(new Intent(getContext(), EditNoteActivity.class));
    }

    @Override
    public void onResume() {
        super.onResume();
        loadNotes();

    }

    @Override
    public void onNoteClick(Note note) {

        Intent edit = new Intent(getContext(), EditNoteActivity.class);
        edit.putExtra(NOTE_EXTRA_Key, note.getId());
        startActivity(edit);

    }

    @Override
    public void onNoteLongClick(final Note note) {

        new AlertDialog.Builder(getContext()).setTitle("Bu notu silmek istiyor musunuz?")
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
