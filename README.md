# Project Title: Foodies
### Key features implemented are as follows:
##### User Registration,Sign In and Edit profiles 
User registration, sign in and edit profile views have been developed, using which the user can create his/her account, can sign-in into the application and can update his/her profile details.

##### Grocery List Creation and Selection
With this feature, the user would be able to view all the groceries available and select the groceries of his choice. He may choose the groceries from a list of popular groceries, or he may do so by browsing through the categories. The details of groceries, stores and their prices have been stored into an SQL database and API code is written to fetch the data with the help of database procedures and store it in JSON format. This has been hosted on a server and the path of the JSON is used in the code to fetch the data into the application.

##### Comparison of prices and placement of order
Upon selection of desired groceries, the user can view the price of each selected item at both the stores. Here, he can select the best priced store for each item and proceed to place an oder for this selection. This data is stored on firebase against the logged in user.

##### Recipe suggestions and View
After the user has placed the order, a person can select the ingredients, and a list of recipes based on the ingredients is fetched via the Yummly API. The recipe details can be viewed by opening the recipe of choice.

##### UI designing
A logo for the application has been developed. Three splash screens have been designed which would be guiding the users about the application.

##### Split Bill 
Using this feature, the user can split the bill amongst a number of users using the total bill amount.

##### Location
This will allow the user to see his current location as well as the location of the nearby stores.


## Libraries
###### Provide a list of ALL the libraries you used for your project. Example:

1. com.google.code.gson:gson:2.8.2 - This is used to convert java objects into JSON format and vice versa. This is used in the grocery selection and comparing prices module.
2. com.github.bumptech.glide:glide:3.7.0 - An image loading and caching library for Android focused on smooth scrolling. This is used to fetch the images.
3. com.android.support:customtabs:26.1.0 - This library extends the chrome browser, and allows to open urls in-app

# Installation Notes

### Yummly API
The code requires Yummly API to run, and it needs to be set first. The API has to be set in the strings file in the code. The API used in the project can be found in the Wiki section of this Git for marking purposes.

### Google Maps API
The code needs the Google Maps API to display the maps to show the current location and location of the nearby stores.

### JSON URLs for grocery selection and comparison of prices
- http://35.190.169.87:9000/test/grocery --> gives all the grocery items in the database.
- http://35.190.169.87:9000/test/price --> gives all the prices of all the groceries in every store in the db
- http://35.190.169.87:9000/test/price/1 --> gives prices of grocery of ID=1 in all the stores in the db
- http://35.190.169.87:9000/test/category --> gives all the available categories
- http://35.190.169.87:9000/test/category/1 --> give all the items in the category with ID =1

# Code Example

## Problem 1: A method was needed to check the validity of the email entered by user. 
###### In order to validate email, a complex regular expression was used.

``` 
public boolean checkEmail(String s)
    {
        String Regular_expression = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(Regular_expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(s);
        if (matcher.find())
        {
            return true;
        }
        else
        {
            return false;
        }
```

## Problem 2: Splash Screens are working completely but code for showing the spalsh screens for on etime is need to incorporate.
###### public class sphomeactivity extends AppCompatActivity {
    public static int timeout = 4000;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sphomeactivity);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent (sphomeactivity.this, spthird_page.class);
                startActivity(homeIntent);
                finish();
    
            }
        },timeout);
    }
## Problem 3: Showing the logo on toolbar
###### Instead of using vector assest for showing logo we had to use image view with png image
 <ImageView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:padding="0dp"
        android:src="@drawable/logoonly1" />
        
 
##  im1 Image used  in header.xml file has been taken from the google images. 
"small pictures for different vegetables - Google Search", Google.ca, 2018. [Online]. Available: https://www.google.ca/search?biw=1280&bih=566&tbm=isch&sa=1&ei=dRqWWvKtHMWEtQX61JzoBw&q=small+pictures+for+different+vegetables&oq=small+pictures+for+different+vegetables&gs_l=psy-ab.3...14755.22279.0.22578.24.21.0.0.0.0.258.2096.6j7j3.16.0....0...1c.1.64.psy-ab..10.0.0....0.HKl9Sc5VAsQ. [Accessed: 09- Apr- 2018]. 


