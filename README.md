# TestREST

given().baseUri(baseURI).get(): "+given().baseUri(baseURI).get()

Added token auth scenario

Added shorter way to read JSON values

Added JIRA Software bypassing JAVA versions restriction, JIRA runs on java version below 20, but with setting temporary env variable, byopass is achiveable

export ignore_jvm_version=true
this sets the env variable temporarily, save it properly in ENV variable session so that it is permanent

Added log, log works when you have given resource path is given at - resources/resource/directory in POM

Wasting time due to small errors, URI incorrect, Cookie not set in correct format. Finally API Call success for Create Issue also. Also, JIRA Software gave me hard time as I didn't install it As An Administrator, rest all good.

#URL for all APIs
https://developer.atlassian.com/cloud/jira/platform/rest/v3/api-group-issue-attachments/#api-group-issue-attachments

28 Nov 2023 - Filter out the contents of response using queryParam, using JsonPath to fetch the respective comment out of 5 of them

1 Dec 2023 - Started scripting for OAuth. Docx file for help, Start from lecture 62

14 Dec 2023 - Added POJO classes and further required dependencies, need to implement in response capture, simple pojo methods implemented, working. output at: /TestREST/src/test/resources/Outputs/POJOResponse_14Dec2023.txt; Logger added in course class, iterated through the size of array length dynamically

15 Dec 2023 - renamed pojo package, json body prepared with pojo class
16 Dec 2023 - #putting STDOUT instead of LOGFILE prints all log lines to log file, else prints only lines from mentioned package [appender.file.name = STDOUT]; added Data class file for get all products from ECOM site