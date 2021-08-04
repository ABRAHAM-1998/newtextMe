// Generated by view binder compiler. Do not edit!
package com.twentytwo.textme.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.twentytwo.textme.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityEditProfileBinding implements ViewBinding {
  @NonNull
  private final ScrollView rootView;

  @NonNull
  public final ImageView ImagePrewiew;

  @NonNull
  public final Button UploadProfile;

  @NonNull
  public final RelativeLayout imgUser;

  @NonNull
  public final RelativeLayout r1;

  @NonNull
  public final RelativeLayout rellay1;

  @NonNull
  public final Button selectImage;

  @NonNull
  public final EditText userNickName;

  @NonNull
  public final EditText usrAbout;

  @NonNull
  public final EditText usrName;

  private ActivityEditProfileBinding(@NonNull ScrollView rootView, @NonNull ImageView ImagePrewiew,
      @NonNull Button UploadProfile, @NonNull RelativeLayout imgUser, @NonNull RelativeLayout r1,
      @NonNull RelativeLayout rellay1, @NonNull Button selectImage, @NonNull EditText userNickName,
      @NonNull EditText usrAbout, @NonNull EditText usrName) {
    this.rootView = rootView;
    this.ImagePrewiew = ImagePrewiew;
    this.UploadProfile = UploadProfile;
    this.imgUser = imgUser;
    this.r1 = r1;
    this.rellay1 = rellay1;
    this.selectImage = selectImage;
    this.userNickName = userNickName;
    this.usrAbout = usrAbout;
    this.usrName = usrName;
  }

  @Override
  @NonNull
  public ScrollView getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityEditProfileBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityEditProfileBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_edit_profile, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityEditProfileBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.ImagePrewiew;
      ImageView ImagePrewiew = rootView.findViewById(id);
      if (ImagePrewiew == null) {
        break missingId;
      }

      id = R.id.UploadProfile;
      Button UploadProfile = rootView.findViewById(id);
      if (UploadProfile == null) {
        break missingId;
      }

      id = R.id.imgUser;
      RelativeLayout imgUser = rootView.findViewById(id);
      if (imgUser == null) {
        break missingId;
      }

      id = R.id.r1;
      RelativeLayout r1 = rootView.findViewById(id);
      if (r1 == null) {
        break missingId;
      }

      id = R.id.rellay1;
      RelativeLayout rellay1 = rootView.findViewById(id);
      if (rellay1 == null) {
        break missingId;
      }

      id = R.id.selectImage;
      Button selectImage = rootView.findViewById(id);
      if (selectImage == null) {
        break missingId;
      }

      id = R.id.userNickName;
      EditText userNickName = rootView.findViewById(id);
      if (userNickName == null) {
        break missingId;
      }

      id = R.id.usrAbout;
      EditText usrAbout = rootView.findViewById(id);
      if (usrAbout == null) {
        break missingId;
      }

      id = R.id.usrName;
      EditText usrName = rootView.findViewById(id);
      if (usrName == null) {
        break missingId;
      }

      return new ActivityEditProfileBinding((ScrollView) rootView, ImagePrewiew, UploadProfile,
          imgUser, r1, rellay1, selectImage, userNickName, usrAbout, usrName);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
