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

import com.intellij.openapi.actionSystem.ActionManager;
import com.intellij.openapi.components.PersistentStateComponent;
import com.intellij.openapi.components.State;
import com.intellij.openapi.components.Storage;
import com.intellij.openapi.module.ModuleComponent;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Idan Moyal
 */
@State(name = "ModuleCodeStyle.State", storages={@Storage(file = "$MODULE_FILE$")})
public class ModuleCodeStyleComponent implements ModuleComponent, PersistentStateComponent<ModuleCodeStyleState> {

    private static final String COMPONENT_UNIQUE_NAME = "Module Code Style Component";
    private static final String REFORMAT_CODE_ACTION_NAME = "ReformatCode";

    private ModuleCodeStyleState state;

    public ModuleCodeStyleComponent() {
        this.state = ModuleCodeStyleState.defaultState();
    }

    @Override
    public void initComponent() {
        final ActionManager manager = ActionManager.getInstance();
        manager.unregisterAction(REFORMAT_CODE_ACTION_NAME);
        manager.registerAction(REFORMAT_CODE_ACTION_NAME, new ModuleCodeStyleReformatCodeAction());
    }

    @Override
    public void disposeComponent() {
    }

    @NotNull
    @Override
    public String getComponentName() {
        return COMPONENT_UNIQUE_NAME;
    }

    @Nullable
    @Override
    public ModuleCodeStyleState getState() {
        return state;
    }

    @Override
    public void loadState(ModuleCodeStyleState state) {
        this.state = state;
    }

    @Override
    public void projectOpened() {
    }

    @Override
    public void projectClosed() {
    }

    @Override
    public void moduleAdded() {
    }
}
