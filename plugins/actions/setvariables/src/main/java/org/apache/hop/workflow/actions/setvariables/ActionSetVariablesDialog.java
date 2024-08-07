/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.hop.workflow.actions.setvariables;

import java.util.ArrayList;
import java.util.List;
import org.apache.hop.core.Const;
import org.apache.hop.core.util.Utils;
import org.apache.hop.core.variables.IVariables;
import org.apache.hop.i18n.BaseMessages;
import org.apache.hop.ui.core.PropsUi;
import org.apache.hop.ui.core.dialog.BaseDialog;
import org.apache.hop.ui.core.dialog.MessageBox;
import org.apache.hop.ui.core.widget.ColumnInfo;
import org.apache.hop.ui.core.widget.TableView;
import org.apache.hop.ui.core.widget.TextVar;
import org.apache.hop.ui.pipeline.transform.BaseTransformDialog;
import org.apache.hop.ui.workflow.action.ActionDialog;
import org.apache.hop.ui.workflow.dialog.WorkflowDialog;
import org.apache.hop.workflow.WorkflowMeta;
import org.apache.hop.workflow.action.IAction;
import org.apache.hop.workflow.actions.setvariables.ActionSetVariables.VariableDefinition;
import org.apache.hop.workflow.actions.setvariables.ActionSetVariables.VariableType;
import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CCombo;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

/** This dialog allows you to edit the Set variables action settings. */
public class ActionSetVariablesDialog extends ActionDialog {
  private static final Class<?> PKG = ActionSetVariables.class;

  private Text wName;

  private Button wVarSubs;

  private ActionSetVariables action;

  private TableView wFields;

  private TextVar wFilename;

  private CCombo wFileVariableType;

  private boolean changed;

  public ActionSetVariablesDialog(
      Shell parent, ActionSetVariables action, WorkflowMeta workflowMeta, IVariables variables) {
    super(parent, workflowMeta, variables);
    this.action = action;

    if (this.action.getName() == null) {
      this.action.setName(BaseMessages.getString(PKG, "ActionSetVariables.Name.Default"));
    }
  }

