package com.example.weshoppers;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;

import java.util.List;

public class ItemAdapter extends ArrayAdapter<Items> {
  Activity content;
  List<Items> itemsList;

    public ItemAdapter( Activity context, List<Items> itemsList) {
        super(context, R.layout.itemfile, itemsList);
        this.content = context;
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=content.getLayoutInflater();
        convertView=inflater.inflate(R.layout.itemfile,null,true);
        ImageView imageView=convertView.findViewById(R.id.itemView);
        TextView title=convertView.findViewById(R.id.TitleView);
        TextView price=convertView.findViewById(R.id.PriceView);
        TextView offers=convertView.findViewById(R.id.OffersView);
        Items items=itemsList.get(position);
        Glide.with(imageView.getContext())
                .load(items.getId1())
                .into(imageView);
        title.setText(items.getTitle());
        price.setText(items.getPrice());
        offers.setText(items.getItemid());
        return convertView;
    }
}
