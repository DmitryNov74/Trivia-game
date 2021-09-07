package com.example.newtriviagame;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.newtriviagame.databinding.ActivityMainBinding;
import com.example.newtriviagame.databinding.ActivityPelialueBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private Button button2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        binding = DataBindingUtil.setContentView(this,R.layout.activity_main);

        button2 = findViewById(R.id.button2);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,Pelialue.class);
                startActivity(intent);
            }
        });

    }
}