package com.example.myfirstapp.activities;

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

import com.example.myfirstapp.R;
import com.example.myfirstapp.adapter.WebtoonListAdapter;
import com.example.myfirstapp.enitites.WebtoonContentsData;
import com.example.myfirstapp.enitites.WebtoonData;
import com.example.myfirstapp.enitites.WebtoonListData;
import com.example.myfirstapp.enitites.WebtoonSearchData;

import java.util.ArrayList;

import static com.example.myfirstapp.GlobalApplication.webtoonList;

public class WebtoonSearchActivity extends AppCompatActivity {

    ImageView ivSearchButton;
    AutoCompleteTextView atvSearch;
    WebtoonListAdapter adapter;
    ListView listView;
    ArrayAdapter<String> arrayAdapter;
    private void search(){
        String str = atvSearch.getText().toString();
        for(WebtoonListData webtoon : webtoonList){
            if(webtoon.getTitle().contains(str)){
                adapter.addItem(webtoon);
            }
        }
        if(adapter.getCount()==0){
            Toast.makeText(this, "검색 결과가 없습니다.", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_search);

        listView = findViewById(R.id.result_search);
        adapter=new WebtoonListAdapter(WebtoonSearchActivity.this, R.layout.item_webtoon_search_result, WebtoonListAdapter.TYPE_LIST);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WebtoonData item = (WebtoonData)listView.getItemAtPosition(position);
                String webtoonName=item.getTitle();
                Intent intent = new Intent(WebtoonSearchActivity.this, WebtoonListActivity.class);
                intent.putExtra("webtoonName", webtoonName);
                startActivity(intent);
            }
        });



        ArrayList<String> webtoonNameList = new ArrayList<>();

        for(WebtoonData e : webtoonList){
            webtoonNameList.add(e.getTitle());
        }

        atvSearch = findViewById(R.id.autoCompleteTextView);
        arrayAdapter = new ArrayAdapter<>(this, R.layout.item_search_autocomplete, webtoonNameList);
        atvSearch.setAdapter(arrayAdapter);

        ivSearchButton = findViewById(R.id.button_search);
        ivSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.clear();
                search();
                Toast.makeText(WebtoonSearchActivity.this, "새로고침", Toast.LENGTH_SHORT).show();
                adapter.notifyDataSetChanged();
            }
        });
    }
}
