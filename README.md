# agdt-java-generic-data-stats19
Java for handling STATS19 data - Personal Injury Road Accident Data for Great Britain

## Data
STATS19 is a series of data compiled and released annually by the UK Department for Transport. Data for Northern Ireland have been collected separately to that from Wales, Scotland and England. The data schema has remained very similar since 1985, but there have been a number of changes. Data from 1985 to 2014 are available as a series from the UKDA:
beta.ukdataservice.ac.uk/datacatalogue/series/series?id=2000045
Data from 2009 to 2018 is currently available from data.gov.uk via:
https://data.gov.uk/dataset/cb7ae6f0-4be6-4935-9277-47e5ce24a11f/road-safety-data

Each annual data set contains data about each individual accident recorded. The data are in 3 related tables: accident, casualty, vehicle. The accident table provides details of the date and time, location (including detailed coordinates), and some summary data about the accident severity, and the vehicles, pedestrians and casualties involved. The casualty table contains details of each casualty and the vehicle table details about each vehicle involved. The relational nature of the table records allows for details of casualties to be linked with details of vehicles. So for instance it is possible to know for example that a cyclist was severely injured and a car passenger was slightly injured and that a car, cyclist and a van were all involved and that the accident happened at a particular junction in particular road and weather conditions.

The Department for Transport Brief Guide to Road Accidents and Safety data:
http://data.dft.gov.uk/road-accidents-safety-data/Brief-guide-to%20road-accidents-and-safety-data.doc
Provides the folowing description of the data:
```
"These files provide detailed data about the circumstances of personal injury road accidents in Great Britain from 2005 onwards, the types of vehicles involved and the consequential casualties. The statistics relate only to personal injury accidents on public roads that are reported to the police, and subsequently recorded, using the STATS19 accident reporting form. Information on damage-only accidents, with no human casualties or accidents on private roads or car parks are not included in this data.

Very few, if any, fatal accidents do not become known to the police although it is known that a considerable proportion of non-fatal injury accidents are not reported to the police. Figures for deaths refer to persons killed immediately or who died within 30 days of the accident.  This is the usual international definition, adopted by the Vienna Convention in 1968"
```

So, the data are incomplete, but nevertheless they are interesting and can be generalised, visualised and analysed in an attempt to improve road safety - and have been for many years. The details of who, where, when and the circumstances of each accident are unique, but the patterns in accidents can be explored temporally, spatially and generalised for different types of accident. The general pattern is a consequence of a combination of intrinsic risk and the exposure to that risk, both of which vary significantly and in complex ways. Many (most?) accidents are a result of driver error and misperception of risk, but some accidents are not. The question of where is it dangerous and for who and in what circumstances and how do we better warn road users so that there are fewer accidents and casualties are less severe does not have a simple anwer, but it is imperative that this question is addressed. This is but one of a number of eforts that tries to help in this way. Another effort which is based on ROpenSci is being spearheaded by my colleague Robin Lovelace here:
https://github.com/ropensci/stats19/

## This library
Some of the source code for loading STATS19 data was automatically written by a code generation library (https://github.com/agdturner/agdt-java-data-STATS19CodeGenerator) which effectively parsed the STATS19 metadata.

Additional classes in this library are for generalising and visualising the data using bespoke methods and additional libraries for charting and producing geographical maps of accident concentrations and accident clusters.

The aim of this library is to provide easily reproducible generalisations and visualisations of the data and to help users explore and analyse it.

To use the library, you need to download the source data. Please contact the developer if you want help getting started.

## Preliminary results
...
