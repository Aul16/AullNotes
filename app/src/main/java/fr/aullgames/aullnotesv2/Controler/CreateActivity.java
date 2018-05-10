package fr.aullgames.aullnotesv2.Controler;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import fr.aullgames.aullnotesv2.R;

public class CreateActivity extends AppCompatActivity {

    private Button mSaveButton;
    private Button mDeleteButton;
    private EditText mNotesInput;

    private int mNotesNumber;
    private String mTitleText;

    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(RESULT_OK == requestCode){
            mNotesNumber = data.getIntExtra(MainActivity.BUNDLE_EXTRA_TITLE, 0);
            mTitleText = data.getStringExtra(MainActivity.BUNDLE_EXTRA_TITLE_TEXT);

        }
    }*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create);

        mDeleteButton = findViewById(R.id.btnDelete);
        mSaveButton = findViewById(R.id.btnSave);
        mNotesInput = findViewById(R.id.etNotes);

        Intent intent = getIntent();
        if (intent != null){
            mNotesNumber = intent.getIntExtra(MainActivity.BUNDLE_EXTRA_TITLE, 0);
            mTitleText = intent.getStringExtra(MainActivity.BUNDLE_EXTRA_TITLE_TEXT);
        }


        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra(MainActivity.BUNDLE_EXTRA_TITLE, mNotesNumber);
                intent.putExtra(MainActivity.BUNDLE_EXTRA_NOTES, mNotesInput.getText().toString());
                intent.putExtra(MainActivity.BUNDLE_EXTRA_TITLE_TEXT, mTitleText);
                setResult(RESULT_OK, intent);
                finish();
            }
        });

        mDeleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mNotesInput.setText("");
            }
        });
    }
}
