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

package org.apache.hop.pipeline.transform;

import org.apache.hop.pipeline.engine.EngineComponent.ComponentExecutionStatus;

/**
 * This class is the base class for the ITransformData and contains the methods to set and retrieve
 * the status of the transform data.
 */
public abstract class BaseTransformData implements ITransformData {

  private static final Class<?> PKG = BaseTransform.class;

  /** The status. */
  private ComponentExecutionStatus status;

  /** Set to true if the transform is running in a Beam pipeline */
  private boolean beamContext;

  /** The Beam bundle number */
  private int beamBundleNr;

  /** Instantiates a new base transform data. */
  public BaseTransformData() {
    status = ComponentExecutionStatus.STATUS_EMPTY;
  }

  /**
   * Set the status of the transform data.
   *
   * @param status the new status.
   */
  @Override
  public void setStatus(ComponentExecutionStatus status) {
    this.status = status;
  }

  /**
   * Get the status of this transform data.
   *
   * @return the status of the transform data
   */
  @Override
  public ComponentExecutionStatus getStatus() {
    return status;
  }

  /**
   * Checks if is empty.
   *
   * @return true, if is empty
   */
  @Override
  public boolean isEmpty() {
    return status == ComponentExecutionStatus.STATUS_EMPTY;
  }

  /**
   * Checks if is initialising.
   *
   * @return true, if is initialising
   */
  @Override
  public boolean isInitialising() {
    return status == ComponentExecutionStatus.STATUS_INIT;
  }

  /**
   * Checks if is running.
   *
   * @return true, if is running
   */
  @Override
  public boolean isRunning() {
    return status == ComponentExecutionStatus.STATUS_RUNNING;
  }

  /**
   * Checks if is idle.
   *
   * @return true, if is idle
   */
  @Override
  public boolean isIdle() {
    return status == ComponentExecutionStatus.STATUS_IDLE;
  }

  /**
   * Checks if is finished.
   *
   * @return true, if is finished
   */
  @Override
  public boolean isFinished() {
    return status == ComponentExecutionStatus.STATUS_FINISHED;
  }

  /**
   * Checks if is stopped.
   *
   * @return true, if is stopped
   */
  public boolean isStopped() {
    return status == ComponentExecutionStatus.STATUS_STOPPED;
  }

  /**
   * Checks if is disposed.
   *
   * @return true, if is disposed
   */
  @Override
  public boolean isDisposed() {
    return status == ComponentExecutionStatus.STATUS_DISPOSED;
  }

  /**
   * Gets beamContext
   *
   * @return value of beamContext
   */
  public boolean isBeamContext() {
    return beamContext;
  }

  /**
   * Sets beamContext
   *
   * @param beamContext value of beamContext
   */
  public void setBeamContext(boolean beamContext) {
    this.beamContext = beamContext;
  }

  /**
   * Gets beamBundleNr
   *
   * @return value of beamBundleNr
   */
  public int getBeamBundleNr() {
    return beamBundleNr;
  }

  /**
   * Sets beamBundleNr
   *
   * @param beamBundleNr value of beamBundleNr
   */
  public void setBeamBundleNr(int beamBundleNr) {
    this.beamBundleNr = beamBundleNr;
  }
}
