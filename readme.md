Application is composed by 3 modules:
- tennis-data-common-repository: contains common utility classed and all data source files including all ATP matches results.
File data sources are provided by github project https://github.com/JeffSackmann/tennis_atp
- tennis-data-scanner: includes api to collect data and create statistics
- tennis-data-scraper: Reads all data in the format provided by project linked above, process in a new format selecting
- only wanted fields and to a different output like csv or mongo database. The job is processed by spring-batch