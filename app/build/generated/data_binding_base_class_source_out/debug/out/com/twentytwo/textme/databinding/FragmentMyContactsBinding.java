// Generated by view binder compiler. Do not edit!
package com.twentytwo.textme.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.twentytwo.textme.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class FragmentMyContactsBinding implements ViewBinding {
  @NonNull
  private final RelativeLayout rootView;

  @NonNull
  public final FloatingActionButton floatAddContact;

  @NonNull
  public final ListView listViw;

  private FragmentMyContactsBinding(@NonNull RelativeLayout rootView,
      @NonNull FloatingActionButton floatAddContact, @NonNull ListView listViw) {
    this.rootView = rootView;
    this.floatAddContact = floatAddContact;
    this.listViw = listViw;
  }

  @Override
  @NonNull
  public RelativeLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static FragmentMyContactsBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static FragmentMyContactsBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.fragment_my_contacts, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static FragmentMyContactsBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.float_add_contact;
      FloatingActionButton floatAddContact = rootView.findViewById(id);
      if (floatAddContact == null) {
        break missingId;
      }

      id = R.id.list_viw;
      ListView listViw = rootView.findViewById(id);
      if (listViw == null) {
        break missingId;
      }

      return new FragmentMyContactsBinding((RelativeLayout) rootView, floatAddContact, listViw);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}