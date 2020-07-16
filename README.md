# Stock-Trades-API  [![build passing](https://img.shields.io/badge/build-passing-brightgreen.svg?style=flat)](https://github.com/dwyl/esta/issues)
```Java``` ```Spring Boot``` ``` Maven``` ``` RestAPI``` ```H2DB```


## Java Spring Boot: Stock Trades API   Back-end Developer

In this challenge, you are part of a team building a brokerage company's accounts and trading management platform. One requirement is for a REST API service to manage trades using the Spring Boot framework. You will need to add functionality to add and delete transactions as well as to perform some queries. You'll be dealing with typical information for financial transactions like account ownership data, shares, price, transaction time, etc. The team has come up with a set of requirements including filtering and ordering requirements, response codes and error messages for the queries you must implement.
 
The definitions and a detailed requirements list follow. You will be graded on whether your application performs data retrieval and manipulation based on given use cases exactly as described in the requirements.


Each trade is a JSON entry with the following keys:


* *id*: The unique ID of the trade.
* *type*: The trade type, either buy or sell.
* *user*: The user responsible for the trade, a JSON entry:
  * *id*: The user's unique ID. 
  * *name*: The user's name.
* *symbol*: The stock symbol. 
* *shares*: The total number of shares traded. The traded shares value is between 10 and 30 shares, inclusive.
* *price*: The price of one share of stock at the time of the trade (up to two places of decimal). The stock price is between 130.42 and 195.65 inclusive.
* *timestamp*: The timestamp for the trade creation given in the format yyyy-MM-dd HH:mm:ss. The timezone is EST (UTC -4).


```
Sample JSON Trade object
{    "id": 1,   
    "type": "buy",
    "user": {       "id": 1, 
                "name": "David"    }, 
      "symbol": "AC", 
      "shares": 28,  
      "price": 162.17, 
      "timestamp": "2014-06-14 13:13:13" }
      
 ```
 
## The REST service should implement the following functionalities:

1. *Erasing all the trades*: The service should be able to erase all the trades by the *DELETE* request at ```/erase```. The *HTTP* response code should be *200*.

2. *Adding new trades*: The service should be able to add a new trade by the POST request at ```/trades```. The event JSON is sent in the request body. If a trade with the same id already exists then the *HTTP* response code should be *400*, otherwise, the response code should be *201*. 

3. *Returning all the trades*: The service should be able to return the JSON array of all the trades through a GET request at ```/trades```. The *HTTP* response code should be *200*. The JSON array should be sorted in ascending order by trade ID. 

4. *Returning the trade records ﬁltered by the user ID*: The service should be able to return the JSON array of all the trades which are performed by the user ID through a *GET* request at ```/trades/users/{userID}```. If the requested user does not exist then the *HTTP* response code should be *404*, otherwise, the response code should be *200*. The JSON array should be sorted in ascending order by trade ID. please note that a user can perform multiple trades from their side. 

5. *Returning the highest and lowest price for the stock symbol in the given date range*: The service should be able to return the JSON object describing the information of highest and lowest price in the given date range speciﬁed by start date and end date inclusive, through a *GET* request at ```/stocks/{stockSymbol}/price?start={startDate}&end={endDate}```. If the requested stock symbol does not exist then the *HTTP* response code should be *404*, otherwise, the response code should be *200*.

 The response JSON should consist of the following three ﬁelds: 

* *symbol*: the symbol for the requested stock
* *highest*: the highest price for the requested stock symbol in the given date range
* *lowest*: the lowest price for the requested stock symbol in the given date range 


### If there are no trades for the requested stock symbol, then the response JSON should be:

```
{   "message":"There are no trades in the given date range" }
```

6. *Returning the ﬂuctuations count, maximum daily rise and maximum daily fall for each stock symbol for the period in the given date range*: The service should be able to return the ﬂuctuations count, maximum daily rise and maximum daily fall for each stock symbol for the period in the given date range inclusive. This will be accomplished through a *GET* request at ```/stocks/stats?start= {startDate}&end={endDate}```. The response code should be *200*. The JSON array should be sorted in ascending order by the stock symbol.

The response JSON should consist of the following four ﬁelds:

* *symbol*: The stock symbol for the requested stock. 
* *fluctuations*: This ﬁeld describes the number of ﬂuctuations, or reversals, in stock price for the given date range. If there are not at least 3 data points, this value should be 0. A ﬂuctuation is deﬁned as:
     * There is a daily rise in price followed by a daily fall in price. 
     * There is a daily fall in price followed by a daily rise in price. 
* *max_rise*: This ﬁeld is the maximum daily price increase for the period.
* *max_fall*: This ﬁeld is the maximum daily price decline for the period. 

If there are no trades for the requested stock symbol, then the response JSON should consist of the following two ﬁelds:

* *symbol*: This is the requested stock symbol. 
* *message*: The message should be "There are no trades in the given date range".
 
You should complete the given incomplete project so that it passes all the test cases when running the provided unit tests. 
The project evaluates the restful API's using an in memory H2 database.
 
## Sample Series of Requests
Requests are received in the following order: 

### POST ```/trades ```

Consider the following POST requests (these are performed in the ascending order of trade id):

