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

For testing, the get_scam_likelihood function is called from the main function using a test transcript and a basic keyword list derived from threat profile research done from a number of sources including Phone Scam Information posted on the FTC's website.
  
  We also have a real time analysis module that is mostly functional. It attempts to mimic an incoming stream of call information from multiple users of the app using the statistical process above by creating multiple worker threads that each process different transcripts at random intervals and report detection of high risk keywords found over time. Currently we are using publically available SMS text message spam and non spam data sets as our test data for this module, so the reported percentages are not indicative of the success of our statistical analysis. However, using this information, we can test the overall functionality of the analysis tool that a professional 3rd party reviewer (potentially representing an insurance company or bank) might be using to analyze large amounts of user data and detect trends in phishing schemes, etc. This tool is available via the process_real_time.py script, which can simply be run as-is with a python interpreter.

Describe python module here!

that were created based on threat profile research done from a number of sources including Phone Scam Information posted on the FTC's website as well the python module

# Assumptions and Notes:
  As integration between the Java modules and the classifier could not be set up successfully for this checkpoint, we are testing the classifier against test transcripts of real scams found online, as well as our own transcripts - both malicious and legitimate.

# Results and Conclusions:
  The classifier spits out a higher percentage when the call transcript is shorter (or when analyzing the first 20 seconds of the call rather than the whole call). This is due to the fact that our keyword bank is primitive, and needs to be expanded and weightages for each keyword has not been assigned yet.
  
# Future Work:
1. Rather than using this statistical processor, we intend to use a logistic regression algorithm to assign weights to keywords. 
2. We intend to update the basic keyword bank by adding keywords to it based on scam alerts received via email from FTC. We will also implement a retention policy to remove keywords based on the freshness of the news.
3. We need to fix communication issues with the server, in order to store the keywords and blacklisted numbers on the database, along with passing the transcript as input and results of classification as output.
4. We intend to improve redaction techniques if possible, to limit the amount of personal information retained in transcripts, so the primary's privacy can be protected from the reviewer.
