package com.example.stylemeup;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Female_outfit extends AppCompatActivity {

    private ImageView imageView1;
    private ImageView imageView2;
    private String selectedGender, selectedStyle,selectedColor,selectedAccessories;
    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.female_outfit);

        imageView1 = findViewById(R.id.imageView1);
        imageView2 = findViewById(R.id.imageView2);

        Intent intent = getIntent();
         selectedGender = intent.getStringExtra("selectedGender");

        // Retrieve the selected style from the intent
         selectedStyle = intent.getStringExtra("selectedStyle");

        // Retrieve the selected color from the intent
         selectedColor = intent.getStringExtra("selectedColor");

         selectedAccessories = intent.getStringExtra("selectedAccessories");


        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event for imageView1
                navigateToNextActivity("Image 1");
            }
        });

        imageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle click event for imageView2
                navigateToNextActivity("Image 2");
            }
        });
    }

    private void navigateToNextActivity(String imageClicked) {

        intent = new Intent(Female_outfit.this, MainActivity2.class);


        intent.putExtra("clicked_image", imageClicked);
        intent.putExtra("selectedGender", selectedGender);
        intent.putExtra("selectedStyle", selectedStyle);
        intent.putExtra("selectedColor", selectedColor);
        intent.putExtra("selectedAccessories", selectedAccessories);
        startActivity(intent);
    }

}

