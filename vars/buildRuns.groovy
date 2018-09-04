
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
