package com.kmu.timetocode.search;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kmu.timetocode.ChallengeItemAdapter;
import com.kmu.timetocode.ChallengeItemModel;
import com.kmu.timetocode.ChallengeListAdapter;
import com.kmu.timetocode.ChallengeListModel;
import com.kmu.timetocode.R;

import java.util.ArrayList;

public class Search extends Fragment {
    EditText editSearch;
    LinearLayout beforeSearch;
    RecyclerView beforeView, afterView;
    SearchAdapter searchAdapter;
    ChallengeListAdapter challengeItemAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);

        editSearch = view.findViewById(R.id.editSearch);
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (!editSearch.getText().toString().equals("")) {
                    beforeSearch.setVisibility(View.GONE);
                    afterView.setVisibility(View.VISIBLE);
                } else {
                    beforeSearch.setVisibility(View.VISIBLE);
                    afterView.setVisibility(View.GONE);
                }
            }
        });

        beforeSearch = view.findViewById(R.id.beforeSearch);

        beforeView = view.findViewById(R.id.beforeView);
        beforeView.setLayoutManager(new LinearLayoutManager(getContext()));
        searchAdapter = new SearchAdapter();
        beforeView.setAdapter(searchAdapter);

        afterView = view.findViewById(R.id.afterView);
        afterView.setLayoutManager(new LinearLayoutManager(getContext()));
        ArrayList<ChallengeListModel> challengeList = new ArrayList<>();
        challengeList.add(new ChallengeListModel("a", "챌린지 이름", "생성자", "60", "github", "알고리즘"));
        challengeItemAdapter = new ChallengeListAdapter(requireContext(), challengeList);

        return view;
    }
}
