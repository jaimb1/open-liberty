/*******************************************************************************
 * Copyright (c) 2017 IBM Corporation and others.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 *******************************************************************************
 * Copyright © 2013 Antonin Stefanutti (antonin.stefanutti@gmail.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package io.astefanutti.metrics.cdi;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Member;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import javax.annotation.Priority;
import javax.inject.Inject;
import javax.interceptor.AroundConstruct;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import org.eclipse.microprofile.metrics.MetricRegistry;
import org.eclipse.microprofile.metrics.annotation.Counted;
import org.eclipse.microprofile.metrics.annotation.Gauge;
import org.eclipse.microprofile.metrics.annotation.Metered;
import org.eclipse.microprofile.metrics.annotation.Timed;

@Interceptor
@MetricsBinding
@Priority(Interceptor.Priority.LIBRARY_BEFORE)
// See http://docs.oracle.com/javaee/7/tutorial/doc/interceptors.htm
/* package-private */ class MetricsInterceptor {

    private final MetricRegistry registry;

    private final MetricResolver resolver;

    @Inject
    private MetricsInterceptor(MetricRegistry registry, MetricResolver resolver) {
        this.registry = registry;
        this.resolver = resolver;
    }

    @AroundConstruct
    private Object metrics(InvocationContext context) throws Exception {
        Class<?> bean = context.getConstructor().getDeclaringClass();

        // Registers the bean constructor metrics
        registerMetrics(bean, context.getConstructor());

        // Registers the methods metrics over the bean type hierarchy
        Class<?> type = bean;
        do {
            // TODO: discover annotations declared on implemented interfaces
            for (Method method : type.getDeclaredMethods())
                if (!method.isSynthetic() && !Modifier.isPrivate(method.getModifiers()))
                    registerMetrics(bean, method);
            type = type.getSuperclass();
        } while (!Object.class.equals(type));

        Object target = context.proceed();

        // Registers the gauges over the bean type hierarchy after the target is constructed as it is required for the gauge invocations
        type = bean;
        do {
            // TODO: discover annotations declared on implemented interfaces
            for (Method method : type.getDeclaredMethods()) {
                MetricResolver.Of<Gauge> gauge = resolver.gauge(bean, method);
                if (gauge.isPresent())
                    registry.register(gauge.metricName(), new ForwardingGauge(method, context.getTarget()));
            }
            type = type.getSuperclass();
        } while (!Object.class.equals(type));

        return target;
    }

    private <E extends Member & AnnotatedElement> void registerMetrics(Class<?> bean, E element) {
        MetricResolver.Of<Counted> counted = resolver.counted(bean, element);
        if (counted.isPresent())
            registry.counter(counted.metadata());

        MetricResolver.Of<Metered> metered = resolver.metered(bean, element);
        if (metered.isPresent())
            registry.meter(metered.metadata());

        MetricResolver.Of<Timed> timed = resolver.timed(bean, element);
        if (timed.isPresent())
            registry.timer(timed.metadata());
    }

    private static final class ForwardingGauge implements org.eclipse.microprofile.metrics.Gauge<Object> {

        private final Method method;

        private final Object object;

        private ForwardingGauge(Method method, Object object) {
            this.method = method;
            this.object = object;
            method.setAccessible(true);
        }

        @Override
        public Object getValue() {
            return invokeMethod(method, object);
        }
    }

    private static Object invokeMethod(Method method, Object object) {
        try {
            return method.invoke(object);
        } catch (IllegalAccessException | InvocationTargetException cause) {
            throw new IllegalStateException("Error while calling method [" + method + "]", cause);
        }
    }
}
