package com.kmu.timetocode.search;

import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.FrameLayout;
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
import com.android.volley.VolleyError;
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

    String historyText = "";
    RecyclerView beforeView, afterView;
    SwipeRefreshLayout refresh2;
    SearchAdapter searchAdapter;
    ChallengeListAdapter challengeItemAdapter;

    RequestQueue queue;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        beforeSearch = view.findViewById(R.id.beforeSearch);
        beforeView = view.findViewById(R.id.beforeView);
        editSearch = view.findViewById(R.id.editSearch);
        btnSearch = view.findViewById(R.id.btnSearch);
        refresh2 = view.findViewById(R.id.refresh2);
        afterView = view.findViewById(R.id.afterView);
        refresh = view.findViewById(R.id.refresh);

        InputMethodManager imm = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        lm.setReverseLayout(true);
        lm.setStackFromEnd(true);
        beforeView.setLayoutManager(lm);
        searchAdapter = new SearchAdapter(requireContext());
        beforeView.setAdapter(searchAdapter);

        refresh.setOnRefreshListener(() -> {
            searchAdapter.refresh();
            beforeView.setAdapter(searchAdapter);
            refresh.setRefreshing(false);
        });

        searchAdapter.setOnItemClickListener((position, data) -> {
            // 최근 검색어 클릭시 검색
            imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
            editSearch.clearFocus();
            editSearch.setText(data);
            historyText = data;
            addSearch(data);
            showSearchList(data);
            beforeSearch.setVisibility(View.GONE);
            afterView.setVisibility(View.VISIBLE);
        });

        LinearLayoutManager lm2 = new LinearLayoutManager(getContext());
        lm2.setReverseLayout(true);
        lm2.setStackFromEnd(true);

        afterView.setLayoutManager(lm2);
        showSearchList("");

        refresh2.setOnRefreshListener(() -> {
            showSearchList(historyText);
            refresh2.setRefreshing(false);
        });

        btnSearch.setOnClickListener(view1 -> {
            // 검색 버튼 클릭시 검색
            imm.hideSoftInputFromWindow(editSearch.getWindowToken(), 0);
            editSearch.clearFocus();
            addSearch(editSearch.getText().toString());
            historyText = editSearch.getText().toString();
            showSearchList(editSearch.getText().toString());
            beforeSearch.setVisibility(View.GONE);
            afterView.setVisibility(View.VISIBLE);
            searchAdapter.refresh();
        });

        editSearch.setOnFocusChangeListener((view12, b) -> {
            beforeView.setAdapter(searchAdapter);
            beforeSearch.setVisibility(View.VISIBLE);
            afterView.setVisibility(View.GONE);
        });

        return view;
    }

    private void addSearch(String search) {
        if (search.equals(""))
            return;
        String url = LoginActivity.url + "/usersearch/add";

        StringRequest sr = new StringRequest(Request.Method.POST, url, response -> {
        }, error -> {
            Log.e("searchAdd", error.toString());
            searchAdapter.refresh();
        }) {
            @Override
            protected Map<String, String> getParams() throws Error {
                Map<String, String> params = new HashMap<>();
                params.put("idUser", Integer.toString(UserProfile.getId()));
                params.put("search", search);
                return params;
            }
        };

        sr.setShouldCache(false);
        queue = Volley.newRequestQueue(requireContext());
        queue.add(sr);
    }

    private void showSearchList(String searchText) {
        String url = LoginActivity.url + "/challenge/challengeList?nameChallenge=" + searchText;
        StringRequest sr = new StringRequest(Request.Method.GET, url, response -> {
            ArrayList<ChallengeListModel> challengeList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String nameChallenge = jsonObject.getString("nameChallenge");
                    String imageLink = jsonObject.getString("imageLink");
                    String madeName = jsonObject.getString("name");
                    int count = jsonObject.getInt("countUser");
                    String tag1 = jsonObject.getString("tagName1");
                    String tag2 = jsonObject.getString("tagName2");
                    challengeList.add(new ChallengeListModel(imageLink, nameChallenge, madeName, Integer.toString(count), tag1, tag2));
                }
            } catch (Exception e) {
                Log.e("SearchListJSON", response);
            }
            challengeItemAdapter = new ChallengeListAdapter(requireContext(), challengeList);
            afterView.setAdapter(challengeItemAdapter);
        }, error -> {
            Log.e("searchList", searchText + "/" + error.toString());
        });

        sr.setShouldCache(false);
        queue = Volley.newRequestQueue(requireContext());
        queue.add(sr);
    }
}
