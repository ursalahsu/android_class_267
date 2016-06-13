package com.example.user.simpleui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

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
            /* Inflater英文意思是膨胀，在Android中应该是扩展的意思吧。 LayoutInflater的作用类似于
            * findViewById(),不同点是LayoutInflater是用来找layout文件夹下的xml布局文件，并且实例化！ 而
            * findViewById()是找具体某一个xml下的具体 widget控件(如:Button,TextView等)。*/
            convertView = inflater.inflate(R.layout.listview_order_item, null);
            TextView drinknameTextView = (TextView) convertView.findViewById(R.id.drinknameTextView);
            TextView noteTextView = (TextView) convertView.findViewById(R.id.noteTextView);

            holder = new Holder();
            holder.drinkname = drinknameTextView;
            holder.note = noteTextView;
            holder.storeinfo =  (TextView)convertView.findViewById(R.id.storeTextView);

            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        Order order = orders.get(position);

        holder.drinkname.setText(order.drinkname);
        holder.note.setText(order.note);
        holder.storeinfo.setText(order.storeInfo);

        return convertView;
    }
    //暫存空間
    class Holder {
        TextView drinkname;
        TextView note;
        TextView storeinfo;
    }
}
