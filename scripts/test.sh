#!/usr/bin/env bash

./gradlew \
  testDebugUnitTest \
  testBuildDebugUnitTest \
  verifyPaparazziDebug \
  verifyPaparazziBuildDebug \
  --continue