# Rate Limiting
## Using bucket4j
* This App is to limit the number of API calls per user at the rate of 50 Requests per Hour.
* If a user tries to access an API for more than 50 times within an hour, then he/she will be sent a response of HTTP Code 429
* After an hour the user can access the same API again.
* Name (end-point) of the Rest API is "/getRateLimitedData".
* Attached the Apache JMeter JMX file and screenshots.
* It is assumed that 50 tokens are available per tenant, per hour. i.e., X = 50 tokens/1 hour/tenant.
* After 1 hour (beginning from the first request) a tenant will be provided with 50 tokens again.
* It is assumed that tenants are authenticated and are provided with an API Key to access API End-Points.
* The API Key must be provided in the header with key as "x-api-key".
* If an unauthorised user tries to access the only api available ("/getRateLimitedData"), then HTTP Status Code 401 is sent by the server.
* If all the 50 tokens are consumed by a tenant, then HTTP Status Code 429 is sent by the server.
* The data accessed by the client for a successful API Request to ("/getRateLimitedData") is of the format:
  * *Here is the rate limited data.... Your API Key is <API_KEY> and number of tokens available for you are: <#_of_tokens_available>*

## Screenshots
![Number of Requests](https://github.com/cherrysri1997/rate-limiter/blob/master/Screenshots/Number%20of%20API%20Requests.jpg)
![HTTP Request](https://github.com/cherrysri1997/rate-limiter/blob/master/Screenshots/HTTP%20Request.jpg)
![HTTP Request Header](https://github.com/cherrysri1997/rate-limiter/blob/master/Screenshots/Header%20of%20an%20API%20Request%20with%20API%20Key.jpg)
![Successful API Reqeuest](https://github.com/cherrysri1997/rate-limiter/blob/master/Screenshots/Second%20Successful%20API%20Request%20SS.jpg)
![Response Body of Successful Request](https://github.com/cherrysri1997/rate-limiter/blob/master/Screenshots/Response%20Body%20of%20a%20Successful%20API%20Request.jpg)
![Failed API Request](https://github.com/cherrysri1997/rate-limiter/blob/master/Screenshots/Failed%20API%20Request.jpg)
![Unauthorized API Request](https://github.com/cherrysri1997/rate-limiter/blob/master/Screenshots/Unauthorized%20API%20Request.jpg)
![User with 2343_API_KEY](https://github.com/cherrysri1997/rate-limiter/blob/master/Screenshots/User%20with%20API%20Key%202343.jpg)
![User with 4323_API_KEY](https://github.com/cherrysri1997/rate-limiter/blob/master/Screenshots/User%20with%20API%20Key%204323.jpg)
