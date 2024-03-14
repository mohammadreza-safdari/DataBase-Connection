package com.project_six.databaseconnection;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.project_six.databaseconnection.adapter.CustomListViewArrayAdapter;
import com.project_six.databaseconnection.model.Country;
import com.project_six.databaseconnection.parser.JsonParser;
import com.project_six.databaseconnection.utils.DataBaseTask;
import com.project_six.databaseconnection.utils.HttpConnectionManager;
import com.project_six.databaseconnection.utils.UrlMaker;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    ArrayList<NetworkTask> tasks_List;
    ProgressBar pb;
    ListView lv;
    List<Country> countries;
    TextView tv;
    CustomListViewArrayAdapter<Country> countriesArrayAdapter;
    //room is wamp server folder
    public static final String main_url = "http://IP:PORT/room/";
    //the code related to [read_data_from_database_php_url] section has not been written yet
    public static final String read_data_from_database_php_url = main_url + "php_countries_database/read_data_from_database.php";
    public static final String add_and_read_data_from_database_php_url = main_url + "php_countries_database/add_and_read_data_from_database.php";
    public static final String only_add_data_to_database_php_url = main_url + "php_countries_database/only_add_all_data_at_once.php";
    public static final String update_database_php_url = main_url + "php_countries_database/update_database.php";
    public static final String delete_data_from_database_php_url = main_url + "php_countries_database/delete_data_from_database.php";
    public static final String photos_url = main_url + "photo/";
    public static final String json_url = main_url + "countries.json";
    public static String operation = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        pb.setVisibility(View.INVISIBLE);
        tasks_List = new ArrayList<NetworkTask>();
        countries = new ArrayList<>();
    }

    private void setupViews() {
        pb = (ProgressBar) findViewById(R.id.pb);
        lv = (ListView) findViewById(R.id.lv);
        tv = (TextView) findViewById(R.id.tv);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int Id = item.getItemId();
        switch (Id){
            case R.id.menu_add_and_get_data_from_server:
                addAndGetDataFromDatabase();
                break;
            case R.id.menu_add_all_data_at_once:
                addAllDataAtOnce();
                break;
            case R.id.menu_update_database:
                updateDatabase();
                break;
            case R.id.menu_delete_item_from_database:
                deleteItemFromDatabase();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void addAndGetDataFromDatabase() {
        operation = "add_and_get_data_from_serve";
        NetworkTask networkTask_0 = new NetworkTask();
        UrlMaker urlMaker_0 = new UrlMaker();
        urlMaker_0.setOperation(operation);
        urlMaker_0.setUri(add_and_read_data_from_database_php_url);
        urlMaker_0.setMethod("GET");
        //we can make an activity and get below data from user
        urlMaker_0.setParameter("id", 11);
        urlMaker_0.setParameter("name", "United Arab Emirates");
        urlMaker_0.setParameter("code", "AE");
        urlMaker_0.setParameter("rank", 22);
        networkTask_0.execute(urlMaker_0);
    }

    private void addAllDataAtOnce() {
        operation = "add_all_data_at_once";
        NetworkTask networkTask_1 = new NetworkTask();
        UrlMaker urlMaker_1 = new UrlMaker();
        urlMaker_1.setOperation(operation);
        urlMaker_1.setUri(json_url);
        urlMaker_1.setMethod("GET");
        networkTask_1.execute(urlMaker_1);
    }

    private void updateDatabase() {
        operation = "update_database";
        DataBaseTask dataBaseTask_0 = new DataBaseTask();
        UrlMaker urlMaker_2 = new UrlMaker();
        urlMaker_2.setOperation(operation);
        urlMaker_2.setUri(update_database_php_url);
        urlMaker_2.setMethod("GET");
        urlMaker_2.setParameter("id", 6);
        urlMaker_2.setParameter("name", "Italy");
        urlMaker_2.setParameter("code", "wtf");
        urlMaker_2.setParameter("rank", 99);
        dataBaseTask_0.execute(urlMaker_2);
    }

    private void deleteItemFromDatabase() {
        operation = "delete_data_from_database";
        DataBaseTask dataBaseTask_1 = new DataBaseTask();
        UrlMaker urlMaker_3 = new UrlMaker();
        urlMaker_3.setOperation(operation);
        urlMaker_3.setUri(delete_data_from_database_php_url);
        urlMaker_3.setMethod("GET");
        urlMaker_3.setParameter("id", 4);
        dataBaseTask_1.execute(urlMaker_3);
    }

    private class NetworkTask extends AsyncTask<UrlMaker, String, List<Country>>{
        String tv_string;
        UrlMaker urlMaker;
        String main_string;
        @Override
        protected void onPreExecute() {
            Toast.makeText(MainActivity.this, "initializing...", Toast.LENGTH_SHORT).show();
            if (tasks_List.size() == 0){
                pb.setVisibility(View.VISIBLE);
            }
            tasks_List.add(this);
            super.onPreExecute();
        }

        @SuppressLint("WrongThread")
        @Override
        protected List<Country> doInBackground(UrlMaker... urlM) {
            urlMaker = urlM[0];
            publishProgress("(" + urlMaker.getUri() + ") " + "connecting to server ...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            HttpConnectionManager manager = new HttpConnectionManager(urlMaker);
            main_string = manager.httpConnect();
            Log.d("main_string", main_string);
            if (urlMaker.getOperation().equals("add_and_get_data_from_serve")){
                String[] parts = main_string.split("#");
                tv_string  = parts[0];
                String json_string = parts[1];
                JsonParser jsonParser = new JsonParser();
                countries = jsonParser.jsonParser(json_string);
            } else if (urlMaker.getOperation().equals("add_all_data_at_once")) {
                JsonParser jsonParser = new JsonParser();
                countries = jsonParser.jsonParserTwo(main_string);
                for (Country country : countries){
                    UrlMaker urlMaker = new UrlMaker();
                    urlMaker.setUri(only_add_data_to_database_php_url);
                    urlMaker.setMethod("GET");
                    //we can make an activity and get below data from user
                    urlMaker.setParameter("id", country.getId());
                    urlMaker.setParameter("name", country.getName());
                    urlMaker.setParameter("code", country.getCode());
                    urlMaker.setParameter("rank", country.getRank());
                    DataBaseTask dataBaseTask = new DataBaseTask();
                    dataBaseTask.execute(urlMaker);
                }
            }
            return countries;
        }
        //ui thread
        @Override
        protected void onProgressUpdate(String... values) {
            Toast.makeText(MainActivity.this, values[0], Toast.LENGTH_SHORT).show();
            super.onProgressUpdate(values);
        }
        //ui thread
        @Override
        protected void onPostExecute(List<Country> countries) {
            if (urlMaker.getOperation().equals("add_all_data_at_once"))
                Log.d("task result : ", main_string);
            if (urlMaker.getOperation().equals("add_and_get_data_from_serve"))
                updateUi(tv_string);
            tasks_List.remove(this);
            if (tasks_List.size() == 0){
                pb.setVisibility(View.INVISIBLE);
            }
            super.onPostExecute(countries);
        }
    }
    private void updateUi(String tv_string){
        tv.setText(tv_string);
        if (countries != null){
            countriesArrayAdapter = new CustomListViewArrayAdapter(this,
                    R.layout.list_item, countries);
            lv.setAdapter(countriesArrayAdapter);
        }
    }
}