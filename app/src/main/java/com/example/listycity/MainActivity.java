package com.example.listycity;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {
    // declaring required objects
    ListView cityList;
    ArrayList<City> dataList;
    ArrayAdapter<City> cityAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // declaring cities new way
        City edmonton = new City("Edmonton", "AB");
        City calgary = new City("Calgary", "AB");
        City toronto = new City("Toronto", "ON");

        City []cities = {edmonton, calgary, toronto};
        dataList = new ArrayList<City>();
        dataList.addAll(Arrays.asList(cities));

        // finding ListView Element from the FrontEnd layout
        cityList = findViewById(R.id.city_list);

        // setting our ArrayAdapter
        cityAdapter = new CityArrayAdapter(this, dataList);

        // setting up our frontend to take advantage of the Adapter object
        cityList.setAdapter(cityAdapter);

        findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAddCityDialog(view);
            }
        });

        findViewById(R.id.delete_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCity(view);
            }
        });

        findViewById(R.id.edit_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("Checked item position: " + cityList.getCheckedItemPosition());
                if (cityList.getCheckedItemPosition() == -1 ||
                        cityList.getCheckedItemPosition() >= dataList.size()) {
                    return;
                }
               showEditCityDialog(view);
            }
        });

    }

    public void deleteCity (View view) {
        int cityPosition = cityList.getCheckedItemPosition();
        Object cityToDelete = cityList.getItemAtPosition(cityPosition);
        cityAdapter.remove((City) cityToDelete);
    }

    public void showAddCityDialog(View view) {
        // bring up pop up dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_city_dialog);
        dialog.show();

        dialog.findViewById(R.id.confirmCity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addCity(dialog);
            }
        });
        dialog.findViewById(R.id.cancelCity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void showEditCityDialog(View view) {
        // get city object
        int cityPosition = cityList.getCheckedItemPosition();
        City cityToEdit = (City) cityList.getItemAtPosition(cityPosition);

        // bring up pop up dialog
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.add_city_dialog);
        dialog.show();

        TextInputEditText cityText = dialog.findViewById(R.id.add_city_input);
        TextInputEditText provinceText = dialog.findViewById(R.id.add_province_input);

        cityText.setText(cityToEdit.getName());
        provinceText.setText(cityToEdit.getProvince());


        dialog.findViewById(R.id.confirmCity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editCity(dialog, cityToEdit);
            }
        });
        dialog.findViewById(R.id.cancelCity_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
    }

    public void addCity(Dialog dialog) {
        // Get the String the user entered
        TextInputEditText cityText = dialog.findViewById(R.id.add_city_input);
        TextInputEditText provinceText = dialog.findViewById(R.id.add_province_input);

        // Ensure text is not null or empty
        if (cityText.getText() == null || cityText.getText().toString().isEmpty() ||
                provinceText.getText() == null || provinceText.getText().toString().isEmpty()) {
            dialog.dismiss();
        } else {
            String newCity = cityText.getText().toString();
            String newProvince = provinceText.getText().toString();
            City newCityObj = new City(newCity, newProvince);
            // add city to list
            cityAdapter.add(newCityObj);
            dialog.dismiss();
        }
    }

    public void editCity(Dialog dialog, City cityObj) {
        TextInputEditText cityText = dialog.findViewById(R.id.add_city_input);
        TextInputEditText provinceText = dialog.findViewById(R.id.add_province_input);

        // Ensure text is not null or empty
        if (cityText.getText() == null || cityText.getText().toString().isEmpty() ||
                provinceText.getText() == null || provinceText.getText().toString().isEmpty()) {
            dialog.dismiss();
        } else {
            String newCity = cityText.getText().toString();
            cityObj.setCity(newCity);
            String newProvince = provinceText.getText().toString();
            cityObj.setProvince(newProvince);

            cityAdapter.notifyDataSetChanged();
            dialog.dismiss();
        }
    }
}