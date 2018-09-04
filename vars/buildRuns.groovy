
def withBuildVars(Map filter) {
    def matches = []
    def previousBuild = currentBuild.previousBuild
    while(previousBuild) {
        def buildVariables = previousBuild.buildVariables
        println "Inspecting ${previousBuild.number} which contains ${buildVariables}"
        def tempFilter = filter.clone()
        def result = tempFilter - buildVariables
        if(result.empty) {
            println "Matched ${previousBuild.number}"
            matches << previousBuild
        }
        previousBuild = previousBuild.previousBuild
    }
    return matches
}
