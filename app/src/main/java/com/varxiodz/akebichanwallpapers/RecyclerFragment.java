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

import com.varxiodz.akebichanwallpapers.db.FileUtilsHelper;

import java.util.List;

public class RecyclerFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recycler, container, false);

        RecyclerView recyclerView = rootView.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(requireContext(), 2)); // Display 2 items per row

        List<String> fileList = FileUtilsHelper.getFilesFromAssets(requireContext());
        FileAdapter fileAdapter = new FileAdapter(requireContext(), fileList, 150, 350, requireActivity()); // Adjust the placeholder dimensions as per your preference
        recyclerView.setAdapter(fileAdapter);

        return rootView;
    }
}
