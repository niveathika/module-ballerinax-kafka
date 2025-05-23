// Copyright (c) 2020 WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
//
// WSO2 Inc. licenses this file to you under the Apache License,
// Version 2.0 (the "License"); you may not use this file except
// in compliance with the License.
// You may obtain a copy of the License at
//
// http://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing,
// software distributed under the License is distributed on an
// "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
// KIND, either express or implied.  See the License for the
// specific language governing permissions and limitations
// under the License.

import ballerina/jballerina.java;

isolated function sendByteArrayValues(Producer producer, byte[] value, string topic, [string, byte[]][] headers, anydata? key, int? partition,
    int? timestamp, string keySerializerType) returns Error? {
    if key is () {
        return sendByteArrayValuesNilKeys(producer, value, topic, partition, timestamp, headers);
    } else if key is byte[] {
        return sendByteArrayValuesByteArrayKeys(producer, value, topic, key, partition, timestamp, headers);
    }
    panic getKeyTypeMismatchError(BYTE_ARRAY);
}

//Send byte[] values with different types of keys
isolated function sendByteArrayValuesNilKeys(Producer producer, byte[] value, string topic, int? partition = (),
    int? timestamp = (), [string, byte[]][] headers = []) returns Error? =
@java:Method {
    'class: "io.ballerina.stdlib.kafka.nativeimpl.producer.SendByteArrayValues"
} external;

isolated function sendByteArrayValuesByteArrayKeys(Producer producer, byte[] value, string topic, byte[] key,
    int? partition = (), int? timestamp = (), [string, byte[]][] headers = []) returns Error? =
@java:Method {
    'class: "io.ballerina.stdlib.kafka.nativeimpl.producer.SendByteArrayValues"
} external;
