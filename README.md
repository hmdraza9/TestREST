# **TestREST**

***given().baseUri(baseURI).get(): "+given().baseUri(baseURI).get()***

---

**Added token auth scenario**

**Added shorter way to read JSON values**

**Added JIRA Software bypassing JAVA versions restriction:**
- JIRA runs on Java versions below 20. By setting a temporary environment variable, the bypass is achievable:
  ```bash
  export ignore_jvm_version=true
Save this in the environment variables session to make it permanent.
Added logging:

Logging works when the resource path is set in resources/resource/directory in the POM file.
Notes:

Avoid common errors: Ensure the URI is correct and cookies are set in the proper format. API calls (e.g., Create Issue) will succeed if these are correct.
JIRA Software troubleshooting: Install JIRA as an Administrator to avoid setup issues.
API Documentation:
View all APIs

# **Development Log**

- **28 Nov 2023**:  
  - Filtered response content using `queryParam`.  
  - Used `JsonPath` to fetch specific comments from the response.

- **1 Dec 2023**:  
  - Started scripting for OAuth.  
  - Refer to the accompanying Docx file for help (start from lecture 62).

- **14 Dec 2023**:  
  - Added POJO classes and required dependencies.  
  - Implemented response capture using POJOs.  
  - Output available at: `/TestREST/src/test/resources/Outputs/POJOResponse_14Dec2023.txt`.  
  - Logger added for the course class; iterated dynamically through the array size.

- **15 Dec 2023**:  
  - Renamed POJO package.  
  - Prepared JSON body using POJO classes.

- **16 Dec 2023**:  
  - Updated logging configuration:  
    - **STDOUT**: Prints all log lines to the log file.  
    - Restricts to specific package logs using: `appender.file.name = STDOUT`.  
  - Added `Data` class for fetching all products from an E-commerce site.
