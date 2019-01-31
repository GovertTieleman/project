# NutriConscious

This app allows users to keep track of their calorie intake throughout their day, by logging the foods they eat.
{SS}
## Overview of app
### MainActivity
This is the app’s homescreen. Here, users can see statistics about their calorie intake across the span of a week. They can see the total calories they had in the week, their daily average and also a pie chart with the ratio of macro nutrients they consumed. The week that is displayed can be changed to the previous or next week. Users can also quickly add foods to their diary by clicking on the floating action button (FAB). Foods added in the main screen will always be added to the current date in the diary. 

At the bottom there is a navigation bar with 3 buttons, this bar is present in MainActivity, ProfileActivity and DiaryActivity. It allows users to easily switch between these 3 activities.
### PlanActivity
This is where users end up when they first install the app. If no user exists in the database, MainActivity will send users here to enter their information. In order to prevent overwhelming users, they are asked to enter their information in easy steps. If they make a mistake or want to go back and change something they entered, they can do so. Once finished, the user will be sent to MainActivity where they can start using the app.
### ProfileActivity
Here, users can adjust the information they entered, should they want to. The activity makes uses of custom dialog boxes to update information in a very user-friendly way. At the bottom, their daily calorie goal is displayed based on their information.
### DiaryActivity
In this activity users will see a list of the foods they consumed for the selected date, by default this will be today. They can see the amount of calories they have left on the day and are able to scroll through dates or pick a date from the date picker at the top.

The FAB sends users to SearchActvity, where they can enter foods into the diary. Foods added through this FAB are added to the current date displayed on the screen. Foods can be clicked or long clicked. This allows users to see and adjust foods details or delete foods, respectively.
### SearchActivity
Using the hacker version of the Nutritionix API, SearchActivity sends JSON requests based on a user’s search query. The results are then displayed in a ListView, showing some details about the foods. When clicked, more details are shown in DetailActivity.
### DetailActivity
Here, users can see details about the food that they clicked on, and they can change the amount of it that they want to add to their diary. The FAB works as a confirm button and sends users back to either Diary or Main, depending on where it was from that they accessed Search.
## A more detailed look
### Classes
There are 4 classes: 
1,	User
Used to keep track of user data, sends and receives user objects to and from the user database.
There is a method to calculate the daily calories the user should eat, based on their information, as well as a method to create the user object by passing a cursor.
2.	FoodItem 
Can hold all the detailed information about a food, including its database id (for updating and deleting foods). 
There is a function to make a date string and a function to create an instance of the object by passing a cursor.
3.	FoodItemSimple
Simpler version of FoodItem, used in SearchActivity. Aside from some basic nutritional information, also includes the food’s API id. This can be used to get the food’s details when the class instance is passed to DetailActivity.
4.	Nutrient
This class is used in order to fill the nutrient ListView inside DetailActivity. 
### Databases
2 databases are used in the app: user and food. These are created in separate classes, with the appropriate methods, such as selecting, updating and deleting. 
The user database will only ever hold 1 user, because of the way the app is designed. 
The foods database is where all the foods inside the user’s diary are stored. Each food has a date and a unique ID.
### Requests
There are 2 request classes: search and detail. 
The search request uses the search function of Nutritionix API, whereas the detail request uses the item function of Nutritionix.
### Adapters
The app uses a number of adapters with custom layouts in order to fill ListViews.
### Dialogs
Regular dialogs are used in DiaryActivity, whereas custom dialogs with separate layout files are used for ProfileActivity. 

