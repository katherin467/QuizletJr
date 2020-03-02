package com.example.kathe.quizletjr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.flashcard_question).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                findViewById(R.id.flashcard_question).setVisibility(View.INVISIBLE);
                findViewById(R.id.flashcard_answer).setVisibility(View.VISIBLE);
            }
        });

        findViewById(R.id.addBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        findViewById(R.id.editBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView curQuestionTxt = findViewById(R.id.flashcard_question);
                final String curQuestion = curQuestionTxt.getText().toString();
                TextView curAnswerTxt = /*(EditText)*/ findViewById(R.id.flashcard_answer);
                final String curAnswer = curAnswerTxt.getText().toString();

                Intent intent = new Intent(MainActivity.this, AddCardActivity.class);
                intent.putExtra("curQ", curQuestion);
                intent.putExtra("curA", curAnswer);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100) { // match the 100 when we called startActivityForResult!
            if (resultCode == RESULT_OK) {
                String newQ = data.getExtras().getString("q");
                String newA = data.getExtras().getString("a");

                TextView tv1 = findViewById(R.id.flashcard_question);
                tv1.setText(newQ);
                tv1.setVisibility(View.VISIBLE);

                TextView tv2 = findViewById(R.id.flashcard_answer);
                tv2.setText(newA);
                tv2.setVisibility(View.INVISIBLE);
            }
        }
    }
}
