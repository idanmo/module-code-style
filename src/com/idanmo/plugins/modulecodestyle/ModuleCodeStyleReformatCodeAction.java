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

import com.intellij.codeInsight.actions.ReformatCodeAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.module.Module;
import com.intellij.psi.codeStyle.CodeStyleScheme;
import com.intellij.psi.codeStyle.CodeStyleSchemes;
import com.intellij.psi.impl.source.codeStyle.PersistableCodeStyleSchemes;

/**
 * @author Idan Moyal
 */
public class ModuleCodeStyleReformatCodeAction extends ReformatCodeAction {

    @Override
    public void actionPerformed(AnActionEvent event) {
        final DataContext dataContext = event.getDataContext();
        final Module module = LangDataKeys.MODULE.getData(dataContext);
        if (module != null) {
            ModuleCodeStyleComponent component = module.getComponent(ModuleCodeStyleComponent.class);
            if (component != null && component.getState() != null) {
                final String moduleCodeStyleScheme = component.getState().getCodeStyleSchemeName();
                final CodeStyleSchemes schemes = PersistableCodeStyleSchemes.getInstance();
                final CodeStyleScheme scheme = schemes.findSchemeByName(moduleCodeStyleScheme);
                if (scheme != null)
                    schemes.setCurrentScheme(scheme);
            }
        }
        super.actionPerformed(event);
    }

}
