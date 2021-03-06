// Generated by view binder compiler. Do not edit!
package com.twentytwo.textme.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.twentytwo.textme.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivitySignupBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final CheckBox checkBox;

  @NonNull
  public final RadioButton femaleBtn;

  @NonNull
  public final EditText inputtPass1;

  @NonNull
  public final EditText inputtPass2;

  @NonNull
  public final RadioButton maleBtn;

  @NonNull
  public final RadioGroup radioGroup;

  @NonNull
  public final Button registerButon;

  @NonNull
  public final EditText sgnEmail;

  @NonNull
  public final EditText sgnName;

  @NonNull
  public final EditText sgnPhone;

  @NonNull
  public final TextView signHead;

  @NonNull
  public final TextView signLlogTxt;

  private ActivitySignupBinding(@NonNull RelativeLayout rootView, @NonNull CheckBox checkBox,
      @NonNull RadioButton femaleBtn, @NonNull EditText inputtPass1, @NonNull EditText inputtPass2,
      @NonNull RadioButton maleBtn, @NonNull RadioGroup radioGroup, @NonNull Button registerButon,
      @NonNull EditText sgnEmail, @NonNull EditText sgnName, @NonNull EditText sgnPhone,
      @NonNull TextView signHead, @NonNull TextView signLlogTxt) {
    this.rootView = rootView;
    this.checkBox = checkBox;
    this.femaleBtn = femaleBtn;
    this.inputtPass1 = inputtPass1;
    this.inputtPass2 = inputtPass2;
    this.maleBtn = maleBtn;
    this.radioGroup = radioGroup;
    this.registerButon = registerButon;
    this.sgnEmail = sgnEmail;
    this.sgnName = sgnName;
    this.sgnPhone = sgnPhone;
    this.signHead = signHead;
    this.signLlogTxt = signLlogTxt;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivitySignupBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_signup, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivitySignupBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.checkBox;
      CheckBox checkBox = rootView.findViewById(id);
      if (checkBox == null) {
        break missingId;
      }

      id = R.id.female_btn;
      RadioButton femaleBtn = rootView.findViewById(id);
      if (femaleBtn == null) {
        break missingId;
      }

      id = R.id.inputt_pass1;
      EditText inputtPass1 = rootView.findViewById(id);
      if (inputtPass1 == null) {
        break missingId;
      }

      id = R.id.inputt_pass2;
      EditText inputtPass2 = rootView.findViewById(id);
      if (inputtPass2 == null) {
        break missingId;
      }

      id = R.id.male_btn;
      RadioButton maleBtn = rootView.findViewById(id);
      if (maleBtn == null) {
        break missingId;
      }

      id = R.id.radioGroup;
      RadioGroup radioGroup = rootView.findViewById(id);
      if (radioGroup == null) {
        break missingId;
      }

      id = R.id.registerButon;
      Button registerButon = rootView.findViewById(id);
      if (registerButon == null) {
        break missingId;
      }

      id = R.id.sgn_email;
      EditText sgnEmail = rootView.findViewById(id);
      if (sgnEmail == null) {
        break missingId;
      }

      id = R.id.sgn_name;
      EditText sgnName = rootView.findViewById(id);
      if (sgnName == null) {
        break missingId;
      }

      id = R.id.sgn_phone;
      EditText sgnPhone = rootView.findViewById(id);
      if (sgnPhone == null) {
        break missingId;
      }

      id = R.id.sign_head;
      TextView signHead = rootView.findViewById(id);
      if (signHead == null) {
        break missingId;
      }

      id = R.id.sign_llogTxt;
      TextView signLlogTxt = rootView.findViewById(id);
      if (signLlogTxt == null) {
        break missingId;
      }

      return new ActivitySignupBinding((RelativeLayout) rootView, checkBox, femaleBtn, inputtPass1,
          inputtPass2, maleBtn, radioGroup, registerButon, sgnEmail, sgnName, sgnPhone, signHead,
          signLlogTxt);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
