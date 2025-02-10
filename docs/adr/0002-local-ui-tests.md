# Prefer local tests over device tests

<!-- vale Google.We = NO -->
<!-- vale Vale.Spelling["Robolectric", "JUnit"] = NO -->

## Summary

When tests need access to Android framework APIs, prefer to write local tests rather than instrumented tests (also known as 'device tests').

Use Robolectric to provide an environment for these tests to run, unless a screenshot test is more appropriate.


## Context

Sometimes tests need access to Android framework APIs, for example UI tests.

Instrumented tests run on real or emulated devices which provide these APIs.

Local tests run on the Java Virtual Machine (JVM) and don't have access to these APIs.

However, instrumented tests are slower and more resource intensive than local tests.

Google recommends to avoid instrumented tests unless there is no other option and provides [Robolectric] for converting instrumented tests into local tests.

> We recommend using instrumented tests only in cases where you must test against the behavior of a real device.
>
> – [instrumented tests]

Robolectric provides a device-like environment within local JVM tests.

The benefits of using Robolectric is that:
- Tests run faster than equivalent instrumented tests
- It can be simpler than manually mocking lots of Android framework classes in local tests
- It's a stable tool maintained and recommended by Google

The downsides of using Robolectric are that:
- It mixes slower UI tests with faster unit tests, slowing down local tests overall
- It's an additional dependency to maintain
- It only works with JUnit 4 and doesn't yet support JUnit 5

This decision shouldn't imply that _all_ UI tests or tests requiring Android APIs should run using Robolectric:
- Screenshot tests wouldn't need to use Robolectric if it isn't the best tool for the job.
- Unit tests may continue to mock framework classes if that's simpler.

We may still need instrumented for niche use cases.

> Robolectric provides enough fidelity to run most UI tests but some cases will still require device tests, for example those related to system UI like edge-to-edge or picture-in-picture, or when relying on unsupported features, like WebView.
>
> – [Robolectric versus device tests]


## Consequences

- We prefer to build local Robolectric tests over instrumented tests.
- Non-interactive UI screenshot tests still don't need to use Robolectric.
- Unit tests that can mock framework classes easily still don't need to use Robolectric.
- The project has an additional dependency and maintenance burden on Robolectric.
- We must maintain JUnit 4 for Robolectric tests alongside JUnit 5 for other local tests.
- In total local tests run for longer because they now include slower UI tests.

[Instrumented tests]: https://developer.android.com/training/testing/instrumented-tests
[Robolectric versus device tests]: https://developer.android.com/training/testing/local-tests/robolectric#robolectric-versus
[Robolectric]: https://robolectric.org/

<!-- vale Google.We = YES -->