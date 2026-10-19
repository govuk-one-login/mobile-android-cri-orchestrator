#!/usr/bin/env bash

# Check the number of arguments
[[ $# -eq 2 ]] || {
  echo "Usage: check_pr_title [PR_TITLE]"
  exit 1
}

pr_title=$1
conventional_commit_regex="^(build|chore|ci|docs|feat|fix|perf|refactor|revert|style|test){1}(\([\w\-\.]+\))?(!)?: ([\w ])+([\s\S]*)"

echo "Checking title ${pr_title} conforms to conventional commit standards" >&2

[[ "${pr_title}" =~ conventional_commit_regex ]] || {
  echo "Error: ${pr_title} doesn't conform to conventional commit standards" >&2
  exit 1
}
