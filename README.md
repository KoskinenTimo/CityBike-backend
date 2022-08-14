Pre assignment / Learning project

This is a backend for citybike app. The app is a pre assignment and learning project to create my first Java spring boot backend.  
You can find the frontend in github: https://github.com/KoskinenTimo/CityBike-frontend/


To start the app in your editor use: ./gradlew bootRun  
// TODO if time, docker container


App data is stored in CSV files under /resources/public/data, and it can be used to seed the database which is used by the frontend.
To seed DB, start backend like said before and make a GET request http://localhost:8080/api/seeddb

If you need to reset the database for whatever reason, just delete the .db file and seed again. Seeding is done via endpoint due to the 
time it would take to do it at startup.


Recourses are divided into journeys and stations. 

Journeys can be requested with path /api/journeys and it can be modified by request params to filter and paginate.

@RequestParam(required = false,defaultValue = "0") int page (which page is wanted)  
@RequestParam(required = false) String filter (station name filter)  
@RequestParam(required = false, defaultValue = "10") int journeysPerPage (how many journeys on a page)  
@RequestParam(required = false) Long departureStationId (get journeys which have given departure id)  
@RequestParam(required = false) Long returnStationId (get journeys which have given return id)  

Stations can be requested with path /api/stations or to get just one station with given id /api/stations/id. getStations also has 
request params to modify response page.

@RequestParam(required = false, defaultValue = "0") Integer page (which page is wanted)  
@RequestParam(required = false) String filter (station name filter)  
@RequestParam(required = false, defaultValue = "10") Integer stationsPerPage (how many stations on a page)  


ISSUED TO SOLVE IF TIME:
Data sent from controllers is converted with wrong/bad converter, if time, finish the @Bean to amend converter
Posting/creating new resourses, missing endpoints for both stations and journeys resources
Services to fetch list of journeys per station, used to get averages and top destinations etc



