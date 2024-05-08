**Mobile Development 2023/24 Portfolio**

# Requirements

Student ID: `22067364`

_Complete the information above and then enumerate your functional and non functional requirements
below.__

## Functional Requirements

- The application must allow users to register, log in, and log out securely.
  The application must synchronize data across different devices implying need of a cloud storage.
- Users can optionally add their skills to the app for listing in the service feed.
- the app should request permission to access the user's location to provide location-based results.
  however, for now we will have to make the user input a location
- Users can view a feed of people with skills. this will be the main page of the app displayed in a
  card containing location skill, name, rate bar and share and favourite buttons. this should be in a
  recyclerview. the recyclerview will have a swipe to refresh feature.
- Users can click on each individual to see detailed skills and contact details
- Users can search for services based on location preferences and skills and receive correct
  results.
- 
- user can edit their profile and view it
- users can view all their recent searches in a recyclerview below the search inputs
- Users can add a service provider to their favourites list by clicking the favourite button on the
  card. this will be stored in a room database and displayed in a recyclerview on the favourites
  fragment. the user can also remove them from favourites.
- the user can access their favourites when offline, i will use room database for this.
- the user can share the service provider's contact details by clicking the share button on the
  card. this will open a dialog with options to share via email, sms or other apps.
- the user can click on the phone number to call the service provider
- the user can click on the email to send an email to the service provider and the email app will
  open with the service provider's email, and basic info pre-filled
- user see a general rating of the user in the card and profile click on at and add a rating and a dialog box for review to appear. later we can add a review system to enable the
  user to rate the service provider
- The user can change the settings of the app to enable dark mode or light mode depending on their
  preference
-
    - later i will enable in app communication between the user and the service provider
-
    - later i will enable the user to rate the service provider and leave a review

## Non-Functional Requirements

- The app must ensure user data is stored securely, with proper authentication and encryption where
  required.
- when the user logs in the app should remember the user and not log out unless the user logs out
- the app must be stable and resilient to errors, with proper error handling and crash recovery
  mechanisms. this will be included in data fetching , proper notification of the user such as when
  the internet is not accessible and user has deleted account 
- The user interface should be intuitive and easy to navigate through the various components and activities hence providing excellent user experience
- The application must have consistent styling and colour themes throughout the app
- later i will enable the app to  adapt to various screen sizes and orientations but for now potrait mode will be used
- I should ensure that the code of the app is well documented and easy to understand for future extension and revisions, especially on code commenting

