// Generated by view binder compiler. Do not edit!
package com.twentytwo.textme.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import com.twentytwo.textme.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemsFeedsBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final CircleImageView ContactsImage;

  @NonNull
  public final LinearLayout L1;

  @NonNull
  public final RelativeLayout L2;

  @NonNull
  public final TextView feedDesc;

  @NonNull
  public final TextView feedLocation;

  @NonNull
  public final TextView feedsDate;

  @NonNull
  public final RelativeLayout feedsROOT;

  @NonNull
  public final TextView feedsTitle;

  @NonNull
  public final ImageView imageView;

  private ItemsFeedsBinding(@NonNull CardView rootView, @NonNull CircleImageView ContactsImage,
      @NonNull LinearLayout L1, @NonNull RelativeLayout L2, @NonNull TextView feedDesc,
      @NonNull TextView feedLocation, @NonNull TextView feedsDate,
      @NonNull RelativeLayout feedsROOT, @NonNull TextView feedsTitle,
      @NonNull ImageView imageView) {
    this.rootView = rootView;
    this.ContactsImage = ContactsImage;
    this.L1 = L1;
    this.L2 = L2;
    this.feedDesc = feedDesc;
    this.feedLocation = feedLocation;
    this.feedsDate = feedsDate;
    this.feedsROOT = feedsROOT;
    this.feedsTitle = feedsTitle;
    this.imageView = imageView;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemsFeedsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemsFeedsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.items_feeds, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemsFeedsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ContactsImage;
      CircleImageView ContactsImage = rootView.findViewById(id);
      if (ContactsImage == null) {
        break missingId;
      }

      id = R.id.L1;
      LinearLayout L1 = rootView.findViewById(id);
      if (L1 == null) {
        break missingId;
      }

      id = R.id.L2;
      RelativeLayout L2 = rootView.findViewById(id);
      if (L2 == null) {
        break missingId;
      }

      id = R.id.feed_desc;
      TextView feedDesc = rootView.findViewById(id);
      if (feedDesc == null) {
        break missingId;
      }

      id = R.id.feed_location;
      TextView feedLocation = rootView.findViewById(id);
      if (feedLocation == null) {
        break missingId;
      }

      id = R.id.feeds_date;
      TextView feedsDate = rootView.findViewById(id);
      if (feedsDate == null) {
        break missingId;
      }

      id = R.id.feedsROOT;
      RelativeLayout feedsROOT = rootView.findViewById(id);
      if (feedsROOT == null) {
        break missingId;
      }

      id = R.id.feeds_title;
      TextView feedsTitle = rootView.findViewById(id);
      if (feedsTitle == null) {
        break missingId;
      }

      id = R.id.imageView;
      ImageView imageView = rootView.findViewById(id);
      if (imageView == null) {
        break missingId;
      }

      return new ItemsFeedsBinding((CardView) rootView, ContactsImage, L1, L2, feedDesc,
          feedLocation, feedsDate, feedsROOT, feedsTitle, imageView);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
