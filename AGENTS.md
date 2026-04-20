# AGENTS.md

## Project

**Parcel On Map** is an Android pet project for visualizing parcel delivery routes on a map.

Goal: build a **clean, portfolio-ready Android prototype** with modern tools, readable code, and pragmatic architecture.

Tech stack:

- Kotlin
- Jetpack Compose
- Navigation Compose
- ViewModel
- Google Maps Compose
- Gradle Kotlin DSL
- multi-module project structure

---

## Core rules

### 1. Prefer simple solutions
Use the simplest solution that is:

- clean
- readable
- maintainable
- appropriate for the current stage of the project

Do not overengineer.

### 2. Respect the existing codebase
Before changing anything:

- inspect nearby files
- follow existing naming
- follow existing structure
- preserve consistency

Do not introduce a new style if the current one is already reasonable.

### 3. Keep changes focused
Prefer small, safe, reviewable changes.

Do not mix unrelated:

- feature work
- refactoring
- file moves
- formatting-only changes

### 4. Optimize for portfolio quality
This is a pet project.

Code should be:

- easy to explain
- pleasant to read
- based on modern Android practices
- realistic, but not bloated

---

## Architecture

Use pragmatic layered architecture.

Prefer:

- clear UI layer
- clear ViewModel/state holder
- simple data/domain separation only where useful

Do not add layers just for architecture purity.

### Modules
- Place code in the most relevant module
- Avoid unnecessary cross-module coupling
- Do not create new modules without a strong reason
- Prefer fewer clear modules over fragmentation

### State
- UI state should be explicit and easy to reason about
- Keep heavy logic out of composables when possible
- Do not scatter screen logic across too many files without need

---

## Kotlin rules

- Prefer idiomatic Kotlin
- Prefer `val` over `var`
- Keep functions focused
- Use clear names
- Avoid giant classes
- Avoid unnecessary abstractions
- Use data classes and sealed types where they improve clarity

---

## Compose rules

- Keep composables small and focused
- Prefer stateless composables where possible
- Hoist state when appropriate
- Separate screen-level UI from reusable components
- Avoid unnecessary recompositions
- Add previews only when they provide real value

Do not put too much business or mapping logic directly inside composables.

---

## Map rules

Maps are a core feature of the project.

When working on map screens:

- keep behavior understandable
- keep route rendering simple and maintainable
- keep marker logic clear
- avoid overcomplicated abstractions around map state

Mock data is acceptable unless real integration is explicitly needed.

---

## Dependency rules

When proposing a dependency:

- prefer official Android/Google solutions first
- avoid libraries for trivial tasks
- justify new dependencies
- keep the project lightweight

Do not update or add dependencies without a clear reason.

---

## Android-specific guidance

- Do not introduce DI, domain layers, or extra patterns "for future scalability" unless there is real need
- Reuse existing composables before creating new abstractions
- Keep ViewModel logic practical and not framework-heavy
- Do not restructure package/module layout without clear benefit
- Prefer modern Android APIs and straightforward implementation

---

## Git and change rules

- Prefer atomic commits
- Keep commit messages clear
- Avoid broad refactors unless requested
- Do not touch unrelated files
- Do not rewrite stable code without need

---

## Task execution rules

When working on a task:

1. Understand local context first
2. Choose the smallest clean solution
3. Reuse existing patterns where possible
4. Keep implementation practical
5. Avoid unnecessary renames and moves

If there are multiple valid options, prefer the one that is:

- simpler
- more maintainable
- easier to explain
- better suited for a portfolio project

---

## What to avoid

Avoid unless explicitly requested:

- overengineering
- unnecessary interfaces
- premature optimization
- boilerplate-heavy architecture
- massive refactors
- complex abstractions for simple features
- introducing patterns with no current payoff

---

## Required output after each completed task

After completing a task, always provide a short post-task explanation in **Russian**.

Use this structure:

### Что сделано
Briefly explain what was implemented.

### Какие файлы изменены
List created or modified files.

### Почему так
Briefly explain why this solution was chosen.

Keep this explanation short, clear, and practical.

---

## Short version

> Build Parcel On Map as a clean, modern, modular Android pet project. Use simple, readable, pragmatic solutions. Respect the existing structure, avoid overengineering, keep changes small and focused, do not introduce architecture "for the future" without need, and after each completed task explain in Russian what was done, which files changed, and why the solution was chosen.

