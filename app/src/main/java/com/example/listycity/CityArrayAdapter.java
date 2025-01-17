package com.example.listycity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CityArrayAdapter extends ArrayAdapter<City> {
    private Context context;
    private ArrayList<City> cities;
    public CityArrayAdapter(Context context, ArrayList<City> cities) {
        super(context, 0, cities);
        this.context = context;
        this.cities = cities;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup
            parent) {
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(R.layout.content,
                    parent, false);
        } else {
            view = convertView;
        }
        City city = cities.get(position);
        TextView cityName = view.findViewById(R.id.content_view);
        TextView provinceName = view.findViewById(R.id.content_view_province);
        cityName.setText(city.getName());
        provinceName.setText(city.getProvince());
        return view;
    }
}

