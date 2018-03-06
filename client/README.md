# Client

Android client application

# Development Environment

1. Download and install Android Studio

https://developer.android.com/studio/index.html

2. Open the project folder called 'client' under 'scam' folder from Android Studio 

File -> Open -> 'client' directory from local copy of repository

3. Click the green arrow ('Run') on the top of the toolbar. This will begin running the application on an emulated Android device. We use a 'Nexus 5X' device running 'Android API 25'. 

Once the emulated device is opened, you can walkthough the various UI elements. Additionally, the emulator allows one to simulate a phone call though:

1. Click '...' button next to Android device
2. Click 'Phone' tab
3. Click 'Call Device'

The recording and transcription is still in progress, but our code identifies the call and begins recording. The recording does not function on the emulated device due to limitations with the emulator. Events appear in the 'Logging' window within Android studio. ('View' -> 'Tool Windows' -> 'Logcat')


##Functions need to be completed:
1. Separate UIs for primary users and reviewers (We will use a initial page to let users to choose whether to be a primary user or a reviewer)
2. QR code generation and camera access to scan QR code
3. The history data need to connect to backend database, and retrieve data from there
4. Send requests to server when a call happens
5. Design a form to let users report to FTC
6. Complete the lifecycle for each activity and fragment


