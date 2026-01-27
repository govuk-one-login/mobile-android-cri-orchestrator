# How to publish a new hotfix version

1. Create a temporary hotfix branch named "temp/hotfix" and push to the repo.
2. Create a hotfix branch in the format "hotfix/M.m.p"
3. Add your changes to the hotfix branch and push to the repo.
4. Create a PR with temp/hotfix as the target branch
5. Once the hotfix PR has been approved,  press the "Squash and merge" button
6. Ensure that the merge title is in the format "Merge pull request #xxx from 
govuk-one-login/release/M.m.p" to allow for the correct version to be extracted and used as a tag.