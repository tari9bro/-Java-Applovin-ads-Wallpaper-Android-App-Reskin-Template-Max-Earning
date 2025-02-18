package com.varxiodz.akebichanwallpapers;



import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.varxiodz.akebichanwallpapers.ads.Ads;
import com.varxiodz.akebichanwallpapers.ads.preferencesHelper;
import com.varxiodz.akebichanwallpapers.db.FileUtilsHelper;

import java.util.List;

public class ImageFragment extends Fragment {
    private static final String ARG_FILE_NAME = "fileName";
    private static final String ARG_BADGE_RES_ID = "badgeResId"; // Add this for badge image

    Ads ads;
    preferencesHelper pref;

    public static ImageFragment newInstance(String fileName) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FILE_NAME, fileName);
        fragment.setArguments(args);
        return fragment;
    }

    // Add this method for badge image
    public static ImageFragment newInstanceWithBadge(String fileName, int badgeResId) {
        ImageFragment fragment = new ImageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FILE_NAME, fileName);
        args.putInt(ARG_BADGE_RES_ID, badgeResId);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_image, container, false);
        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);

        pref = new preferencesHelper(requireActivity());
        ads = new Ads(requireActivity(),requireContext());

        GridLayoutManager layoutManager = new GridLayoutManager(requireContext(),1); // or GridLayoutManager if you are using a grid layout
        recyclerView.setLayoutManager(layoutManager);

        List<String> fileList = FileUtilsHelper.getFilesFromAssets(requireContext());
        FileAdapter2 fileAdapter = new FileAdapter2(requireContext(), fileList, requireActivity()); // Adjust the placeholder dimensions as per your preference
        recyclerView.setAdapter(fileAdapter);

        layoutManager.scrollToPosition(pref.LoadInt("position"));











        // Add this block for badge image


        return rootView;
    }





}