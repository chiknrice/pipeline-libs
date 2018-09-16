import hudson.model.Run
import org.jenkinsci.plugins.workflow.job.WorkflowRun
import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper

def withBuildVars(Map filter) {
    def matches = []
    def previousBuild = currentBuild.previousBuild
    while(previousBuild) {
        def buildVariables = previousBuild.buildVariables
        println "Inspecting ${previousBuild.number} which contains ${buildVariables}"
        def tempFilter = filter.clone()
        println "Trying to match with ${tempFilter}"
        def result = tempFilter - buildVariables
        println "Result is ${result}"
        if(!result) {
            println "Matched ${previousBuild.number}"
            matches << previousBuild
        }
        previousBuild = previousBuild.previousBuild
    }
    return matches
}

def inspectPreviousRuns(int buildToInspect, RunWrapper thisBuild = currentBuild) {
    WorkflowRun run = thisBuild.rawBuild
    while (run.number > buildToInspect) {
        run = run.previousBuild
    }
    println "Actions of build ${buildToInspect}"
    run.allActions.each { println it }
}

def deleteArtifacts(int buildNumber, RunWrapper thisBuild = currentBuild) {
    WorkflowRun run = thisBuild.rawBuild
    while (run.number > buildNumber) {
        run = run.previousBuild
    }
    run.deleteArtifacts()
    CacheExtension cacheExtension = Jenkins.instance.getExtensionList(CacheExtension).get(0)
    cacheExtension.getRunCache().invalidate(run.externalizableId)
}

def doSomething(Closure something) {
    something()
}