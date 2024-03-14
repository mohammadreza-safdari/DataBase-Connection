package com.project_six.databaseconnection.parser;

import android.util.Log;
import com.project_six.databaseconnection.model.Country;
import com.project_six.databaseconnection.utils.Helper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class JsonParser {
    List<Country> countries;
    public List<Country> jsonParser(InputStream json_inputStream){
        String json_string = Helper.inputStreamToStringV2(json_inputStream);
        return jsonParser(json_string);
    }
    public List<Country> jsonParser(String json_string){
        //make json output from all data in database
        countries = new ArrayList<Country>();
        try {
            JSONArray jsonArray = new JSONArray(json_string);
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Country country = new Country();
                country.setName(jsonObject.getString("name"));
                country.setCode(jsonObject.getString("code"));
                country.setRank(jsonObject.getInt("rank"));
                country.setId(jsonObject.getInt("id"));
                countries.add(country);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("_ERROR_", e.toString());
        }
        return countries;
    }
    public List<Country> jsonParserTwo(String json_string){
        //read json file in server
        countries = new ArrayList<Country>();
        try {
            JSONObject jsonObject_0 = new JSONObject(json_string);
            JSONArray jsonArray = jsonObject_0.getJSONArray("countries");
            for (int i = 0; i < jsonArray.length(); i++){
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                Country country = new Country();
                country.setName(jsonObject.getString("name"));
                country.setCode(jsonObject.getString("code"));
                country.setRank(jsonObject.getInt("rank"));
                country.setId(jsonObject.getInt("id"));
                countries.add(country);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("_ERROR_", e.toString());
        }
        return countries;
    }
}
