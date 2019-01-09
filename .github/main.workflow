workflow "Jenkins single-shot master" {
  on = "push"
  resolves = ["jenkinsfile-runner-lazyloaded", "jenkinsfile-runner-prepackaged"]
}

action "jenkinsfile-runner-prepackaged" {
  uses = "jonico/jenkinsfile-runner-github-actions/jenkinsfile-runner-prepackaged@master"
  secrets = ["GITHUB_TOKEN"]
}

action "jenkinsfile-runner-lazyloaded" {
  uses = "docker://jonico/jenkinsfile-runner-lazyloaded"
  secrets = ["GITHUB_TOKEN"]
}
