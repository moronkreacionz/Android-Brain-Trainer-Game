package com.moronkreacionz.braintrainer20170316;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    Button startGameButton ;
    GridLayout answerGridLayout;
    TextView questionTextView , resultTextView, scoreTextView ,timerTextView  ;
    Boolean gameIsActive = false;
    int locationOfCorrectAnswer;
    int score=0; // quiz score
    int numberOfQuestions=0; // no of Questions
    int totalQuestions = 15 ;

    private final String INITIAL_SCORE ="0/0";
    private final int MILLISECONDS= 1000;
    private final int GAME_TIMER_IN_SECONDS = 10;
    private final String INITIAL_GAME_TIMER ="30s";
    private final String RESULT_CORRECT ="Correct!";
    private final String RESULT_INCORRECT ="Incorrect!";
    private final String SECONDS_TIMER_SUFFIX = "s";

    ArrayList<Integer> answersArray = new ArrayList<Integer>();

    public void checkAnswer(View view){

        if(gameIsActive){
            //Log.i("checking answer", "answer clicked");
            //Button answerButton = (Button) view;
            //Log.i("answer tapped is:", (String) view.getTag());
            // show result after answer is compared for each question

            if(locationOfCorrectAnswer == Integer.parseInt((String) view.getTag())){
                // Log.i("match found","answer is at location "+ locationOfCorrectAnswer );
                score++;
                resultTextView.setText(RESULT_CORRECT);
            }
            else
            {
                //Log.i("match not found","answer is not at location " + (String) view.getTag() );
                resultTextView.setText(RESULT_INCORRECT);
            }

            numberOfQuestions++;
            resultTextView.setVisibility(View.VISIBLE);
            scoreTextView.setText(Integer.toString(score)+"/"+Integer.toString(numberOfQuestions));
            // answerButton.setText(Integer.toString(answersArray.get(i)));

            generateQuestion();
            // after result is displayed for all questions
            // answerGridLayout.setVisibility(View.INVISIBLE);
            // startGameButton.setVisibility(View.VISIBLE);

        }
    }

    public void generateQuestion(){

        Random rand = new Random();

        int maxRange = 21;
        int a = rand.nextInt(maxRange) ;
        int b = rand.nextInt(maxRange) ;
        int incorrectAnswer ;

        int correctAnswer = a + b ;
        //Log.i("correctAnswer", "value "+Integer.toString(correctAnswer));

        answersArray.clear();

        questionTextView.setText(Integer.toString(a) + " + " + Integer.toString(b)+ " = ? ");

        locationOfCorrectAnswer = rand.nextInt(4);
        for( int i = 0 ; i < 4 ; i++ ){
            if(locationOfCorrectAnswer == i ) {
                answersArray.add(correctAnswer);
            }else{
                incorrectAnswer = rand.nextInt(2*maxRange);
                while(incorrectAnswer == correctAnswer){
                    incorrectAnswer = rand.nextInt(2*maxRange);
                }
                answersArray.add( incorrectAnswer );
            }
            //Log.i("answer array value", "value "+Integer.toString(answersArray.get(i)));
        }//


        for( int i = 0 ; i < answerGridLayout.getChildCount(); i++){
            Button answerButton = (Button)answerGridLayout.getChildAt(i);
            answerButton.setText(Integer.toString(answersArray.get(i)));
            //Log.i("answer loop", "value"+Integer.toString(answersArray.get(i)));
        }
    }

    public void finishGame(){

        gameIsActive = false;
        Log.i("timer finish:", "game over");
        timerTextView.setText("0"+SECONDS_TIMER_SUFFIX);

        // disable game board
        updateGameBoardStatus(false);

        // show score at bottom result section
        resultTextView.setText("Your Score is "+scoreTextView.getText());

        // show start button
        startGameButton.setText("Game Over \n Play Again!");
        startGameButton.setVisibility(View.VISIBLE);

    }

    public void startTraining(View view){
        Log.i("restart game:", "play game invoked");
        startGameButton.setVisibility(View.INVISIBLE);
        gameIsActive = true;
        playGame();
    }

    public void updateGameBoardStatus(Boolean status){
        answerGridLayout.setEnabled(true);
        for( int i = 0 ; i < answerGridLayout.getChildCount(); i++){
            Button answerButton = (Button)answerGridLayout.getChildAt(i);
            answerButton.setEnabled(status);
        }
    }

    public void playGame(){
        updateGameBoardStatus(true);
        resultTextView.setVisibility(View.INVISIBLE);
        timerTextView.setText(INITIAL_GAME_TIMER);
        scoreTextView.setText(INITIAL_SCORE);
        score=0;
        numberOfQuestions=0;

        generateQuestion(); // start the game with first question

        new CountDownTimer( ((GAME_TIMER_IN_SECONDS * MILLISECONDS)+100), 1000){

            @Override
            public void onTick(long millisUntilFinished) {
                timerTextView.setText(String.valueOf((int)millisUntilFinished/MILLISECONDS)+SECONDS_TIMER_SUFFIX);
            }

            @Override
            public void onFinish() {
                finishGame();
            }
        }.start();

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        startGameButton = (Button) findViewById(R.id.startGameButton);
        questionTextView = (TextView) findViewById(R.id.questionTextView);
        answerGridLayout = (GridLayout) findViewById(R.id.answerGridLayout);
        resultTextView = (TextView) findViewById(R.id.resultTextView);
        timerTextView = (TextView) findViewById(R.id.timerTextView);
        scoreTextView = (TextView) findViewById(R.id.scoreTextView);
        resultTextView.setSingleLine(false);
        resultTextView.setVisibility(View.INVISIBLE);
        startGameButton.setVisibility(View.VISIBLE);
        startGameButton.setText("Lets Start!");
        updateGameBoardStatus(false);

    }
}
