# Use Anvil for dependency injection

<!-- vale Google.We = NO -->
<!-- vale Vale.Spelling["SDKs"] = NO -->

## Context

Android apps often use [Hilt] for dependency injection (DI). Hilt is an opinionated layer around [Dagger] which generates Dagger components and injects Android components with their dependencies without much boilerplate.

The decision to use Hilt for apps is fairly straightforward because [Hilt is Google's recommended approach for DI in apps].

However, we also need to create shared libraries and software development kits (SDK), such as this project, to extract reusable tools to share between products. Libraries and SDKs are also likely to need a simple way to manage dependencies and could benefit from a dependency injection framework to do this heavy lifting.

## Options

### Hilt

Apps in Government Digital Service (GDS) use Hilt for dependency injection. There is also some precedent for using Hilt in SDKs at GDS, however it's worth noting that this has always resulted from refactoring code from an app into an SDK without changing the DI framework.

Downsides of using Hilt:
- Using Hilt in an SDK or library project is unconventional; it's not designed for this use case.
- Using Hilt forces the host app to use Hilt and annotate their app with `@HiltApplication`.
- Library dependencies may conflict with app dependencies. Hilt only provides a predefined set of scopes, such as singleton, activity and view model, to which developers can associate software components. To overcome this, SDKs and libraries must add qualifiers to all dependencies.

### Dagger

[Dagger] is a well known dependency injection framework and is the foundation onto which [Hilt] and [Anvil] build.

Dagger requires developers to write more boilerplate but is less opinionated and more flexible.

Benefits of pure Dagger:
- There is no risk of conflicts with the host app because library developers can define their own components and scopes.

Downsides of pure Dagger:
- It requires boilerplate to merge separate Dagger components
- It requires boilerplate to attach Dagger modules to Dagger components

### Anvil (chosen)

[Anvil] makes dependency injection with Dagger easier by automatically merging Dagger modules and component interfaces. Instead of manually adding modules to a Dagger component and making the Dagger component extend all component interfaces, Anvil includes these modules and interfaces in a component automatically.

Benefits of Anvil:
-  It reduces the amount of boilerplate we need to write, in a similar way to Hilt.
-  It retains the flexibility to define custom scopes, in a similar way to Dagger.

Downsides of Anvil:
- There's less information or community support around it.

### Manual dependency injection

For completeness, let's not forget that manual dependency injection is an option.

The benefit of the manual approach is that it's fast to compile and can be simpler depending on the size of the project.

Sometimes a software library is very 'flat' and doesn't require a deep graph of interdependent objects. For example, a maths library that contains mostly pure functions and a few, small configurable utilities. In this case, it can be simpler to avoid the complexity of dependency injection frameworks and instead, construct dependencies by hand.

However, for a project of this scale, it's not practical.

## Consequences

- This SDK uses Anvil for dependency injection
- Production code in this SDK doesn't depend on Hilt
- Consuming apps may use any dependency injection framework
- The test wrapper app may continue to use any dependency injection framework

[Hilt]: https://github.com/square/anvil
[Hilt is Google's recommended approach for DI in apps]: https://developer.android.com/training/dependency-injection#hilt
[Dagger]: https://dagger.dev
[Anvil]: https://github.com/square/anvil