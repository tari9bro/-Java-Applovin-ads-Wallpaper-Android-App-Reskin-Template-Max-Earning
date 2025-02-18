package com.varxiodz.akebichanwallpapers;


import static com.varxiodz.akebichanwallpapers.ads.Ads.rewardedAd;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.lifecycle.LiveData;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.varxiodz.akebichanwallpapers.ads.Ads;
import com.varxiodz.akebichanwallpapers.ads.preferencesHelper;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class FileAdapter2 extends RecyclerView.Adapter<FileAdapter2.FileViewHolder> {
    private final Context context;
    private final Activity activity;
    private final List<String> fileList;
    String fileName;
          Ads ads;

    LiveData<String>  fn ;
    preferencesHelper pref;

    WallpaperSetter wallpaperSetter;



    public FileAdapter2(Context context, List<String> fileList, Activity activity) {
        this.context = context;
        this.fileList = fileList;

        this.activity = activity;
    }
    public static boolean containsDigitThree(int number) {
        String numberString = String.valueOf(number);
        return numberString.contains("3");
    }
    @NonNull
    @Override
    public FileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context).inflate(R.layout.item_file2, parent, false);
        return new FileViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull FileViewHolder holder, @SuppressLint("RecyclerView") int position) {


        fileName = fileList.get(position);
        pref = new preferencesHelper(activity);
        wallpaperSetter = new WallpaperSetter(context);

        ads = new Ads(activity,context);
        if (!containsDigitThree(position) ) {
            holder.badgeImageView.setVisibility(View.GONE);
        } else {
            holder.badgeImageView.setVisibility(View.VISIBLE);
        }
        // Load image from assets folder using AssetManager
        try {
            AssetManager assetManager = context.getAssets();
            InputStream inputStream = assetManager.open(fileName);


            // Create a Bitmap with the desired dimensions
            Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);
            if (originalBitmap != null) {
                Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap,500, 500, false);

                // Create a RoundedBitmapDrawable from the Bitmap and set it as the placeholder
                RoundedBitmapDrawable roundedBitmapDrawable = RoundedBitmapDrawableFactory.create(context.getResources(), scaledBitmap);
                roundedBitmapDrawable.setCornerRadius(16f); // Adjust the corner radius as per your preference
                holder.fileImageView.setImageDrawable(roundedBitmapDrawable);

                // Remember to recycle the originalBitmap to release memory
                originalBitmap.recycle();
            } else {
                // If decoding fails, set a default image
                holder.fileImageView.setImageResource(R.drawable.default_image);
            }
        } catch (IOException e) {
            e.printStackTrace();
            holder.fileImageView.setImageResource(R.drawable.default_image);
        }





        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ( containsDigitThree(position)) {
                    if (rewardedAd.isReady()){
                        ShowDialog(fileList.get(position));
                    }else{
                        Toast.makeText(context,"not ready, try later", Toast.LENGTH_SHORT).show();

                    }

                }else {

                        wallpaperSetter.showWallpaperDialog(fileList.get(position));

            }
            }});



        }

    @Override
    public int getItemCount() {
        return fileList.size();
    }

    static class FileViewHolder extends RecyclerView.ViewHolder {
        ImageView fileImageView;
        ImageView badgeImageView;


        FileViewHolder(@NonNull View itemView) {
            super(itemView);
            fileImageView = itemView.findViewById(R.id.fileImageView);
            badgeImageView = itemView.findViewById(R.id.badgeImageView);

        }
    }

        private void ShowDialog(String fileName) {
            MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(context);
            dialog.setTitle(R.string.exit_dialog_title);
            dialog.setIcon(R.drawable.ic_exit);
            dialog.setMessage(R.string.rewarded_dialog);
            dialog.setCancelable(false);
            dialog.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {

                    if(pref.LoadBool("Rewarded")){
                        pref.SaveBool("Rewarded",false);
                        WallpaperSetter wallpaperSetter = new WallpaperSetter(context);
                            wallpaperSetter.showWallpaperDialog(fileName);

                    }else{
                        ads.playRewarded();
                    }
                }
            });

            dialog.setNegativeButton(R.string.no, (dialogInterface, i) -> dialogInterface.dismiss()) ;
            dialog.show();

        }
}
