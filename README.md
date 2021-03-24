### Description:

The project uses MusicBrainz API https://musicbrainz.org/ and allows the user to see information 
about artists and their albums. The Home Screen contains a Search field where the user can enter 
(part of) an artist's name and see the search results on a list. Clicking on a list item opens a 
Details Screen where the user can see information for the selected Artist and their albums.

### Technologies:

The project is written in Kotlin with Clean Architecture + MVVM and uses JetPack 
i.e. Navigation, Coroutines, LiveData, ViewModel. Also some libraries have been used
i.e. Retrofit, Gson, Dagger, Mockk, Espresso.

### Tests:

The project contains Unit Tests and Instrumented Tests.

### Screenshots:

#### Home
![Alt text](screenshots/search-screen-1.png?raw=true "app screenshot")

![Alt text](screenshots/search-screen-2.png?raw=true "app screenshot")

#### Details
![Alt text](screenshots/details-screen-1.png?raw=true "app screenshot")

![Alt text](screenshots/details-screen-2.png?raw=true "app screenshot")