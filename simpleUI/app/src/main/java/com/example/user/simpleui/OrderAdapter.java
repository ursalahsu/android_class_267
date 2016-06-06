package com.example.user.simpleui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by user on 2016/6/6.
 */
public class OrderAdapter extends BaseAdapter {
    ArrayList<Order> orders;
    LayoutInflater inflater;

    public OrderAdapter(Context context, ArrayList<Order> orders) {
        this.orders = orders;
        this.inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return orders.size();
    }

    @Override
    public Object getItem(int position) {
        return orders.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.listview_order_item, null);
            TextView drinknameTextView = (TextView) convertView.findViewById(R.id.drinknameTextView);
            TextView noteTextView = (TextView) convertView.findViewById(R.id.noteTextView);
            holder= new Holder();
            holder.drinkname=drinknameTextView;
            holder.note=noteTextView;

            convertView.setTag(holder);
        }else{
            holder= (Holder)convertView.getTag();
        }

        Order order = orders.get(position);

        holder.drinkname.setText(order.drinkname);
        holder.note.setText(order.note);

        return convertView;
    }

    class Holder{
        TextView drinkname;
        TextView note;
    }
}
