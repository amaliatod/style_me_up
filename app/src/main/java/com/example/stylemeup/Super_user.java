package com.example.stylemeup;

import static com.android.volley.VolleyLog.TAG;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Super_user extends Activity {
    private Button scrape;

    private Handler handler;
    private View loadingView;
    private RadioGroup radioGroup;
    String selectedDatabase;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.super_user);
        scrape=findViewById(R.id.scrapeButton);
        loadingView = findViewById(R.id.progressBar);
        radioGroup = findViewById(R.id.radioGroup);
        loadingView.setVisibility(View.GONE);
        handler = new Handler();

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton selectedRadioButton = findViewById(checkedId);
                selectedDatabase = selectedRadioButton.getText().toString();
            }
        });

        scrape.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedDatabase.equals("Woman")) {
                    ImageScrapingTaskWoman imageScrapingTaskWoman = new ImageScrapingTaskWoman(loadingView);
                    imageScrapingTaskWoman.execute();
                }
                else if (selectedDatabase.equals("Man")){
                    ImageScrapingTaskMan imageScrapingTaskMan = new ImageScrapingTaskMan(loadingView);
                    imageScrapingTaskMan.execute();

                }
//

            }
        });


}

    private class ImageScrapingTaskMan extends AsyncTask<Void, Void, List<String>> {

        private static final int MAX_IMAGES_PER_WEBSITE = 10;

        private final View loadingView;


        public ImageScrapingTaskMan(View loadingView) {
            this.loadingView = loadingView;
        }

        @Override
        protected void onPreExecute() {
            // Show the loading view
            loadingView.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<String> doInBackground(Void... params) {
            Context context = Super_user.this;
            Database_man db = new Database_man(context);

            boolean isDatabaseEmpty = db.isDatabaseEmpty();

            if (!isDatabaseEmpty) {
                db.deleteDatabase();
            }

            List<String> clothingURL = new ArrayList<>();
            List<String> urls = new ArrayList<>();
            urls.add("https://www.aboutyou.ro/c/barbati/haine/tricouri-20324?brand=53709%2C290%2Cjordan-61263"); // tricouri
            urls.add("https://www.aboutyou.ro/c/barbati/haine/tricouri/tank-tops-20325?brand=53709%2C290%2Cjordan-61263"); //maieu sport
            urls.add("https://www.aboutyou.ro/c/barbati/haine/camasi/camasi-casual-23687"); //camasa casual
            urls.add("https://www.aboutyou.ro/c/barbati/haine/jeans-20331"); // jeans
            urls.add("https://www.aboutyou.ro/c/barbati/haine/pantaloni/pantaloni-de-trening-23689"); //pantaloni sport
            urls.add("https://www.aboutyou.ro/c/barbati/haine/pantaloni/pantaloni-chino-20972"); //pantaloni party
            urls.add("https://www.aboutyou.ro/c/barbati/haine/camasi/camasi-de-blugi-20912"); //camasa denim
            urls.add("https://www.aboutyou.ro/c/barbati/haine/sweat/hanorace-20964"); // bluze sport
            urls.add("https://www.aboutyou.ro/c/barbati/haine/camasi/camasi-business-69619"); //camasa party
            urls.add("https://www.aboutyou.ro/c/barbati/haine/pulovere-cardigane-20322"); //pulovere
            urls.add("https://www.aboutyou.ro/c/barbati/haine/tricouri-20324?brand=540%2C364"); //tricouri casual
            urls.add("https://www.aboutyou.ro/c/barbati/pantofi/pantofi-sport-514809"); //pantofi sport
            urls.add("https://www.aboutyou.ro/c/barbati/pantofi/pantofi-20342"); //pantofi party
            urls.add("https://www.aboutyou.ro/c/barbati/pantofi/bocanci-cizme/boots-101445"); //pantofi casual
            urls.add("https://www.aboutyou.ro/c/barbati/pantofi/sneaker-20345?brand=540%2Ctommy-hilfiger-364"); //pantofi casual
            urls.add("https://www.aboutyou.ro/c/barbati/accesorii/cravate-accesorii-101464?sort=price_low&brand=1122%2C39%2C70149%2C6801"); //cravata
            urls.add("https://www.aboutyou.ro/c/barbati/accesorii/curele/curele-clasice-101461?sort=price_low"); //curele party
            urls.add("https://www.aboutyou.ro/c/barbati/accesorii/sepci-caciuli/sepci-20296?sort=price_low&brand=290%2Cnike-sportswear-53709"); //sepci sport
            urls.add("https://www.aboutyou.ro/c/barbati/accesorii/sepci-caciuli-20306?sort=price_low&brand=tommy-hilfiger-364"); // sepci casual


            String regex_barbati_camasa_casual = ".*barbati.*camasi-casual.*";
            String regex_barbati_thank_top = ".*barbati.*tank-tops.*";
            String regex_barbati_camasa_denim = ".*barbati.*camasi-de-blugi.*";
            String regex_barbati_camasa_party = ".*barbati.*camasi-business.*";
            String regex_barbati_tricouri_casual = ".*barbati.*tricouri-20324?brand=540%2C364.*";
            String regex_barbati_bluze_sport = ".*barbati.*hanorace.*";
            String regex_barbati_bluze_casual = ".*barbati.*pulovere.*";
            String regex_barbati_pantaloni_party = ".*barbati.*pantaloni-chino.*";
            String regex_barbati_tricouri = ".*barbati.*tricouri.*";
            String regex_barbati_pantaloni_sport = ".*barbati.*pantaloni-de-trening.*";
            String regex_barbati_jeans = ".*barbati.*jeans.*";
            String regex_barbati_pantofi_sport = ".*barbati.*pantofi-sport.*";
            String regex_barbati_pantofi_casual_sneaker = ".*barbati.*sneaker.*";
            String regex_barbati_pantofi_party = ".*barbati.*pantofi-20342.*";
            String regex_barbati_pantofi_casual = ".*barbati.*boots.*";
            String regex_barbati_cravate = ".*barbati.*cravate.*";
            String regex_barbati_curele = ".*barbati.*curele.*";
            String regex_barbati_sepci_sport = ".*barbati.*sepci-20296.*";
            String regex_barbati_sepci_casual = ".*barbati.*sepci-caciuli-20306.*";





            try {
                for (String url : urls) {
                    // Connect to the website and retrieve its HTML content
                    Document document = Jsoup.connect(url).get();

                    // Select all div elements with data-testid="productImage"
                    Elements divElements = document.select("div[data-testid=productImage]");
                    int imageCount = 0;
                    // Extract the clothing URL from the alt attribute of the image elements
                    for (Element divElement : divElements) {
                        if (imageCount >= MAX_IMAGES_PER_WEBSITE) {
                            break; // Stop scraping if the maximum image count is reached
                        }
                        Element imageElement = divElement.selectFirst("img");
                        if (imageElement != null) {
                            String altText = imageElement.attr("src");
                            clothingURL.add(altText);

                            // Save the image URL in the database
                            if (url.matches(regex_barbati_tricouri)) {
                                db.insertInMenDb("tops", altText, getDominantColorFromImage(altText),"sport");
                            }
                            if (url.matches(regex_barbati_thank_top)) {
                                db.insertInMenDb("tops", altText, getDominantColorFromImage(altText),"sport");
                            }
                            if (url.matches(regex_barbati_jeans)) {
                                db.insertInMenDb("bottoms", altText, getDominantColorFromImage(altText),"casual");
                            }
                            if (url.matches(regex_barbati_pantaloni_sport)) {
                                db.insertInMenDb("bottoms", altText, getDominantColorFromImage(altText),"sport");
                            }
                            if (url.matches(regex_barbati_bluze_casual)) {
                                db.insertInMenDb("tops", altText, getDominantColorFromImage(altText),"casual");
                            }
                            if (url.matches(regex_barbati_tricouri_casual)) {
                                db.insertInMenDb("tops", altText, getDominantColorFromImage(altText),"casual");
                            }
                            if (url.matches(regex_barbati_camasa_denim)) {
                                db.insertInMenDb("tops", altText, getDominantColorFromImage(altText),"casual");
                            }
                            if (url.matches(regex_barbati_pantaloni_party)) {
                                db.insertInMenDb("bottoms", altText, getDominantColorFromImage(altText),"party");
                            }
                            if (url.matches(regex_barbati_camasa_party)) {
                                db.insertInMenDb("tops", altText, getDominantColorFromImage(altText),"party");
                            }
                            if (url.matches(regex_barbati_camasa_casual)) {
                                db.insertInMenDb("tops", altText, getDominantColorFromImage(altText),"casual");
                            }
                            if (url.matches(regex_barbati_bluze_sport)) {
                                db.insertInMenDb("tops", altText, getDominantColorFromImage(altText),"sport");
                            }
                            if (url.matches(regex_barbati_pantofi_casual)) {
                                db.insertInMenDb("shoes", altText, getDominantColorFromImage(altText),"casual");
                            }
                            if (url.matches(regex_barbati_pantofi_party)) {
                                db.insertInMenDb("shoes", altText, getDominantColorFromImage(altText),"party");
                            }
                            if (url.matches(regex_barbati_pantofi_casual_sneaker)) {
                                db.insertInMenDb("shoes", altText, getDominantColorFromImage(altText),"sport");
                            }
                            if (url.matches(regex_barbati_pantofi_sport)) {
                                db.insertInMenDb("shoes", altText, getDominantColorFromImage(altText),"sport");
                            }
                            if (url.matches(regex_barbati_cravate)) {
                                db.insertInMenDb("accessories", altText, getDominantColorFromImage(altText),"party");
                            }
                            if (url.matches(regex_barbati_curele)) {
                                db.insertInMenDb("accessories", altText, getDominantColorFromImage(altText),"party");
                            }
                            if (url.matches(regex_barbati_sepci_sport)) {
                                db.insertInMenDb("accessories", altText, getDominantColorFromImage(altText),"sport");
                            }
                            if (url.matches(regex_barbati_sepci_casual)) {
                                db.insertInMenDb("accessories", altText, getDominantColorFromImage(altText),"casual");
                            }

                            imageCount++;
                        }
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "Error scraping images: " + e.getMessage());
            }finally {
                // Close the database connection
                db.close();
            }

            runOnUiThread(() -> {

            });

            return clothingURL;
        }
        @Override
        protected void onPostExecute(List<String> clothingURL) {
            super.onPostExecute(clothingURL);

            if (clothingURL != null) {
                // Process the scraped URLs here

            }
            loadingView.setVisibility(View.GONE);
        }
    }

    private class ImageScrapingTaskWoman extends AsyncTask<Void, Void, List<String>> {
        private static final int MAX_IMAGES_PER_WEBSITE = 10;
        private final View loadingView;


        public ImageScrapingTaskWoman(View loadingView) {
            this.loadingView = loadingView;
        }

        @Override
        protected void onPreExecute() {
            // Show the loading view
            loadingView.setVisibility(View.VISIBLE);
        }

        @Override
        protected List<String> doInBackground(Void... params) {

            Context context = Super_user.this;
            Database_woman db = new Database_woman(context);

            boolean isDatabaseEmpty = db.isDatabaseEmpty();

            if (!isDatabaseEmpty) {
                db.deleteDatabase();
            }


            List<String> clothingURL = new ArrayList<>();
            List<String> urls = new ArrayList<>();
            urls.add("https://www.aboutyou.ro/c/femei/haine/tricouri-topuri-517223?brand=290%2C53709%2C272%2Cthe-north-face-199"); // tricouri
            urls.add("https://www.aboutyou.ro/c/femei/haine/tricouri-topuri/topuri/topuri-elegante-140101"); //topuri elegante
            urls.add("https://www.aboutyou.ro/c/femei/haine/tricouri-topuri/topuri/topuri-party-140100?materialStyle=57138%2C56690%2C56689&pattern=35005"); //topuri party
            urls.add("https://www.aboutyou.ro/c/femei/haine/jeans/straight-leg-22821"); // jeans
            urls.add("https://www.aboutyou.ro/c/femei/haine/pantaloni/pantaloni-de-trening-101215"); //pantaloni sport
            urls.add("https://www.aboutyou.ro/c/femei/haine/pantaloni/pantaloni-cu-pliuri-140106?materialStyle=56690"); //pantaloni party
            urls.add("https://www.aboutyou.ro/c/femei/haine/pulovere-haine-tricotate/pulovere-22847?sort=price_high&pattern=35005&brand=4739%2C5905&sleeveLength=35033&materialStyle=56696%2C35015"); //bluze casual
            urls.add("https://www.aboutyou.ro/c/femei/haine/sweat/hanorace-100215?sort=price_high&brand=290%2Cnike-sportswear-53709"); // bluze sport
            urls.add("https://www.aboutyou.ro/c/femei/haine/fuste/fuste-mini-20668?sort=price_high"); // fuste party
            urls.add("https://www.aboutyou.ro/c/femei/haine/fuste/fuste-de-blugi-101232?sort=price_high"); //fuste casual
            urls.add("https://www.aboutyou.ro/c/femei/pantofi/sneaker-20273?sort=price_high&brand=170334%2C182%2Cadidas-performance-221"); //pantofi sport
            urls.add("https://www.aboutyou.ro/c/femei/pantofi/pumps-pantofi-cu-toc-101349?sort=price_high"); //pantofi cu toc
            urls.add("https://www.aboutyou.ro/c/femei/pantofi/botine-20276?sort=price_high&toecap=35109&fastenerType=71746"); //pantofi casual
            urls.add("https://www.aboutyou.ro/c/femei/haine/rochii/rochii-de-seara-20423?sort=topseller"); //rochii party
            urls.add("https://www.aboutyou.ro/c/femei/haine/rochii/rochii-de-blugi-101138?bottomLength=35063%2C35059&sort=topseller"); //rochii de blugi
            urls.add("https://www.aboutyou.ro/c/femei/haine/rochii/rochii-de-vara-22794?sort=topseller"); // rochii de vara
            urls.add("https://www.aboutyou.ro/c/femei/haine/rochii/rochii-din-jerseu-20440?sort=topseller&brand=53709%2C290%2Cjordan-61263"); // rochii sport
            urls.add("https://www.aboutyou.ro/c/femei/accesorii/palarii-caciuli/sepci-21543?brand=53709%2Cadidas-originals-290"); // sepci sport
            urls.add("https://www.aboutyou.ro/c/femei/accesorii/genti-si-rucsacuri/genti/posete-plic-21547?materialStyle=35026%2C57138%2C35021"); //geanta party
            urls.add("https://www.aboutyou.ro/c/femei/accesorii/genti-si-rucsacuri/genti/genti-de-umar-140146"); // genti casual

            String regex_femei_bluze_casual = ".*femei.*pulovere.*";
            String regex_femei_bluze_sport = ".*femei.*hanorace.*";
            String regex_femei_pantaloni_casual = ".*femei.*pantaloni-lungi.*";
            String regex_femei_pantaloni_party = ".*femei.*pantaloni-cu-pliuri.*";
            String regex_femei_tricouri = ".*femei.*tricouri-topuri-517223.*";
            String regex_femei_topuri_elegante = ".*femei.*topuri-elegante.*";
            String regex_femei_topuri_party = ".*femei.*topuri-party.*";
            String regex_femei_pantaloni_sport = ".*femei.*pantaloni-de-trening.*";
            String regex_femei_jeans = ".*femei.*jeans.*";
            String regex_femei_fuste_party = ".*femei.*fuste-mini.*";
            String regex_femei_fuste_casual = ".*femei.*fuste-de-blugi.*";
            String regex_femei_pantofi_sport = ".*femei.*sneaker.*";
            String regex_femei_pantofi_cu_toc = ".*femei.*pantofi-cu-toc.*";
            String regex_femei_pantofi_casual = ".*femei.*botine.*";
            String regex_femei_genti_party = ".*femei.*posete-plic.*";
            String regex_femei_genti_casual = ".*femei.*genti-de-umar.*";
            String regex_femei_sepci_sport = ".*femei.*sepci-21543.*";
            String regex_femei_rochii_party = ".*femei.*rochii-de-seara.*";
            String regex_femei_rochii_blugi = ".*femei.*rochii-de-blugi.*";
            String regex_femei_rochii_vara = ".*femei.*rochii-de-vara.*";
            String regex_femei_rochii_sport = ".*femei.*rochii-din-jerseu.*";


            try {
                for (String url : urls) {
                    // Connect to the website and retrieve its HTML content
                    Document document = Jsoup.connect(url).get();

                    // Select all div elements with data-testid="productImage"
                    Elements divElements = document.select("div[data-testid=productImage]");
                    int imageCount = 0;

                    // Extract the clothing URL from the alt attribute of the image elements
                    for (Element divElement : divElements) {
                        if (imageCount >= MAX_IMAGES_PER_WEBSITE) {
                            break; // Stop scraping if the maximum image count is reached
                        }

                        Element imageElement = divElement.selectFirst("img");
                        if (imageElement != null) {
                            String altText = imageElement.attr("src");
                            clothingURL.add(altText);

                            // Save the image URL in the database
                            if (url.matches(regex_femei_tricouri)) {
                                db.insertInWomenDb("tops", altText, getDominantColorFromImage(altText), "sport");
                            } else if (url.matches(regex_femei_topuri_elegante)) {
                                db.insertInWomenDb("tops", altText, getDominantColorFromImage(altText), "casual");
                            } else if (url.matches(regex_femei_topuri_party)) {
                                db.insertInWomenDb("tops", altText, getDominantColorFromImage(altText), "party");
                            } else if (url.matches(regex_femei_jeans)) {
                                db.insertInWomenDb("bottoms", altText, getDominantColorFromImage(altText), "casual");
                            } else if (url.matches(regex_femei_pantaloni_sport)) {
                                db.insertInWomenDb("bottoms", altText, getDominantColorFromImage(altText), "sport");
                            } else if (url.matches(regex_femei_pantaloni_casual)) {
                                db.insertInWomenDb("bottoms", altText, getDominantColorFromImage(altText), "casual");
                            } else if (url.matches(regex_femei_pantaloni_party)) {
                                db.insertInWomenDb("bottoms", altText, getDominantColorFromImage(altText), "party");
                            } else if (url.matches(regex_femei_bluze_casual)) {
                                db.insertInWomenDb("tops", altText, getDominantColorFromImage(altText), "casual");
                            } else if (url.matches(regex_femei_bluze_sport)) {
                                db.insertInWomenDb("tops", altText, getDominantColorFromImage(altText), "sport");
                            } else if (url.matches(regex_femei_fuste_party)) {
                                db.insertInWomenDb("bottoms", altText, getDominantColorFromImage(altText), "party");
                            } else if (url.matches(regex_femei_fuste_casual)) {
                                db.insertInWomenDb("bottoms", altText, getDominantColorFromImage(altText), "casual");
                            } else if (url.matches(regex_femei_pantofi_casual)) {
                                db.insertInWomenDb("shoes", altText, getDominantColorFromImage(altText), "casual");
                            } else if (url.matches(regex_femei_pantofi_cu_toc)) {
                                db.insertInWomenDb("shoes", altText, getDominantColorFromImage(altText), "party");
                            } else if (url.matches(regex_femei_pantofi_sport)) {
                                db.insertInWomenDb("shoes", altText, getDominantColorFromImage(altText), "sport");
                            }else if (url.matches(regex_femei_rochii_party)) {
                                    db.insertInWomenDb("dresses", altText, getDominantColorFromImage(altText), "party");
                            }else if (url.matches(regex_femei_rochii_blugi)) {
                                db.insertInWomenDb("dresses", altText, getDominantColorFromImage(altText), "casual");
                            }else if (url.matches(regex_femei_rochii_vara)) {
                                db.insertInWomenDb("dresses", altText, getDominantColorFromImage(altText), "casual");
                            }else if (url.matches(regex_femei_rochii_sport)) {
                                db.insertInWomenDb("dresses", altText, getDominantColorFromImage(altText), "sport");
                            }else if (url.matches(regex_femei_genti_party)) {
                                db.insertInWomenDb("accessories", altText, getDominantColorFromImage(altText), "party");
                            }
                            else if (url.matches(regex_femei_genti_casual)) {
                                db.insertInWomenDb("accessories", altText, getDominantColorFromImage(altText), "casual");
                            }
                            else if (url.matches(regex_femei_sepci_sport)) {
                                db.insertInWomenDb("accessories", altText, getDominantColorFromImage(altText), "sport");
                            }

                            imageCount++;
                        }
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "Error scraping images: " + e.getMessage());
            }finally {
                // Close the database connection
                db.close();
            }

            runOnUiThread(() -> {

            });

            return clothingURL;
        }
        @Override
        protected void onPostExecute(List<String> clothingURL) {
            super.onPostExecute(clothingURL);

            if (clothingURL != null) {
                // Process the scraped URLs here

            }
            loadingView.setVisibility(View.GONE);
        }
    }


    private String getDominantColorFromImage(String imageUrl) {
        try {
            // Load the image from the URL and convert it to Bitmap
            Bitmap bitmap = BitmapFactory.decodeStream((InputStream) new URL(imageUrl).getContent());

            // Calculate the dominant color of the bitmap
            int dominantColor = getDominantColorExcludingBackground(bitmap);

            // Convert the integer value to the actual color string
            String colorString = String.format("#%06X", 0xFFFFFF & dominantColor);

            return colorString;
        } catch (IOException e) {
            Log.e(TAG, "Error getting dominant color from image: " + e.getMessage());
        }

        return ""; // Default empty color string if an error occurs
    }

    private int getDominantColorExcludingBackground(Bitmap bitmap) {
        // Calculate the dominant color of the bitmap, excluding the specified background color
        Map<Integer, Integer> colorMap = new HashMap<>();
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        int dominantColor = Color.TRANSPARENT;
        int maxColorCount = 0;

        int excluded_color = Color.parseColor("#F4F4F4");
        int excluded_color2 = Color.parseColor("#F5F5F5");

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int pixel = bitmap.getPixel(x, y);
                int color = pixel | 0xFF000000; // Set alpha to opaque

                if (color != excluded_color && color !=excluded_color2) {
                    if (colorMap.containsKey(color)) {
                        int count = colorMap.get(color) + 1;
                        colorMap.put(color, count);
                        if (count > maxColorCount) {
                            dominantColor = color;
                            maxColorCount = count;
                        }
                    } else {
                        colorMap.put(color, 1);
                        if (maxColorCount == 0) {
                            dominantColor = color;
                            maxColorCount = 1;
                        }
                    }
                }
            }
        }

        return dominantColor;
    }


}
