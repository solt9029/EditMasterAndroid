package com.solt9029.editmasterandroid.di;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;

import dagger.android.AndroidInjection;
import dagger.android.support.AndroidSupportInjection;
import dagger.android.support.HasSupportFragmentInjector;

public class AppInjector {

    private AppInjector() {
    }

    public static void init(AppApplication application) {
        DaggerAppComponent.builder().application(application).build().inject(application);
        application.registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                handleActivity(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {

            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    private static void handleActivity(Activity activity) {
        if (activity instanceof Injectable) {
            AndroidInjection.inject(activity);
        }

//        if (activity instanceof HasSupportFragmentInjector) {
//            AndroidInjection.inject(activity);
//        }
//
//        if (activity instanceof FragmentActivity) {
//            FragmentManager.FragmentLifecycleCallbacks callback = new FragmentManager.FragmentLifecycleCallbacks() {
//                @Override
//                public void onFragmentCreated(@NonNull FragmentManager manager, @NonNull Fragment fragment, Bundle savedInstanceState) {
//                    if (fragment instanceof Injectable) {
//                        AndroidSupportInjection.inject(fragment);
//                    }
//                }
//            };
//
//            ((FragmentActivity) activity).getSupportFragmentManager().registerFragmentLifecycleCallbacks(callback, true);
//        }
    }
}