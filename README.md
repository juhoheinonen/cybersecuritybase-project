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

### Description

A logged-in user can insert any kind of text in the guestbook. As evil user he/she could insert JavaScript code
in title or content. The flaw is caused by using th:utext to render content of model object on screen, allowing raw html.

### Fix

At least you can us th:text instead of th:utext so that JavaScript won't be run.

## FLAW 3: SQL Injection

### Description

I replaced using SignupRepository with just sql queries. On page /signup, you can search with e.g. string "') or 1=1 to" to display all signups, also of other users.

### Fix

Use e.g. JPARepository or PreparedStatement to prevent sql injection.

## FLAW 4: Security Misconfiguration

### Description

This is closely related to flaw 3: SQL Injection.
Steps to reproduce:
1) Go to signup page.
2) Insert into Search text box one single quote '.
3) You are shown Whitelable error Page with verbose error message containing the sql statement.

### FIX:

Show custom error page instead of detailed error page. Write to log about error.

## FLAW 5: Broken authentication

### Description

If you know the user name of a signed up user, a hacker can freely use fuzzer to try to break the password by
sending continuous requests to login method. The first response that contains Location: http://localhost:8080/ without login?error in the url is the one with
correct password.

### Fix:

There are several possible fixes. First is to force long enough passwords. It's better to force long passwords than passwords with special characters, different letter
sizes etc. Also multi-factor authentication. Also one could limit allowed log in trials from certain ip, or lock the whole account, even though this could cause difficulties for
honest users.
