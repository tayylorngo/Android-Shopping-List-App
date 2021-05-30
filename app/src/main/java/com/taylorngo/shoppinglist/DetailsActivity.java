package com.taylorngo.shoppinglist;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class DetailsActivity extends AppCompatActivity {

    TextView name, description, price;
    ImageView itemImage;

    String data1, data2, data3, data4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        name = findViewById(R.id.nameTxt);
        description = findViewById(R.id.descriptionTxt);
        price = findViewById(R.id.priceTxt);
        itemImage = findViewById(R.id.detailedImage);
        getData();
        setData();
    }

    private void getData(){
        if(getIntent().hasExtra("data1") && getIntent().hasExtra("data2")
                && getIntent().hasExtra("data3")){
            data1 = getIntent().getStringExtra("data1");
            data2 = getIntent().getStringExtra("data2");
            data3 = getIntent().getStringExtra("data3");
            data4 = getIntent().getStringExtra("data4");
        }
        else{
            Toast.makeText(this, "No data.", Toast.LENGTH_SHORT).show();
        }
    }

    private void setData(){
        name.setText(data1);
        description.setText(data2);
        price.setText(data3);
        if(data4.equals("Food")){
            itemImage.setImageResource(R.drawable.food);
        }
        else if(data4.equals("Animal")){
            itemImage.setImageResource(R.drawable.animal);
        }
        else if(data4.equals("Clothing")){
            itemImage.setImageResource(R.drawable.clothing);
        }
        else{
            itemImage.setImageResource(R.drawable.technology);
        }

    }
}