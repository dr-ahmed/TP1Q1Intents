package com.dev.tp1q1intents;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SecondActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView infoTxt;
    private Button goBackBtn;
    private String name, mention;
    private int age;
    private float height;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        initViews();
        setListeners();
        setDataFromMainActivityIntent();
        calculateMention();
        displayMessage();
    }

    private void initViews() {
        infoTxt = findViewById(R.id.details_txt);
        goBackBtn = findViewById(R.id.go_back_btn);
    }

    private void setDataFromMainActivityIntent() {
        name = getIntent().getStringExtra("name");
        age = getIntent().getIntExtra("age", 0);
        height = getIntent().getFloatExtra("height", 0);
    }

    private void calculateMention() {
        mention = (height == 1.7f) ? "moyenne" : (height > 1.7f ? "élancée" : "courte");
    }

    private void displayMessage() {
        StringBuilder message = new StringBuilder();
        message.append("Bonjour ")
                .append(name)
                .append(", ")
                .append("vous êtes âgé de ")
                .append(age)
                .append(" et vous êtes de taille ")
                .append(mention);
        infoTxt.setText(message);
    }

    private void setListeners() {
        goBackBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.go_back_btn)
            sendDataToMainActivity();
    }

    private void sendDataToMainActivity() {
        Intent intent = new Intent(getBaseContext(), MainActivity.class);
        intent.putExtra("name", name);
        intent.putExtra("age", age);
        intent.putExtra("height", height);
        startActivity(intent);
    }
}