### Navigation
An attempt was made to make navigation as smooth and intuitive as possible, with no trailing activities being left open. Wherever they are within the app, when pressing the back button repeatedly the user will eventually find themselves in MainActivity. When pressed again, the app will then close.  Also, a bottom navigation bar was added to the three main activities in the app. This bar works with image buttons, using intuitive icons. Onclick methods are used to send users where they need to go. 
Aside from this, the app mainly uses FABs with onclick methods and list items with onClickListeners to send users to their desired destination. 
### setViews
setViews is a method that is used in multiple activities. Usually called after a user action, such as changing the date in their diary, or updating the information in their profile, this function simply updates the relevant views inside the activity. This method is also called when receiving data from a JSONrequest.
### MainActivity
Aside from the more general functions that have been discussed above, MainActivity contains the following functions:
1.	checkForUser
o	called in onCreate and returns true if a user exists and false if not. Constructs the user instance if it does exist. If no user exists, the false boolean will send the user to PlanActivity.
2.	getDaysOfWeek
o	Returns and ArrayList of date strings, Monday through Sunday for the week that the user wants to see. 
3. getMacroPercentages
o	Returns a list of floats with the percentage of each macro nutrient in the current week. 
4.	addDataSet
o	Method used to add data to the pie chart.
### DiaryActivity
Most functions are relatively straightforward, and do as their name suggests. Users can navigate dates by clicking the next or previous buttons, which call their respective functions. Another way to pick dates is through the date picker, which calls the pickDate function, which then creates a custom dialog from a layout file. Aside from that, there are long and short click listeners for the ListView. 
Something should be said about the use of intent, mainly in the diary, search and detail activities. The date and dateOffset are saved and sent along, starting and returning at DiaryActivity, in order to save the foods to the correct date, as well as showing the right day in the diary once the users returns there after adding a food in DetailActivity. 
A source string is also added to the intent. This lets DetailActivity know whether users are coming from search or diary. This is important for loading the views in the correct way, because when coming from diary the foods are loaded from the database, whereas when coming from search the foods are loaded through the API.
### PlanActivity
The most prominent part of this activity is made up by two large switch statements, one for loading the correct views and another for adding the correct data to the database. Ample use was made of the possibility to make views appear and reappear by using the .setVisibility method. The switch statements both rely on the “stage” at which the user finds himself in the sign up process. 
There is also a method which pulls up the keyboard, which is called whenever the user is asked for input that requires typing.
Finally, makePlan is called in order to insert the user into the database.  
### ProfileActivity
Most of the methods in this Activity are used for making and using the various dialog windows that arise when clicking on an item in the list of user info. 
In getView, the right layout to inflate is chosen using a switch statement, based on the position of the item in the list. 
When a user changes their information, applyChange is called, which also uses a switch statement based on position
### SearchActivity
This activity includes the straightforward use of a callback interface with a JSON request. 
A function to hide the keyboard after a search was added to increase user friendliness. 
### DetailActivity
A listener is added to the editText field where users change the amount of servings they had. The listener updates the views as soon as the user starts typing, by calling the changeNutrients method.
## Challenges
I found it extremely challenging to come up with a detailed plan before starting to code. For this reason, my DESIGN.md document was very basic. I’m not completely sure if this was because of my lack of experience in making apps, or if it is something to do with my style of working. I would assume that it is a combination of both. I was sure that any plans made before starting would change so drastically during the course of the project that it would not be efficient to spend much time on it.
I kept the planning phase to a minimum and quickly started working on the code of my app. The first big challenge was working with an API, I had some problems getting an access code to my desired API, but luckily one of the people in my team was using the same API and together we figured out how to get it to work. I had already spent some time using another API (since at the time I thought I wouldn’t be able to get an access code to Nutritionix). While I did lose some time switching APIs, in the end it was definitely worth it. 
Something I had not foreseen during the planning phase was that I would want to add a diary and profile activity to the app. I feel these are very important to create a good user experience, so I started work on that. This also made me rethink the signup process and I realized it wasn’t very user-friendly, so I also completely reworked that part of the app. Creating the food database and allowing users to add foods to it and showing the right foods in the diary was another big challenge. Part of this was working with dates, and this also taught me a lot. 
One of my main goals was to make the app look as professional as possible. During the 8-week app course I focused almost exclusively on functionality, and never really got into making a nice-looking layout. By using the material.io website, recommended to me by my mentor Marijn, I feel like I was able to create a very nice-looking layout, that is also highly functional. 
A big challenge in making a nice user interface was working with fragments. I had never done this before, and it took some time to figure out how to show a nice dialog without crashing my app. In the end it was very rewarding, and a very nice addition to the app. 
## Looking back
as discussed above, I realized at the start of the project that my plans would change a lot. Early on I had the plan to include more information about nutrients, especially vitamins. This turned out to be more difficult than I thought, because of how the API worked. It would have been possible given more time, and I would definitely have added it at some point during the development. 
Creating a complete app was my main priority, and I feel like adding the diary and profile, as well as creating a pleasant and intuitive interface were more important than giving users more detailed information. I did keep the idea for information about vitamin content in the back of my mind, and it would be relatively quick work to add it in now. 
I’m not 100% happy with how my code Is organized, but in the end, there was not enough time to go back and change it all. As the end of the project drew closer, and my app got more complex I did start noticing that changing anything became more and more time consuming, because of all the interdependent methods and activities. Thus, I feel I definitely have more to learn about coding architecture and making sure everything is as modular as possible. 
All in all, I really enjoyed making the app and I feel the 8-week app course gave me a very solid foundation to make a nice app. I recycled a lot of code from that course, like the API requests, ListView adapters, database helpers etc. Given enough time, I am confident that I would be able to create a professional app and this is something that I appreciate a lot about the course and the minor in general. I really feel like I learned a lot, and not just anything, but some very useful skills. 
