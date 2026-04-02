package com.github.dzenali.plugin.listeners

import com.intellij.coverage.BaseCoverageSuite
import com.intellij.coverage.CoverageDataManager
import com.intellij.coverage.CoverageSuite
import com.intellij.coverage.CoverageSuiteListener
import com.intellij.coverage.CoverageSuitesBundle
import com.github.dzenali.plugin.achievements.*
import com.github.dzenali.plugin.services.GamificationService
import com.intellij.openapi.application.ApplicationManager
import com.intellij.openapi.progress.ProgressIndicator
import com.intellij.openapi.progress.ProgressManager
import com.intellij.openapi.progress.Task
import com.intellij.openapi.project.Project
import com.github.dzenali.plugin.util.CoverageInfo
import com.github.dzenali.plugin.util.GameMode
import com.github.dzenali.plugin.util.Util
import com.intellij.openapi.components.service
import java.lang.reflect.Field

object CoverageListener: CoverageSuiteListener {
    lateinit var project: Project
    private lateinit var testRunName: String

    override fun coverageGathered(suite: CoverageSuite) {
        project = suite.project

        testRunName = (suite as BaseCoverageSuite).configuration!!.name

        super.coverageGathered(suite)
    }
    override fun beforeSuiteChosen() = Unit

    override fun afterSuiteChosen() {
        val dataManager = CoverageDataManager.getInstance(project)
        if (ApplicationManager.getApplication().isUnitTestMode) {
            return
        }

        val suitesBundle: CoverageSuitesBundle = dataManager.currentSuitesBundle ?: return
        val annotator = suitesBundle.coverageEngine.getCoverageAnnotator(project)

        val modalTask: Task.Modal = object : Task.Modal(project, "Modal Cancelable Task", false) {
            override fun run(indicator: ProgressIndicator) {
                if (annotator::class.simpleName == "JavaCoverageAnnotator") {
                    javaCoverage()
                }
            }

            fun javaCoverage() {
                // Check for class coverage information
                val classCoverageInfosField: Field = annotator.javaClass.getDeclaredField("myClassCoverageInfos")
                classCoverageInfosField.isAccessible = true
                val classCoverageInfosValue: Map<Any, Any> = classCoverageInfosField.get(annotator) as Map<Any, Any>

                val gamificationService = project.service<GamificationService>()
                val gameMode = gamificationService.getGameMode()

                val runClassName = testRunName.split(".").first().replace("Test", "")

                for ((key, value) in classCoverageInfosValue.filter { (it.key as String).contains(runClassName)  && !Util.isTestExcluded(it.key as String) }) {
                    val coverageInfo = extractCoverageInfos(value)
                    Cover10LinesAchievement.takeIf { !it.isDone() }?.triggerAchievement(coverageInfo, project)
                    Cover33LinesAchievement.takeIf { !it.isDone() }?.triggerAchievement(coverageInfo, project)
                    Cover100LinesAchievement.takeIf { !it.isDone() }?.triggerAchievement(coverageInfo, project)
                    Cover200LinesAchievement.takeIf { !it.isDone() }?.triggerAchievement(coverageInfo, project)
                    gamificationService.updateCoverage(coverageInfo, key as String, testRunName, project)
                }


                val extensionCoverageField: Field = annotator.javaClass.getDeclaredField("myDirCoverageInfos")
                extensionCoverageField.isAccessible = true
            }
        }

        ApplicationManager.getApplication().invokeLater(fun() {
            ProgressManager.getInstance().run(modalTask)
        })
    }

    private fun extractCoverageInfos(coverageInfo: Any): CoverageInfo {
        val coveredLineCount = coverageInfo.javaClass.getMethod("getCoveredLineCount").invoke(coverageInfo) as Int
        val totalLineCount = getFieldAsInt(coverageInfo, "totalLineCount")
        val totalClassCount = getFieldAsInt(coverageInfo, "totalClassCount")
        val coveredClassCount = getFieldAsInt(coverageInfo, "coveredClassCount")
        val totalMethodCount = getFieldAsInt(coverageInfo, "totalMethodCount")
        val coveredMethodCount = getFieldAsInt(coverageInfo, "coveredMethodCount")
        val coveredBranchCount = getFieldAsInt(coverageInfo, "coveredBranchCount")
        val totalBranchCount = getFieldAsInt(coverageInfo, "totalBranchCount")
        return CoverageInfo(
            totalClassCount,
            coveredClassCount,
            totalMethodCount,
            coveredMethodCount,
            totalLineCount,
            coveredLineCount,
            totalBranchCount,
            coveredBranchCount
        )
    }

    private fun getFieldAsInt(coverageInfo: Any, fieldName: String): Int {
        val field: Field? = findUnderlyingField(coverageInfo.javaClass, fieldName)
        if (field == null) {
            return 0
        }

        return field.get(coverageInfo) as Int

    }

    private fun findUnderlyingField(clazz: Class<*>, fieldName: String): Field? {
        var current = clazz

        do {
            try {
                return current.getDeclaredField(fieldName)
            } catch (_: Exception) {}
        } while (current.superclass.also { current = it } != null)

        return null
    }

}