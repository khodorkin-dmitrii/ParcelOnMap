# 📦 Parcel On Map — Android parcel route visualization prototype

<table>
  <tr>
    <td valign="top" width="55%">
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
    <td valign="top" width="45%">
      <h2>🎥 Demo</h2>
      <a>
        <img src="./docs/demo.gif" alt="Parcel On Map demo" width="100%">
      </a>
    </td>
  </tr>
  <tr>
    <td valign="top" width="50%">
      <h2>🧱 Project structure</h2>
      <p>The project currently consists of three modules:</p>
      <ul>
        <li><code>:app</code> — app entry point, navigation, dependency wiring, ViewModels</li>
        <li><code>:ui</code> — composables, theme, UI models</li>
        <li><code>:data</code> — parcel models, repository contracts, mock data implementation</li>
      </ul>
      <p>The architecture is intentionally lightweight for now and is based on:</p>
      <ul>
        <li>repository pattern</li>
        <li>manual dependency container</li>
        <li>ViewModel → UI state mapping</li>
        <li>single-activity Compose app structure</li>
      </ul>
    </td>
    <td valign="top" width="50%">
      <h2>🛠 Tech stack</h2>
      <ul>
        <li><strong>Kotlin</strong></li>
        <li><strong>Jetpack Compose</strong></li>
        <li><strong>Material 3</strong></li>
        <li><strong>Navigation Compose</strong></li>
        <li><strong>Android Lifecycle / ViewModel</strong></li>
        <li><strong>Google Maps Compose</strong></li>
        <li><strong>Google Play Services Maps</strong></li>
        <li><strong>JUnit4</strong></li>
        <li><strong>Gradle multi-module setup</strong></li>
      </ul>
    </td>
  </tr>
</table>

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
