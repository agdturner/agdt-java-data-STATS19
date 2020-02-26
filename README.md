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
* Provides the following description of the data which is helpful in outlining the variable details and completeness of the data:
"These files provide detailed data about the circumstances of personal injury road accidents in Great Britain from 2005 onwards, the types of vehicles involved and the consequential casualties. The statistics relate only to personal injury accidents on public roads that are reported to the police, and subsequently recorded, using the STATS19 accident reporting form. Information on damage-only accidents, with no human casualties or accidents on private roads or car parks are not included in this data."
* "Very few, if any, fatal accidents do not become known to the police although it is known that a considerable proportion of non-fatal injury accidents are not reported to the police. Figures for deaths refer to persons killed immediately or who died within 30 days of the accident.  This is the usual international definition, adopted by the Vienna Convention in 1968"

So, the data are incomplete, but nevertheless they are interesting and can be generalised, visualised and analysed in an attempt to improve road safety - and have been for many years. The details of who, where, when and the circumstances of each accident are unique, but the patterns in accidents can be explored temporally, spatially and generalised for different types of accident. The general pattern is a consequence of a combination of intrinsic risk and the exposure to that risk, both of which vary significantly and in complex ways. Many (most?) accidents are a result of driver error and misperception of risk, but some accidents are not. The question of where is it dangerous and for who and in what circumstances and how do we better warn road users so that there are fewer accidents and casualties are less severe does not have a simple anwer, but it is imperative that this question is addressed. This is but one of a number of eforts that tries to help in this way. Another effort which is based on ROpenSci is being spearheaded by my colleague Robin Lovelace here:
https://github.com/ropensci/stats19/

