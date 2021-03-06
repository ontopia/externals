# Externals

This repository contains several libraries used by the main Ontopia repository, that are not available in the Maven Central repository, 
or have been patched during Ontopia development. We re-release these artifacts within the net.ontopia groupId to avoid naming conflicts.

## List of artifacts

### TMAPI 

[The TMAPI project](http://www.tmapi.org/) provides a cross-engine Topic Maps API. Ontopia uses a copy the 2.0.2 version of this API. 
There were **no** modifications made to this code. 

*To be removed when TMAPI is added to maven central*

### TMAPI-Tests

[The TMAPI project](http://www.tmapi.org/) provides cross-engine Topic Maps API test cases in order to the the cross engine API. Ontopia 
uses a copy the 2.0.2 version of these test cases. There were **no** modifications made to this code. 

*To be removed when TMAPI is added to maven central*

### CXTM Tests

[The CXTM project](http://cxtm-tests.sourceforge.net/) provides a suite of tests for Topic Maps implementations, based around the various 
Topic Maps syntaxes. Automated download of the test data was replaced with maven artifact, and reproduced into this project for release 
to maven central. There were **no** modifications made to the actual test cases.

