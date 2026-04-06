# 📦 Parcel On Map — Android parcel route visualization prototype

<table>
  <tr>
    <td valign="top" width="70%">
      <h2>About the project</h2>
      <p><strong>Parcel On Map</strong> is an Android pet project focused on visualizing parcel delivery routes on map.</p>
      <p>The current version displays a list of mock parcels and lets the user open a details screen where the route is shown on a map with markers and an animated polyline.</p>
      <p>This project brings together:</p>
      <ul>
        <li>modern Android development with <strong>Kotlin</strong> and <strong>Jetpack Compose</strong></li>
        <li>a <strong>multi-module project structure</strong></li>
        <li>simple <strong>ViewModel-driven UI state</strong></li>
        <li><strong>Navigation Compose</strong></li>
        <li>map-based UI with <strong>Google Maps Compose</strong></li>
      </ul>
      <p>At the moment, the project should be treated as a <strong>work-in-progress prototype</strong> rather than a finished production app.</p>
      <h2>✨ Current functionality</h2>
      <ul>
        <li>Parcel list screen with tracking number, status, last known city, and updated time</li>
        <li>Navigation from the list screen to the parcel route screen</li>
        <li>Parcel route map with markers and animated route drawing</li>
        <li>Fallback state for missing parcel data or empty route points</li>
        <li>Mock repository with several sample shipments</li>
        <li>Basic unit tests for route ordering and ViewModel mapping logic</li>
      </ul>
    </td>
    <td valign="top" width="30%">
      <h2>🎥 Demo</h2>
      <a>
        <img src="./docs/demo.gif" alt="Parcel On Map demo" width="100%">
      </a>
    </td>
  </tr>
</table>

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

## 📍 Roadmap / TODO

- [ ] Replace mock data with a real data source
- [ ] Improve loading, error, and refresh handling
- [ ] Improve map UX and camera fitting
- [ ] Add search, filtering, or grouping for parcels
- [ ] Introduce a proper DI solution
- [ ] Expand test coverage
- [ ] Clean up package naming and local configuration
- [ ] Polish the UI for a stronger demo experience

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
