# Classification of Calls as Scams

Algorithm to determine the likelihood of a call being a scam, or legitimate.

# Setup Instructions:

To run the Java programs, set up the JDK and JRE on Windows 10 using the following steps. (Or refer to https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_Howto.html)

1. Since having multiple versions of Java can be messy, if you have previously installed older version(s) of JDK/JRE, un-install ALL of them. Goto "Control Panel" ⇒ "Programs" ⇒ "Programs and Features" ⇒ Un-install ALL programs begin with "Java", such as "Java SE Development Kit ...", "Java SE Runtime ...", "Java X Update ...", and etc.
2. Goto Java SE download site @ http://www.oracle.com/technetwork/java/javase/downloads/index.html.
3. Click on the download button under JDK in the Java Platform, Standard Edition section for the latest version.
4. Accept the License Agreement and download the exe for Windows.
5. Run the downloaded installer and use the default settings during installation.
6. Using the File Explorer, goto "C:\Program Files\Java" and note the name of the JDK directory.
7. Launch "Control Panel" ⇒ System and Security ⇒ System ⇒ Click "Advanced system settings" on the left pane.
8. Switch to "Advanced" tab ⇒ Push "Environment Variables" button.
9. Under "System Variables" (the bottom pane), scroll down to select "Path" ⇒ Click "Edit...".
10. You shall see a TABLE listing all the existing PATH entries. Click "New" ⇒ Enter the JDK's "bin" directory "c:\Program Files\Java\jdk-x.y.z\bin" (Replace x,y,z with your installation number) ⇒ Select "Move Up" to move this entry all the way to the TOP.

To verify that the above steps were successful,

1. Open command prompt.
2. Type path and check if "c:\Program Files\Java\jdk-x.y.z\bin" is at the beginning of the result.
3. Type java -version to see if JRE was installed correctly.
4. Type javac -version to see if JDK was installed correctly.

# Method of Execution:

  To test whether scam likelihood is being printed correctly and to view significant words extracted from a transcript, follow these steps:
  1. Open the command prompt. 
  1. Traverse to the scams/classify/javaprograms/ folder in the local copy of the repo.
  1. Type javac ProcessNoJson.java
  1. Type java ProcessNoJson

# Current Work:
  The current algorithm focuses on statistical processing of call transcript.

The get_scam_likelihood function takes a transcript and a space separated list of scam-indicating-keywords as String inputs.

1. It removes any numeric value present in the transcript, as a primitive redaction method to clear out personal information such as bank account numbers, etc.
1. Next, excluding common words, the program counts the number of words in the transcript that are indicative a scam. Words indicative of a scam are keywords read from the database, written into by the python module described later.
1. Finally, the percentage of 'suspicious' words present in the transcript is printed.

For testing, the get_scam_likelihood function is called from the main function using a test transcript and a basic keyword list.

The extract_info function takes a transcript and a flag indicating whether the call was finally classified as a scam as inputs.

1. It removes any numeric value present in the transcript, as a primitive redaction method to clear out personal information such as bank account numbers, etc.
1. Next, excluding common words, the program collects the remaining words in the transcript so that they can be given as input, along with scam flag to the python module described later.

For testing, the extract_info function is called from the main function using a test transcript.

These functions and their helper functions are included in the client-side code in scams/tree/develop/client/app/src/main/java/edu/cmu/eps/scams/classify/ClassifyFacade.java. The scam likelihood is calculated whenever a call is recorded and transcribed. The percentage returned is used to determine the action to be taken (ignore, alert reviewer, drop call).

Once a day, call data (extracted words along with whether the call was classified as a scam or not) for all calls in all the clients are collected by the python module to be consolidated to create an updated keyword list. The python module then writes this keyword list to the database. This is the keyword list queried every time get_scam_likelihood is called. Before any call data is consolidated, the initial keyword list is created based on threat profile research done from a number of sources including Phone Scam Information posted on the FTC's website.

This python module is still being tested and modified, but is based on process_real_time.py, which was demonstrated in the demo of the last prototype version.

# Assumptions and Notes:
  The calls to functions in ClassifyFacade has not been tested during an actual call yet. However, to observe and test the actual classification and keyword extraction, the ProcessNoJson program can be used, giving different sample transcripts as input.

# Results and Conclusions:
  The classifier spits out a higher percentage when the call transcript is shorter (or when analyzing the first 20 seconds of the call rather than the whole call). This is due to the fact that our keyword bank is primitive, and needs to be expanded and weightages for each keyword has not been assigned yet.
  
# Future Work:
  In basic classifier:
 1. Redaction can be improved by removing name of primary as well as names present in the primary's phonebook.
 1. The blacklist database can be updated by adding the phone numbers from which scam calls are made.
 1. The confidence of speech-to-text translator can be taken into consideration for classification (in case of robocalls or calls from those who mask their voice, confidence of the translator may be different).
 1. Take into account the format of the phone number from which call is made. Eg: Often if your number is (555) 867-5309, then scammers call you from a number that looks like (555) 867-####.
