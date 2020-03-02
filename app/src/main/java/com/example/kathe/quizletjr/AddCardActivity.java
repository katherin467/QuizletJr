package com.example.kathe.quizletjr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddCardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);

        String curQuestion = getIntent().getStringExtra("curQ");
        String curAnswer = getIntent().getStringExtra("curA");

        EditText et1 = findViewById(R.id.question_input);
        et1.setText(curQuestion);
        EditText et2 = findViewById(R.id.question_answer);
        et2.setText(curAnswer);

        findViewById(R.id.saveBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EditText questionTxt = /*(EditText)*/ findViewById(R.id.question_input);
                final String question = questionTxt.getText().toString();
                EditText answerTxt = /*(EditText)*/ findViewById(R.id.question_answer);
                final String answer = answerTxt.getText().toString();

                if (question.equals("") || answer.equals("")) {
                    Toast.makeText(getApplicationContext(),
                        "Must enter both a question and answer!", Toast.LENGTH_SHORT).show();
                    //displayToast("Must enter both a question and answer!");
                    return;
                }

                Intent data = new Intent(AddCardActivity.this, MainActivity.class);
                data.putExtra("q", question);
                data.putExtra("a", answer);
                setResult(RESULT_OK, data); // set result code and bundle data for response
                finish();
            }
        });

        findViewById(R.id.exitBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    /*
    private void displayToast(String message) {
        // Inflate toast XML layout
        View layout = getLayoutInflater().inflate(R.layout.toast_layout,
                (ViewGroup) findViewById(R.id.toast_layout_root));
        // Fill in the message into the textview
        TextView text =  layout.findViewById(R.id.text);
        text.setText(message);
        // Construct the toast, set the view and display
        Toast toast = new Toast(getApplicationContext());
        toast.setView(layout);
        toast.show();
    } */

}

