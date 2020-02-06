# TODO FOR SCP - KETER ESCAPE

These are related to the Android app.

##### Low Priority:

- [x] Add [Klint](https://github.com/pinterest/ktlint) with [Spotless](https://github.com/diffplug/spotless)
- [ ] Add animation for gradient in main screen
- [ ] Add themes (for example one for every GOI, O5 etc.)
- [ ] add bottom banner displaying "news" in the Home Fragment

#### Medium Priority:

- [ ] Online multiplayer

- [ ] local (bluetooth) multiplayer

- [ ] Add "Flavour" to the app

- [ ] Add custom font

- [x] Add SCP 2293 (see about screen)

- [ ] Add SCP 1471 (see settings screen), maybe show it sometimes on fragment change animation

- [x] rename db columns (with style var_name)

- [ ] add toString to classes for easier debugging

- [x] change package and app name (Keter Escape? Broken Day?)

- [ ] Add api to retrieve SCPs: [Wikidot Api](http://developer.wikidot.com/doc:api), [Java Api](https://github.com/shane-smith/Wikidot-API-Open-Source)

- [ ] replace constructor parameter assignation (it's done automatically)

- [ ] replace clicklistener passed around with lambdas (see CreatePlayerDialogFragment.NewPlayerDialogListener)

- [ ] add exception to logs -> Log.d(TAG, "Activity description", exception)

- [x] change rxjava to livedata in the ui side

- [ ] decide if the mvp implementation of basegame makes sense

- [ ] add PK and FK annotations to UMLs

- [ ] use kotlin scope functions

- [ ] with KOIN 2.1.0 add KoinApplication and KoinFragmentFactory

#### High Priority

- [ ] Tutorial

- [ ] Mini wiki for the powers, scp, roles etc

- [ ] Add documentation to code

- [ ] Clean code from generated lines

- [ ] add credits page

- [ ] Change flowable to Observable or Single where applicable

- [ ] Unsubscribe/Dispose rxjava when not needed

- [ ] add base interface to presenter to dispose of view when it's destroyed

- [ ] add null? to all the return types of the db that could be nullable in the DAOs
