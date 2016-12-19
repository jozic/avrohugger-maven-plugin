/*
 * Copyright 2016 the original author or authors
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

package at.makubi.maven.plugin.avrohugger;

import org.apache.commons.io.FileUtils;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;
import org.apache.maven.plugin.testing.resources.TestResources;
import org.apache.maven.project.MavenProject;
import org.junit.After;
import org.junit.Before;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class AvrohuggerGeneratorTest extends AbstractMojoTestCase {

    AvrohuggerGenerator avrohuggerGenerator;
    Path outputDirectory;

    @Before
    public void setUp() throws Exception {
        avrohuggerGenerator = new AvrohuggerGenerator();
        outputDirectory = Files.createTempDirectory(Paths.get(getBasedir()).resolve("target"), AvrohuggerGeneratorTest.class.getCanonicalName());
    }

    public void testAvrohuggerGenerator() throws IOException {
        Path inputDirectory = Paths.get(getBasedir()).resolve("../src/test/resources/unit/avrohugger-maven-plugin");
        Path schemaDirectory = inputDirectory.resolve("schema");

        File expectedRecord = inputDirectory.resolve("expected/Record.scala").toFile();
        File actualRecords = outputDirectory.resolve("at/makubi/maven/plugin/model/Record.scala").toFile();

        avrohuggerGenerator.generateScalaFiles(schemaDirectory.toFile(), outputDirectory.toString(), new SystemStreamLog());

        assertTrue("Generated Scala file does not match expected one", FileUtils.contentEquals(expectedRecord, actualRecords));
    }

    @After
    public void tearDown() throws IOException {
        FileUtils.deleteDirectory(outputDirectory.toFile());
    }

}