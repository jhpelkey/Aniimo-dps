# Aniimo DPS — Public Community Companion App

**Aniimo DPS** is an Android community app for **Aniimo** featuring:
- **126 Canonical Aniimo Roster** (Breezy Plains & Whisperwake Isles) with verified dual typings, Prismana & regional forms, and Exhibition Wall Sparkle variants.
- **4-Aniimo Team Builder & DPS Simulator** with 1 Held Item per Aniimo, 6-Stat Awakening sliders (`ATK`, `BREAK`, `REGEN`, `HP`, `PDEF`, `MDEF`), and instant `ANIIMO-V4` share codes.
- **Speedrun Leaderboard** with custom runner search, future Alpha boss support, video proof links, and community Legit / Not Legit verification voting.
- **Community Discussion Board** with image & video showcase support for Prismana and Home builds.
- **Customizable Themes & Live Patch Notes** in Settings.

---

## 📲 How to Install on Android (From GitHub)

Anyone can install **Aniimo DPS** directly from this GitHub repository:

1. Look at the **Releases** section on the right-hand sidebar of this GitHub repository (or go to the **`/releases`** tab).
2. Under **Assets** on the latest release (**`Aniimo DPS v1.0`**), tap **`Aniimo-DPS-v1.0.apk`** to download it to your Android phone.
3. Open the downloaded **`Aniimo-DPS-v1.0.apk`** file on your phone and tap **Install**.

---

## 🚀 Automated GitHub Actions Pipeline

This repository includes an automated workflow (`.github/workflows/android-release.yml`) that:
1. Restores `debug.keystore` from `debug.keystore.base64` and generates a release signing key (`my-upload-key.jks`).
2. Installs **JDK 21**, **Gradle 9.3.1**, and **Android SDK 36**.
3. Builds both **`Aniimo-DPS-v1.0.apk`** (for direct Android installation) and **`Aniimo-DPS-v1.0.aab`** (for Google Play Store).
4. Automatically publishes **`Aniimo-DPS-v1.0.apk`** and **`Aniimo-DPS-v1.0.aab`** to the **GitHub Releases** page on every push to `main` / `master`.
