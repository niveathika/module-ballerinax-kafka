/*
 * Copyright (c) 2022, WSO2 Inc. (http://www.wso2.org) All Rights Reserved.
 *
 * WSO2 Inc. licenses this file to you under the Apache License,
 * Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.ballerina.stdlib.kafka.plugin;

import io.ballerina.projects.plugins.codeaction.CodeActionArgument;
import io.ballerina.projects.plugins.codeaction.CodeActionInfo;
import io.ballerina.tools.text.LinePosition;
import io.ballerina.tools.text.LineRange;
import org.testng.annotations.Test;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

import static io.ballerina.stdlib.kafka.plugin.CompilerPluginTestUtils.BALLERINA_SOURCES;
import static io.ballerina.stdlib.kafka.plugin.CompilerPluginTestUtils.EXPECTED_SOURCES;
import static io.ballerina.stdlib.kafka.plugin.CompilerPluginTestUtils.RESOURCE_DIRECTORY;
import static io.ballerina.stdlib.kafka.plugin.PluginConstants.CODE_TEMPLATE_NAME_WITHOUT_CALLER;
import static io.ballerina.stdlib.kafka.plugin.PluginConstants.CODE_TEMPLATE_NAME_WITH_CALLER;
import static io.ballerina.stdlib.kafka.plugin.PluginConstants.NODE_LOCATION;

/**
 * A class for testing code actions.
 */
public class CodeSnippetGenerationCodeActionTest extends AbstractCodeActionTest {

    @Test
    public void testEmptyServiceCodeAction()
            throws IOException {
        Path filePath = RESOURCE_DIRECTORY.resolve(BALLERINA_SOURCES)
                .resolve("snippet_gen_service_1")
                .resolve("service.bal");
        Path resultPath = RESOURCE_DIRECTORY.resolve(EXPECTED_SOURCES)
                .resolve("service_1")
                .resolve("result.bal");
        performTest(filePath, LinePosition.from(2, 0),
                getExpectedCodeAction("service.bal", 2, 64, "Insert service template with caller",
                        CODE_TEMPLATE_NAME_WITH_CALLER), resultPath);
    }

    @Test
    public void testServiceWithVariablesCodeAction()
            throws IOException {
        Path filePath = RESOURCE_DIRECTORY.resolve(BALLERINA_SOURCES)
                .resolve("snippet_gen_service_2")
                .resolve("service.bal");
        Path resultPath = RESOURCE_DIRECTORY.resolve(EXPECTED_SOURCES)
                .resolve("service_2")
                .resolve("result.bal");
        performTest(filePath, LinePosition.from(2, 0),
                getExpectedCodeAction("service.bal", 5, 1, "Insert service template with caller",
                        CODE_TEMPLATE_NAME_WITH_CALLER), resultPath);
    }

    @Test
    public void testEmptyServiceCodeActionWithoutCaller()
            throws IOException {
        Path filePath = RESOURCE_DIRECTORY.resolve(BALLERINA_SOURCES)
                .resolve("snippet_gen_service_1")
                .resolve("service.bal");
        Path resultPath = RESOURCE_DIRECTORY.resolve(EXPECTED_SOURCES)
                .resolve("service_3")
                .resolve("result.bal");
        performTest(filePath, LinePosition.from(2, 0),
                getExpectedCodeAction("service.bal", 2, 64, "Insert service template without caller",
                        CODE_TEMPLATE_NAME_WITHOUT_CALLER), resultPath);
    }

    @Test
    public void testServiceWithVariablesCodeActionWithoutCaller()
            throws IOException {
        Path filePath = RESOURCE_DIRECTORY.resolve(BALLERINA_SOURCES)
                .resolve("snippet_gen_service_2")
                .resolve("service.bal");
        Path resultPath = RESOURCE_DIRECTORY.resolve(EXPECTED_SOURCES)
                .resolve("service_4")
                .resolve("result.bal");
        performTest(filePath, LinePosition.from(2, 0),
                getExpectedCodeAction("service.bal", 5, 1, "Insert service template without caller",
                        CODE_TEMPLATE_NAME_WITHOUT_CALLER), resultPath);
    }

    private CodeActionInfo getExpectedCodeAction(String filePath, int line, int offset,
                                                 String actionName, String templateName) {
        LineRange lineRange = LineRange.from(filePath, LinePosition.from(2, 0),
                LinePosition.from(line, offset));
        CodeActionArgument locationArg = CodeActionArgument.from(NODE_LOCATION, lineRange);
        CodeActionInfo codeAction = CodeActionInfo.from(actionName, List.of(locationArg));
        codeAction.setProviderName("KAFKA_111/ballerinax/kafka/" + templateName);
        return codeAction;
    }
}
