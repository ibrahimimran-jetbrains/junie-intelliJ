#!/bin/bash

# This script detaches a forked repository using GitHub API
# You need to provide your GitHub Personal Access Token as an argument

if [ -z "$1" ]; then
  echo "Error: GitHub Personal Access Token is required"
  echo "Usage: ./detach_fork.sh YOUR_PERSONAL_ACCESS_TOKEN"
  exit 1
fi

TOKEN=$1
USERNAME="ibrahimimran-jetbrains"
REPO="junie-intelliJ"

echo "Detaching fork relationship for $USERNAME/$REPO..."

curl -X PATCH \
  -H "Accept: application/vnd.github+json" \
  -H "Authorization: Bearer $TOKEN" \
  -H "X-GitHub-Api-Version: 2022-11-28" \
  https://api.github.com/repos/$USERNAME/$REPO \
  -d '{"name":"'$REPO'","source":null}'

echo -e "\nFork detachment request sent. Please check your repository on GitHub."
echo "The 'forked from' label should disappear and only your contributions should be visible."
