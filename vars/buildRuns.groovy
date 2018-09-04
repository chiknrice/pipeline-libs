import org.jenkinsci.plugins.workflow.support.steps.build.RunWrapper

def withBuildVars(Map filter) {
    def matches = []
    def previousBuild = currentBuild.previousBuild
    while(previousBuild) {
        def buildVariables = previousBuild.buildVariables
        def tempFilter = filter.clone()
        def result = tempFilter - buildVariables
        if(result.empty) {
            matches << previousBuild
        }
        previousBuild = previousBuild.previousBuild
    }
    return matches
}
