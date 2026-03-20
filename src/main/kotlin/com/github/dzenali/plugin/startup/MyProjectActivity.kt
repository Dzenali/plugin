package com.github.dzenali.plugin.startup

import com.github.dzenali.plugin.services.GamificationService
import com.github.dzenali.plugin.services.MyProjectService
import com.intellij.ide.util.PropertiesComponent
import com.intellij.openapi.components.service
import com.intellij.openapi.diagnostic.thisLogger
import com.intellij.openapi.project.Project
import com.intellij.openapi.startup.ProjectActivity

class MyProjectActivity : ProjectActivity {

    override suspend fun execute(project: Project) {
        project.service<MyProjectService>()
        project.service<GamificationService>()
    }
}