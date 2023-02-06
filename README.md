# Currencny Converter

✨ A Currency Converter with MVVM + Clean Architecture using [Fixer API](https://fixer.io/) ✨
The application is offline first. On Free API. It fetches currency data for first time and then gives users ability to pull new data on demand.
If the Fixer API had a paid plan, then a WorkManager would be scheduled to fetch new data at a fixed interval e.g "Pull fresh currency rates every 60 minutes: 


## Architecture

<img width="679" alt="Screenshot 2023-02-04 at 7 20 31 PM" src="https://user-images.githubusercontent.com/5016570/216902862-c60eeb23-8088-40ed-acc6-c74504717d83.png">

Uses concepts of the notorious Uncle Bob's architecture called [Clean Architecture](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html).</br>
* Better separation of concerns. Each module has a clear API., Feature related classes life in different modules and can't be referenced without explicit module dependency.
* Every layer is using their own model and a 'LayerObjectMapper' class is used to convert an object to its relative pattern 
* Usecases represents a UI use cases, communication between Repo and UI is done through it and maps repo object back to UI objects. 


## Tech stack - Library:
- [Kotlin](https://kotlinlang.org/)
- [Coroutines](https://github.com/Kotlin/kotlinx.coroutines) - A coroutine is a concurrency design pattern that you can use on Android to simplify code that executes asynchronously
- [Flow](https://kotlin.github.io/kotlinx.coroutines/kotlinx-coroutines-core/kotlinx.coroutines.flow/) - Flow is used to pass (send) a stream of data that can be computed asynchronously
- [Dagger-Hilt](https://developer.android.com/training/dependency-injection/hilt-android) - for dependency injection.
 - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - For reactive style programming (from VM to UI). 
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - Used to create room db and store the data.
  - [Navigation](https://developer.android.com/guide/navigation/navigation-getting-started) - Used to navigate between fragments
  - [Data Binding](https://developer.android.com/topic/libraries/data-binding) - Used to bind UI components in your XML layouts.
- [Retrofit](https://github.com/square/retrofit) - Used for REST api communication.
- [OkHttp](http://square.github.io/okhttp/) - HTTP client that's efficient by default: HTTP/2 support allows all requests to the same host to share a socket
- [Gson](https://github.com/square/moshi) - Used to convert Java Objects into their JSON representation and vice versa.
