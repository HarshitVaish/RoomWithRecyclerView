package com.example.harshitvaish.roomwithrecyclerview;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProvider;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static final int Add_note = 1;
    public static final int Edit_note = 2;
    private NoteViewModel noteViewModel;
    private RecyclerView recycle;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recycle = findViewById(R.id.recycle);
        fab = findViewById(R.id.add_fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                startActivityForResult(intent, Add_note);
            }
        });
        recycle.setLayoutManager(new LinearLayoutManager(this));
        recycle.setHasFixedSize(true);
        final NoteAdapter adapter = new NoteAdapter();
        recycle.setAdapter(adapter);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        noteViewModel.getAllNotes().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                adapter.submitList(notes);

            }
        });
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {

                noteViewModel.delete(adapter.getNoteAt(viewHolder.getAdapterPosition()));
                Toast.makeText(MainActivity.this, "Note Deleted", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recycle);
        adapter.setOnItemClickListener(new NoteAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Note note) {
                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
                intent.putExtra(AddNoteActivity.EXTRA_ID, note.getId());
                intent.putExtra(AddNoteActivity.EXTRA_Title, note.getTitle());
                intent.putExtra(AddNoteActivity.EXTRA_Desc, note.getDesciption());
                intent.putExtra(AddNoteActivity.EXTRA_pr, note.getPriority());
                startActivityForResult(intent,Edit_note);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Add_note && resultCode == RESULT_OK) {
            String title = data.getStringExtra(AddNoteActivity.EXTRA_Title);
            String desc = data.getStringExtra(AddNoteActivity.EXTRA_Desc);
            int pr = data.getIntExtra(AddNoteActivity.EXTRA_pr, 1);
            Note note = new Note(title, desc, pr);
            noteViewModel.insert(note);
            Toast.makeText(MainActivity.this, "Note Saved", Toast.LENGTH_SHORT).show();

        }if (requestCode == Edit_note && resultCode == RESULT_OK) {
            int id=data.getIntExtra(AddNoteActivity.EXTRA_ID,-1);
            if(id==-1){
                Toast.makeText(MainActivity.this, "Note Cant Be Updated", Toast.LENGTH_SHORT).show();
           return;
            }
            String title = data.getStringExtra(AddNoteActivity.EXTRA_Title);
            String desc = data.getStringExtra(AddNoteActivity.EXTRA_Desc);
            int pr = data.getIntExtra(AddNoteActivity.EXTRA_pr, 1);
   Note note=new Note(title,desc,pr);
      note.setId(id);
      noteViewModel.update(note);
        } else {
            Toast.makeText(this, "Note not saved", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.del_al_notes:
                noteViewModel.deleteAllNotes();
                Toast.makeText(MainActivity.this, "All Notes Deleted", Toast.LENGTH_SHORT).show();
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}
