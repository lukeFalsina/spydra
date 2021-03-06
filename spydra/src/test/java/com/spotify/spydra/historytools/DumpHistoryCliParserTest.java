/*
 * Copyright 2017 Spotify AB.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package com.spotify.spydra.historytools;

import com.google.common.collect.Lists;

import com.spotify.spydra.CliTestHelpers;
import com.spotify.spydra.historytools.commands.DumpHistoryCommand;
import com.spotify.spydra.submitter.runner.CliConsts;
import com.spotify.spydra.submitter.runner.CliParser;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class DumpHistoryCliParserTest {

  private static final DumpHistoryCliParser PARSER = new DumpHistoryCliParser();
  private static final String DUMMY_APP_ID = "application_1345678910111_123456";
  private static final String DUMMY_CLIENT_ID = "pretty-client-2342";
  private static final String DUMMY_BUCKET = "bucket";

  @Test
  public void testParse() {
    DumpHistoryCommand dumpCmd = PARSER.parse(new String[]{
        CliTestHelpers.toStrOpt(CliConsts.JOB_ID_OPTION_NAME, DUMMY_APP_ID),
        CliTestHelpers.toStrOpt(CliConsts.CLIENT_ID_OPTION_NAME, DUMMY_CLIENT_ID),
        CliTestHelpers.toStrOpt(CliConsts.LOG_BUCKET_OPTION_NAME, DUMMY_BUCKET)
    });

    assertEquals(dumpCmd.applicationId().toString(), DUMMY_APP_ID);
    assertEquals(dumpCmd.clientId(), DUMMY_CLIENT_ID);
    assertEquals(dumpCmd.logBucket(), DUMMY_BUCKET);
  }

  @Test
  public void testMissingArgs() {
    // Ensure both options are mandatory
    List<String[]> failingLines = Lists.newArrayList(
        new String[]{
            CliTestHelpers.toStrOpt(CliConsts.CLIENT_ID_OPTION_NAME, DUMMY_CLIENT_ID)
        },
        new String[]{
            CliTestHelpers.toStrOpt(CliConsts.JOB_ID_OPTION_NAME, DUMMY_APP_ID)
        },
        new String[]{
            CliTestHelpers.toStrOpt(CliConsts.CLIENT_ID_OPTION_NAME, DUMMY_CLIENT_ID),
            CliTestHelpers.toStrOpt(CliConsts.JOB_ID_OPTION_NAME, DUMMY_APP_ID)
        }
    );

    CliTestHelpers.ensureAllThrow(PARSER, failingLines, CliParser.ParsingException.class);
  }
}
