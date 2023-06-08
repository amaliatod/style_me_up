package com.example.stylemeup;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity3 extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        Button scrapeButton = findViewById(R.id.scrape_button);
        scrapeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageScrapingTask().start();
            }
        });
    }

    private class ImageScrapingTask extends Thread {
        int k =0;
        @Override
        public void run() {
            Context context = MainActivity3.this;
            Database db = new Database(context);
            List<String> clothingURL = new ArrayList<>();
            List<String> urls = new ArrayList<>();
            urls.add("https://www.aboutyou.ro/c/femei/haine/bluze-tunici-22798");
            urls.add("https://www.aboutyou.ro/c/femei/haine/pantaloni-20257");
            urls.add("https://www.aboutyou.ro/c/femei/haine/tricouri-topuri/tricouri-20250");
            urls.add("https://www.aboutyou.ro/c/femei/haine/tricouri-topuri/topuri-20255");
            urls.add("https://www.aboutyou.ro/c/femei/haine/jeans-20258");
            urls.add("https://www.aboutyou.ro/c/femei/haine/rochii-20236");
            urls.add("https://www.aboutyou.ro/c/femei/haine/pulovere-haine-tricotate/pulovere-22847");
            urls.add("https://www.aboutyou.ro/c/femei/haine/fuste-20259");
            urls.add("https://www.aboutyou.ro/c/femei/haine/geci-22822");

            String regex_femei_bluze = ".*femei.*bluze.*";
            String regex_femei_pantaloni = ".*femei.*pantaloni.*";
            String regex_femei_tricouri = ".*femei.*tricouri.*";
            String regex_femei_jeans = ".*femei.*jeans.*";
            String regex_femei_rochii = ".*femei.*rochii.*";
            String regex_femei_pulovere = ".*femei.*pulovere.*";
            String regex_femei_fuste = ".*femei.*fuste.*";
            String regex_femei_geci = ".*femei.*geci.*";



            try {
                for (String url : urls) {
                    // Connect to the website and retrieve its HTML content
                    Document document = Jsoup.connect(url).get();

                    // Select all div elements with data-testid="productImage"
                    Elements divElements = document.select("div[data-testid=productImage]");

                    // Extract the clothing URL from the alt attribute of the image elements
                    for (Element divElement : divElements) {
                        Element imageElement = divElement.selectFirst("img");
                        if (imageElement != null) {
                            String altText = imageElement.attr("src");
                            clothingURL.add(altText);
                            k++;
                            // Save the image URL in the database
                            if (url.matches(regex_femei_bluze)) {
                                db.insertInWomenDb("bluze",  altText);
                            }
                            if (url.matches(regex_femei_pantaloni)) {
                                db.insertInWomenDb("pantaloni",  altText);
                            }
                            if (url.matches(regex_femei_fuste)) {
                                db.insertInWomenDb("fuste", altText);
                            }
                            if (url.matches(regex_femei_rochii)) {
                                db.insertInWomenDb("rochii", altText);
                            }
                            if (url.matches(regex_femei_tricouri)) {
                                db.insertInWomenDb("tricouri", altText);
                            }
                            if (url.matches(regex_femei_pulovere)) {
                                db.insertInWomenDb("pulovere",  altText);
                            }
                            if (url.matches(regex_femei_jeans)) {
                                db.insertInWomenDb("jeans", altText);
                            }
                            if (url.matches(regex_femei_geci)) {
                                db.insertInWomenDb("geci", altText);
                            }
                        }
                    }
                }
            } catch (IOException e) {
                Log.e(TAG, "Error scraping images: " + e.getMessage());
            }
            System.out.println("k="+k);

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    for (String label : clothingURL) {
                        Log.d(TAG, "Scraped clothing URL: " + label);
                    }
                }
            });
        }
    }


//    private boolean isClothingImage(String altText) {
//
//        String[] clothingKeywords = {"bluză","rochie", "fusta", "sacou", "pulover", "cămașă", "hanorac", "jachetă", "trench", "palton", "costum", "salopetă", "jeanși", "pantaloni scurți", "salopetă", "sacou", "vesta", "body", "kimono", "trening", "sutien", "chilot", "slip", "sosete", "ciorapi", "rochie de seară", "rochie de cocktail", "rochie de mireasă", "salopetă scurtă", "sacou casual", "top cu bretele", "palton lung", "trench elegant", "rochie midi", "bluză cu mâneci lungi", "cămașă cu imprimeu", "pantaloni cu talie înaltă", "fustă plisată", "pantaloni evazați", "salopetă de vară", "cardigan", "pulover cu guler înalt", "palton din lână", "costum de birou", "jachetă de piele", "rochie de ocazie", "rochie de zi", "bluză vaporoasă", "tricou cu imprimeu", "pantaloni skinny", "fustă creion", "salopetă cu dungi", "rochie de plajă", "rochie cu volane", "rochie lungă", "bluza cu dantelă", "palton pufos", "trening sport", "sutien push-up", "chilot brazilian", "sosete lungi", "ciorapi mătăsoși", "rochie maxi", "sacou cu imprimeu", "geacă matlasată", "rochie bodycon", "top cu umerii goi", "cămașă albă clasică", "pantaloni cargo", "fustă mini", "pantaloni culottes", "salopetă din denim", "cardigan oversize", "pulover cu imprimeu", "palton camel", "costum de seară", "jachetă bomber", "rochie cu spatele gol", "rochie cu paiete", "bluză cu volane", "tricou cu dungi", "pantaloni largi", "fustă plisată midi", "pantaloni cu imprimeu floral", "salopetă eleganță", "pulover cu glugă", "palton din blană artificială", "costum pantalon", "jachetă din piele ecologică", "rochie cu dantelă"};
//        for (String keyword : clothingKeywords) {
//            if (altText.toLowerCase().contains(keyword)) {
//                return true;
//            }
//        }
//        return false;
//    }
}

