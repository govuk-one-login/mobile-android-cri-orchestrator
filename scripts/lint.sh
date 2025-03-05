#!/usr/bin/env bash

./gradlew \
  detekt \
  ktlintCheck \
  vale \
  --continue