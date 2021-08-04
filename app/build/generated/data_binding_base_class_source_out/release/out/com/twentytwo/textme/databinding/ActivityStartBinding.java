// Generated by view binder compiler. Do not edit!
package com.twentytwo.textme.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.button.MaterialButton;
import com.twentytwo.textme.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityStartBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final ImageView image;

  @NonNull
  public final MaterialButton joinMeeting;

  @NonNull
  public final EditText meetingId;

  @NonNull
  public final MaterialButton startMeeting;

  private ActivityStartBinding(@NonNull RelativeLayout rootView, @NonNull ImageView image,
      @NonNull MaterialButton joinMeeting, @NonNull EditText meetingId,
      @NonNull MaterialButton startMeeting) {
    this.rootView = rootView;
    this.image = image;
    this.joinMeeting = joinMeeting;
    this.meetingId = meetingId;
    this.startMeeting = startMeeting;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityStartBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityStartBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_start, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityStartBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.image;
      ImageView image = rootView.findViewById(id);
      if (image == null) {
        break missingId;
      }

      id = R.id.join_meeting;
      MaterialButton joinMeeting = rootView.findViewById(id);
      if (joinMeeting == null) {
        break missingId;
      }

      id = R.id.meeting_id;
      EditText meetingId = rootView.findViewById(id);
      if (meetingId == null) {
        break missingId;
      }

      id = R.id.start_meeting;
      MaterialButton startMeeting = rootView.findViewById(id);
      if (startMeeting == null) {
        break missingId;
      }

      return new ActivityStartBinding((RelativeLayout) rootView, image, joinMeeting, meetingId,
          startMeeting);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
