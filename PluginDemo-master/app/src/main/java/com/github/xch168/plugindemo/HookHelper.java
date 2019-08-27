package com.github.xch168.plugindemo;

import android.app.Instrumentation;
import android.content.Context;
import android.os.Build;

import com.github.xch168.plugindemo.util.ReflectUtil;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by XuCanHui on 2019/1/22.
 */
public class HookHelper {

    public static final String TARGET_INTENT = "target_intent";

    public static void hookInstrumentation(Context context) throws Exception {
        Class<?> contextImplClass = Class.forName("android.app.ContextImpl");
        Object activityThread = ReflectUtil.getField(contextImplClass, context, "mMainThread");
        Class<?> activityThreadClass = Class.forName("android.app.ActivityThread");
        Object mInstrumentation = ReflectUtil.getField(activityThreadClass, activityThread, "mInstrumentation");

        ReflectUtil.setField(activityThreadClass, activityThread, "mInstrumentation",
                new InstrumentationProxy((Instrumentation) mInstrumentation, context.getPackageManager()));
    }
}
