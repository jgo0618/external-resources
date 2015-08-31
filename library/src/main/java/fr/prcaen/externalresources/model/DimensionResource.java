package fr.prcaen.externalresources.model;

import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.TypedValue;

public final class DimensionResource {
  private static final String TYPE_DP = "dp";
  private static final String TYPE_DIP = "dip";
  private static final String TYPE_SP = "sp";
  private static final String TYPE_PT = "pt";
  private static final String TYPE_IN = "in";
  private static final String TYPE_MM = "mm";
  private static final String TYPE_PX = "px";

  private final int type;
  private final float value;

  public DimensionResource(int type, float value) {
    this.type = type;
    this.value = value;
  }

  public float toFloat(DisplayMetrics metrics) {
    return TypedValue.applyDimension(type, value, metrics);
  }

  public static DimensionResource fromString(@NonNull String resource) throws IllegalArgumentException {
    return new DimensionResource(getTypeFromString(resource), getValueFromString(resource));
  }

  private static float getValueFromString(@NonNull String str) {
    String[] split = str.trim().split("[a-z-A-Z]");

    if (split.length > 0) {
      try {
        return Float.valueOf(split[0]);
      } catch (NumberFormatException ignored) {
      }
    } else {
      try {
        return Float.valueOf(str.trim());
      } catch (NumberFormatException ignored) {
      }
    }

    throw new IllegalArgumentException(str + " is not a valid dimension format.");
  }

  private static int getTypeFromString(@NonNull String str) {
    String[] split = str.trim().split("[0-9]");

    if (split.length > 0) {
      final String typeStr = split[0];

      switch (typeStr) {
        case TYPE_DP:
        case TYPE_DIP:
          return TypedValue.COMPLEX_UNIT_DIP;
        case TYPE_SP:
          return TypedValue.COMPLEX_UNIT_SP;
        case TYPE_PT:
          return TypedValue.COMPLEX_UNIT_PT;
        case TYPE_IN:
          return TypedValue.COMPLEX_UNIT_IN;
        case TYPE_MM:
          return TypedValue.COMPLEX_UNIT_MM;
        case TYPE_PX:
          return TypedValue.COMPLEX_UNIT_PX;
        default:
          throw new IllegalArgumentException(str + " is not a valid dimension format.");
      }
    } else {
      return TypedValue.COMPLEX_UNIT_PX;
    }
  }
}
