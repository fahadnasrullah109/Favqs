
# Favqs Android App

MVVM - Clean architecture in Kotlin.

# Features:
- Login
- Register
- Logout
- View Random Quote
- View List of Quotes
- View Quote detail
- Search quotes by querystring
- Unit Testing (Db, ViewModel, Usecase, Repository)
- NDK, hide Base url and token in Cpp file

## Components:
- Navigation Component
- Retrofit (Networking)
- Room (Offline - Cache)
- ViewModel
- Usecases 
- Repository
- View Binding
- Dagger Hilt


### Usage:
- App will look for logged in user from database
- If no user found, then shows Login screen
- If logged in user found, then will show HomeScreen with Tabs
- Home Tab, contains Random Quote (First check from db and how, if not found, then fetch from server and show)
- List Tab, fetch quotes from server having search functionality
- When tap on any Quote, will show the Quote detail page.


# Favqs
