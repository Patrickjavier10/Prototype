// Generated by view binder compiler. Do not edit!
package com.apps.kunalfarmah.omrscanner.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.apps.kunalfarmah.omrscanner.R;
import java.lang.NullPointerException;
import java.lang.Override;

public final class ListCustomtextBinding implements ViewBinding {
  @NonNull
  private final TextView rootView;

  @NonNull
  public final TextView listCustomText;

  private ListCustomtextBinding(@NonNull TextView rootView, @NonNull TextView listCustomText) {
    this.rootView = rootView;
    this.listCustomText = listCustomText;
  }

  @Override
  @NonNull
  public TextView getRoot() {
    return rootView;
  }

  @NonNull
  public static ListCustomtextBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ListCustomtextBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.list_customtext, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ListCustomtextBinding bind(@NonNull View rootView) {
    if (rootView == null) {
      throw new NullPointerException("rootView");
    }

    TextView listCustomText = (TextView) rootView;

    return new ListCustomtextBinding((TextView) rootView, listCustomText);
  }
}
