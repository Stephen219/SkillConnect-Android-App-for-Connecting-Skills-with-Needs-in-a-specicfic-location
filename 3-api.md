**Mobile Development 2023/24 Portfolio**

# API description

Student ID: `22067364`

_Complete the information above and then write your 300-word API description here.__

The UI is designed with mostly ConstraintLayout as the foundational
element,
offering flexibility in accommodating various screen sizes and orientations. To efficiently display
lists of service providers, I use RecyclerView with customized adapters and layout managers.
This setup ensures smooth scrolling and a consistent user experience even with large datasets.
ScrollViews are used where longer content must be displayed, allowing users to navigate
effortlessly through extended information. rating bar was also under implemented to allow the user to rate the service provider.

Navigation is handled using Fragments combined with a bottom navigation bar. This approach
provides a smooth structure, allowing users to switch between different sections of the app, such as
home, favourites, and search without reloading entire activities. This choice
enhances responsiveness and makes the app easier to maintain. Toolbars are included in some of the
activities
to provide a consistent navigation experience and quick access to common actions.

For user authentication, I use Firebase Authentication due to its simplicity and security. For now i
adopted the use of email and password. however it can enable easy integration of other auth methods
such as google and facebook. To manage real-time data
synchronization, I utilize Firebase Realtime Database. This allows for instant data updates
across devices, ensuring that users receive the most current information on service providers and
other critical data.

To store user-specific data locally, such as a list of favorite service providers, I use Room.
Room provides an abstraction layer over SQLite, making it easier to manage local databases and
ensuring data persistence even when offline with minimal sql. This choice is appropriate for scenarios where quick
access to data is required without a constant network connection. To save on the user theme's
preference and their recent searches i used shared preferences making them available even after the app is closed.