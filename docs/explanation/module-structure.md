<!-- vale Google.We = NO -->
<!-- vale Vale.Spelling["SDK", "SDKs"] = NO -->

# About the module structure

The purpose of this document is to explain the rationale behind the module structure this project. For reference, the module structure is based on the [Android SDK architecture sample project].

## Why build modular SDKs?

Before thinking about the structure of feature modules, why build an SDK in a modular way at all?

We're building SDKs that provide whole features with lots of screens, navigation, and internal functionality. With this in mind, we can reasonably expect these SDKs to grow in size and complexity as we add more and more features. So, unlike a simple utility library (like a network client) that's naturally limited in scope, we should build a feature-rich SDK the same attention to scaling that an app codebase would. One way we can do this is by modularising it (see [the growing codebase problem]).

## Why build feature modules?

When we build a modular codebase, we can do this horizontally by layer (for example data, domain, UI) or vertically by feature. Both can work but as the codebase grows, modularising by feature can help us to achieve a design with [high cohesion and low coupling] which is easier to maintain ("code that changes together stays together").

## Why split API and implementation modules?

### To encourage decoupling and encapsulation

Firstly, splitting API and implementation into separate modules helps to encourage a more decoupled design which is easier to maintain; it exposes where implementation details are inadvertently leaked and reduces the likelihood of features' implementations becoming coupled together.

Keeping a module's interfaces separate makes it clear what is and isn't part of a module's API and highlights problems before they become deeply ingrained and difficult to unpick.

### To manage module dependencies and communication

Secondly, splitting API and implementation helps us manage dependencies between modules. Say we have several feature modules but now we want two features to be able to communicate with each other. We can't have two features’ implementations depending on each other otherwise we'd introduce a cyclical dependency.

One solution to this problem is to introduce 'mediator' modules to manage communication and lower level modules to provide a shared view of the world for the feature modules (as shown in [module-to-module communication]). Although this solution works, it results in unintended coupling in both mediator and lower level modules. It also results in lower cohesion as lower level 'data' modules start to gather feature specific functionality.

A solution that overcomes these problems is for features to supply a separate API module. Feature API modules contain only interfaces and act as a shared contract for all features. Implementations can then be provided behind the scenes using dependency injection (see how this works through [dependency inversion]).

### To improve build performance

Another benefit of splitting API from implementation is the improvement on incremental compilation performance which leads to better developer experience. If we had features depending directly on other features, changing one feature can trigger a cascade of recompilation across many other dependent features. In contrast, when splitting API and implementation, we can change a feature's implementation without recompiling any others. As the codebase grows, this benefit becomes more noticeable.

## Why add a public API module?

Next, we need to consider that an SDK also needs to provide an actual public API. This is unlike an app where there is no 'public API' (only a user interface). To use the terminology from the [sample project], an app’s feature module would only provide an 'internal API', whereas an SDK has an 'internal API' and a 'public API'.

The internal API handles communication between features within the SDK and doesn’t need to be exposed outside of it. However, we need to share the public API externally so developers can interact with the SDK’s features. To manage this, we introduce a third type of module specifically for the SDK’s public API.

One positive side-effect of using this separation is that changes to the public API of the SDK become obvious. We can more easily spot breaking changes in code review or require additional reviews on these interfaces for example.

One thing to note is that not all features would need a public API. And not all modules may need an API/implementation split (simple utility libraries, for example).

---

[Android SDK architecture sample project]: https://github.com/govuk-one-login/mobile-android-sdk-architecture-sample/blob/main/ARCHITECTURE.md
[the growing codebase problem]: https://developer.android.com/topic/modularization#growing-codebase
[high cohesion and low coupling]: https://developer.android.com/topic/modularization/patterns#cohesion-coupling
[module-to-module communication]: https://developer.android.com/topic/modularization/patterns#communication
[dependency inversion]: https://developer.android.com/topic/modularization/patterns#dependency_inversion
[sample project]: https://github.com/govuk-one-login/mobile-android-sdk-architecture-sample