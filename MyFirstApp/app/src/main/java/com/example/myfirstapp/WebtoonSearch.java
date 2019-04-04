package com.example.myfirstapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class WebtoonSearch extends AppCompatActivity {

    ImageView ivSearchButton;
    AutoCompleteTextView atvSearch;
    SearchListViewAdapter adapter;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    private void search(){
        String str = atvSearch.getText().toString();
        for(int i=0; i<MainActivity.WebtoonNames.length; i++){
            if(MainActivity.WebtoonNames[i].contains(str)){
                adapter.addItem(MainActivity.WebtoonNames[i], MainActivity.WebtoonThumnails[i]);
                Toast.makeText(this, MainActivity.WebtoonNames[i]+" 추가", Toast.LENGTH_SHORT).show();
            };
        };
        Toast.makeText(this, adapter.getCount()+"개의 검색결과", Toast.LENGTH_SHORT).show();
        if(adapter.getCount()==0){
            adapter.addItem("검색 결과가 없습니다.", R.drawable.ic_checked);
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_search);

        listView = findViewById(R.id.result_search);
        adapter=new SearchListViewAdapter(WebtoonSearch.this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                ListItem item =(ListItem)listView.getItemAtPosition(position);
                String webtoonName=item.getTvTitle();
                Intent intent = new Intent(WebtoonSearch.this, WebtoonList.class);
                intent.putExtra("webtoonName", webtoonName);
                startActivity(intent);
            }
        });


        atvSearch = findViewById(R.id.autoCompleteTextView);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.item_search_autocomplete, MainActivity.WebtoonNames);
        atvSearch.setAdapter(arrayAdapter);

        ivSearchButton = findViewById(R.id.button_search);
        ivSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                search();
                Toast.makeText(WebtoonSearch.this, "새로고침", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
