package fr.aullgames.aullnotesv2.Controler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.aullgames.aullnotesv2.R;

public class EditActivity extends AppCompatActivity {

    private int mEditNotesNumber;
    private String mEditNotesText;
    private String mEditTitleText;

    private Button mEditDeleteButton;
    private Button mEditSaveButton;
    private EditText mEditNotes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        Intent intent = getIntent();
        if (intent != null){
            mEditNotesNumber = intent.getIntExtra(MainActivity.BUNDLE_EXTRA_TITLE, 0);
            mEditNotesText = intent.getStringExtra(MainActivity.BUNDLE_EXTRA_NOTES);
            mEditTitleText = intent.getStringExtra(MainActivity.BUNDLE_EXTRA_TITLE_TEXT);

        }

        mEditDeleteButton = findViewById(R.id.btnEditDelete);
        mEditSaveButton = findViewById(R.id.btnEditSave);
        mEditNotes = findViewById(R.id.etEditNotes);

        mEditNotes.setText(mEditNotesText);

        mEditDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mEditNotes.setText("");
            }
        });

        mEditSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.BUNDLE_EXTRA_TITLE, mEditNotesNumber);
                intent.putExtra(MainActivity.BUNDLE_EXTRA_NOTES, mEditNotes.getText().toString());
                intent.putExtra(MainActivity.BUNDLE_EXTRA_TITLE_TEXT, mEditTitleText);
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }
}
