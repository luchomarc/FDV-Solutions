

package com.luchomarc.codechallenge;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

  private List<User>users=new ArrayList<>();
  private GridView gridView;
  private UsersAdapter usersAdapter;
  private ProgressBar progressBar;
  private ProgressBar lowerProgressBar;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    progressBar=(ProgressBar)findViewById(R.id.progressBar);
    lowerProgressBar =(ProgressBar)findViewById(R.id.lowerProgressBar);
    gridView = (GridView)findViewById(R.id.gridview);
    usersAdapter = new UsersAdapter(this, users);
    gridView.setAdapter(usersAdapter);
    generateUsers();
    gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
      @Override
      public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        Intent intent = new Intent(MainActivity.this, UserInfoActivity.class);
        intent.putExtra("username", users.get(position).getUsername());
        intent.putExtra("name", users.get(position).getName());
        intent.putExtra("email", users.get(position).getEmail());
        intent.putExtra("largeImageUrl", users.get(position).getLargeImageUrl());
        startActivity(intent);
      }
    });
    gridView.setOnScrollListener(new EndlessScrollListener() {
      @Override
      public boolean onLoadMore(int page, int totalItemsCount) {
        lowerProgressBar.setVisibility(View.VISIBLE);
        generateUsers();
        return true;
      }
    });
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    super.onSaveInstanceState(outState);
  }

  private void generateUsers(){

    OkHttpClient client = new OkHttpClient();
    Request request = new Request.Builder()
            .url("https://randomuser.me/api/?results=51")
            .build();

    Call call = client.newCall(request);
    call.enqueue(new Callback() {
      @Override
      public void onFailure(Call call, final IOException e) {

        runOnUiThread(new Runnable() {
          public void run() {
            Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
          }
        });
      }
      @Override
      public void onResponse(Call call, Response response) throws IOException {
        try{

          if(response.isSuccessful()){
            final String userString=response.body().string();
            JSONObject usersJson = new JSONObject(userString);
            JSONArray usersArray=usersJson.getJSONArray("results");
            for(int i=0;i<51;i++){
              JSONObject userObject = usersArray.getJSONObject(i);
              final String userEmail=userObject.getString("email");
              JSONObject userLoginObject=userObject.getJSONObject("login");
              final String userUsername=userLoginObject.getString("username");
              JSONObject userNameObject=userObject.getJSONObject("name");
              final String userFirstname=userNameObject.getString("first");
              final String userLastname=userNameObject.getString("last");
              final String userFullname=userFirstname+" "+userLastname;
              JSONObject userPicsObject=userObject.getJSONObject("picture");
              final String userThumbnail=userPicsObject.getString("thumbnail");
              final String userLarge=userPicsObject.getString("large");

              User randomUser=new User(userUsername,userFullname,userEmail,userThumbnail,userLarge);
              users.add(randomUser);
            }

            runOnUiThread(new Runnable() {
              @Override
              public void run() {

                usersAdapter.notifyDataSetChanged();
                if(users.size()>51){
                  lowerProgressBar.setVisibility(View.INVISIBLE);
                }
                else{
                  progressBar.setVisibility(View.INVISIBLE);
                }
              }
            });

              runOnUiThread(new Runnable() {
                public void run() {
                  Toast.makeText(MainActivity.this,users.size()+" users",Toast.LENGTH_SHORT).show();
                }
              });
          }
        }
        catch (final IOException e){
          runOnUiThread(new Runnable() {
            public void run() {
              Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
          });

        } catch (final JSONException e) {
          runOnUiThread(new Runnable() {
            public void run() {
              Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
            }
          });
        }
      }
    });
  }
}


