package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {
        Sandwich sandwich = new Sandwich();
        try {
            JSONObject sandwichObject = new JSONObject(json);
            JSONObject nameObject = new JSONObject(sandwichObject.getJSONObject("name").toString());
            sandwich.setMainName(nameObject.getString("mainName"));
            sandwich.setAlsoKnownAs(getParsedArray(nameObject.getJSONArray("alsoKnownAs")));
            sandwich.setPlaceOfOrigin(sandwichObject.getString("placeOfOrigin"));
            sandwich.setDescription(sandwichObject.getString("description"));
            sandwich.setImage(sandwichObject.getString("image"));
            sandwich.setIngredients(getParsedArray(sandwichObject.getJSONArray("ingredients")));
            return sandwich;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static List<String> getParsedArray(JSONArray objectToParse) {
        List<String> items = new ArrayList<>();
        if (objectToParse.length() <= 0) {
            return null;
        }
        int count = objectToParse.length();
        for (int i = 0; i < count; i++) {
            try {
                items.add(objectToParse.getString(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return items;
    }
}
