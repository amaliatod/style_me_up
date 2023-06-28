package com.example.stylemeup;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class Form extends Activity {
    private String selectedGender;
    private String selectedStyle;
    private String selectedColor;
    private String selectedAccessories;
    private RadioGroup radioGroupOutfitType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.form);


        // Find views
        RadioGroup genderRadioGroup = findViewById(R.id.radioGroupGender);
        RadioGroup occasionRadioGroup = findViewById(R.id.radioGroupOccasion);
        RadioGroup colorRadioGroup = findViewById(R.id.radioGroupColor);
        RadioGroup accessoriesRadioGroup = findViewById(R.id.radioGroupAccesories);
        Button doneButton = findViewById(R.id.buttonDone);

        // Set listeners
        genderRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedGender = selectedRadioButton.getText().toString();
            }
        });

        occasionRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedStyle = selectedRadioButton.getText().toString();
            }
        });

        colorRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedColor = selectedRadioButton.getText().toString();
            }
        });

        accessoriesRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedAccessories = selectedRadioButton.getText().toString();
            }
        });

        doneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedGender == null || selectedStyle == null || selectedColor == null || selectedAccessories == null) {
                    ErrorUtils.showErrorDialog(Form.this,"", "Please answer all questions.");
                    return;
                }
                if (selectedGender.equals("Female")) {
                    Intent intent = new Intent(Form.this, Female_outfit.class);
//
//                // Pass the selected information to the next activity using intent extras
                    intent.putExtra("selectedGender", selectedGender);
                    intent.putExtra("selectedStyle", selectedStyle);
                    intent.putExtra("selectedColor", selectedColor);
                    intent.putExtra("selectedAccessories", selectedAccessories);
//
//                // Start the next activity
                    startActivity(intent);
                } else {
                    // Create an intent to start the desired activity
                    Intent intent = new Intent(Form.this, MainActivity2.class);
//
//                // Pass the selected information to the next activity using intent extras
                    intent.putExtra("selectedGender", selectedGender);
                    intent.putExtra("selectedStyle", selectedStyle);
                    intent.putExtra("selectedColor", selectedColor);
                    intent.putExtra("selectedAccessories", selectedAccessories);
//
//                // Start the next activity
                    startActivity(intent);
                }
            }
        });
    }
}


