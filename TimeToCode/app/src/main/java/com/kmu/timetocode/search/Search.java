package com.kmu.timetocode.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kmu.timetocode.ChallengeItemAdapter;
import com.kmu.timetocode.ChallengeItemModel;
import com.kmu.timetocode.ChallengeListAdapter;
import com.kmu.timetocode.ChallengeListModel;
import com.kmu.timetocode.R;
import com.kmu.timetocode.login.LoginActivity;
import com.kmu.timetocode.login.UserProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Search extends Fragment {
    EditText editSearch;
    ImageView btnSearch;
    LinearLayout beforeSearch;
    SwipeRefreshLayout refresh;
    RecyclerView beforeView, afterView;
    SearchAdapter searchAdapter;
    ChallengeListAdapter challengeItemAdapter;

    RequestQueue queue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        editSearch = view.findViewById(R.id.editSearch);

        beforeSearch = view.findViewById(R.id.beforeSearch);

        beforeView = view.findViewById(R.id.beforeView);
        beforeView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter(requireContext());
        beforeView.setAdapter(searchAdapter);

        refresh = view.findViewById(R.id.refresh);
        refresh.setOnRefreshListener(() -> {
            searchAdapter.refresh();
            refresh.setRefreshing(false);
        });

        searchAdapter.setOnItemClickListener((position, data) -> {
            // 최근 검색어 클릭시 검색
            searchAdapter.delete(position);
            addSearch();
            showSearchList(editSearch.getText().toString());
            beforeSearch.setVisibility(View.GONE);
            afterView.setVisibility(View.VISIBLE);
        });

        afterView = view.findViewById(R.id.afterView);
        afterView.setLayoutManager(new LinearLayoutManager(getContext()));

        btnSearch = view.findViewById(R.id.btnSearch);
        btnSearch.setOnClickListener(view1 -> {
            // 검색 버튼 클릭시 검색
            addSearch();
            showSearchList(editSearch.getText().toString());
            beforeSearch.setVisibility(View.GONE);
            afterView.setVisibility(View.VISIBLE);
        });

        return view;
    }

    private void addSearch() {
        String url = LoginActivity.url + "/usersearch/add";

        StringRequest sr = new StringRequest(Request.Method.POST, url, response -> {
            Log.e("searchAdd", response);
        }, error -> {
            Log.e("searchAdd", error.toString());
        }) {
            @Override
            protected Map<String, String> getParams() throws Error {
                Map<String, String> params = new HashMap<>();
                params.put("idUser", Integer.toString(UserProfile.getId()));
                params.put("Search", editSearch.getText().toString());
                return params;
            }
        };

        sr.setShouldCache(false);
        queue = Volley.newRequestQueue(requireContext());
        queue.add(sr);
    }

    private void showSearchList(String searchText) {
        String url = LoginActivity.url + "/challenge/nameChallenge";

        StringRequest sr = new StringRequest(Request.Method.POST, url, response -> {
            Log.e("SearchList", response);
            ArrayList<ChallengeListModel> challengeList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String nameChallenge = jsonObject.getString("nameChallenge");
                    String imageLink = jsonObject.getString("imageLink");
                    int madeIdUser = jsonObject.getInt("madeIdUser");
                    int countUser = jsonObject.getInt("countUser");
                    challengeList.add(new ChallengeListModel(imageLink, nameChallenge, Integer.toString(madeIdUser), Integer.toString(countUser), "github", "알고리즘"));
                }
            } catch (Exception e) {
                Log.e("SearchJSON", "예외 발생");
            }
            challengeItemAdapter = new ChallengeListAdapter(requireContext(), challengeList);
        }, error -> {
            Log.e("searchResult", error.toString());
        }) {
            @Override
            protected Map<String, String> getParams() throws Error {
                Map<String, String> params = new HashMap<>();
                params.put("nameChallenge", searchText);
                return params;
            }
        };

        sr.setShouldCache(false);
        queue = Volley.newRequestQueue(requireContext());
        queue.add(sr);
    }
}
