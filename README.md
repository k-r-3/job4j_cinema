<h1>
job4j_cinema
</h1>

[![Build Status](https://app.travis-ci.com/k-r-3/job4j_cinema.svg?branch=master)](https://app.travis-ci.com/k-r-3/job4j_cinema)

<h3>
Description :
</h3>
<p>
This project represent ticket purchase web-service 
</p>
<h4>
Used technologies :
</h4>
<ul>
<li>Servlets</li>
<li>JDBC</li>
<li>Slf4j</li>
<li>Checkstyle</li>
</ul>
<p>
Web-Service has a 3 screens
</p>
<ul>
<li>
Cinema hall schema. Reserved places cannot be selected
</li>
</ul>
<ul>

![ScreenShot](images/main.png)
<li>
Payment form. Phone and email are unique for each user, 
data in this pair should not coincide with the data of another user 
</li>
</ul>

![ScreenShot](images/pay.png)
<ul>
<li>
Ticket with username and seat number
</li>
</ul>

![ScreenShot](images/ticket.png)
<h3>
Configurations :
</h3>
<p>
To app boot is necessary use Apache Tomcat and edit run configuration. 
This configuration should have follow points : 
</p>
<ul>
<li>
'http://localhost:8080/cinema' as start page url;
</li>
<li>
'job4j_cinema:war exploded' as build artifact;
</li>
<li>
'/cinema' as application context
</li>
</ul>
<h3>Contact</h3>
<p>If You have any question, please contact me:</p>
<p>https://t.me/roman_kozlov</p>
