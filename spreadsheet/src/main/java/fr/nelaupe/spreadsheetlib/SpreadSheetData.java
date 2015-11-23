/*
 * Copyright 2015-present Lucas Nelaupe
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package fr.nelaupe.spreadsheetlib;

import java.lang.reflect.Field;
import java.util.ArrayList;

/**
 * Created with IntelliJ
 * Created by Lucas Nelaupe
 * Date 26/03/15
 */
@SuppressWarnings({"unused", "unchecked"})
public abstract class SpreadSheetData {

    protected ArrayList<AnnotationFields> defineField() {
        ArrayList<AnnotationFields> fields = new ArrayList<>();

        for (Field field : this.getClass().getDeclaredFields()) {
            if (field.isAnnotationPresent(SpreadSheetCell.class)) {
                fields.add(new AnnotationFields(field, field.getAnnotation(SpreadSheetCell.class)));
            }
        }

        return fields;

    }

}
