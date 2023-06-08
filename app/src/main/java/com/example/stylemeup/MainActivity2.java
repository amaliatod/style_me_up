package com.example.stylemeup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import java.io.ByteArrayOutputStream;
import java.io.IOException;



import android.content.Intent;
import android.graphics.Bitmap;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity2 extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView imageView;
    private Button pickButton, womanButton, manButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        imageView = findViewById(R.id.image_view);
        pickButton = findViewById(R.id.pick_button);
        womanButton = findViewById(R.id.woman_button);
        manButton = findViewById(R.id.man_button);


        pickButton.setOnClickListener(v -> openGallery());

        womanButton.setVisibility(View.GONE);
        manButton.setVisibility(View.GONE);

        womanButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,MainActivity3.class);
                startActivity(intent);

            }
        });

        manButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity2.this,MainActivity4.class);
                startActivity(intent);

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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                imageView.setImageBitmap(bitmap);

                // Show additional buttons
                womanButton.setVisibility(View.VISIBLE);
                manButton.setVisibility(View.VISIBLE);

                performImageRecognition(bitmap);

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
            String jsonRequest = "{\"requests\": [{\"image\": {\"content\": \"" + imageBase64 + "\"}, \"features\": [{\"type\": \"LABEL_DETECTION\", \"maxResults\": 5}]}]}";

            // Send the POST request to the Vision API
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(jsonRequest),
                    response -> {
                        try {
                            // Process the response
                            JSONArray labelAnnotations = response.getJSONArray("responses")
                                    .getJSONObject(0)
                                    .getJSONArray("labelAnnotations");

                            // Extract and display the labels
                            StringBuilder predictionsBuilder = new StringBuilder();
                            for (int i = 0; i < labelAnnotations.length(); i++) {
                                JSONObject label = labelAnnotations.getJSONObject(i);
                                String description = label.getString("description");
                                double score = label.getDouble("score");

                                if (isClothingLabel(description)) {
                                    predictionsBuilder.append("Label: ").append(description).append(", Score: ").append(score).append("\n");
                                }
                            }

                            System.out.println(predictionsBuilder);
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
        String[] clothingKeywords = {"t-shirt", "shirt", "blouse", "polo shirt", "tank top", "sweater", "hoodie", "cardigan", "jacket", "coat", "blazer", "suit", "dress", "skirt", "shorts", "pants", "jeans", "leggings", "jumpsuit", "romper", "trousers", "capris", "cap", "hat", "beanie", "scarf", "shawl", "wrap", "poncho", "kimono", "gloves", "mittens", "socks", "stockings", "tights", "underwear", "bra", "panties", "boxers", "briefs", "thong", "swimwear", "bikini", "swimsuit", "one-piece", "robe", "pajamas", "nightgown", "nightdress", "slippers", "sneakers", "running shoes", "sports shoes", "sandals", "flip-flops", "boots", "ankle boots", "heels", "wedges", "flats", "loafers", "oxfords", "mules", "espadrilles", "tie", "bow tie", "belt", "suspenders", "cufflinks", "pocket square", "scarf", "tie clip", "sunglasses", "watch", "bracelet", "necklace", "earrings", "ring", "handbag", "tote bag", "clutch", "crossbody bag", "backpack", "wallet", "messenger bag", "briefcase", "suitcase", "umbrella", "coat hanger", "lingerie", "corset", "waistcoat", "tuxedo", "cummerbund", "tunic", "sari", "kimono", "hijab", "turban", "veil"};
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


}
