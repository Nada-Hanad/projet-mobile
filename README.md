# Food ordering app (Android, Kotlin)

A native Android app for ordering from restaurants, built in 2023 by a team of two. Users go through an intro slider, sign in (email or Google), browse restaurants and their menus, fill a cart, place orders, follow them in an orders list, and get push notifications when an order changes.

## Architecture

- **Kotlin**, single-activity navigation with fragments (Navigation component).
- **MVVM for authentication:** `LoginViewModel`, `LoginRepository`, `LoginDataSource`, `LoginFormState` and `LoginResult`, with LiveData driving the form.
- **Networking** with Retrofit and Gson against a REST API (`EndPoint.kt`); images with Picasso and `androidsvg`.
- **Firebase:** Authentication (including Google sign-in through Play Services) and Cloud Messaging (`MyFirebaseMessagingService`) for order notifications.
- RecyclerView adapters for restaurants, orders and notifications; ViewPager2 for the intro slider.

## Screens

Intro slider, login and registration, home, restaurant page, cart, all orders, notifications, profile.

## Running it

Open the project in Android Studio and run the `app` configuration. Firebase features need your own `google-services.json`.

## Team

[Nada Hanad](https://github.com/Nada-Hanad) (7 commits) and [hasnizmt13](https://github.com/hasnizmt13) (4).
