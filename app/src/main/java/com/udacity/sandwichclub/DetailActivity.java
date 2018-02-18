package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private TextView description;
    private TextView origin;
    private TextView alsoKnown;
    private TextView ingredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);
        description = findViewById(R.id.description_tv);
        origin = findViewById(R.id.origin_tv);
        alsoKnown = findViewById(R.id.also_known_tv);
        ingredients = findViewById(R.id.ingredients_tv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        if (!TextUtils.isEmpty(sandwich.getDescription())) {
            description.setText(sandwich.getDescription());
        } else {
            findViewById(R.id.description_label).setVisibility(View.GONE);
        }
        if (!TextUtils.isEmpty(sandwich.getPlaceOfOrigin())) {
            origin.setText(sandwich.getPlaceOfOrigin());
        } else {
            findViewById(R.id.place_of_origin_label).setVisibility(View.GONE);
        }
        if (sandwich.getAlsoKnownAs() != null && !sandwich.getAlsoKnownAs().isEmpty()) {
            alsoKnown.setText(getBeautifulList(sandwich.getAlsoKnownAs()));
        } else {
            findViewById(R.id.also_known_label).setVisibility(View.GONE);
        }
        if (sandwich.getIngredients() != null && !sandwich.getIngredients().isEmpty()) {
            ingredients.setText(getBeautifulList(sandwich.getIngredients()));
        } else {
            findViewById(R.id.ingredients_label).setVisibility(View.GONE);
        }
    }

    private String getBeautifulList(List<String> list) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < list.size() - 1; i++) {
            result.append(list.get(i));
            result.append("\n");
        }
        result.append(list.get(list.size() - 1));
        return result.toString();
    }
}
