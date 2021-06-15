// Generated by view binder compiler. Do not edit!
package com.kr.weathertest.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import com.kr.weathertest.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ResultActivityBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final TextView cloudinfo;

  @NonNull
  public final TextView humdata;

  @NonNull
  public final TextView likeweathertv;

  @NonNull
  public final TextView maxtemp;

  @NonNull
  public final TextView mintemp;

  @NonNull
  public final TextView onehourrain;

  @NonNull
  public final TextView resultwind;

  @NonNull
  public final TextView thrhourrain;

  @NonNull
  public final TextView weathermain;

  private ResultActivityBinding(@NonNull LinearLayout rootView, @NonNull TextView cloudinfo,
      @NonNull TextView humdata, @NonNull TextView likeweathertv, @NonNull TextView maxtemp,
      @NonNull TextView mintemp, @NonNull TextView onehourrain, @NonNull TextView resultwind,
      @NonNull TextView thrhourrain, @NonNull TextView weathermain) {
    this.rootView = rootView;
    this.cloudinfo = cloudinfo;
    this.humdata = humdata;
    this.likeweathertv = likeweathertv;
    this.maxtemp = maxtemp;
    this.mintemp = mintemp;
    this.onehourrain = onehourrain;
    this.resultwind = resultwind;
    this.thrhourrain = thrhourrain;
    this.weathermain = weathermain;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ResultActivityBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ResultActivityBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.result_activity, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ResultActivityBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.cloudinfo;
      TextView cloudinfo = rootView.findViewById(id);
      if (cloudinfo == null) {
        break missingId;
      }

      id = R.id.humdata;
      TextView humdata = rootView.findViewById(id);
      if (humdata == null) {
        break missingId;
      }

      id = R.id.likeweathertv;
      TextView likeweathertv = rootView.findViewById(id);
      if (likeweathertv == null) {
        break missingId;
      }

      id = R.id.maxtemp;
      TextView maxtemp = rootView.findViewById(id);
      if (maxtemp == null) {
        break missingId;
      }

      id = R.id.mintemp;
      TextView mintemp = rootView.findViewById(id);
      if (mintemp == null) {
        break missingId;
      }

      id = R.id.onehourrain;
      TextView onehourrain = rootView.findViewById(id);
      if (onehourrain == null) {
        break missingId;
      }

      id = R.id.resultwind;
      TextView resultwind = rootView.findViewById(id);
      if (resultwind == null) {
        break missingId;
      }

      id = R.id.thrhourrain;
      TextView thrhourrain = rootView.findViewById(id);
      if (thrhourrain == null) {
        break missingId;
      }

      id = R.id.weathermain;
      TextView weathermain = rootView.findViewById(id);
      if (weathermain == null) {
        break missingId;
      }

      return new ResultActivityBinding((LinearLayout) rootView, cloudinfo, humdata, likeweathertv,
          maxtemp, mintemp, onehourrain, resultwind, thrhourrain, weathermain);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
