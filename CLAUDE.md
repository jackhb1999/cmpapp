# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Build System

This project uses **amper** (JetBrains' declarative build tool), not Gradle. The root `project.yaml` declares all modules.

- Module descriptors live in `module.yaml` files in each module root.
- Dependency catalog: `gradle/libs.versions.toml` (referenced as `$libs.*` in module.yaml).
- Amper wrapper scripts: `.\amper.bat` (Windows) or `./amper` (Unix).

### Common commands

```shell
# Run JVM desktop app (with hot reload)
.\amper.bat run --compose-hot-reload-mode --module jvm-app

# Run JVM desktop app (without hot reload)
.\amper.bat run --module jvm-app

# Run server
.\amper.bat run --module server

# Run Android app (requires connected device/emulator)
.\amper.bat run --module android-app
```

### Testing

```shell
# Run all tests in common module
.\amper.bat test --module common

# Run all tests in shared module
.\amper.bat test --module shared
```

JUnit 5 is used for `common` and `shared` tests. Test source sets use platform-specific directories: `common/test@jvm`, `shared/test@jvm`, `shared/test@android`.

## Module Architecture

```
common            — Shared data models, repository interfaces, RPC service interfaces. Platform: lib (jvm+android).
  ├── model/      — Serializable data classes (Post, Auth, Profile, etc.)
  ├── repository/ — Interfaces (UserRepository, PostRepository, etc.) and model types
  ├── service/    — @Rpc-annotated interfaces for kRPC (AwesomeService)
  └── util/       — IdGenerator, Result sealed class, Constants

shared            — Compose Multiplatform UI and client logic. Platform: lib (jvm+android).
  ├── screen/     — Voyager Screen classes (one per route)
  ├── view/       — Composable view functions
  ├── viewmodel/  — ViewModels (extends androidx ViewModel, provided by Koin)
  ├── usecase/    — UseCase classes called by ViewModels
  ├── tab/        — Voyager Tab-based navigation (HomeTab, ProfileTab, SettingTab)
  ├── service/    — REST/Ktor API service classes (AuthService, PostApiService, FollowsApiService)
  ├── repository/ — Client-side repository implementations
  ├── common/     — KtorApi (REST client base), KtorRpc (WebSocket RPC client base)
  ├── di/         — Koin modules (authModule, otherModule, platformModule)
  ├── data/       — UserPreferences storage interface
  ├── components/ — Reusable Compose components
  └── util/       — DispatcherProvider, PagingManage, Setting

server            — Ktor server (Netty), JVM only.
  ├── route/      — Route definitions (AuthRoute, PostRoute, RpcRoute, etc.)
  ├── dao/        — Exposed ORM DAOs (UserDao, PostDao, etc.), table definitions, DatabaseFactory
  ├── repository/ — Server-side repository implementations
  ├── di/         — Koin AppModule
  ├── plugins/    — Ktor plugin config (Serialization, Security/JWT, kRPC, Routing)
  ├── model/      — Server-specific models (Auth, DeptTable)
  ├── security/   — HashingService (HMAC-SHA1 password hashing)
  └── util/       — Call extensions, file utils

jvm-app            — Desktop entry point. Thin module: calls getDI() then getMain() from shared.
android-app        — Android entry point. Uses AppCompatActivity + setContent calling getDI()/getMain().

rs_lib             — Rust native library. Compiled via Cargo, JNI bindings via UniFFI.
                     The rs_build amper plugin auto-copies compiled .dll/.so into resources.
                     Depended on by jvm-app (not wired into android-app yet).
rs_build           — Custom amper plugin that copies Rust build outputs into resources.
```

## Key Architecture Decisions

### Navigation
**Voyager** for navigation. Screens implement `cafe.adriel.voyager.core.screen.Screen`. The entry point in both `World.kt` (android/jvm) wraps `Navigator(HomeScreen())` with `SlideTransition`. Tabs use `TabNavigator` with `HomeTab`, `ProfileTab`, `SettingTab`.

### Dependency Injection
**Koin** throughout. The shared module defines `authModule` (auth, use cases, repos), `otherModule` (remaining ViewModels), `platformModule` (expect/actual per platform), and `getSharedModules()` combines them. Server uses `appModule` with its own DI config. Both Android and JVM desktop call `getDI()` which calls `startKoin { modules(authModule + otherModule + getSharedModules()) }`.

### Communication: Dual protocol
1. **REST** — `KtorApi` base class (`shared/src/common/KtorApi.kt`) provides an `HttpClient` with JSON content negotiation. Service classes like `AuthService` extend it and call `endPoint(path)`.
2. **kRPC (WebSocket)** — `KtorRpc` base class (`shared/src/common/KtorRpc.kt`) connects via WebSocket to `ws://localhost:8088/rpc`. Interfaces in `common/src/service/` annotated with `@Rpc` define the contract. Server registers these services in `RpcRoute.kt`.

### Database (server)
**PostgreSQL** accessed via **Exposed ORM** (v1.0.0) with **HikariCP** connection pooling. `DatabaseFactory.init()` creates tables if they don't exist (uses the old Exposed `v1.jdbc` API). DAOs follow `interface Dao` / `class DaoImpl` pattern. Every DAO is a Koin singleton.

### State Management
MVVM with Jetpack ViewModel (via `androidx.lifecycle.viewmodel.compose`). ViewModels expose coroutine-backed state; Screens observe it. UseCases encapsulate business logic between ViewModels and Repositories. Each tab screen (HomeTab, ProfileTab, etc.) has its own Screen → View with callbacks → ViewModel → UseCase chain.

### Shared UI Pattern
Screen composables typically delegate to a `*View` composable with callback functions (e.g., `HomeView(goPostDetail, goProfileClick)`). This keeps Screens thin and Views testable.

### Platform-specific code
Uses Kotlin `expect`/`actual` via source set directories:
- `shared/src` — common code
- `shared/src@jvm` — JVM actual implementations (desktop)
- `shared/src@android` — Android actual implementations

Key expect functions in `shared/src/World.kt`:
- `expect fun getDI()` — initializes Koin
- `@Composable expect fun getMain()` — root composable
- `expect fun getWorld(): String` — diagnostic

### Paging
Custom `PagingManage` interface in `shared/src/util/PagingManage.kt` (not the AndroidX Paging library). `DefaultPagingManage` implements page-by-page loading with load/reset semantics, wrapping `onRequest` with page tracking and loading-state callbacks.
