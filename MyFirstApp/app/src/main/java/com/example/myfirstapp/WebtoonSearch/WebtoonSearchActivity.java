package com.example.myfirstapp.WebtoonSearch;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.myfirstapp.R;
import com.example.myfirstapp.Singleton;
import com.example.myfirstapp.WebtoonContentsList.WebtoonContentsListActivity;
import com.example.myfirstapp.WebtoonSearch.Entities.ResponseWebtoonSearchData;
import com.example.myfirstapp.common.Adapter.WebtoonListAdapter;
import com.example.myfirstapp.baseClass.BaseWebtoonData;
import com.example.myfirstapp.common.Entities.WebtoonData;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebtoonSearchActivity extends AppCompatActivity {

    private Context context;

    private ImageView ivSearchButton;
    private AutoCompleteTextView atvSearch;
    private WebtoonListAdapter adapter;
    private ListView lvSearchResult;
    private ArrayAdapter<String> arrayAdapter;
    private ArrayList<WebtoonData> searchResultList = new ArrayList<>();

    private void init() {
        context = this;

        lvSearchResult = findViewById(R.id.result_search);
        atvSearch = findViewById(R.id.autoCompleteTextView);
        ivSearchButton = findViewById(R.id.button_search);

        adapter = new WebtoonListAdapter(WebtoonSearchActivity.this, searchResultList, R.layout.item_list_webtoon_loose_form, WebtoonListAdapter.TYPE_LIST);

    }
    private void toWebtoonContentsList(WebtoonData item){
        if(Singleton.isStartActivity){
            return;
        }
        Singleton.isStartActivity = true;
        Intent intent = new Intent(WebtoonSearchActivity.this, WebtoonContentsListActivity.class);
        intent.putExtra("comic", item);
        startActivity(intent);
    }
    private void setSearchListView() {
        lvSearchResult.setAdapter(adapter);
        lvSearchResult.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                WebtoonData item = (WebtoonData) lvSearchResult.getItemAtPosition(position);
                toWebtoonContentsList(item);
            }
        });
    }

    private void requestGetSearchResult() {
        String query = atvSearch.getText().toString();
        try {
            String queryEncode = URLEncoder.encode(query, "UTF-8");

            Call<ResponseWebtoonSearchData> searchDataCall =
                    Singleton.softcomicsService.getSearchResult(queryEncode);
            searchDataCall.enqueue(new Callback<ResponseWebtoonSearchData>() {
                @Override
                public void onResponse(Call<ResponseWebtoonSearchData> call, Response<ResponseWebtoonSearchData> response) {
                    if (response.isSuccessful()) {
                        switch (response.body().getCode()) {
                            case 100://가져오기 성공
                                List<WebtoonData> list = response.body().getResult();
                                searchResultList = new ArrayList<>(list);
                                adapter.setDataList(searchResultList);
                                break;
                            default:
                                Toast.makeText(context, "code : " + response.body().getCode(), Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Log.d("에러내용 ", call.toString());
                        Toast.makeText(context, "에러", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseWebtoonSearchData> call, Throwable t) {
                    Toast.makeText(context, "서버 통신 에러", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            Toast.makeText(context, "검색 실패 : " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webtoon_search);
        Singleton.isStartActivity = false;

        init();
        setSearchListView();

        ivSearchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestGetSearchResult();
            }
        });
    }
}
