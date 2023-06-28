package com.example.stylemeup;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
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

public class MainActivity3 extends Activity {

    private boolean accessoriesIncluded = false;
    private List<String> list_without_shoes= new ArrayList<>(Arrays.asList("TOPS", "BOTTOMS"));
    private List<String> list_without_bottoms= new ArrayList<>(Arrays.asList("TOPS", "SHOES"));
    private List<String> list_without_tops= new ArrayList<>(Arrays.asList("BOTTOMS", "SHOES"));

    private List<String> list_of_bottoms = new ArrayList<>(Arrays.asList(
            "Jeans", "Pants", "Trousers", "Leggings", "Shorts", "Skirt", "Midi Skirt", "Maxi Skirt", "Pencil Skirt", "A-line Skirt", "Pleated Skirt", "Denim Skirt", "Culottes", "Capri Pants", "Cargo Pants", "Chinos", "Corduroy Pants", "Flared Pants", "Wide-Leg Pants", "Joggers", "Track Pants", "Palazzo Pants", "High-Waisted Pants", "Low-Waisted Pants", "Cropped Pants", "Paperbag Waist Pants", "Skinny Pants", "Straight-Leg Pants", "Bootcut Pants", "Tapered Pants", "Printed Pants", "Patterned Pants", "Checkered Pants", "Striped Pants", "Leather Pants", "Faux Leather Pants", "Velvet Pants", "Satin Pants", "Sequined Pants", "Metallic Pants", "Wide-Leg Jeans", "Skinny Jeans", "Boyfriend Jeans", "Bootcut Jeans", "Flared Jeans", "Cropped Jeans", "High-Waisted Jeans", "Low-Waisted Jeans", "Distressed Jeans", "Ripped Jeans", "Patchwork Jeans", "Acid Wash Jeans", "Mom Jeans", "Cargo Shorts", "Denim Shorts", "Bermuda Shorts", "Chino Shorts", "Linen Shorts", "Athletic Shorts", "Pleated Shorts", "High-Waisted Shorts", "Low-Waisted Shorts", "Paperbag Waist Shorts", "Culotte Shorts", "Skort", "Leather Skirt", "Pleated Leather Skirt", "Denim Culottes", "High-Waisted Culottes", "Paperbag Waist Culottes", "Linen Pants", "Linen Culottes", "Linen Skirt", "Crochet Skirt", "Satin Skirt", "Velvet Skirt", "Sequined Skirt", "Metallic Skirt", "Wrap Skirt", "Flounce Skirt", "Tiered Skirt", "Asymmetrical Skirt", "Ruffled Skirt", "Pleated Midi Skirt", "Pleated Maxi Skirt", "A-line Midi Skirt", "A-line Maxi Skirt", "Mermaid Skirt", "High-Low Skirt", "Peplum Skirt", "Tulip Skirt", "Bubble Skirt", "Slit Skirt", "Gaucho Pants", "Harem Pants", "Jeggings", "Cigarette Pants", "Carrot Pants", "Track Pants", "Drawstring Pants"
    ));
    private List<String> list_of_shoes = new ArrayList<>(Arrays.asList(
            "Shoe", "Footwear", "Sneaker", "Boot", "High heels", "Sandal", "Slipper", "Flip-flop", "Loafer", "Oxford shoe", "Ballet flat", "Moccasin", "Espadrille", "Wedge", "Clog", "Platform shoe", "Stiletto", "Running shoe", "Athletic shoe", "Hiking boot", "Work boot", "Dress shoe", "Formal shoe", "Casual shoe", "Tennis shoe", "Soccer cleat", "Basketball shoe", "Golf shoe", "Skate shoe", "Bowling shoe", "Cycling shoe", "Sandalwood slipper", "Boat shoe", "Rain boot", "Snow boot", "Winter boot", "Cowboy boot", "Riding boot", "Ankle boot", "Combat boot", "Chelsea boot", "Wellington boot", "Wader", "Golf sandal", "Soccer shoe", "Football cleat", "Baseball cleat", "Softball cleat", "Track shoe", "Running sneaker", "Training shoe", "Crossfit shoe", "Trail running shoe", "Hiking shoe", "Climbing shoe", "Ballet shoe", "Tap shoe", "Jazz shoe", "Dance shoe", "Figure skate", "Ice skate", "Roller skate", "Slip-on shoe", "Walking shoe", "Canvas shoe", "Crocs", "Jelly shoe", "Mary Jane shoe", "Monk shoe", "Saddle shoe", "Slingback shoe", "T-bar shoe", "T-strap shoe", "Woven shoe", "Peep-toe shoe", "Pointed-toe shoe", "Square-toe shoe", "Round-toe shoe", "Wingtip shoe", "Derby shoe", "Brogue shoe", "Monk strap shoe", "Boat shoe", "Driving shoe", "Penny loafer", "Suede shoe", "Leather shoe", "Patent leather shoe", "Synthetic shoe", "Mesh shoe", "Slip-resistant shoe", "Chef shoe", "Nursing shoe", "Orthopedic shoe", "Arch support shoe", "Diabetic shoe", "Wide-fit shoe", "Narrow-fit shoe", "Vintage shoe", "Retro shoe"
    ));
    private List<String> list_of_tops = new ArrayList<>(Arrays.asList("Shirt", "T-shirt", "Blouse", "Top", "Tank top", "Sweater", "Pullover", "Hoodie", "Jacket", "Coat", "Blazer", "Cardigan", "Vest", "Sweatshirt", "Crop top", "Camisole", "Tube top", "Peplum top", "Wrap top", "Off-the-shoulder top", "Cold shoulder top", "Kimono top", "Bell sleeve top", "Tunic", "Polo shirt", "Button-down shirt", "Flannel shirt", "Denim shirt", "Chambray shirt", "Oversized shirt", "Tunic shirt", "Turtleneck", "Cowl neck top", "Halter top", "Bustier", "Corset top", "Bodysuit", "Sleeveless top", "Long-sleeve top", "Ruffle top", "Puff sleeve top", "Embroidered top", "Lace top", "Sheer top", "Mesh top", "Printed top", "Striped top", "Polka dot top", "Plaid top", "Checked top", "Colorblock top", "Graphic top", "Logo top", "Sequined top", "Metallic top", "Velvet top", "Silk top", "Chiffon top", "Satin top", "Linen top", "Cotton top", "Knit top", "Ribbed top", "Crochet top", "Embellished top", "High-neck top", "V-neck top", "Scoop neck top", "Square neck top", "Round neck top", "Boat neck top", "One-shoulder top", "Asymmetrical top", "Wrap front top", "Bow tie top", "Tie-front top", "Tie-back top", "Peplum blouse", "Pleated top", "Smocked top", "Ruched top", "Wrap blouse", "Keyhole top", "Cropped sweater", "Oversized sweater", "Cable knit sweater", "Chunky knit sweater", "Cashmere sweater", "Turtleneck sweater", "Crew neck sweater", "V-neck sweater", "Cardigan sweater", "Hooded sweater", "Tie-dye sweater", "Striped sweater", "Fair isle sweater", "Patterned sweater", "Embroidered sweater", "Lace sweater"
    ));




