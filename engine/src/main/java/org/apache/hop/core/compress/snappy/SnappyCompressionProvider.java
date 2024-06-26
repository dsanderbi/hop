/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.core.compress.snappy;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.apache.hop.core.compress.CompressionPlugin;
import org.apache.hop.core.compress.ICompressionProvider;

@CompressionPlugin(id = "SNAPPY", name = "Snappy", description = "Snappy compression")
public class SnappyCompressionProvider implements ICompressionProvider {

  @Override
  public SnappyCompressionInputStream createInputStream(InputStream in) throws IOException {
    return new SnappyCompressionInputStream(in, this);
  }

  @Override
  public boolean supportsInput() {
    return true;
  }

  @Override
  public SnappyCompressionOutputStream createOutputStream(OutputStream out) throws IOException {
    return new SnappyCompressionOutputStream(out, this);
  }

  @Override
  public boolean supportsOutput() {
    return true;
  }

  @Override
  public String getDescription() {
    return "Snappy compression";
  }

  @Override
  public String getName() {
    return "Snappy";
  }

  @Override
  public String getDefaultExtension() {
    return null;
  }
}
