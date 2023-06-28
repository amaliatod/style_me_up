package com.example.stylemeup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity5 extends Activity {
    private Bitmap selectedImageBitmap;
    private boolean accessoriesIncluded = false;
    private List<String> list_without_shoes= new ArrayList<>(Arrays.asList("DRESSES"));
    private List<String> list_without_dress = new ArrayList<>(Arrays.asList("SHOES"));


    private List<String> list_of_dresses = new ArrayList<>(Arrays.asList("Dress"));
    private List<String> list_of_shoes = new ArrayList<>(Arrays.asList(
            "Shoe", "Footwear", "Sneaker", "Boot", "High heels", "Sandal", "Slipper", "Flip-flop", "Loafer", "Oxford shoe", "Ballet flat", "Moccasin", "Espadrille", "Wedge", "Clog", "Platform shoe", "Stiletto", "Running shoe", "Athletic shoe", "Hiking boot", "Work boot", "Dress shoe", "Formal shoe", "Casual shoe", "Tennis shoe", "Soccer cleat", "Basketball shoe", "Golf shoe", "Skate shoe", "Bowling shoe", "Cycling shoe", "Sandalwood slipper", "Boat shoe", "Rain boot", "Snow boot", "Winter boot", "Cowboy boot", "Riding boot", "Ankle boot", "Combat boot", "Chelsea boot", "Wellington boot", "Wader", "Golf sandal", "Soccer shoe", "Football cleat", "Baseball cleat", "Softball cleat", "Track shoe", "Running sneaker", "Training shoe", "Crossfit shoe", "Trail running shoe", "Hiking shoe", "Climbing shoe", "Ballet shoe", "Tap shoe", "Jazz shoe", "Dance shoe", "Figure skate", "Ice skate", "Roller skate", "Slip-on shoe", "Walking shoe", "Canvas shoe", "Crocs", "Jelly shoe", "Mary Jane shoe", "Monk shoe", "Saddle shoe", "Slingback shoe", "T-bar shoe", "T-strap shoe", "Woven shoe", "Peep-toe shoe", "Pointed-toe shoe", "Square-toe shoe", "Round-toe shoe", "Wingtip shoe", "Derby shoe", "Brogue shoe", "Monk strap shoe", "Boat shoe", "Driving shoe", "Penny loafer", "Suede shoe", "Leather shoe", "Patent leather shoe", "Synthetic shoe", "Mesh shoe", "Slip-resistant shoe", "Chef shoe", "Nursing shoe", "Orthopedic shoe", "Arch support shoe", "Diabetic shoe", "Wide-fit shoe", "Narrow-fit shoe", "Vintage shoe", "Retro shoe"
    ));





    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);

        Button regenerateButton = findViewById(R.id.regenerateButton);

        regenerateButton.setOnClickListener(view -> {
            // Clear existing images

            Intent intent = getIntent();

            // Retrieve the selected gender from the intent
            String selectedGender = intent.getStringExtra("selectedGender");

            // Retrieve the selected style from the intent
            String selectedStyle = intent.getStringExtra("selectedStyle");

            // Retrieve the selected color from the intent
            String selectedColor = intent.getStringExtra("selectedColor");


            String correct_Label = intent.getStringExtra("correct_Label");

            System.out.println(correct_Label);
            int desiredWidth = 500;
            int desiredHeight = 500;

            Database_woman db = new Database_woman(MainActivity5.this);


            ImageView imageView1 = findViewById(R.id.imageView1);
            ImageView imageView2 = findViewById(R.id.imageView2);
            TextView textView = findViewById(R.id.textView1);
            textView.setText("You can pair your "+correct_Label.toLowerCase()+" with:");

            imageView1.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));
            imageView2.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));


            // Set the image resources and other attributes
            imageView1.setImageResource(R.drawable.random_pic);
            imageView1.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView1.setRotation(45);

            imageView2.setImageResource(R.drawable.random_pic);
            imageView2.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView2.setRotation(15);


            imageView1.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));
            imageView2.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));



            imageView1.setImageBitmap(null);
            imageView2.setImageBitmap(null);



            // Load new images
            if (db != null) {
                if (list_of_dresses.contains(correct_Label)) {
                    if(accessoriesIncluded){
                        list_without_dress.add("ACCESSORIES");
                    }
                    for (int i = 0; i < list_without_dress.size(); i++) {
                        String randomImageUrl = db.getRandomImageUrl(list_without_dress.get(i), selectedStyle, selectedColor);
                        if (randomImageUrl != null) {
                            System.out.println("image" + randomImageUrl);
                            // Use the random image URL to load the image into imageView
                            if (i == 0) {
                                Picasso.get().load(randomImageUrl).into(imageView1);
                            }
                            if (i == 1) {
                                Picasso.get().load(randomImageUrl).into(imageView2);
                            }
                        }
                    }
                } else if (list_of_shoes.contains(correct_Label)) {
                    if(accessoriesIncluded){
                        list_without_shoes.add("ACCESSORIES");
                    }
                    for (int i = 0; i < list_without_shoes.size(); i++) {
                        String randomImageUrl = db.getRandomImageUrl(list_without_shoes.get(i), selectedStyle, selectedColor);
                        if (randomImageUrl != null) {
                            System.out.println("image" + randomImageUrl);
                            // Use the random image URL to load the image into imageView
                            if (i == 0) {
                                Picasso.get().load(randomImageUrl).into(imageView1);
                            }
                            if (i == 1) {
                                Picasso.get().load(randomImageUrl).into(imageView2);
                            }
                        }
                    }
                }
            }
        });

        Intent intent = getIntent();

        // Retrieve the selected gender from the intent
        String selectedGender = intent.getStringExtra("selectedGender");

        // Retrieve the selected style from the intent
        String selectedStyle = intent.getStringExtra("selectedStyle");

        // Retrieve the selected color from the intent
        String selectedColor = intent.getStringExtra("selectedColor");
        String selectedAccessories = intent.getStringExtra("selectedAccessories");

        String correct_Label = intent.getStringExtra("correct_Label");

        System.out.println(correct_Label);

        int desiredWidth = 500;
        int desiredHeight = 500;

        Context context;
        Database_woman db = null;
        if (selectedGender.equals("Female")) {
            context = MainActivity5.this;
            db = new Database_woman(context);
        }

        // Find the ImageView references
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        TextView textView = findViewById(R.id.textView1);
        textView.setText("You can pair your "+ correct_Label.toLowerCase()+" with:");

        if (selectedAccessories.equals("Yes")) {
            imageView2.setVisibility(View.VISIBLE);
            accessoriesIncluded = true;
        } else {
            imageView2.setVisibility(View.GONE);
        }


        imageView1.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));
        imageView2.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));

        // Set the image resources and other attributes
        imageView1.setImageResource(R.drawable.random_pic);
        imageView1.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView1.setRotation(45);

        imageView2.setImageResource(R.drawable.random_pic);
        imageView2.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView2.setRotation(15);

        imageView1.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));
        imageView2.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));

        if (db != null) {
            if (list_of_dresses.contains(correct_Label)) {
                if(accessoriesIncluded){
                    list_without_dress.add("ACCESSORIES");
                }
                for (int i = 0; i < list_without_dress.size(); i++) {
                    String randomImageUrl = db.getRandomImageUrl(list_without_dress.get(i), selectedStyle, selectedColor);
                    if (randomImageUrl != null) {
                        System.out.println("image" + randomImageUrl);
                        // Use the random image URL to load the image into imageView
                        if (i == 0) {
                            Picasso.get().load(randomImageUrl).into(imageView1);
                        }
                        if (i == 1) {
                            Picasso.get().load(randomImageUrl).into(imageView2);
                        }
                    }
                }
            } else if (list_of_shoes.contains(correct_Label)) {
                if(accessoriesIncluded){
                    list_without_shoes.add("ACCESSORIES");
                }
                for (int i = 0; i < list_without_shoes.size(); i++) {
                    String randomImageUrl = db.getRandomImageUrl(list_without_shoes.get(i), selectedStyle, selectedColor);
                    if (randomImageUrl != null) {
                        System.out.println("image" + randomImageUrl);
                        // Use the random image URL to load the image into imageView
                        if (i == 0) {
                            Picasso.get().load(randomImageUrl).into(imageView1);
                        }
                        if (i == 1) {
                            Picasso.get().load(randomImageUrl).into(imageView2);
                        }
                    }
                }
            }
        }
    }






}
