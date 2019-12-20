# DEVELOPMENT GUIDE

The project is being developed entirely in Kotlin [https://kotlinlang.org/](https://kotlinlang.org/) under Android Studio 3.6 RC-1. This version or more recent ones are needed as  [viewbinding](https://developer.android.com/topic/libraries/view-binding) is being used.

The project is currently using the [MVP pattern](https://www.journaldev.com/14886/android-mvp) to organize its structure.

Room is being used for the database, and an Entity-Relationship scheme can be found at [https://github.com/LivingWithHippos/Keter-Escape/tree/master/UML](https://github.com/LivingWithHippos/Keter-Escape/tree/master/UML), editable with [Umlet](https://www.umlet.com/).

[RxJava 2.x](https://github.com/ReactiveX/RxJava/tree/2.x) is used for notifying data updates around the application.

Dependency injection is managed with [KOIN](https://insert-koin.io/).

Navigation between fragments and activities can be useful for comprehending the structure of the project (see navigation and safe args [here](https://developer.android.com/jetpack/androidx/releases/navigation)). Navigation files are under the [res/navigation](https://github.com/LivingWithHippos/Keter-Escape/tree/master/Android/app/src/main/res/navigation) folder of the source code. 

[Ktlint](https://ktlint.github.io/) has been added with [spotless](https://github.com/diffplug/spotless) as a Kotlin lint checker. From Android Studio, go to terminal -> "gradlew spotlessCheck" to run the lint tests or "gradlew spotlessApply" to apply the suggestions.

Tests still have to be implemented at present.

Please note that this project was created to learn more about Android programming and Kotlin. As such, it may use some overkill strategies or mix different technologies and patterns.
