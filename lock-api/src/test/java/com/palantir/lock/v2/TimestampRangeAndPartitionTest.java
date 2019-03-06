/*
 * (c) Copyright 2019 Palantir Technologies Inc. All rights reserved.
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

package com.palantir.lock.v2;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.palantir.lock.client.SharedConstants;
import com.palantir.timestamp.TimestampRange;

public class TimestampRangeAndPartitionTest {
    private static final int PARTITION = 2;

    @Test
    public void getTimestampsShouldYieldCorrectResult_singleTimestamp() {
        long timestamp = PARTITION + SharedConstants.TRANSACTION_NUM_PARTITIONS;
        TimestampRangeAndPartition timestampRange = TimestampRangeAndPartition.of(
                TimestampRange.createInclusiveRange(timestamp, timestamp),
                PARTITION
        );

        assertThat(timestampRange.getStartTimestamps()).containsOnly(timestamp);
    }

    @Test
    public void getTimestampsShouldYieldCorrectResult_multipleTimestamps() {
        long lowerTimestamp = PARTITION + SharedConstants.TRANSACTION_NUM_PARTITIONS;
        long middleTimestamp = PARTITION + 2 * SharedConstants.TRANSACTION_NUM_PARTITIONS;
        long upperTimestamp = PARTITION + 3 * SharedConstants.TRANSACTION_NUM_PARTITIONS;
        TimestampRangeAndPartition timestampRange = TimestampRangeAndPartition.of(
                TimestampRange.createInclusiveRange(lowerTimestamp, upperTimestamp),
                PARTITION
        );

        assertThat(timestampRange.getStartTimestamps()).containsOnly(lowerTimestamp, middleTimestamp, upperTimestamp);
    }
}