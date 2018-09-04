import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper

def inspect(RunWrapper currentBuild) {
    def previousBuild = currentBuild.previousBuild
    while(previousBuild) {
        println "Build ${previousBuild.number}: ${previousBuild.buildVariables}"
        previousBuild = previousBuild.previousBuild
    }
}