  @Override
  public IAction open() {

    shell = new Shell(getParent(), SWT.DIALOG_TRIM | SWT.MIN | SWT.MAX | SWT.RESIZE);
    PropsUi.setLook(shell);
    WorkflowDialog.setShellImage(shell, action);

    ModifyListener lsMod = e -> action.setChanged();
    changed = action.hasChanged();

    FormLayout formLayout = new FormLayout();
    formLayout.marginWidth = PropsUi.getFormMargin();
    formLayout.marginHeight = PropsUi.getFormMargin();

    shell.setLayout(formLayout);
    shell.setText(BaseMessages.getString(PKG, "ActionSetVariables.Title"));

    int middle = props.getMiddlePct();
    int margin = PropsUi.getMargin();

    // Buttons go at the very bottom
    //
    Button wOk = new Button(shell, SWT.PUSH);
    wOk.setText(BaseMessages.getString(PKG, "System.Button.OK"));
    wOk.addListener(SWT.Selection, e -> ok());
    Button wCancel = new Button(shell, SWT.PUSH);
    wCancel.setText(BaseMessages.getString(PKG, "System.Button.Cancel"));
    wCancel.addListener(SWT.Selection, e -> cancel());
    BaseTransformDialog.positionBottomButtons(shell, new Button[] {wOk, wCancel}, margin, null);

    // Name line
    Label wlName = new Label(shell, SWT.RIGHT);
    wlName.setText(BaseMessages.getString(PKG, "ActionSetVariables.Name.Label"));
    PropsUi.setLook(wlName);
    FormData fdlName = new FormData();
    fdlName.left = new FormAttachment(0, 0);
    fdlName.right = new FormAttachment(middle, -margin);
    fdlName.top = new FormAttachment(0, margin);
    wlName.setLayoutData(fdlName);
    wName = new Text(shell, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    PropsUi.setLook(wName);
    wName.addModifyListener(lsMod);
    FormData fdName = new FormData();
    fdName.left = new FormAttachment(middle, 0);
    fdName.top = new FormAttachment(0, margin);
    fdName.right = new FormAttachment(100, 0);
    wName.setLayoutData(fdName);

    Group gFilename = new Group(shell, SWT.SHADOW_NONE);
    PropsUi.setLook(gFilename);
    gFilename.setText(BaseMessages.getString(PKG, "ActionSetVariables.FilenameGroup.Label"));

    FormLayout groupFilenameLayout = new FormLayout();
    groupFilenameLayout.marginWidth = 10;
    groupFilenameLayout.marginHeight = 10;
    gFilename.setLayout(groupFilenameLayout);

    // Name line
    Label wlFilename = new Label(gFilename, SWT.RIGHT);
    wlFilename.setText(BaseMessages.getString(PKG, "ActionSetVariables.Filename.Label"));
    PropsUi.setLook(wlFilename);
    FormData fdlFilename = new FormData();
    fdlFilename.left = new FormAttachment(0, 0);
    fdlFilename.right = new FormAttachment(middle, -margin);
    fdlFilename.top = new FormAttachment(0, margin);
    wlFilename.setLayoutData(fdlFilename);
    wFilename = new TextVar(variables, gFilename, SWT.SINGLE | SWT.LEFT | SWT.BORDER);
    PropsUi.setLook(wFilename);
    wFilename.addModifyListener(lsMod);
    FormData fdFilename = new FormData();
    fdFilename.left = new FormAttachment(middle, 0);
    fdFilename.top = new FormAttachment(0, margin);
    fdFilename.right = new FormAttachment(100, 0);
    wFilename.setLayoutData(fdFilename);

    // file variable type line
    Label wlFileVariableType = new Label(gFilename, SWT.RIGHT);
    wlFileVariableType.setText(
        BaseMessages.getString(PKG, "ActionSetVariables.FileVariableType.Label"));
    PropsUi.setLook(wlFileVariableType);
    FormData fdlFileVariableType = new FormData();
    fdlFileVariableType.left = new FormAttachment(0, 0);
    fdlFileVariableType.right = new FormAttachment(middle, -margin);
    fdlFileVariableType.top = new FormAttachment(wFilename, margin);
    wlFileVariableType.setLayoutData(fdlFileVariableType);
    wFileVariableType = new CCombo(gFilename, SWT.SINGLE | SWT.LEFT | SWT.BORDER | SWT.READ_ONLY);
    PropsUi.setLook(wFileVariableType);
    wFileVariableType.addModifyListener(lsMod);
    FormData fdFileVariableType = new FormData();
    fdFileVariableType.left = new FormAttachment(middle, 0);
    fdFileVariableType.top = new FormAttachment(wFilename, margin);
    fdFileVariableType.right = new FormAttachment(100, 0);
    wFileVariableType.setLayoutData(fdFileVariableType);
    wFileVariableType.setItems(VariableType.getDescriptions());

    FormData fdgFilename = new FormData();
    fdgFilename.left = new FormAttachment(0, margin);
    fdgFilename.top = new FormAttachment(wName, margin);
    fdgFilename.right = new FormAttachment(100, -margin);
    gFilename.setLayoutData(fdgFilename);

    //
    // START OF SETTINGS GROUP
    //
    Group gSettings = new Group(shell, SWT.SHADOW_NONE);
    PropsUi.setLook(gSettings);
    gSettings.setText(BaseMessages.getString(PKG, "ActionSetVariables.Settings.Label"));

    FormLayout groupLayout = new FormLayout();
    groupLayout.marginWidth = 10;
    groupLayout.marginHeight = 10;
    gSettings.setLayout(groupLayout);

    Label wlVarSubs = new Label(gSettings, SWT.RIGHT);
    wlVarSubs.setText(BaseMessages.getString(PKG, "ActionSetVariables.VarsReplace.Label"));
    PropsUi.setLook(wlVarSubs);
    FormData fdlVarSubs = new FormData();
    fdlVarSubs.left = new FormAttachment(0, 0);
    fdlVarSubs.top = new FormAttachment(wName, margin);
    fdlVarSubs.right = new FormAttachment(middle, -margin);
    wlVarSubs.setLayoutData(fdlVarSubs);
    wVarSubs = new Button(gSettings, SWT.CHECK);
    PropsUi.setLook(wVarSubs);
    wVarSubs.setToolTipText(BaseMessages.getString(PKG, "ActionSetVariables.VarsReplace.Tooltip"));
    FormData fdVarSubs = new FormData();
    fdVarSubs.left = new FormAttachment(middle, 0);
    fdVarSubs.top = new FormAttachment(wlVarSubs, 0, SWT.CENTER);
    fdVarSubs.right = new FormAttachment(100, 0);
    wVarSubs.setLayoutData(fdVarSubs);
    wVarSubs.addListener(SWT.Selection, e -> action.setChanged());

    FormData fdgSettings = new FormData();
    fdgSettings.left = new FormAttachment(0, margin);
    fdgSettings.top = new FormAttachment(gFilename, margin);
    fdgSettings.right = new FormAttachment(100, -margin);
    gSettings.setLayoutData(fdgSettings);

    // ///////////////////////////////////////////////////////////
    // / END OF SETTINGS GROUP
    // ///////////////////////////////////////////////////////////

    Label wlFields = new Label(shell, SWT.NONE);
    wlFields.setText(BaseMessages.getString(PKG, "ActionSetVariables.Variables.Label"));
    PropsUi.setLook(wlFields);
    FormData fdlFields = new FormData();
    fdlFields.left = new FormAttachment(0, 0);
    fdlFields.top = new FormAttachment(gSettings, margin);
    wlFields.setLayoutData(fdlFields);

    int rows = action.getVariableDefinitions().size();
    final int FieldsRows = rows;

    ColumnInfo[] colinf = {
      new ColumnInfo(
          BaseMessages.getString(PKG, "ActionSetVariables.Fields.Column.VariableName"),
          ColumnInfo.COLUMN_TYPE_TEXT,
          false),
      new ColumnInfo(
          BaseMessages.getString(PKG, "ActionSetVariables.Fields.Column.Value"),
          ColumnInfo.COLUMN_TYPE_TEXT,
          false),
      new ColumnInfo(
          BaseMessages.getString(PKG, "ActionSetVariables.Fields.Column.VariableType"),
          ColumnInfo.COLUMN_TYPE_CCOMBO,
          VariableType.getDescriptions(),
          false),
    };
    colinf[0].setUsingVariables(true);
    colinf[1].setUsingVariables(true);

    wFields =
        new TableView(
            variables,
            shell,
            SWT.BORDER | SWT.FULL_SELECTION | SWT.MULTI,
            colinf,
            FieldsRows,
            lsMod,
            props);

    FormData fdFields = new FormData();
    fdFields.left = new FormAttachment(0, 0);
    fdFields.top = new FormAttachment(wlFields, margin);
    fdFields.right = new FormAttachment(100, 0);
    fdFields.bottom = new FormAttachment(wOk, -2 * margin);
    wFields.setLayoutData(fdFields);

    getData();

    BaseDialog.defaultShellHandling(shell, c -> ok(), c -> cancel());

    return action;
  }

  /** Copy information from the meta-data input to the dialog fields. */
  public void getData() {
    wName.setText(Const.nullToEmpty(action.getName()));

    wFilename.setText(Const.NVL(action.getFilename(), ""));
    wFileVariableType.setText(action.getFileVariableType().getDescription());

    wVarSubs.setSelection(action.isReplaceVars());

    if (action.getVariableDefinitions() != null) {
      int i = 0;
      for (VariableDefinition definition : action.getVariableDefinitions()) {
        TableItem item = wFields.table.getItem(i++);
        item.setText(1, Const.nullToEmpty(definition.getName()));
        item.setText(2, Const.nullToEmpty(definition.getValue()));
        item.setText(3, definition.getType().getDescription());
      }
      wFields.setRowNums();
      wFields.optWidth(true);
    }

    wName.selectAll();
    wName.setFocus();
  }

  private void cancel() {
    action.setChanged(changed);
    action = null;
    dispose();
  }

  private void ok() {
    if (Utils.isEmpty(wName.getText())) {
      MessageBox mb = new MessageBox(shell, SWT.OK | SWT.ICON_ERROR);
      mb.setText(BaseMessages.getString(PKG, "System.TransformActionNameMissing.Title"));
      mb.setMessage(BaseMessages.getString(PKG, "System.ActionNameMissing.Msg"));
      mb.open();
      return;
    }

    action.setName(wName.getText());
    action.setFilename(wFilename.getText());
    action.setFileVariableType(VariableType.lookupDescription(wFileVariableType.getText()));
    action.setReplaceVars(wVarSubs.getSelection());

    int nrItems = wFields.nrNonEmpty();
    List<VariableDefinition> list = new ArrayList<>();
    for (int i = 0; i < nrItems; i++) {
      String name = wFields.getNonEmpty(i).getText(1);
      if (name != null && name.length() != 0) {
        String value = wFields.getNonEmpty(i).getText(2);
        VariableType scope = VariableType.lookupDescription(wFields.getNonEmpty(i).getText(3));
        list.add(new VariableDefinition(name, value, scope));
      }
    }
    action.setVariableDefinitions(list);

    dispose();
  }
}
