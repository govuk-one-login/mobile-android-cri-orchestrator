<!-- vale Google.We = NO -->
<!-- vale Vale.Spelling["KDDs", "KDD"] = NO -->

# Storing Key Design Decisions (KDDs)

## Summary

A Key Design Decision (KDD) is a design choice that's architecturally significant.

We can store KDDs within the `/docs` folder of this repository. These log the local architectural choices we've made, why we've made them, and the consequences.

This doesn't duplicate [govuk-one-login/architecture] or [Technical Design Forum], which have a different intended audience and permissions model.

Strategic decisions, with cross programme consequence, continue to live in [govuk-one-login/architecture] where architecture leads review them.

Decisions with cross-pod impact continue to evolve through [Technical Design Forum] (TDF) where Mobile Pod leads review them.

Tactical decisions within the team, describing how we structure this project, live in this repository. The team reviews and updates them.

## Context

We'd like to record the decisions to help contributors understand the rationale behind the architecture of the project. In the development of [govuk-one-login/mobile-android-cri-orchestrator], we make architectural decisions impacting future work. The rationale behind these decisions isn't always clear when looking at the code alone.

We also want a place to document our team's decisions which don't need a full cross-pod or cross-programme review. ADRs with strategic implications in [govuk-one-login/architecture] need a review from a Lead Architect, Security and Technical Writer. Technical Designs (TD) need a review from Tech Leads, Security, Architects, and QA. Teams have autonomy to design and deliver a solution within those goalposts.

The intended audience for these documents include:

- New developers to the team who are trying to understand this project.
- Developers from other teams curious to see our implementation and compare ideas.
- Developers within the team, looking for a reference architecture when delivering work.

## Format

We should keep formatting simple:

- Infer dates and authors from the Git history.
- Write in markdown.
- Aim to keep headings consistent with this PR, although they can vary if needed.
- Once the team has reviewed and merged a KDD, they have adopted the decision.
- Deprecate superseded local KDDs by amending them with `Status: superseded.` Contributors must add a link referencing any new KDD or reason.

## Consequences

- We'll store KDDs relating to [govuk-one-login/mobile-android-cri-orchestrator] in `docs/design-decisions`.
- Where there are cross-pod consequences, we may have a stub here, but it should point to a cross-pod TD in [TDF Items].
- Where there are cross-programme consequences, we may have a stub here, but it should point to a cross-programme ADR in [govuk-one-login/architecture].

[govuk-one-login/architecture]: https://github.com/govuk-one-login/architecture
[govuk-one-login/mobile-android-cri-orchestrator]: https://github.com/govuk-one-login/mobile-android-cri-orchestrator
[Technical Design Forum]: https://govukverify.atlassian.net/wiki/spaces/DCMAW/pages/3436183661/What+Is+TDF
[TDF Items]: https://govukverify.atlassian.net/wiki/spaces/DCMAW/pages/3436544089/TDF+Items

<!-- vale Google.We = YES -->
