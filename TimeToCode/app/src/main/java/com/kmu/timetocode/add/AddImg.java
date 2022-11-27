package com.kmu.timetocode.add;

import android.net.Uri;
import android.util.Log;

import com.google.firebase.storage.FirebaseStorage;

public class AddImg {
    public static void AddUri(Uri uri, String key) {
        //파이어베이스에 내 사진을 올리고, Uri 를 서버로.
        FirebaseStorage.getInstance().getReference().child("UserImages_" + key).putFile(uri).addOnCompleteListener(task1 -> {
            Log.d("Firebase", "사진 올림");
        });
    }
}