```
{  "id": 1,
   "type": "buy",   
   "user": {      
       "id": 1,
        "name": "David"    },  
    "symbol": "AC", 
    "shares": 28,
    "price": 162.17,
    "timestamp": "2014-06-14 13:13:13" }
```
```
{    "id": 2,
     "type": "buy",
     "user": { 
           "id": 3,  
           "name": "Brandon"    },   
      "symbol": "ACC",
      "shares": 25, 
      "price": 146.09, 
      "timestamp": "2014-06-25 13:40:13" }
  ```

``` 
{    "id": 3, 
     "type": "buy", 
     "user": {      
       "id": 2,     
       "name": "Omar"    }, 
      "symbol": "AC",
      "shares": 13,
      "price": 146.09,
      "timestamp": "2014-06-25 13:40:13" }
```
```
{    "id": 4, 
     "type": "buy", 
     "user": {      
        "id": 1,   
        "name": "David"    }, 
      "symbol": "AC",
      "shares": 12,  
      "price": 137.39, 
      "timestamp": "2014-06-25 13:44:13" }
  ```
``` 
{    "id": 5, 
      "type": "buy",
      "user": {    
       "id": 3,   
       "name": "Brandon"    },
      "symbol": "AC",  
      "shares": 15,  
      "price": 161.35,   
      "timestamp": "2014-06-26 13:15:18" }
     
```
```
{    "id": 6,  
      "type": "sell",   
      "user": {   
        "id": 3,    
        "name": "Brandon"    }, 
      "symbol": "AC", 
      "shares": 10,   
      "price": 162.37,  
      "timestamp": "2014-06-26 15:15:18" }
```
```
{    "id": 7, 
     "type": "buy",
     "user": {      
        "id": 3,    
        "name": "Brandon"    }, 
     "symbol": "ACC",  
     "shares": 17,  
     "price": 146.08,  
     "timestamp": "2014-06-27 10:10:31" }
```
```

{    "id": 8,  
     "type": "buy",   
     "user": {    
       "id": 3, 
      "name": "Brandon"    },   
     "symbol": "ACC",  
     "shares": 15,
     "price": 146.11, 
     "timestamp": "2014-06-27 11:08:23" }
```
```
{    "id": 9,  
     "type": "buy", 
     "user": {   
           "id": 3,    
           "name": "Brandon"    }, 
     "symbol": "ACC", 
     "shares": 25,  
     "price": 146.09,  
     "timestamp": "2014-06-27 12:17:17" }
```
```
{    "id": 10, 
     "type": "buy",   
     "user": {  
          "id": 1,   
          "name": "David"    },  
     "symbol": "ABR",    
     "shares": 10,   
     "price": 136.27, 
     "timestamp": "2014-06-28 13:11:13" }
```

### GET ```/trades/users/1``` 
The response of the GET request is the following JSON array with the HTTP response code 200:
```
[  
  {      
    "id": 1, 
    "type": "buy",   
    "user": {     
      "id": 1,       
      "name": "David"      
      }, 
    "symbol": "AC",  
    "shares": 28,     
    "price": 162.17,     
    "timestamp": "2014-06-14 13:13:13"    
  }, 
  {   
    "id": 4,      
    "type": "buy", 
     "user": {       
       "id": 1,    
       "name": "David"   
       },   
    "symbol": "AC",  
    "shares": 12, 
    "price": 137.39,   
    "timestamp": "2014-06-25 13:44:13"   
  },  
  {   
    "id": 10,   
    "type": "buy",  
    "user": {    
       "id": 1,    
       "name": "David"   
       },   
    "symbol": "ABR",   
    "shares": 10,   
    "price": 136.27,  
    "timestamp": "2014-06-28 13:11:13"   
   } 
  ]
  ```
 ### GET ```/stocks/ACC/price?start=2014-06-25&end=2014-06-26```
 
 The response of the GET request is the following JSON with the HTTP response code 200: 
 ```
       {  
       "symbol": "ACC",   
       "highest": 146.09,
       "lowest": 146.09 
       }
````
### GET ```/stocks/stats?start=2014-06-14&end=2014-06-26```

The response of the GET request is the following JSON array with the HTTP response code 200: 
```
[   
  { 
      "stock": "ABR",
      "message": "There are no trades in the given date range"
   },  
   {      
         "stock": "AC", 
         "fluctuations": 1,
         "max_rise": 23.96, 
         "max_fall": 16.08  
     },   
     {   
         "stock": "ACC",   
         "fluctuations": 0, 
         "max_rise": 0.0,   
         "max_fall": 0.0    
      }
] 
```
### GET ```/stocks/stats?start=2014-06-14&end=2014-06-27```

The response of the GET request is the following JSON array with the HTTP response code 200: 
```
[   
    {
        "stock": "ABR",
        "message": "There are no trades in the given date range" 
     }, 
     {   
         "stock": "AC", 
         "fluctuations": 1,  
         "max_rise": 23.96,    
         "max_fall": 16.08
      }, 
      {   
          "stock": "ACC",  
          "fluctuations": 2, 
          "max_rise": 0.03,  
          "max_fall": 0.02  
       } 
]
```
## License

Spring Boot is Open Source software released under the [Apache 2.0 license](https://www.apache.org/licenses/LICENSE-2.0.html).

 # Questions and Comments:saipasyanthi@gmail.com
