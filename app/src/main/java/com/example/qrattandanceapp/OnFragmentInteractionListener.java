package com.example.qrattandanceapp;

import android.net.Uri;
import android.view.View;

public interface OnFragmentInteractionListener {
    void onFragment(View v);
    void onFragment(Uri uri);

    void onFragmentInteraction(Uri uri);
}
