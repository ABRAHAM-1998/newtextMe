// Generated by view binder compiler. Do not edit!
package com.twentytwo.textme.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.twentytwo.textme.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemMessageFromImageBinding implements ViewBinding {
  @NonNull
  private final FrameLayout rootView;

  @NonNull
  public final ImageView imageView;

  @NonNull
  public final RelativeLayout messageRoot;

  @NonNull
  public final TextView textTime;

  @NonNull
  public final ImageView tickread;

  private ItemMessageFromImageBinding(@NonNull FrameLayout rootView, @NonNull ImageView imageView,
      @NonNull RelativeLayout messageRoot, @NonNull TextView textTime,
      @NonNull ImageView tickread) {
    this.rootView = rootView;
    this.imageView = imageView;
    this.messageRoot = messageRoot;
    this.textTime = textTime;
    this.tickread = tickread;
  }

  @Override
  @NonNull
  public FrameLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemMessageFromImageBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemMessageFromImageBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_message_from_image, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemMessageFromImageBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView;
      ImageView imageView = rootView.findViewById(id);
      if (imageView == null) {
        break missingId;
      }

      id = R.id.message_root;
      RelativeLayout messageRoot = rootView.findViewById(id);
      if (messageRoot == null) {
        break missingId;
      }

      id = R.id.textTime;
      TextView textTime = rootView.findViewById(id);
      if (textTime == null) {
        break missingId;
      }

      id = R.id.tickread;
      ImageView tickread = rootView.findViewById(id);
      if (tickread == null) {
        break missingId;
      }

      return new ItemMessageFromImageBinding((FrameLayout) rootView, imageView, messageRoot,
          textTime, tickread);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
