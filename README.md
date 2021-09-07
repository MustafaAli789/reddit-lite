# reddit-lite

A Reddit Clone built with Spring Boot MVC, Jpa/Hibernate + PostgreSQL, JWT authentication/authorization with Spring Security angular and rxjs. 
Will expect to use Firebase cloud storage for media storage along with websocket for live notifs and user chatting using STOMP.

Deployed to heroku at: https://reddit-lite-ang.herokuapp.com/

This project is using a dummy SMTP server so emails wont actually be recieved by anyone except for me. You can use the following test account however:
- Username: TestUser4
- Password: password5

### Todo
- Verification token expiry date
- Mapstruct
- Should make username and subreddit name unique
- View posts ona specific subreddit page ---> /view-subreddit endpoint
-JUnit + Mockito Tests

- Infinite scrolling
- Comment threads
- U.I update
- Live notifs for subreddit subscriptions
- User chatting
- Audio Huddles
