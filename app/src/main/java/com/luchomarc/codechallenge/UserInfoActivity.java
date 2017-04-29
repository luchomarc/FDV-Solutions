package com.luchomarc.codechallenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by Laptop on 29-04-2017.
 */

public class UserInfoActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_info);

        ImageView largeImage = (ImageView) findViewById(R.id.largeImageUrl);
        TextView username = (TextView) findViewById(R.id.username);
        TextView name = (TextView) findViewById(R.id.name);
        TextView email = (TextView) findViewById(R.id.email);

        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;

        if (height>500){
            getWindow().setLayout((int) (width * 0.8), (int) (height * 0.7));
        }
        else {
            getWindow().setLayout((int) (width * 0.9), (int) (height * 0.9));
        }

        Picasso.with(this).load(getIntent().getStringExtra("largeImageUrl")).into(largeImage);

        username.setText(getIntent().getStringExtra("username"));
        name.setText(getIntent().getStringExtra("name"));
        email.setText(getIntent().getStringExtra("email"));



    }


}
