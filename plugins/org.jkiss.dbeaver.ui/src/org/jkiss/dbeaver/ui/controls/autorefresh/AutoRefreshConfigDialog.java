/*
 * DBeaver - Universal Database Manager
 * Copyright (C) 2010-2026 DBeaver Corp and others
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
package org.jkiss.dbeaver.ui.controls.autorefresh;

import org.eclipse.jface.dialogs.IDialogSettings;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Spinner;
import org.jkiss.code.NotNull;
import org.jkiss.dbeaver.ui.UIIcon;
import org.jkiss.dbeaver.ui.UIUtils;
import org.jkiss.dbeaver.ui.dialogs.BaseDialog;
import org.jkiss.dbeaver.ui.internal.UIMessages;

public class AutoRefreshConfigDialog extends BaseDialog {

    private static final String DIALOG_ID = "DBeaver.AutoRefreshConfigDialog";//$NON-NLS-1$

    private RefreshSettings refreshSettings;

    AutoRefreshConfigDialog(Shell parentShell, RefreshSettings settings) {
        super(parentShell, UIMessages.dialog_auto_refresh_title, UIIcon.CLOCK_START);
        this.refreshSettings = new RefreshSettings(settings);
    }

    public RefreshSettings getRefreshSettings() {
        return refreshSettings;
    }

    @Override
    protected IDialogSettings getDialogBoundsSettings()
    {
        return UIUtils.getDialogSettings(DIALOG_ID);
    }

    @NotNull
    @Override
    protected Composite createDialogArea(@NotNull Composite parent) {
        Composite composite = super.createDialogArea(parent);

        Composite settingsGroup = UIUtils.createTitledComposite(composite, UIMessages.dialogs_name_and_password_dialog_group_settings, 2, GridData.FILL_BOTH);
        final Spinner intervalSpinner = UIUtils.createLabelSpinner(
            settingsGroup,
            UIMessages.dialog_auto_refresh_interval_label,
            UIMessages.dialog_auto_refresh_interval_tip,
            refreshSettings.getRefreshInterval(),
            1,
            Integer.MAX_VALUE
        );
        intervalSpinner.addSelectionListener(SelectionListener.widgetSelectedAdapter(e ->
            refreshSettings.setRefreshInterval(intervalSpinner.getSelection())));
        final Button stopOnErrorCheck = UIUtils.createCheckbox(
            settingsGroup,
            UIMessages.dialog_auto_refresh_stop_on_error,
            UIMessages.dialog_auto_refresh_stop_on_error_tip,
            refreshSettings.isStopOnError(),
            2
        );
        stopOnErrorCheck.addSelectionListener(SelectionListener.widgetSelectedAdapter(e ->
            refreshSettings.setStopOnError(stopOnErrorCheck.getSelection())));

        return composite;
    }

}
