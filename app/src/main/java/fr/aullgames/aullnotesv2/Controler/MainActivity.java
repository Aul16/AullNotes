package fr.aullgames.aullnotesv2.Controler;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Debug;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import java.util.Locale;

import fr.aullgames.aullnotesv2.R;

public class MainActivity extends AppCompatActivity {

    public static final int EDIT_ACTIVITY_REQUEST_CODE = 2;
    public static final String NUMBER_PREFERENCES = "NUMBER_PREFERENCES";
    public static final String BUNDLE_EXTRA_TITLE = "BUNDLE_EXTRA_TITLE";
    public static final String BUNDLE_EXTRA_TITLE_TEXT = "BUNDLE_EXTRA_TITLE_TEXT";
    public static final String BUNDLE_EXTRA_NOTES = "BUNDLE_EXTRA_NOTES";
    public static final int CREATE_ACTIVITY_REQUEST_CODE = 3;
    private int numberPreferences;
    public SharedPreferences mPreferences;
    private LinearLayout ll;
    private String mTitle;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(CREATE_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            int titlePreferences = data.getIntExtra(MainActivity.BUNDLE_EXTRA_TITLE, 0);
            String Notes = data.getStringExtra(MainActivity.BUNDLE_EXTRA_NOTES);
            String title = data.getStringExtra(MainActivity.BUNDLE_EXTRA_TITLE_TEXT);

            mPreferences.edit().putString(String.valueOf(titlePreferences + title), Notes).apply();
            titlePreferences++;
            mPreferences.edit().putInt(NUMBER_PREFERENCES, titlePreferences).apply();
            init();
        }
        if(EDIT_ACTIVITY_REQUEST_CODE == requestCode && RESULT_OK == resultCode){
            String title = data.getStringExtra(MainActivity.BUNDLE_EXTRA_TITLE_TEXT);
            int titlePreferences = data.getIntExtra(MainActivity.BUNDLE_EXTRA_TITLE, 0);
            String notes = data.getStringExtra(MainActivity.BUNDLE_EXTRA_NOTES);

            mPreferences.edit().putString(String.valueOf(titlePreferences + title), notes).apply();
            init();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mPreferences = getPreferences(MODE_PRIVATE);

        numberPreferences = mPreferences.getInt(NUMBER_PREFERENCES, 1);
        mPreferences.edit().putInt(NUMBER_PREFERENCES, numberPreferences).apply();

        ll = findViewById(R.id.notesList);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("TITLE")
                        .setMessage("Enter title");

                final EditText titleInput = new EditText(MainActivity.this);
                LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.MATCH_PARENT);
                titleInput.setLayoutParams(lp);
                builder.setView(titleInput);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        mTitle = titleInput.getText().toString();
                        if (mTitle.length() != 0){
                            mPreferences.edit().putString(String.valueOf(numberPreferences), mTitle).apply();
                            Log.i("DEBUG", String.valueOf(numberPreferences));

                            Intent intent = new Intent(MainActivity.this, CreateActivity.class);
                            intent.putExtra(BUNDLE_EXTRA_TITLE, numberPreferences);
                            intent.putExtra(BUNDLE_EXTRA_TITLE_TEXT, mTitle);
                            startActivityForResult(intent, CREATE_ACTIVITY_REQUEST_CODE);
                        }
                    }
                });
                builder.create()
                        .show();
            }
        });
        init();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        if(id == R.id.action_reset){
            mPreferences.edit().clear().apply();
            init();
            numberPreferences = 1;
        }

        return super.onOptionsItemSelected(item);
    }

    public void init(){

        ll.removeAllViews();

        for (int i = 1; i <= mPreferences.getInt(NUMBER_PREFERENCES, 1); i++){

            numberPreferences = mPreferences.getInt(NUMBER_PREFERENCES, 1);

            if (mPreferences.getString(String.valueOf(i), null) != null){
                Button btn = new Button(this);
                btn.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT));
                btn.setId(i);
                btn.setText(mPreferences.getString(String.valueOf(i), null));
                final String btnText = mPreferences.getString(String.valueOf(i), null);
                final int btnNumber = i;
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(MainActivity.this, EditActivity.class);
                        intent.putExtra(BUNDLE_EXTRA_NOTES, mPreferences.getString(String.valueOf(btnNumber + btnText), "salut"));
                        intent.putExtra(BUNDLE_EXTRA_TITLE, btnNumber);
                        intent.putExtra(BUNDLE_EXTRA_TITLE_TEXT, btnText);
                        startActivityForResult(intent, EDIT_ACTIVITY_REQUEST_CODE);
                    }
                });
                ll.addView(btn);
            }
        }
    }
}
