// Generated by view binder compiler. Do not edit!
package com.twentytwo.textme.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.viewbinding.ViewBinding;
import com.twentytwo.textme.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ItemAddContactListBinding implements ViewBinding {
  @NonNull
  private final CardView rootView;

  @NonNull
  public final Button addFrnfBtn;

  @NonNull
  public final TextView nickNAme;

  @NonNull
  public final TextView usrLastSeen;

  @NonNull
  public final TextView usrName;

  @NonNull
  public final TextView ustLkStatus;

  private ItemAddContactListBinding(@NonNull CardView rootView, @NonNull Button addFrnfBtn,
      @NonNull TextView nickNAme, @NonNull TextView usrLastSeen, @NonNull TextView usrName,
      @NonNull TextView ustLkStatus) {
    this.rootView = rootView;
    this.addFrnfBtn = addFrnfBtn;
    this.nickNAme = nickNAme;
    this.usrLastSeen = usrLastSeen;
    this.usrName = usrName;
    this.ustLkStatus = ustLkStatus;
  }

  @Override
  @NonNull
  public CardView getRoot() {
    return rootView;
  }

  @NonNull
  public static ItemAddContactListBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ItemAddContactListBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.item_add_contact_list, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ItemAddContactListBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.addFrnfBtn;
      Button addFrnfBtn = rootView.findViewById(id);
      if (addFrnfBtn == null) {
        break missingId;
      }

      id = R.id.nickNAme;
      TextView nickNAme = rootView.findViewById(id);
      if (nickNAme == null) {
        break missingId;
      }

      id = R.id.usrLastSeen;
      TextView usrLastSeen = rootView.findViewById(id);
      if (usrLastSeen == null) {
        break missingId;
      }

      id = R.id.usrName;
      TextView usrName = rootView.findViewById(id);
      if (usrName == null) {
        break missingId;
      }

      id = R.id.ustLkStatus;
      TextView ustLkStatus = rootView.findViewById(id);
      if (ustLkStatus == null) {
        break missingId;
      }

      return new ItemAddContactListBinding((CardView) rootView, addFrnfBtn, nickNAme, usrLastSeen,
          usrName, ustLkStatus);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}