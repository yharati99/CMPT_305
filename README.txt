STUDENT PROPERTY FINDER (CMPT 305)

A JavaFX application designed to help university students in Edmonton find rental properties near their campuses. The app filters city property assessment data based on distance to specific schools and estimated monthly rent, displaying the results interactively on a map.

--------------------------------------------------
GETTING STARTED
--------------------------------------------------

1. GENERATE THE DATASET
Before running the main application, you must generate the filtered dataset. If this file is missing, the application will not be able to perform searches.
   a. Run the 'main' method inside 'DatasetGenerator.java'.
   b. This will read the raw property assessment data ('Property_Assessment_Data_2025.csv') and output a new file named 'Available_Properties.csv' into your project root.

2. LAUNCH THE APPLICATION
Run the 'main' method inside 'Launcher.java'.

--------------------------------------------------
HOW TO USE THE APP
--------------------------------------------------

1. SELECT A CAMPUS: Choose your target school (UofA, MacEwan, NAIT, or NorQuest) from the top-left dropdown menu.

2. SET YOUR PARAMETERS: Use the spinners to set your Maximum Price ($/month) and Maximum distance radius (in meters).

3. SEARCH: Click the "Search" button. 
   - A red pin will drop on the exact location of the selected school.
   - Blue pins will appear for all properties that match your criteria.

4. INTERACT WITH RESULTS: 
   - HIGHLIGHTING: Click on any property card in the list view on the right to automatically highlight its corresponding pin on the map.
   - SORTING: Use the 'Sort' links above the list to organize the properties either by Price (Ascending/Descending) or by Distance from the campus.

--------------------------------------------------
BUILT WITH
--------------------------------------------------
- Java / JavaFX (Core application logic and user interface)
- Leaflet.js (Interactive map rendering and coordinate plotting)