package com.kmu.timetocode.search;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
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
import com.kmu.timetocode.login.UserProfile;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.ViewHolder> {
    private Context context;
    private ArrayList<String> mData = new ArrayList<>();
    private OnItemClickListener mListener;
    private RequestQueue queue;

    public interface OnItemClickListener {
        void onItemClick(int position, String data);
    }

    public SearchAdapter(Context context) {
        this.context = context;
        refresh();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public void refresh() {
        String url = LoginActivity.url + "/usersearch/get?idUser=" + UserProfile.getId();

        @SuppressLint("NotifyDataSetChanged")
        StringRequest sr = new StringRequest(Request.Method.GET, url, response -> {
            // 최근검색어 리스트
            mData.clear();
            try {
                JSONArray jsonArray = new JSONArray(response);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    mData.add(jsonObject.getString("search"));
                }
            } catch (Exception e) {
                Log.e("SearchHistoryJSON", response);
            }
            notifyDataSetChanged();
        }, error -> {
            Log.e("SearchHistory", error.toString());
        });

        sr.setShouldCache(false);
        queue = Volley.newRequestQueue(context);
        queue.add(sr);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textSearch;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textSearch = itemView.findViewById(R.id.textSearch);
        }

        public TextView getTextView() {
            return textSearch;
        }

        void onBind(String search, int position) {
            textSearch.setText(search);
        }
    }

    @NonNull
    @Override
    public SearchAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_search_page, parent, false);
        SearchAdapter.ViewHolder viewHolder = new SearchAdapter.ViewHolder(view);
        view.setOnClickListener(view1 -> {
            String data = "";
            int position = viewHolder.getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                data = viewHolder.getTextView().getText().toString();
            }
            mListener.onItemClick(position, data);
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.ViewHolder holder, int position) {
        holder.onBind(mData.get(position), position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }
}
