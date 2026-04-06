# 📦 Parcel On Map — Android parcel route visualization prototype

## About the project

**Parcel On Map** is an Android pet project focused on visualizing parcel delivery routes on Google Maps.

The current version displays a list of mock parcels and lets the user open a details screen where the route is shown on a map with markers and an animated polyline.

I started this project as a practical way to explore and demonstrate:

- modern Android development with **Kotlin** and **Jetpack Compose**
- a **multi-module project structure**
- simple **ViewModel-driven UI state**
- **Navigation Compose**
- map-based UI with **Google Maps Compose**

At the moment, the project should be treated as a **work-in-progress prototype** rather than a finished production app.

## 🎥 Demo
https://drive.google.com/file/d/11dWPW8Tm1MQU5ZZqhvThxUhmkSqlDc4r/view?usp=sharing

## ✨ Current functionality

- Parcel list screen with tracking number, status, last known city, and updated time
- Navigation from the list screen to the parcel route screen
- Parcel route map with markers and animated route drawing
- Fallback state for missing parcel data or empty route points
- Mock repository with several sample shipments
- Basic unit tests for route ordering and ViewModel mapping logic

## 🛠 Tech stack

- **Kotlin**
- **Jetpack Compose**
- **Material 3**
- **Navigation Compose**
- **Android Lifecycle / ViewModel**
- **Google Maps Compose**
- **Google Play Services Maps**
- **JUnit4**
- **Gradle multi-module setup**

## 🧱 Project structure

The project currently consists of three modules:

- `:app` — app entry point, navigation, dependency wiring, ViewModels
- `:ui` — composables, theme, UI models
- `:data` — parcel models, repository contracts, mock data implementation

The architecture is intentionally lightweight for now and is based on:

- repository pattern
- manual dependency container
- ViewModel → UI state mapping
- single-activity Compose app structure

## 🚧 Project status

This project is **under active development**.

Right now it already demonstrates the core flow:

**parcel list → parcel details → route on map**

At the same time, it still uses **mock data only**, and several areas are intentionally kept simple while the foundation is being built.

Currently incomplete or planned for later:

- real backend integration
- persistence / local storage
- richer loading and error states
- improved map UX
- more advanced list interactions
- stronger test coverage
- package and config cleanup

## 📍 Roadmap / TODO

- [ ] Replace mock parcel data with a real data source
- [ ] Add loading, refresh, and error handling
- [ ] Improve map camera fitting and route presentation
- [ ] Add parcel search, filtering, or grouping
- [ ] Expand ViewModel and UI test coverage
- [ ] Rename generic package identifiers
- [ ] Improve local configuration and API key handling
- [ ] Polish visuals and interactions for a cleaner demo experience

## ▶️ How to run

### Requirements

- Android Studio
- JDK 21
- Android SDK
  - `minSdk = 24`
  - `compileSdk = 36`
  - `targetSdk = 36`

### Local setup

Add your Google Maps API key to `local.properties`:

```properties
MAPS_API_KEY=your_api_key_here
