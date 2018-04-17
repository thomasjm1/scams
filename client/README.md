# Client

Android client application

# Development Environment

1. Download and install Android Studio

https://developer.android.com/studio/index.html

2. Open the project folder called 'client' under 'scam' folder from Android Studio 

File -> New ->Import Project -> 'client' directory from local copy of repository

Wait while it syncs and processes. If any errors arise regarding whitespace just ignore or click OK.

Click "Install missing platforms if necessary". This may be slow.

3. Click the green arrow ('Run') on the top of the toolbar. This will begin running the application on an emulated Android device. We use a 'Nexus 5X' device running 'Android API 27'. It is best to create a new Android virtual device to provide a clean testing environment. 

Dismiss any "cold boot" messages on the emulated device.
If any "System UI not responding" messages areise on the emulated  device, click "Wait". This from the compiler taking too long.

Once the emulated device is opened, you can walkthough the various UI elements. First, the user must sleect the type of user:

* Reviewer
* Primary User
* Clear (for debugging purposes only!)

After selecting a user type, the app should navigate to the main activity where the user must accept the permissions.

From the main activity the user can view the history, add reviewers, and view settings.

Additionally, the emulator allows one to simulate a phone call though:

1. Click '...' button next to Android device
2. Click 'Phone' tab
3. Click 'Call Device'

The recording functionality should work and the transcription functionality will fail due to an expired token (usually we update the token when developing, eventually the token will be retrieved from the server).


##Functions need to be completed:
1. Separate UIs for primary users and reviewers (We will use a initial page to let users to choose whether to be a primary user or a reviewer)
2. QR code generation and camera access to scan QR code
3. The history data need to connect to backend database, and retrieve data from there
4. Send requests to server when a call happens
5. Design a form to let users report to FTC
6. Complete the lifecycle for each activity and fragment


