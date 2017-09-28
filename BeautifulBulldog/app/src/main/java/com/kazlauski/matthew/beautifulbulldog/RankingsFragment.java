package com.kazlauski.matthew.beautifulbulldog;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import io.realm.Realm;
import io.realm.RealmResults;


/**
 * A simple {@link Fragment} subclass.
 */
public class RankingsFragment extends Fragment {
    private ListView bulldogList;
    MainActivity mainActivity;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_bulldog_list, container, false);

        bulldogList = (ListView) view.findViewById(R.id.bulldog_list);

        mainActivity = (MainActivity) this.getActivity();

        BulldogRankingArray adapter = new BulldogRankingArray(this.getActivity(), getAvailableBulldogs());
        bulldogList.setAdapter(adapter);

        bulldogList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                final Bulldog bulldog = (Bulldog) adapterView.getItemAtPosition(i);
                Intent intent = new Intent(view.getContext(), BulldogActivity.class);
                intent.putExtra("bulldog", bulldog.getId());
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        BulldogRankingArray adapter = new BulldogRankingArray(this.getActivity(), this.getAvailableBulldogs());
        bulldogList.setAdapter(adapter);
    }


    public ArrayList<Bulldog> getAvailableBulldogs() {
        ArrayList<Bulldog> bulldogs2 = new ArrayList<Bulldog>(mainActivity.realm.where(Bulldog.class).findAll());

        Collections.sort(bulldogs2, new Comparator<Bulldog>() {
            @Override
            public int compare(Bulldog bulldog1, Bulldog bulldog2) {
                return ((Double) bulldog1.getVotes().average("rating")).compareTo((Double) bulldog2.getVotes().average("rating"));
            }
        });
        return bulldogs2;

    }
}