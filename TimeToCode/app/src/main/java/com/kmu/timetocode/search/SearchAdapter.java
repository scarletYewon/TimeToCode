package com.kmu.timetocode.search;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.kmu.timetocode.R;
import com.kmu.timetocode.login.LoginActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> mData = new ArrayList<>();
    private List<String> keys = new ArrayList<>();
    private OnItemClickListener mListener;
    private RequestQueue queue;

    public interface OnItemClickListener {
        void onItemClick(int component, int position);
    }

    public SearchAdapter() {
        mData.add("github");
//        StringBuilder searchUrl = new StringBuilder();
//        searchUrl.append(LoginActivity.url);
//        searchUrl.append("/usersearch/get");
//
//        StringRequest sr = new StringRequest(Request.Method.POST, searchUrl.toString(), response -> {
//            // 최근검색어 리스트
//            // mData.clear();
//            // mData.add("string~");
//            // notifyDataSetChanged();
//        }, error -> {
//
//        }) {
//            @Override
//            protected Map<String, String> getParams() throws Error {
//                Map<String, String> params = new HashMap<>();
//                // 회원 id 가져오기
//                // params.put("idUser", id);
//                return params;
//            }
//        };
//
//        sr.setShouldCache(false);
//        queue = Volley.newRequestQueue(context);
//        queue.add(sr);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textSearch;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            textSearch = itemView.findViewById(R.id.textSearch);
        }
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_page, parent, false);
        context = view.getContext();
        return new SearchAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
