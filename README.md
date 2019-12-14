## LINK: https://github.com/juhoheinonen/cybersecuritybase-project

## Installation: should be straight-forward, if you open the project in NetBeans. Just build and run.

## FLAW 1: Cross-Site Request Forgery

### Description

If the user is logged in, it's possible using malicious hyperlink to cause the
user unknowingly submit the form with any data. 

#### Steps to reproduce:
1) The user is logged on in the vulnerable site
2) He/she receives an email with a hyperlink to a scam site.
3) He/she clicks the link
4) In the scam site there's a prefilled form which targets the action on target
site where the user is logged in. The scam site in this case could contain this html:

```
<body onload="document.forms[0].submit()">
    <form action="http://localhost:8080/" method="POST">
        <input type="hidden" name="name" value="gangster" />
        <input type="hidden" name="address" value="wrongevent" />
        <input type="submit"/>
    </form>
</body>
```

5) A signup is sent to the application with any parameters. When the user browses to http://localhost:8080 he/she
sees the signup sent there by scam site.

### Fix

Enable cross site request forgery setting in Spring.
It's normally enabled but it has been on purpose disabled in this demo application to make it more vulnerable.
If the setting is enabled then a hidden csrf element is inserted in html form and the controller
processing action of the form requires the hidden token to be included in post.
Otherwise 403 status code is returned from controller action.


## FLAW 2: Cross-Site Scripting (XSS)

### Description

A logged-in user can insert any kind of text in the guestbook. As evil user he/she could insert JavaScript code
in title or content. The flaw is caused by using th:utext to render content of model object on screen, allowing raw html.

#### Steps to reproduce:

1) Open http://localhost:8080/guestbook
2) Insert Javascript code either in title or in content and press submit.
3) The JavaScript code is run.

### Fix

At least you can us th:text instead of th:utext so that JavaScript won't be run.

## FLAW 3: SQL Injection

### Description

If an application uses sql queries that get parameters from user interface, then it's possible that the user includes malicious code in input, showing e.g. other users'
data. I had to take extra measures to make site easier to attack with sql injection, as I found it difficult to do the attack with JPARepository. I instead used sql
queries to explicitly save and fetch signups using database.

#### Steps to reproduce:

1) Go to page localhost:8080 where you can send signups. 
2) Put text "') or 1=1 --" in the Search textbox.
3) Press Submit (or Lähetä in Finnish)
4) Instead of showing only signups for current user with matching name or address, shows all signups found in signup table, no matter who uses or what search word.

### Fix

Use e.g. JPARepository or PreparedStatement to prevent sql injection. Possibly also validate that no sql is inserted in text boxes. Also important is to limit rights
of logged in user so that he/she won't have access to all tables and actions, such as dropping tables or other clients' or environments databases.

## FLAW 4: Security Misconfiguration

### Description

If an unhandled exception occurs in server-side functionality of the application, a too detailed error message is shown to users. This makes it easier for hackers
to find vulnerabilities.

#### Steps to reproduce:

1) Go to signup page.
2) Insert into Search text box one single quote '.
3) You are shown Whitelabel error Page with verbose error message containing the sql statement. This kind of information makes it really easy to prepare an sql injection attack.

### FIX:

Show custom error page to users instead of detailed error page. Probably set a remoteOnly setting for detailed error page so that developers will see the verbose error
message when they are developing the application on local machine. After deploying for testing and to production showing only custom error page with no details of the
error and writing more detailed message about error to a log available for developers and administrators.

## FLAW 5: Broken authentication

### Description

If you know the user name of a signed up user, a hacker can freely use fuzzer to try to break the password by
sending continuous requests to login method. 

#### Steps to reproduce:

1) Perform a fuzzing attack on the site's localhost:8080/login(password, submit, username) method by setting "ben" as username and using a 10000 most common passswords file
in SecLists as the payload. The password "test" should be in most common passwords lists.
2) The first response that contains Location: http://localhost:8080/ without login?error in the url is the one with
correct password.
3) Try to log in with the password.

### Fix:

There are several possible fixes. First is to force long enough passwords. It's better to force long passwords than passwords with special characters, different letter
sizes etc. Also multi-factor authentication. Also one could limit allowed log in trials from certain ip for certain times. I have also seen such solutions where a user account
is locked if too many failed attempts to sign in have been made. This latter solution could cause unfortunately some extra work for honest users who sometimes type wrong or forget
passwords.