## This library
Some of the source code for loading STATS19 data was automatically written by a code generation library (https://github.com/agdturner/agdt-java-data-STATS19CodeGenerator) which effectively parsed the STATS19 metadata.

Additional classes in this library are for generalising and visualising the data using bespoke methods and additional libraries for charting and producing geographical maps of accident concentrations and accident clusters.

The aim of this library is to provide easily reproducible generalisations and visualisations of the data and to help users explore and analyse it.

To use the library, you need to download the source data. Please contact the developer if you want help getting started.

## Preliminary results
As shown in the fllowing tables, there appears to be a significant increase in the accident count, accident frequency, the death count, the death frequency, the cyclist casualty count and the the cyclist casualty count frequency in 2014 compared with previous years. The general trend is for the counts and frequencies to come down, but why was there an increase in 2014? The change in death frequency is particularly clear. Are these older people dying? Are the results localised? 
```
Accident count (by year and month)
Year,Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec
2009,13417,10950,13202,12715,13811,13936,14300,13415,13792,14834,15473,13709
2010,10637,11724,13165,12248,13220,13644,13527,13027,13904,14429,14544,10345
2011,11761,11150,12432,12342,12623,13048,13118,12031,13339,13748,13197,12685
2012,11836,10863,12171,10820,12439,11470,12818,11878,13066,13120,13305,11785
2013,10226,9721,10284,9962,11449,11843,13012,11774,12012,13322,13168,11887
2014,12086,10780,11915,11008,12303,12532,13026,12108,11832,13450,13246,12036
2015,11601,10284,10939,10874,11441,12058,12771,11470,12201,12409,12378,11630
2016,11688,10657,10836,10592,11482,11046,11777,11461,11571,11624,12741,11146
2017,11147,9740,10806,9773,10922,11130,11450,10301,11091,11194,11958,10470
2018,10115,9256,8930,9037,11050,10995,10985,9679,10495,10897,11168,10028
```
```
Accidents per day (by year and month)
Year,Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec
2009,432.81,391.07,425.87,423.83,445.52,464.53,461.29,432.74,459.73,478.52,515.77,442.23
2010,343.13,418.71,424.68,408.27,426.45,454.80,436.35,420.23,463.47,465.45,484.80,333.71
2011,379.39,398.21,401.03,411.40,407.19,434.93,423.16,388.10,444.63,443.48,439.90,409.19
2012,381.81,374.59,392.61,360.67,401.26,382.33,413.48,383.16,435.53,423.23,443.50,380.16
2013,329.87,347.18,331.74,332.07,369.32,394.77,419.74,379.81,400.40,429.74,438.93,383.45
2014,389.87,385.00,384.35,366.93,396.87,417.73,420.19,390.58,394.40,433.87,441.53,388.26
2015,374.23,367.29,352.87,362.47,369.06,401.93,411.97,370.00,406.70,400.29,412.60,375.16
2016,377.03,367.48,349.55,353.07,370.39,368.20,379.90,369.71,385.70,374.97,424.70,359.55
2017,359.58,347.86,348.58,325.77,352.32,371.00,369.35,332.29,369.70,361.10,398.60,337.74
2018,326.29,330.57,288.06,301.23,356.45,366.50,354.35,312.23,349.83,351.52,372.27,323.48
```
```
Death count (by year and month)
Year,Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec
2009,202,178,175,196,185,177,197,220,179,176,175,162
2010,131,133,150,132,177,158,140,197,175,178,159,120
2011,157,139,149,164,146,177,150,178,166,147,172,156
2012,136,120,158,124,144,134,161,147,162,136,145,187
2013,124,95,117,140,161,138,163,151,154,157,157,156
2014,240,248,308,331,354,391,399,376,276,250,256,209
2015,228,262,252,322,314,350,379,331,327,268,237,265
2016,243,243,294,288,310,291,390,346,325,266,253,224
2017,244,208,268,295,267,313,330,332,237,295,270,221
2018,202,195,177,261,293,311,297,268,276,247,233,214
```
```
Deaths per day (by year and month)
Year,Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec
2009,6.52,6.36,5.65,6.53,5.97,5.90,6.35,7.10,5.97,5.68,5.83,5.23
2010,4.23,4.75,4.84,4.40,5.71,5.27,4.52,6.35,5.83,5.74,5.30,3.87
2011,5.06,4.96,4.81,5.47,4.71,5.90,4.84,5.74,5.53,4.74,5.73,5.03
2012,4.39,4.14,5.10,4.13,4.65,4.47,5.19,4.74,5.40,4.39,4.83,6.03
2013,4.00,3.39,3.77,4.67,5.19,4.60,5.26,4.87,5.13,5.06,5.23,5.03
2014,7.74,8.86,9.94,11.03,11.42,13.03,12.87,12.13,9.20,8.06,8.53,6.74
2015,7.35,9.36,8.13,10.73,10.13,11.67,12.23,10.68,10.90,8.65,7.90,8.55
2016,7.84,8.38,9.48,9.60,10.00,9.70,12.58,11.16,10.83,8.58,8.43,7.23
2017,7.87,7.43,8.65,9.83,8.61,10.43,10.65,10.71,7.90,9.52,9.00,7.13
2018,6.52,6.96,5.71,8.70,9.45,10.37,9.58,8.65,9.20,7.97,7.77,6.90
```
```
Cyclist_casualties count (by year and month)
Year,Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec
2009,1009,809,1364,1363,1593,1969,1748,1575,1883,1627,1396,1020
2010,731,975,1314,1481,1733,1983,1982,1651,1930,1767,1411,568
2011,1194,1167,1482,1763,1829,1947,2024,1657,1890,1839,1727,1102
2012,1400,1164,1702,1200,1804,1546,1963,1968,2111,1889,1679,1097
2013,1137,1099,1057,1374,1679,2040,2547,1987,2020,2086,1682,1196
2014,1557,1320,1703,1564,1885,2238,2482,1875,2156,2042,1716,1247
2015,1301,1153,1455,1623,1713,1950,2081,1659,1887,1768,1440,1258
2016,1336,1218,1339,1480,1729,1710,1937,1784,1907,1693,1623,1181
2017,1318,1273,1570,1460,1865,1890,1870,1510,1672,1732,1667,1025
2018,1284,1092,1082,1206,1871,2030,1942,1522,1646,1696,1547,1114
```
```
Cyclist_casualtiess per day (by year and month)
Year,Jan,Feb,Mar,Apr,May,Jun,Jul,Aug,Sep,Oct,Nov,Dec
2009,32.55,28.89,44.00,45.43,51.39,65.63,56.39,50.81,62.77,52.48,46.53,32.90
2010,23.58,34.82,42.39,49.37,55.90,66.10,63.94,53.26,64.33,57.00,47.03,18.32
2011,38.52,41.68,47.81,58.77,59.00,64.90,65.29,53.45,63.00,59.32,57.57,35.55
2012,45.16,40.14,54.90,40.00,58.19,51.53,63.32,63.48,70.37,60.94,55.97,35.39
2013,36.68,39.25,34.10,45.80,54.16,68.00,82.16,64.10,67.33,67.29,56.07,38.58
2014,50.23,47.14,54.94,52.13,60.81,74.60,80.06,60.48,71.87,65.87,57.20,40.23
2015,41.97,41.18,46.94,54.10,55.26,65.00,67.13,53.52,62.90,57.03,48.00,40.58
2016,43.10,42.00,43.19,49.33,55.77,57.00,62.48,57.55,63.57,54.61,54.10,38.10
2017,42.52,45.46,50.65,48.67,60.16,63.00,60.32,48.71,55.73,55.87,55.57,33.06
2018,41.42,39.00,34.90,40.20,60.35,67.67,62.65,49.10,54.87,54.71,51.57,35.94
```

The next steps are to break this down geographically and to present the results in graphs and geographical maps...
