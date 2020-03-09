package com.example.kathe.quizletjr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FlashcardDatabase flashcardDatabase;
    List<Flashcard> allFlashcards;
    int currentCardDisplayedIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        allFlashcards = flashcardDatabase.getAllCards();

        // Show a saved flashcard in the database; if not, then show default
        if (allFlashcards != null && allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(0).getAnswer());
        }

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

        findViewById(R.id.nextBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // advance our pointer index so we can show the next card
                currentCardDisplayedIndex++;

                // make sure we don't get an IndexOutOfBoundsError if we are viewing the last indexed card in our list
                if (currentCardDisplayedIndex > allFlashcards.size() - 1) {
                    currentCardDisplayedIndex = 0;
                }

                // Only do stuff if there are cards in the database
                if (allFlashcards.size() > 0) {
                    // set the question and answer TextViews with data from the database
                    ((TextView) findViewById(R.id.flashcard_question)).setText(allFlashcards.get(currentCardDisplayedIndex).getQuestion());
                    ((TextView) findViewById(R.id.flashcard_answer)).setText(allFlashcards.get(currentCardDisplayedIndex).getAnswer());
                    findViewById(R.id.flashcard_question).setVisibility(View.VISIBLE);
                    findViewById(R.id.flashcard_answer).setVisibility(View.INVISIBLE);
                }
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

                flashcardDatabase.insertCard(new Flashcard(newQ, newA));
                allFlashcards = flashcardDatabase.getAllCards();


                // set our pointer index to the end so we can show the added card
                currentCardDisplayedIndex = allFlashcards.size() - 1;

            }
        }
    }
}
