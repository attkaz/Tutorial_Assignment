package com.kazlauski.matthew.beautifulbulldog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Matthew on 9/27/2017.
 */

public class BulldogRankingArray extends BaseAdapter{
        private Context mContext;
        private LayoutInflater mInflater;
        private ArrayList<Bulldog> mDataSource;

        public BulldogRankingArray(Context context, ArrayList<Bulldog> items) {
            mContext = context;
            mDataSource = items;
            mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return mDataSource.size();
        }
        @Override
        public Bulldog getItem(int position) {
            return mDataSource.get(position);
        }
        @Override
        public long getItemId(int position) {
            return position;
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View rowView = mInflater.inflate(R.layout.bulldog_cell, parent, false);

            TextView name = (TextView) rowView.findViewById(R.id.name_label);
            name.setText(getItem(position).getName());

            TextView age = (TextView) rowView.findViewById(R.id.age_label);
            age.setText((int)getItem(position).getVotes().average("rating"));


            return rowView;
        }

}