    @SuppressLint("WrongThread")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

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

            Database_woman db = new Database_woman(MainActivity3.this);


            ImageView imageView1 = findViewById(R.id.imageView1);
            ImageView imageView2 = findViewById(R.id.imageView2);
            ImageView imageView3 = findViewById(R.id.imageView3);
            TextView textView = findViewById(R.id.textView1);
            textView.setText("You can pair your "+correct_Label.toLowerCase()+" with:");



            imageView1.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));
            imageView2.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));
            imageView3.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));

            // Set the image resources and other attributes
            imageView1.setImageResource(R.drawable.random_pic);
            imageView1.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView1.setRotation(45);

            imageView2.setImageResource(R.drawable.random_pic);
            imageView2.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView2.setRotation(15);

            imageView3.setImageResource(R.drawable.random_pic);
            imageView3.setScaleType(ImageView.ScaleType.FIT_CENTER);
            imageView3.setRotation(-30);


            imageView1.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));
            imageView2.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));
            imageView3.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));


            imageView1.setImageBitmap(null);
            imageView2.setImageBitmap(null);



            // Load new images
            if (db != null) {
                if (list_of_bottoms.contains(correct_Label)) {
                    if(accessoriesIncluded){
                        list_without_bottoms.add("ACCESSORIES");
                    }
                    for (int i = 0; i < list_without_bottoms.size(); i++) {
                        String randomImageUrl = db.getRandomImageUrl(list_without_bottoms.get(i), selectedStyle, selectedColor);
                        if (randomImageUrl != null) {
                            System.out.println("image" + randomImageUrl);
                            // Use the random image URL to load the image into imageView
                            if (i == 0) {
                                Picasso.get().load(randomImageUrl).into(imageView1);
                            }
                            if (i == 1) {
                                Picasso.get().load(randomImageUrl).into(imageView2);
                            }
                            if( i==2 ) {
                                Picasso.get().load(randomImageUrl).into(imageView3);
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
                            if( i==2 ) {
                                Picasso.get().load(randomImageUrl).into(imageView3);
                            }

                        }
                    }
                } else {
                    if (list_of_tops.contains(correct_Label)) {
                        if(accessoriesIncluded){
                            list_without_tops.add("ACCESSORIES");
                        }
                        for (int i = 0; i < list_without_tops.size(); i++) {
                            String randomImageUrl = db.getRandomImageUrl(list_without_tops.get(i), selectedStyle, selectedColor);
                            if (randomImageUrl != null) {
                                System.out.println("image" + randomImageUrl);
                                // Use the random image URL to load the image into imageView
                                if (i == 0) {
                                    Picasso.get().load(randomImageUrl).into(imageView1);
                                }
                                if (i == 1) {
                                    Picasso.get().load(randomImageUrl).into(imageView2);
                                }
                                if( i==2 ) {
                                    Picasso.get().load(randomImageUrl).into(imageView3);
                                }

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
            context = MainActivity3.this;
            db = new Database_woman(context);
        }

        // Find the ImageView references
        ImageView imageView1 = findViewById(R.id.imageView1);
        ImageView imageView2 = findViewById(R.id.imageView2);
        ImageView imageView3 = findViewById(R.id.imageView3);
        TextView textView = findViewById(R.id.textView1);
        textView.setText("You can pair your "+correct_Label.toLowerCase()+" with:");

        if (selectedAccessories.equals("Yes")) {

            imageView3.setVisibility(View.VISIBLE);
            accessoriesIncluded = true;
        } else {

            imageView3.setVisibility(View.GONE);
        }


        imageView1.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));
        imageView2.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));
        imageView3.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));


        // Set the image resources and other attributes
        imageView1.setImageResource(R.drawable.random_pic);
        imageView1.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView1.setRotation(45);

        imageView2.setImageResource(R.drawable.random_pic);
        imageView2.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView2.setRotation(15);


        imageView3.setImageResource(R.drawable.random_pic);
        imageView3.setScaleType(ImageView.ScaleType.FIT_CENTER);

        imageView3.setRotation(-30);


        imageView1.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));
        imageView2.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));
        imageView3.setLayoutParams(new LinearLayout.LayoutParams(desiredWidth, desiredHeight));


        if (db != null) {
            if (list_of_bottoms.contains(correct_Label)) {
                if(accessoriesIncluded){
                    list_without_bottoms.add("ACCESSORIES");
                }
                for (int i = 0; i < list_without_bottoms.size(); i++) {
                    String randomImageUrl = db.getRandomImageUrl(list_without_bottoms.get(i), selectedStyle, selectedColor);
                    if (randomImageUrl != null) {
                        System.out.println("image" + randomImageUrl);
                        // Use the random image URL to load the image into imageView
                        if (i == 0) {
                            Picasso.get().load(randomImageUrl).into(imageView1);
                        }
                        if (i == 1) {
                            Picasso.get().load(randomImageUrl).into(imageView2);
                        }
                        if( i==2 ) {
                            Picasso.get().load(randomImageUrl).into(imageView3);
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
                        if( i==2 ) {
                            Picasso.get().load(randomImageUrl).into(imageView3);
                        }

                    }
                }
            } else if (list_of_tops.contains(correct_Label)) {
                    if(accessoriesIncluded){
                        list_without_tops.add("ACCESSORIES");
                    }
                    for (int i = 0; i < list_without_tops.size(); i++) {
                        String randomImageUrl = db.getRandomImageUrl(list_without_tops.get(i), selectedStyle, selectedColor);
                        if (randomImageUrl != null) {
                            System.out.println("image" + randomImageUrl);
                            // Use the random image URL to load the image into imageView
                            if (i == 0) {
                                Picasso.get().load(randomImageUrl).into(imageView1);
                            }
                            if (i == 1) {
                                Picasso.get().load(randomImageUrl).into(imageView2);
                            }
                            if( i==2 ) {
                                Picasso.get().load(randomImageUrl).into(imageView3);
                            }

                        }
                    }
                }
            }
        }
    }







