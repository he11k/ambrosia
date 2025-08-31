/*
 * Copyright (c) Forge Development LLC
 * SPDX-License-Identifier: LGPL-2.1-only
 */
package net.minecraftforge.eventbus;

import net.minecraftforge.eventbus.api.IEventListener;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public interface IEventListenerFactory {

    IEventListener create(Method callback, Object target) throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException, ClassNotFoundException;

    default String getUniqueName(Method callback) {
        return String.format("%s.__%s_%s_%s",
                callback.getDeclaringClass().getPackageName(),
                callback.getDeclaringClass().getSimpleName(),
                callback.getName(),
                callback.getParameterTypes()[0].getSimpleName()
        );
    }

}
