package com.example.harshitvaish.roomwithrecyclerview;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Toast;

public class AddNoteActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.harshitvaish.roomwithrecyclerview.ID";

    public static final String EXTRA_Title =
            "com.example.harshitvaish.roomwithrecyclerview.TITLE";
    public static final String EXTRA_Desc =
            "com.example.harshitvaish.roomwithrecyclerview.DESC";
    public static final String EXTRA_pr =
            "com.example.harshitvaish.roomwithrecyclerview.PRIORITY";
    private EditText title, desc;
    private NumberPicker num_pick;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_note);
        title = findViewById(R.id.title);
        desc = findViewById(R.id.desc);
        num_pick = findViewById(R.id.num_pick);

        num_pick.setMinValue(1);
        num_pick.setMaxValue(10);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_close_black_24dp);
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Add Note");
            title.setText(intent.getStringExtra(EXTRA_Title));
            desc.setText(intent.getStringExtra(EXTRA_Desc));
            num_pick.setValue(intent.getIntExtra(EXTRA_pr, 1));
        } else {
            setTitle("Add Note");
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.add_note_menu, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_note: {
                saveNote();
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    private void saveNote() {
        String tit = title.getText().toString();
        String des = desc.getText().toString();
        int priority = num_pick.getValue();
        if (tit.trim().isEmpty() || des.trim().isEmpty()) {
            Toast.makeText(this, "Please insert the title and description", Toast.LENGTH_SHORT).show();
            return;
        }
        Intent data = new Intent();

        data.putExtra(EXTRA_Title, tit);
        data.putExtra(EXTRA_Desc, des);
        data.putExtra(EXTRA_pr, priority);
        int id = getIntent().getIntExtra(EXTRA_ID, -1);
        if (id != -1) {
            data.putExtra(EXTRA_ID, id);
        }
        setResult(RESULT_OK, data);
        finish();


    }
}
