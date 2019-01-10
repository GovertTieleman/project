# DESIGN DOCUMENT

## SKETCH

![Alt text](/proposal1.png?raw=true "Portrait")

## UTILITY MODULES
Class: User
Attributes: gender, height, age, weight, goal, activity level
Operations: calculate caloric intake goal

Class: Food
Attributes: carbs, fat, protein, amount, vitamins
Operations: calculate calories

UserDatabase:
user,
Food[] 

makeUser
addFood

## APIs AND DATA SOURCES
This will have to be determined at a later time. There are definitely a lot of APIs and data sources for nutrient information on the web, and we will try to find the one('s) best suitable for this app.

Currently working on getting the API from nutritionix.com working. While they are a paid API, they have a free plan for students and I am currently waiting to get my account verified.

## DATABASE TABLES
1. User - in this DB we will keep the basic user information such as height, weight etc.
2. Food - in this DB we will keep the diary of the user's food consumption.
