// Generated by view binder compiler. Do not edit!
package com.ridzi.pokemon1.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.ridzi.pokemon1.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ListItemBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView name;

  @NonNull
  public final ImageView pokemonImage;

  @NonNull
  public final TextView url;

  private ListItemBinding(@NonNull LinearLayout rootView, @NonNull TextView name,
      @NonNull ImageView pokemonImage, @NonNull TextView url) {
    this.rootView = rootView;
    this.name = name;
    this.pokemonImage = pokemonImage;
    this.url = url;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ListItemBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ListItemBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.list_item, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ListItemBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.name;
      TextView name = ViewBindings.findChildViewById(rootView, id);
      if (name == null) {
        break missingId;
      }

      id = R.id.pokemonImage;
      ImageView pokemonImage = ViewBindings.findChildViewById(rootView, id);
      if (pokemonImage == null) {
        break missingId;
      }

      id = R.id.url;
      TextView url = ViewBindings.findChildViewById(rootView, id);
      if (url == null) {
        break missingId;
      }

      return new ListItemBinding((LinearLayout) rootView, name, pokemonImage, url);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
