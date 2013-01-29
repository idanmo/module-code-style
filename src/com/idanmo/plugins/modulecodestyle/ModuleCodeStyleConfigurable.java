/*
 *  Copyright (c) 2013 Idan Moyal
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.idanmo.plugins.modulecodestyle;

import com.intellij.openapi.module.Module;
import com.intellij.openapi.options.BaseConfigurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.psi.codeStyle.CodeStyleScheme;
import com.intellij.psi.codeStyle.CodeStyleSchemes;
import com.intellij.psi.impl.source.codeStyle.PersistableCodeStyleSchemes;
import com.intellij.ui.CollectionComboBoxModel;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Idan Moyal
 */
public class ModuleCodeStyleConfigurable extends BaseConfigurable {

    private static final String DISPLAY_NAME = "Code Style";

    private JPanel mainPanel;
    private JComboBox codeStyleSchemesComboBox;
    private JLabel codeStyleSchemeLabel;

    private ModuleCodeStyleComponent component;

    public ModuleCodeStyleConfigurable(Module module) {
        this.component = module.getComponent(ModuleCodeStyleComponent.class);
        codeStyleSchemesComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent event) {
                setModified(true);
            }
        });
    }

    @Nls
    @Override
    public String getDisplayName() {
        return DISPLAY_NAME;
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    @Override
    public boolean isModified() {
        return super.isModified();
    }

    @Nullable
    @Override
    public JComponent createComponent() {
        loadAvailableCodeStyleSchemes();
        return mainPanel;
    }

    private void loadAvailableCodeStyleSchemes() {
        final CodeStyleSchemes schemes = PersistableCodeStyleSchemes.getInstance();
        if (schemes != null) {
            final List<String> schemesList = new ArrayList<String>(schemes.getSchemes().length);

            String selectedCodeStyleSchemeName = getModuleCodeStyleSchemeName();
            boolean schemeExists = false;
            for (CodeStyleScheme scheme : schemes.getSchemes()) {
                schemesList.add(scheme.getName());
                if (scheme.getName().equals(selectedCodeStyleSchemeName))
                    schemeExists = true;
            }

            if (!schemeExists)
                selectedCodeStyleSchemeName = ModuleCodeStyleState.defaultState().getCodeStyleSchemeName();

            final ComboBoxModel model = new CollectionComboBoxModel(schemesList, selectedCodeStyleSchemeName);
            codeStyleSchemesComboBox.setModel(model);
        } else {
            mainPanel.setEnabled(false);
        }
    }

    private String getModuleCodeStyleSchemeName() {
        final ModuleCodeStyleState state = component.getState() != null ? component.getState() : ModuleCodeStyleState.defaultState();
        return state.getCodeStyleSchemeName();
    }

    @Override
    public void apply() throws ConfigurationException {
        if (codeStyleSchemesComboBox.getSelectedItem() != null) {
            String selectedSchemeName = codeStyleSchemesComboBox.getSelectedItem().toString();
            component.getState().setCodeStyleSchemeName(selectedSchemeName);
        }
    }

    @Override
    public void reset() {
    }

    @Override
    public void disposeUIResources() {
    }

}
