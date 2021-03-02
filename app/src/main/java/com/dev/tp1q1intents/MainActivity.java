package com.dev.tp1q1intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "MainActivity";
    private EditText nameEdt, ageEdt, heightEdt;
    private Button mentionBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();
        setListeners();
        getDataFromSecondActivity();
    }

    private void initViews() {
        nameEdt = findViewById(R.id.name_edt);
        ageEdt = findViewById(R.id.age_edt);
        heightEdt = findViewById(R.id.height_edt);
        mentionBtn = findViewById(R.id.mention_btn);
    }

    private void setListeners() {
        mentionBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.mention_btn) {
            EditText[] editTexts = {nameEdt, ageEdt, heightEdt};
            for (EditText editText : editTexts)
                if (isEdittextEmpty(editText)) {
                    dontAllowEmptyEdittext(editText);
                    return;
                }

            sendDataToSecondActivity();
        }
    }

    private boolean isEdittextEmpty(EditText editText) {
        return editText.getText().toString().trim().isEmpty();
    }

    private void dontAllowEmptyEdittext(EditText editText) {
        editText.requestFocus();
        editText.setError("Champ obligatoire!");
    }

    private void sendDataToSecondActivity() {
        Intent intent = new Intent(getBaseContext(), SecondActivity.class);
        intent.putExtra("name", nameEdt.getText().toString());
        intent.putExtra("age", Integer.parseInt(ageEdt.getText().toString()));

        try {
            intent.putExtra("height", Float.parseFloat(heightEdt.getText().toString()));
        } catch (NumberFormatException e) {
            Log.e(TAG, "Traitement échoué !\n" +
                    "Exception : " + e.getClass().getSimpleName() + "\n" +
                    "Message : " + e.getMessage());
            // Log.v(TAG, Log.getStackTraceString(e));
            return;
        }

        Log.v(TAG, "Données envoyées pour traitement ...");

        startActivity(intent);
    }

    private void getDataFromSecondActivity() {
        if (getIntent().hasExtra("name")) {
            nameEdt.setText(getIntent().getStringExtra("name"));
            ageEdt.setText(String.valueOf(getIntent().getIntExtra("age", 0)));
            heightEdt.setText(String.valueOf(getIntent().getFloatExtra("height", 0)));
            Log.i(TAG, "Traitement réussi, données reçues.");
        }
    }
}
