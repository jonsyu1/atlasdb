/*
 * (c) Copyright 2018 Palantir Technologies Inc. All rights reserved.
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
package com.palantir.atlasdb.keyvalue.api;

import java.util.Iterator;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonTypeInfo;

@SuppressWarnings("DangerousJsonTypeInfoUsage")
@JsonTypeInfo(use = JsonTypeInfo.Id.CLASS, property = "@class")
public interface RowColumnRangeIterator extends Iterator<Map.Entry<Cell, Value>> {
    //
}
