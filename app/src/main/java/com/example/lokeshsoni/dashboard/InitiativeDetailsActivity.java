package com.example.lokeshsoni.dashboard;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.intrusoft.scatter.ChartData;
import com.intrusoft.scatter.PieChart;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class InitiativeDetailsActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    RatingAdapter adapter;
    List<Rating> ratingList = new ArrayList<>();
    String initiativeId;
    TextView textViewDesc, textViewTitle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_initiative_details);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        initiativeId = "2";
        adapter = new RatingAdapter(ratingList,getApplicationContext());
        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        textViewTitle = (TextView)findViewById(R.id.textViewTitle);
        textViewDesc = (TextView)findViewById(R.id.textViewDesc);
        new GetRatingList().execute();

    }
    public class GetRatingList extends AsyncTask{

        String response;
        int code;
        ProgressDialog dialog;
        RestClient client;
        @Override
        protected void onPreExecute() {
            System.out.print("dsfd");
            dialog = new ProgressDialog(InitiativeDetailsActivity.this);
            dialog.setMessage("Doing something, please wait.");
            dialog.show();
             client = new RestClient("https://aicte.herokuapp.com/initiative/"+initiativeId+"/ratings",getApplicationContext());

            super.onPreExecute();
        }


        @Override
        protected Object doInBackground(Object[] params) {


            try {
                client.executeGet();
            } catch (Exception e) {
                e.printStackTrace();
            }
            response = client.getResponse();
            code = client.getCode();
            return response;
        }
        @Override
        protected void onPostExecute( Object o) {
            if (dialog.isShowing()) {
                dialog.dismiss();
            }
            if(code ==200){
                try {
                    System.out.println(response);
                    JSONObject object = new JSONObject(response);
                    JSONObject summary = object.getJSONObject("summary");
                    Iterator<String> iter = summary.keys();
                    while (iter.hasNext()) {
                        String key = iter.next();
                        String value = summary.getString(key);
                        Rating rating = new Rating(key, Integer.parseInt(value));
                        ratingList.add(rating);
                    }
                    adapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }else{
                System.out.print(code+"");
            }
            super.onPostExecute(0);
        }
    }
//    {
//        "summary": {
//        "random2": 12,
//                "random1": 12
//    },
//        "ratings": [
//        {
//            "id": 1,
//                "initiative": 1,
//                "parameters": [
//            {
//                "name": "random1",
//                    "value": "12"
//            },
//            {
//                "name": "random2",
//                    "value": "12"
//            }
//      ],
//            "beneficiary": {
//            "id": 1,
//                    "name": "Aditya Gurjar",
//                    "email": "aditya@gmail.com",
//                    "initiative": 1
//        }
//        }
//  ],
//        "status": "OK"
//    }
}
