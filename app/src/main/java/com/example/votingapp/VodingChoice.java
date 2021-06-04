package com.example.votingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class VodingChoice extends AppCompatActivity {


    List<String> candi = new ArrayList<>();
    List<Integer> draw = new ArrayList<>();
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voding_choice);


        candi.add("aitc");
        candi.add("bjp");
        candi.add("inc");
        candi.add("ncp");
        candi.add("bsp");

        draw.add(R.drawable.aitc);
        draw.add(R.drawable.bjp);
        draw.add(R.drawable.inc);
        draw.add(R.drawable.ncp);
        draw.add(R.drawable.bsp);




        lv = findViewById(R.id.lv);

        final DemoViewAdapter adapter = new DemoViewAdapter();
        lv.setAdapter(adapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(VodingChoice.this, ""+candi.get(position).toUpperCase()+" selected", Toast.LENGTH_SHORT).show();

                /*
                *
                * Items slected here
                *
                * */

                Intent intent = new Intent(getApplicationContext(),VoteDone.class);
                intent.putExtra("image",draw.get(position));
                intent.putExtra("candi_name",candi.get(position));
                startActivity(intent);

            }
        });


    }
    class DemoViewAdapter extends BaseAdapter {


        @Override
        public int getCount() {
            return candi.size();
        }

        @Override
        public Object getItem(int position) {
            return candi.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            LayoutInflater layoutInflater = getLayoutInflater();
            View v = layoutInflater.inflate(R.layout.list_choice,null);

            TextView candiNames = v.findViewById(R.id.candi_name);
            ImageView profileImage = v.findViewById(R.id.profile_image);

            profileImage.setImageResource(draw.get(position));

            candiNames.setText(candi.get(position));


            return v;
        }
    }
}