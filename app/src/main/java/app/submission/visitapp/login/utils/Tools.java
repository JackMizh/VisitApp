package app.submission.visitapp.login.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import androidx.annotation.ColorRes;

public class Tools {

//    @SuppressLint("ObsoleteSdkInt")
//    public static void setSystemBarColor(Activity act) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = act.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(act.getResources().getColor(R.color.colorPrimaryDark));
//        }
//    }

    @SuppressLint("ObsoleteSdkInt")
    public static void setSystemBarColor(Activity act, @ColorRes int color) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = act.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(act.getResources().getColor(color));
        }
    }

//    @SuppressLint("ObsoleteSdkInt")
//    public static void setSystemBarColorInt(Activity act, @ColorInt int color) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = act.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(color);
//        }
//    }

//    @SuppressLint("ObsoleteSdkInt")
//    public static void setSystemBarColorDialog(Context act, Dialog dialog, @ColorRes int color) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = dialog.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
//            window.setStatusBarColor(act.getResources().getColor(color));
//        }
//    }

    public static void setSystemBarLight(Activity act) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View view = act.findViewById(android.R.id.content);
            int flags = view.getSystemUiVisibility();
            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
            view.setSystemUiVisibility(flags);
        }
    }

//    public static void setSystemBarLightDialog(Dialog dialog) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            View view = dialog.findViewById(android.R.id.content);
//            int flags = view.getSystemUiVisibility();
//            flags |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
//            view.setSystemUiVisibility(flags);
//        }
//    }
//
//    public static void clearSystemBarLight(Activity act) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
//            Window window = act.getWindow();
//            window.setStatusBarColor(ContextCompat.getColor(act, R.color.colorPrimaryDark));
//        }
//    }

//    /**
//     * Making notification bar transparent
//     */
//    @SuppressLint("ObsoleteSdkInt")
//    public static void setSystemBarTransparent(Activity act) {
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            Window window = act.getWindow();
//            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
//            window.setStatusBarColor(Color.TRANSPARENT);
//        }
//    }
}
