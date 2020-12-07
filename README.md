# TI-Team 5-Carbon Vision App

## Mobile Application to determine Carbon Footprint(Carbon Emission)
To overcome the challenge of global climate change and its consequences, we need to take part in it and should be aware of what role we are playing in it.
Carbon emission is the main reason for increasing global warming. The more percent of the carbon dioxide comes from the vehicles we daily use for transportation.
Providing an application to determine the carbon footprint (carbon emission) of the vehicle used by the individual and let them keep a track of it. 
It will be integrated with Google maps that would help the user to keep track of the routes and carbon emission. 
It will also provide an analysis of the carbon footprint for the routes followed by the user over a period. 
It will also provide the user with the alternate option to switch over to our clean source of transportation services.

## Application(.apk) file

<img src="/screenshots/logo.png" alt="alt text" width="100px" height="100px">
<a id="raw-url" href="https://git.cs.dal.ca/mayank/ti-team-5-carbon-vision-app/-/raw/master/Carbon-Vision.apk">Download FILE</a>

## Target Audience:
We are planning to target the people having their vehicle and transportation industry (Cabs Services, courier Service, etc.) in Nova Scotia first and then slowly transition to the other provinces in Canada,
targeting people age between 14-60 years old of any gender. 
We are thinking to target the people who are more concerned about climate change and the global cause. 
Students of environmental studies. People who are willing to be responsible for climate changes

## Libraries:

Firebase database and auth library: This library is used to store the items available in the restaurants. Data from the firebase is retrieved and displayed to the user.
Google play services maps: This library is used to detect the user's device location.
Bumptech library: This library is used to show the routes of user
Dexter library: This library is used to ask permission from the user to access the device location.

## Installation Notes:
- Install Android Studio from https://developer.android.com/studio/install
- Download or clone(https://git.cs.dal.ca/mayank/ti-team-5-carbon-vision-app.git) our repository from the Gitlab.
- Select open existing project option in android studio
- Select our directory to open the project
- Provide Kotlin SDK(version>=1.3.50) to run the application
- Set the compileSdkVersion 29, minSdkVersion 24, targetSdkVersion 29
- While running on the emulator run on a device with API level 26 or higher.


## Functionalities 
Our application boasts some basic features such as:
- Calculating carbon footprint 
- Interactive dashboard
- Notifications
- Integrated with Google Maps API
- Tips to reduce their carbon footprint
- Periodic information/visualization of the carbon emission



## Application Flow

### Splash Screen - Launch Screen 

<img src="/screenshots/home.png" alt="alt text" width="160px" height="270px">


### Login Screen - Login with google account

##### User would be redirected to login screen where user would be asked to login with their Google account

<img src="/screenshots/login.png" alt="alt text" width="160px" height="270px">


### Add Vehicle Screen - Login with google account

##### Once User login, for the first time user, application would ask user to provide in their Vehcile details


<img src="/screenshots/vehicledetails.png" alt="alt text" width="160px" height="270px">



### Dashboard Screen - Login with google account

##### After providing vehicle details, user would be redirected to dashboard

<img src="/screenshots/dashboard.png" alt="alt text" width="160px" height="270px">


### Travel History Screen - Login with google account

##### Second tab enable user to see travel/trips history of user. 

<img src="/screenshots/triphistory1.png" alt="alt text" width="160px" height="270px">

##### It also provide user to add trips manually 

<img src="/screenshots/addtrip.png" alt="alt text" width="160px" height="270px">


### Profile Management Screen - Manage USer profile

<img src="/screenshots/profile.png" alt="alt text" width="160px" height="270px">

##### User are allowed to edit provide vehicle details

<img src="/screenshots/editvehicle1.png" alt="alt text" width="160px" height="270px">



## References:
- "Udacity", Classroom.udacity.com, 2020. [Online]. Available: https://classroom.udacity.com/courses/ud9012. [Accessed: 28- Nov- 2020].
- "SearchView  |  Android Developers", Android Developers, 2020. [Online]. Available: https://developer.android.com/reference/android/widget/SearchView. [Accessed: 21- Nov- 2020].
- "10 Heuristics for User Interface Design: Article by Jakob Nielsen", Nielsen Norman Group, 2020. [Online]. Available: https://www.nngroup.com/articles/ten-usability-heuristics/. [Accessed: 02- Apr- 2020].
- "Karumi/Dexter", GitHub, 2020. [Online]. Available: https://github.com/Karumi/Dexter. [Accessed: 03- Dec- 2020].
- J. Joseph, "How to pass values from RecycleAdapter to MainActivity or Other Activities", Stack Overflow, 2020. [Online]. Available: https://stackoverflow.com/questions/35008860/how-to-pass-values-from-recycleadapter-to-mainactivity-or-other-activities. [Accessed: 03- Dec- 2020].
- "Coroutines With Room Persistence Library", raywenderlich.com, 2020. [Online]. Available: https://www.raywenderlich.com/7414647-coroutines-with-room-persistence-library. [Accessed: 03- Apr- 2020].
- A. Pervaiz, "Add a Back Button to Action Bar Android Studio (Kotlin)", Devofandroid.blogspot.com, 2020. [Online]. Available: https://devofandroid.blogspot.com/2018/03/add-back-button-to-action-bar-android.html. [Accessed: 03- Dec- 2020].
- "Bottom Navigation - Material Components for Android", Material Components for Android, 2020. [Online]. Available: https://material.io/develop/android/components/bottom-navigation-view/. [Accessed: 03- Dec- 2020].
