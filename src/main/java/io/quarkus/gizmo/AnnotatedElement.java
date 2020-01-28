/*
 * Copyright 2018 Red Hat, Inc.
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
 */

package io.quarkus.gizmo;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.jboss.jandex.AnnotationInstance;
import org.jboss.jandex.AnnotationValue;

public interface AnnotatedElement {

    default AnnotationCreator addAnnotation(String annotationType) {
        return addAnnotation(annotationType, RetentionPolicy.RUNTIME);
    }

    AnnotationCreator addAnnotation(String annotationType, RetentionPolicy retentionPolicy);

    default AnnotationCreator addAnnotation(Class<?> annotationType) {
        Retention retention = annotationType.getAnnotation(Retention.class);
        return addAnnotation(annotationType.getName(), retention == null ? RetentionPolicy.SOURCE : retention.value());
    }

    default void addAnnotation(AnnotationInstance annotation) {
        AnnotationCreator ac = addAnnotation(annotation.name().toString());
        for (AnnotationValue member : annotation.values()) {
            if (member.kind() == AnnotationValue.Kind.NESTED) {
                throw new RuntimeException("Not Yet Implemented: Cannot generate annotation " + annotation);
            } else if (member.kind() == AnnotationValue.Kind.BOOLEAN) {
                ac.addValue(member.name(), member.asBoolean());
            } else if (member.kind() == AnnotationValue.Kind.BYTE) {
                ac.addValue(member.name(), member.asByte());
            } else if (member.kind() == AnnotationValue.Kind.SHORT) {
                ac.addValue(member.name(), member.asShort());
            } else if (member.kind() == AnnotationValue.Kind.INTEGER) {
                ac.addValue(member.name(), member.asInt());
            } else if (member.kind() == AnnotationValue.Kind.LONG) {
                ac.addValue(member.name(), member.asLong());
            } else if (member.kind() == AnnotationValue.Kind.FLOAT) {
                ac.addValue(member.name(), member.asFloat());
            } else if (member.kind() == AnnotationValue.Kind.DOUBLE) {
                ac.addValue(member.name(), member.asDouble());
            } else if (member.kind() == AnnotationValue.Kind.STRING) {
                ac.addValue(member.name(), member.asString());
            } else if (member.kind() == AnnotationValue.Kind.ARRAY) {
                ac.addValue(member.name(), member.value());
            }
        }
    }

}