## Problem 4: Passing intent among activities and fragments was difficult
  
  if (menuItem.getItemId() == R.id.Home)
        {
            fragmentClass = RecipeActivity.class;
        }
        else if (menuItem.getItemId() == R.id.Login)
        {
            startActivity(new Intent(sidemenu.this, sign_in.class));
        }
        else if (menuItem.getItemId() == R.id.Orders)
        {
            startActivity(new Intent(sidemenu.this, MapsActivityCurrentPlace.class));
        }
        else if (menuItem.getItemId() == R.id.neworder)
        {
            fragmentClass = Ordering.class;
        }
        else if (menuItem.getItemId() == R.id.Recipes)
        {
            fragmentClass = IngredientsList.class;
        }
        else if (menuItem.getItemId() == R.id.profile)
        {
            startActivity(new Intent(sidemenu.this, user_edit_profile.class));
        }
# Feature Section

###### 1) User Registration, Sign In and Edit profile feature:
Here, the user can create a new account using the sign up form and can use the sign in form to login into the application. The user can also edit his/her profile.

###### 2) Splash Screens:
These will be the very first screens of the application and these screens will explain some of the features of the application.

###### 3) Selecting groceries:
This screen will allow the user to select the groceries he wants to order from the list of popular items or by browsing through the categories available.

###### 4) Comparison of prices for the stores
This will allow the user to view the prices of the groceries that he has selected for both the stores. By side-to-side comparison, he can select the store with the best price.

###### 5) Placing an order
This will allow the user to place an order after he has selected the store for each grocery item that he wishes to purchase.

###### 6) Split Bill :
Here, the user can split the bill by specifying the number of users amongst whom the bill is to be split and the total bill amount.

###### 7)  Location
This will allow the user to see his current location as well as the location of the nearby stores.

# Final Project Status
The project has been completed, along with all the minimum, expected and bonus functionalities. The future scope of this project would be to include the data from the actual stores and place orders with the stores via our application. 

## Minimum Functionality

 

##### Feature 1: User Registration and Sign In forms (Completed)

##### Feature 2: Grocery selection module with database connection (Completed)

##### Feature 3: Best offers comparision for grocery items selected (Completed)

 

## Expected Functionality

 

##### Feature 1: Grocery ordering (Completed)

##### Feature 2: Recipe module (Completed)

##### Feature 3: GPS Module (Completed)

##### Feature 4: User edit profile Module(Completed) 

 

## Bonus Functionality


##### Feature 1: Split bill Module (Completed)


# Sources
###### [Reference 1] firebase.google.com, "Add Firebase to Your Android Project",firebase.google.com, para.1-5, Feb.,14,2018 [Online]. Available: https://firebase.google.com/docs/android/setup?authuser=0 .[Accessed Mar.16, 2018].

###### [Reference 2]Stema, “Java Regex to Validate Full Name allow only Spaces and Letters”, stackoverflow.com, para. 2, Apr. 4, 2013. [Online]. Available:https://stackoverflow.com/questions/15805555/java-regex-to-validate-full-name-allow-only-spaces-and-letters [Accessed Feb.11, 2018].

###### [Reference 3]Jason Buberel, “Java regex email” stackoverflow.com, para. 2, Nov. 20, 2011. [Online]. Available:https://stackoverflow.com/questions/8204680/java-regex-email. [Accessed Feb.11, 2018].

###### [Reference 4]Ravi Tamada, "Inserting data" in "Android working with Firebase Realtime Database",para.3.1, [Online]. Available:  https://www.androidhive.info/2016/10/android-working-with-firebase-realtime-database/ .  [Accessed Mar.16, 2018].

###### [Reference 5]Benyamine Malki,Frank van Puffelen, "How to get child of child value from firebase in android?",stackoverflow.com ,para.3.1, Aug.,19,2017 [Online]. Available: https://stackoverflow.com/questions/43293935/how-to-get-child-of-child-value-from-firebase-in-android/43295289.   [Accessed Mar.16, 2018].

###### [Reference 6]Pankaj Rai,"Use map to create well formed structure" in "Firebase the dynamic database!" para. 10, May 9,2017,[Online], Available:https://android.jlelse.eu/firebase-the-dynamic-database-5b7878ebba2d .[Accessed:Mar. 18,2018].

