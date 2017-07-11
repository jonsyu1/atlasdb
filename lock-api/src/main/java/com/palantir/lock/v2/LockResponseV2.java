/*
 * Copyright 2017 Palantir Technologies, Inc. All rights reserved.
 *
 * Licensed under the BSD-3 License (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://opensource.org/licenses/BSD-3-Clause
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.palantir.lock.v2;

import java.util.Optional;

import org.immutables.value.Value;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Value.Immutable
@JsonSerialize(as=ImmutableLockResponseV2.class)
@JsonDeserialize(as=ImmutableLockResponseV2.class)
public interface LockResponseV2 {

    @Value.Parameter
    Optional<LockTokenV2> getTokenOrEmpty();

    default boolean wasSuccessful() {
        return getTokenOrEmpty().isPresent();
    }

    default LockTokenV2 getToken() {
        if (!wasSuccessful()) {
            throw new IllegalStateException("This lock response was not succesful");
        }
        return getTokenOrEmpty().get();
    }

    static LockResponseV2 successful(LockTokenV2 token) {
        return ImmutableLockResponseV2.of(Optional.of(token));
    }

    static LockResponseV2 timedOut() {
        return ImmutableLockResponseV2.of(Optional.empty());
    }

}
