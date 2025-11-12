#!/usr/bin/env bash

# Check the number of arguments
[[ $# -eq 2 ]] || {
  echo "Usage: ensure_prerelease_tag [GITHUB_REF_NAME] [PRERELEASE_TYPE]"
  exit 1
}

# Value of GITHUB_REF_NAME
# See https://docs.github.com/en/actions/reference/workflows-and-actions/variables#default-environment-variables
github_ref_name=$1

# The type of pre-release, for example 'alpha'
# This should match the start of the pre-release version that follow
prerelease_type=$2

echo "Checking ${github_ref_name} is a valid ${prerelease_type} release" >&2

[[ "${github_ref_name}" =~ v.*-$prerelease_type\..* ]] || {
  echo "Error: ${github_ref_name} isn't a valid ${prerelease_type} release" >&2
  exit 1
}
