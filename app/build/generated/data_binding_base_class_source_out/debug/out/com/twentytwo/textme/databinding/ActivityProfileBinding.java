// Generated by view binder compiler. Do not edit!
package com.twentytwo.textme.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;
import com.twentytwo.textme.R;
import de.hdodenhof.circleimageview.CircleImageView;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityProfileBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final CircleImageView ImagePrewiew;

  @NonNull
  public final Button LogOutBtm;

  @NonNull
  public final Button btEditProfile;

  @NonNull
  public final RelativeLayout imgUser;

  @NonNull
  public final RelativeLayout l3;

  @NonNull
  public final TextView l4;

  @NonNull
  public final RecyclerView mainRecyclerView;

  @NonNull
  public final LinearLayout rellay1;

  @NonNull
  public final Button sendMessageProfile;

  @NonNull
  public final TextView tvName;

  @NonNull
  public final TextView tvNickname;

  @NonNull
  public final TextView tvStatus;

  private ActivityProfileBinding(@NonNull ScrollView rootView,
      @NonNull CircleImageView ImagePrewiew, @NonNull Button LogOutBtm,
      @NonNull Button btEditProfile, @NonNull RelativeLayout imgUser, @NonNull RelativeLayout l3,
      @NonNull TextView l4, @NonNull RecyclerView mainRecyclerView, @NonNull LinearLayout rellay1,
      @NonNull Button sendMessageProfile, @NonNull TextView tvName, @NonNull TextView tvNickname,
      @NonNull TextView tvStatus) {
    this.rootView = rootView;
    this.ImagePrewiew = ImagePrewiew;
    this.LogOutBtm = LogOutBtm;
    this.btEditProfile = btEditProfile;
    this.imgUser = imgUser;
    this.l3 = l3;
    this.l4 = l4;
    this.mainRecyclerView = mainRecyclerView;
    this.rellay1 = rellay1;
    this.sendMessageProfile = sendMessageProfile;
    this.tvName = tvName;
    this.tvNickname = tvNickname;
    this.tvStatus = tvStatus;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ImagePrewiew;
      CircleImageView ImagePrewiew = rootView.findViewById(id);
      if (ImagePrewiew == null) {
        break missingId;
      }

      id = R.id.LogOutBtm;
      Button LogOutBtm = rootView.findViewById(id);
      if (LogOutBtm == null) {
        break missingId;
      }

      id = R.id.bt_editProfile;
      Button btEditProfile = rootView.findViewById(id);
      if (btEditProfile == null) {
        break missingId;
      }

      id = R.id.imgUser;
      RelativeLayout imgUser = rootView.findViewById(id);
      if (imgUser == null) {
        break missingId;
      }

      id = R.id.l3;
      RelativeLayout l3 = rootView.findViewById(id);
      if (l3 == null) {
        break missingId;
      }

      id = R.id.l4;
      TextView l4 = rootView.findViewById(id);
      if (l4 == null) {
        break missingId;
      }

      id = R.id.main_recyclerView;
      RecyclerView mainRecyclerView = rootView.findViewById(id);
      if (mainRecyclerView == null) {
        break missingId;
      }

      id = R.id.rellay1;
      LinearLayout rellay1 = rootView.findViewById(id);
      if (rellay1 == null) {
        break missingId;
      }

      id = R.id.sendMessageProfile;
      Button sendMessageProfile = rootView.findViewById(id);
      if (sendMessageProfile == null) {
        break missingId;
      }

      id = R.id.tv_name;
      TextView tvName = rootView.findViewById(id);
      if (tvName == null) {
        break missingId;
      }

      id = R.id.tv_Nickname;
      TextView tvNickname = rootView.findViewById(id);
      if (tvNickname == null) {
        break missingId;
      }

      id = R.id.tv_status;
      TextView tvStatus = rootView.findViewById(id);
      if (tvStatus == null) {
        break missingId;
      }

      return new ActivityProfileBinding((ScrollView) rootView, ImagePrewiew, LogOutBtm,
          btEditProfile, imgUser, l3, l4, mainRecyclerView, rellay1, sendMessageProfile, tvName,
          tvNickname, tvStatus);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}