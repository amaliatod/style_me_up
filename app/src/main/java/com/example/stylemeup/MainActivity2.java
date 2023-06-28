package com.example.stylemeup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity2 extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView;
    private Button pickButton, nextButton;
    private String correct_Label;
    private Bitmap selectedImageBitmap;
    Intent intent2;
    String clickedImage;
    String selectedGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        String selectedGender = intent.getStringExtra("selectedGender");
        if (selectedGender.equals("Female")){
            setContentView(R.layout.activity_main2_woman);
        }
        else {
            setContentView(R.layout.activity_main2_man);
        }

        imageView = findViewById(R.id.image_view);
        pickButton = findViewById(R.id.pick_button);
        nextButton = findViewById(R.id.next_button);
        pickButton.setOnClickListener(v -> openGallery());
        nextButton.setVisibility(View.GONE);


        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = getIntent();
                String selectedGender = intent.getStringExtra("selectedGender");

                // Retrieve the selected style from the intent
                String selectedStyle = intent.getStringExtra("selectedStyle");

                // Retrieve the selected color from the intent
                String selectedColor = intent.getStringExtra("selectedColor");
                clickedImage = intent.getStringExtra("clicked_image");

                String selectedAccessories = intent.getStringExtra("selectedAccessories");


                if (selectedGender.equals("Female")) {
                    if(clickedImage.equals("Image 1")) {
                        intent2 = new Intent(MainActivity2.this, MainActivity3.class);
                    } else if (clickedImage.equals("Image 2"))
                    {
                        intent2 = new Intent(MainActivity2.this, MainActivity5.class);

                    }
                    intent2.putExtra("selectedGender", selectedGender); // Pass the compressed data instead of the original bitmap
                    intent2.putExtra("selectedStyle", selectedStyle); // Pass the compressed data instead of the original bitmap
                    intent2.putExtra("selectedColor", selectedColor);
                    intent2.putExtra("selectedAccessories", selectedAccessories);
                    String correctLabel = getFirstLabel();
                    intent2.putExtra("correct_Label", correctLabel);
                    startActivity(intent2);

                }
                else {
                    Intent intent2 = new Intent(MainActivity2.this, MainActivity4.class);
                    intent2.putExtra("selectedGender", selectedGender); // Pass the compressed data instead of the original bitmap
                    intent2.putExtra("selectedStyle", selectedStyle); // Pass the compressed data instead of the original bitmap
                    intent2.putExtra("selectedColor", selectedColor);
                    intent2.putExtra("selectedAccessories", selectedAccessories);
                    String correctLabel = getFirstLabel();
                    intent2.putExtra("correct_Label", correctLabel);
                    startActivity(intent2);

                }

                setResult(RESULT_OK);
                // Finish MainActivity2 to return to Form activity
                finish();
            }
        });



    }

    private void openGallery() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            Uri imageUri = data.getData();
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(selectedImageBitmap);

                // Show additional buttons
                nextButton.setVisibility(View.VISIBLE);


                performImageRecognition(selectedImageBitmap);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void performImageRecognition(Bitmap bitmap) {
        String apiKey = "AIzaSyBegy4c48uXczOI-8DSCwukC808A-gflWw"; // Google Cloud API key
        try {
            // Convert the Bitmap to a base64-encoded string
            String imageBase64 = encodeImageToBase64(bitmap);

            // Create the request URL
            String url = "https://vision.googleapis.com/v1/images:annotate?key=" + apiKey;

            // Create the JSON request payload
            String jsonRequest = "{\"requests\": [{\"image\": {\"content\": \"" + imageBase64 + "\"}, \"features\": [{\"type\": \"OBJECT_LOCALIZATION\", \"maxResults\": 50}]}]}";
            // Send the POST request to the Vision API
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonRequest),
                    response -> {
                        try {
                            // Process the object detection response
                            JSONArray objectAnnotations = response.getJSONArray("responses")
                                    .getJSONObject(0)
                                    .getJSONArray("localizedObjectAnnotations");
                            processObjectDetectionResults(objectAnnotations);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    },
                    error -> {
                        Log.e("ImageRecognition", "Error: " + error.getMessage());
                    });

            // Add the request to the request queue
            RequestQueue queue = Volley.newRequestQueue(this);
            queue.add(request);



        } catch (IOException | JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean isClothingLabel(String label) {
        String[] clothingKeywords = {"t-shirt", "shirt", "polo shirt", "", "tank top", "sweater", "hoodie", "cardigan", "jacket", "coat", "blazer", "suit", "dress", "skirt", "shorts", "pants", "jeans", "leggings", "jumpsuit", "romper", "trousers", "capris", "shawl", "wrap", "poncho", "kimono", "mittens", "stockings", "tights", "robe", "pajamas", "nightgown", "nightdress", "slippers", "sneakers", "running shoes", "sports shoes", "sandals", "flip-flops", "boots", "ankle boots", "heels", "wedges", "flats", "loafers", "oxfords", "mules", "espadrilles", "suspenders"};

        for (String keyword : clothingKeywords) {
            if (label.toLowerCase().contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    private String encodeImageToBase64(Bitmap bitmap) throws IOException {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    private void processObjectDetectionResults(JSONArray objectAnnotations) {
        // Process object detection results
        Intent intent3 = getIntent();
        List<String> dress_oufit = new ArrayList<>(Arrays.asList("Shoe", "Dress"));
        List<String> top_bottoms_oufit = new ArrayList<>(Arrays.asList("Dress"));

        for (int i = 0; i < objectAnnotations.length(); i++) {
            try {
                JSONObject objectAnnotation = objectAnnotations.getJSONObject(i);
                String name = objectAnnotation.getString("name");
                clickedImage = intent3.getStringExtra("clicked_image");
                String gender = intent3.getStringExtra("selectedGender");

                if(isClothingLabel(name)) {

                    if (correct_Label == null) {
                        correct_Label = name;

                    }
                    if (gender.equals("Female")){
                    if(clickedImage.equals("Image 2") && (!(dress_oufit.contains(correct_Label)))){
                        correct_Label = null;
                        ErrorUtils.showErrorDialog(MainActivity2.this,"", "You need to insert a photo with a dress or a photo with a pair of shoes.");
                        nextButton.setVisibility(nextButton.GONE);
                        return;

                    }}
                    if (gender.equals("Female")){
                    if(clickedImage.equals("Image 1") && (top_bottoms_oufit.contains(correct_Label))){
                        correct_Label = null;
                        ErrorUtils.showErrorDialog(MainActivity2.this,"", "You need to insert a photo with a top, bottoms or a photo with a pair of shoes.");
                        nextButton.setVisibility(nextButton.GONE);
                        return;

                    }}

                }
                else{

                        ErrorUtils.showErrorDialog(MainActivity2.this,"", "This is not a clothing item. Please insert another picture");
                        nextButton.setVisibility(nextButton.GONE);
                        return;
                    }

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    public String getFirstLabel() {

        return correct_Label;
    }

}