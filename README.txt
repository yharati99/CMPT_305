# Student Property Finder (CMPT 305)

A JavaFX application designed to help university students in Edmonton find rental properties near their campuses. The app filters city property assessment data based on distance to specific schools and estimated monthly rent, displaying the results interactively on a map.

---

## Getting Started

### 1. Generate the Dataset
Before running the main application, you must generate the filtered dataset. If this file is missing, the application will not be able to perform searches.

* Run the `main` method inside `DatasetGenerator.java`.
* This will read the raw property assessment data (`Property_Assessment_Data_2025.csv`) and output a new file named `Available_Properties.csv` into your project root.

### 2. Launch the Application
Run the `main` method inside `Launcher.java`.

---

## How to Use the App

1. **Select a Campus:** Choose your target school (UofA, MacEwan, NAIT, or NorQuest) from the top-left dropdown menu.
2. **Set Your Parameters:** Use the spinners to set your Maximum Price ($/month) and Maximum distance radius (in meters).
3. **Search:** Click the "Search" button. 
   * A red pin will drop on the exact location of the selected school.
   * Blue pins will appear for all properties that match your criteria.
4. **Interact with Results:** * **Highlighting:** Click on any property card in the list view on the right to automatically highlight its corresponding pin on the map.
   * **Sorting:** Use the 'Sort' links above the list to organize the properties either by Price (Ascending/Descending) or by Distance from the campus.

---

## Built With

* **Java / JavaFX:** Core application logic and user interface.
* **Leaflet.js:** Interactive map rendering and coordinate plotting.
