I. Description
-----------------
This project is an Android mobile application for tracking fitness activities: driving, running, walking, etc.
IT uses gps sensor of the phone to measure that speed, distance, etc. during an activity.


II. XML File list
-----------------

activity_main.xml          contains the layout of the welcome page screen
loginpage.xml              contains the layout of the login screen
registration.xml           contains the layout of the registration page
menu.xml                   contains the layout of the menu page 
recordingmode.xml      	   contains the the layout of the running journey
searchjourney.xml          contains the the layout of the "search journey" screen
statsresult1.xml       	   contains the layout of the result of the search by category
statsresult2.xml           contains the layout of the result of the longest 10 journeys (time)
statsresult3.xml           contains the layout of the result of the longest 10 journeys (distance)
showmap.xml                contains the map fragment
row.xml                    contains the layout of the listview
database.xml               contains the database layout
AndroidManifest.xml        contains all the intents + Key + the configchange under "recordingmode" activity to stop the activity from restarting when the screen orientation changes.

III. Java File list
-----------------
MainActivity.java          contains the welcome page screen code
LoginPage.java             contains the login code
Registration.java          contains the registration code
Menu.java                  contains the menu code 
showMap.java               contains the map code to visualise start and end location
Recordingmode.java         contains the running journey details code
Searchjourney.java         contains the code of the "search journey" screen
Statsresult1.java          contains the code of the result of the search by category
Statsresult2.java          contains the code of the result of the longest 10 journeys (time)
Statsresult3.java          contains the code layout of the result of the longest 10 journeys (distance)
MyDBManager.java           contains the database manager
Database.java              contains the database screen code


IV. Execution
--------------
The app-release.apk is included in the app folder
-------------------------------------------------
A.  After running and entering the application using "Enter Application" button , the user will see the login page as follows:

----------Username and password edittext fields --

------------------Login button -----------------

----------------- Register Button --------------


The user is asked to enter a username and a password. If one of them is incorrect, an error message will be displayed in red color at the top of the screen.
If the user is not registered, he must click the "register button" and register his/her details as follows:
1. username
2. password
3. gender (male/female)
4. preference (speed units: km/miles) 
Then the user clicks "register" button and login with the saved details.

B.  . After clicking ---login --- The user views a menu as folows:

------------------Start Recording -----------------

----------------- View Journeys --------------
	
1. Under ---Start Recording --- 
	* The user views the journey details:

		- Select mode (walking, cycling, jogging, driving)
		- Start time
		- End time
		- Duration
		- start location
		- Current location		
		- End location
		- Maximum speed
		- Distance

----------------- Start Button ------------- to start journey
----------------- Stop Button -------------- to stop journey
----------------- Save Button -------------- to save journey

2. Under ---View Journeys --- 


	* The user can search for his journeys after choosing a particular category:

		- Search by category (All,walking, cycling, jogging, driving)
			* The journey map can be viewed under this section
		- Search 10 longest journeys (time)
		- Search 10 longest journeys (distance)




	