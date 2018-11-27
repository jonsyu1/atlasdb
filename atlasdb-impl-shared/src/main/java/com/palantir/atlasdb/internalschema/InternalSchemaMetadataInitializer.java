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

package com.palantir.atlasdb.internalschema;

import com.palantir.atlasdb.coordination.CoordinationService;

/**
 * Writes an initial version of the {@link InternalSchemaMetadata} to the database.
 */
public class InternalSchemaMetadataInitializer {
    private static final long ZERO_TIMESTAMP = 0;

    private final CoordinationService<InternalSchemaMetadata> coordinationService;

    public InternalSchemaMetadataInitializer(
            CoordinationService<InternalSchemaMetadata> coordinationService) {
        this.coordinationService = coordinationService;
    }

    /**
     * Once this method returns, it can be guaranteed that the {@link CoordinationService} associated with this
     * {@link InternalSchemaMetadataInitializer} will contain a value. This may not necessarily be the initial
     * value.
     */
    public void ensureInternalSchemaMetadataInitialized() {
        while (!coordinationService.getValueForTimestamp(ZERO_TIMESTAMP).isPresent()) {
            coordinationService.tryTransformCurrentValue(valueAndBound -> valueAndBound.value()
                    .orElseGet(this::getDefaultInternalSchemaMetadata));
        }
    }

    private InternalSchemaMetadata getDefaultInternalSchemaMetadata() {
        return InternalSchemaMetadata.builder()
                .timestampFromWhichWeShouldUseTransactions2(Long.MAX_VALUE)
                .build();
    }
}
