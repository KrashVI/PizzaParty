package com.zybooks.pizzaparty;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = "Sean";

    public final int SLICES_PER_PIZZA = 8;

    private EditText mNumAttendEditText;
    private TextView mNumPizzasTextView;
//    private RadioGroup mHowHungryRadioGroup;
    private Spinner mHowHungrySpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNumAttendEditText = findViewById(R.id.num_attend_edit_text);
        mNumAttendEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //We leave this area blank, as we do not wish to clear the text beforehand
                //From my understanding, the CharSequence s is our placeholder for the string before it gets changed
                //Then the start, count, and after are all interacting with the string character by character using index references
                //For instance, start takes into account the first changed part of the string
                //Count keeps track of the length of changes
                //After takes into account the length of the new text
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //In this method, we are triggering the blank string when text gets changed
                //The arguments supplied are similar to the above method, however they are triggered on the change instead of before
                mNumAttendEditText.setText("");
            }

            @Override
            public void afterTextChanged(Editable s) {
                //Now that changes have been made, Editable s becomes our new string
                //This can be used in the above methods once again, should we need it. Rinse and Repeat so to speak
            }
        });
        mNumPizzasTextView = findViewById(R.id.num_pizzas_text_view);
        mHowHungrySpinner = findViewById(R.id.hunger_level);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.hunger_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mHowHungrySpinner.setAdapter(adapter);
        mHowHungrySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id){
                String item = (String)parent.getItemAtPosition(position);
                Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent){

            }
        });

        //Log.d(TAG, "onCreate was called");
    }

    public void calculateClick(View view){
        String numAttendStr = mNumAttendEditText.getText().toString();
        int numAttend = Integer.parseInt(numAttendStr);

        int slicesPerPerson = 0;
        String selected = String.valueOf(mHowHungrySpinner.getSelectedItemPosition());
        if(selected.equals("Light")){
            slicesPerPerson = 2;
        }
        else if(selected.equals("Medium")){
            slicesPerPerson = 3;
        }
        else if(selected.equals("Ravenous")){
            slicesPerPerson = 4;
        }
        int totalPizzas = (int) Math.ceil(numAttend * slicesPerPerson /
                (double) SLICES_PER_PIZZA);
        mNumPizzasTextView.setText("Total pizzas: " + totalPizzas);
    }
}