###### [Reference 7]Pulkit,Jorgesys,"Setting values in Preference:" in "Android Shared preferences example [closed]",para. 1, July 6,2017,[Online], Available:https://stackoverflow.com/questions/23024831/android-shared-preferences-example. [Accessed: Apr.3,2018]

###### Reference [8]: Alex Jolig, “How to have EditText with border in Android Lollipop”, stackoverflow.com, para. 5, Dec. 26, 2017. [Online]. Available:https://stackoverflow.com/questions/35762006/how-to-have-edittext-with-border-in-android-lollipop [Accessed Apr.1, 2018].

###### Reference [9]: How does TabLayout work? (2018). How does TabLayout work?. [online] Stackoverflow.com. Available at: https://stackoverflow.com/questions/40947477/how-does-tablayout-work [Accessed 8 Apr. 2018].

###### Reference [10]: GSON, C. (2018). Complex object (de)serialization into JSON using GSON. [online] Stackoverflow.com. Available at: https://stackoverflow.com/questions/16915788/complex-object-deserialization-into-json-using-gson?rq=1 [Accessed 8 Apr. 2018]. 

###### Reference [11]: GSON, D. (2018). Deserialize type extending abstract class in GSON. [online] Stackoverflow.com. Available at: https://stackoverflow.com/questions/26309773/deserialize-type-extending-abstract-class-in-gson [Accessed 8 Apr. 2018].

###### Reference [12]: Developer.android.com. (2018). StrictMode.ThreadPolicy.Builder | Android Developers. [online] Available at: https://developer.android.com/reference/android/os/StrictMode.ThreadPolicy.Builder.html [Accessed 8 Apr. 2018].

###### Reference [13]:  Google Developers. (2018). Select Current Place and Show Details on a Map  |  Google Maps Android API  |  Google Developers. [online] Available at: https://developers.google.com/maps/documentation/android-api/current-place-tutorial [Accessed 8 Apr. 2018].

###### Reference [14]: Google Developers. (2018). Place Types  |  Google Places API  |  Google Developers. [online] Available at: https://developers.google.com/places/supported_types [Accessed 8 Apr. 2018].

###### Reference [15]: Udacity. (2018). Firebase in a Weekend | Course explaining the use of firebase in Android [online] Available at: https://classroom.udacity.com/courses/ud0352 [Accessed 8 Apr. 2018].

###### Reference [15]: Google Developers (2018). LruCache. Used to get images from a url in the project. Available at: https://developer.android.com/reference/android/util/LruCache.html [Accessed 8 Apr. 2018].

###### References [16]: "BottomNavigationView with Fragments - Android Studio Tutorial", YouTube, 2018. [Online]. Available: https://www.youtube.com/watch?v=tPV8xA7m-iw. [Accessed: 09- Apr- 2018].

###### Refernces [17]: R. Tamada, "Android Custom ListView with Image and Text using Volley", AndroidHive, 2018. [Online]. Available: https://www.androidhive.info/2014/07/android-custom-listview-with-image-and-text-using-volley/. [Accessed: 09- Apr- 2018].

###### Reference [18]: "Stack Overflow - Where Developers Learn, Share, & Build Careers", Stackoverflow.com, 2018. [Online]. Available: https://stackoverflow.com/. [Accessed: 09- Apr- 2018].

###### References [19]: Create a Navigation Drawer | Android Developers", Developer.android.com, 2018. [Online]. Available: https://developer.android.com/training/implementing-navigation/nav-drawer.html#ListItemClicks. [Accessed: 09- Apr- 2018].

###### References [18]: How to Create Welcome Screen (Splash Screen) in Android Studio", YouTube, 2018. [Online]. Available: https://www.youtube.com/watch?v=jXtof6OUtcE. [Accessed: 09- Apr- 2018].

###### References [19]: "small pictures for different vegetables - Google Search", Google.ca, 2018. [Online]. Available: https://www.google.ca/search?biw=1280&bih=566&tbm=isch&sa=1&ei=dRqWWvKtHMWEtQX61JzoBw&q=small+pictures+for+different+vegetables&oq=small+pictures+for+different+vegetables&gs_l=psy-ab.3...14755.22279.0.22578.24.21.0.0.0.0.258.2096.6j7j3.16.0....0...1c.1.64.psy-ab..10.0.0....0.HKl9Sc5VAsQ. [Accessed: 09- Apr- 2018]. 





