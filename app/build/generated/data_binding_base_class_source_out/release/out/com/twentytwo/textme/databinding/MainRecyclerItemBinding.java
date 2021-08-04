// Generated by view binder compiler. Do not edit!
package com.twentytwo.textme.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import com.twentytwo.textme.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class MainRecyclerItemBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final TextView deletePost;

  @NonNull
  public final ImageView mainRecLogo;

  @NonNull
  public final ProgressBar progressBar;

  private MainRecyclerItemBinding(@NonNull CardView rootView, @NonNull TextView deletePost,
      @NonNull ImageView mainRecLogo, @NonNull ProgressBar progressBar) {
    this.rootView = rootView;
    this.deletePost = deletePost;
    this.mainRecLogo = mainRecLogo;
    this.progressBar = progressBar;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static MainRecyclerItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static MainRecyclerItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.main_recycler_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static MainRecyclerItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.deletePost;
      TextView deletePost = rootView.findViewById(id);
      if (deletePost == null) {
        break missingId;
      }

      id = R.id.main_rec_logo;
      ImageView mainRecLogo = rootView.findViewById(id);
      if (mainRecLogo == null) {
        break missingId;
      }

      id = R.id.progressBar;
      ProgressBar progressBar = rootView.findViewById(id);
      if (progressBar == null) {
        break missingId;
      }

      return new MainRecyclerItemBinding((CardView) rootView, deletePost, mainRecLogo, progressBar);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
