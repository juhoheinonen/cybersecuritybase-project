# Instructions:
You will then write a 1000 word report (hard limits: 800-1500) that pinpoints the flaws and the describes how they can be fixed.
The report must follow the following structure:

## LINK: https://github.com/juhoheinonen/cybersecuritybase-project

## FLAW 1: Cross-Site Request Forgery

### Description
If the user is logged in, it's possible using malicious hyperlink to cause the
user unknowingly submit the form with any data. Steps the vulnerability can be
used:
1) The user is logged on in the vulnerable site
2) He/she receives an email with a hyperlink to a scam site
3) He/she clicks the link
4) In the scam site there's a prefilled form which targets the action on target
site where the user is logged in
5) A sign up is sent to the application with any parameters.

### Fix

Enable cross site request forgery setting in Spring.
It's normally enabled but it has been on purpose disabled in this demo application.
If it's on then a hidden csrf element is inserted in html form and the controller
processing action of the form requires the hidden token to be included in post.
Otherwise 403 status code is returned from controller action.

## FLAW 2: Cross-Site Scripting (XSS)

<how to fix it>

## FLAW 3:
<description of flaw 3>
<how to fix it>

## FLAW 4:
<description of flaw 4>
<how to fix it>

## FLAW 5:
<description of flaw 5>
<how to fix it>
