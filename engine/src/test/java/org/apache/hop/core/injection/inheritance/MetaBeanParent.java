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
package org.apache.hop.core.injection.inheritance;

import java.util.List;
import org.apache.hop.core.injection.Injection;
import org.apache.hop.core.injection.InjectionDeep;
import org.apache.hop.pipeline.transform.BaseTransformMeta;
import org.apache.hop.pipeline.transform.ITransform;
import org.apache.hop.pipeline.transform.ITransformData;

public class MetaBeanParent<T extends MetaBeanParentItem, A>
    extends BaseTransformMeta<ITransform, ITransformData> {

  @InjectionDeep public List<T> items;

  @Injection(name = "A")
  A obj;

  @InjectionDeep(prefix = "ITEM")
  public T test1() {
    return null;
  }

  @InjectionDeep(prefix = "SUB")
  public List<T> test2() {
    return null;
  }

  @Override
  public void setDefault() {
    // Do nothing
  }
}
