![](https://img.shields.io/badge/Java-%3E%3D%208-orange)
![](https://img.shields.io/badge/Maven-3-red)
![](https://img.shields.io/badge/Spring%20boot-%202.5.2-green)
![](https://img.shields.io/badge/-Bootstrap-blueviolet)
![](https://img.shields.io/badge/-Thymeleaf-darkgreen)
![](https://img.shields.io/badge/PostgreSQL-%3E%3D%209-informational)
![](https://img.shields.io/badge/-JDBC-blue)
![](https://img.shields.io/badge/-H2%20-blueviolet)
![](https://img.shields.io/badge/-Liquibase-blue)
![](https://img.shields.io/badge/JUnit-%3E%3D%204-yellowgreen)
![](https://img.shields.io/badge/-Mockito-brightgreen)
![](https://img.shields.io/badge/-checkstyle-lightgrey)

# job4j_cinema

 - [О проекте]()
 - [Технологии]() 
 - [Как использовать]()  

О проекте
=
сайт по покупки билетов в кинотеатр. Интерфейс:<br>

 - страница index.html - афиша кинотетра.<br>
 - страница hall.html - выбор сеанса и покупка билетов.<br>
 - страница personlInfo.html - личный кабинет пользователя.<br>
 - страницы авторизации loginPage.html и addUser.html регистрации.<br>

В системе следующие модели: 
- User (Пользователи).<br>
- Session (Сеансы).<br>
- Ticket (Купленный билет).<br>
- MovieHall (имитирует зал кинотетра).<br>
- Seat (Ряд и место).<br>

Технологии
=
 * Frontend - **HTML**, **CSS**, **BOOTSTRAP**, **Thymeleaf**;
 * Backend - **Java 12**, **JDBC**, **Spring Boot**;
 * Сборщик проектов - **Maven**;
 * СУБД - **PostgreSQL**, **H2**;
 * библиотека для управления обновлений схем БД - **Liquibase**;
 * библиотека для модульного тестирова    ния - **JUnit**;
 * библиотеки для тестирования - **JUnit**, **Mockito**;
 * Инструмент анализа стиля кода - **Checkstyle**;

Как использовать
=
1. В самом начале работы приложения мы попадает на страницу авторизации:<br>
![Image of login](https://github.com/IvanPavlovets/job4j_cinema/blob/master/images/loginPage.png)<br>
Варианты возможных дейсвий - **войти** под своей учетной записью или **перейти на страницу регистрацции**. <br>
После входа под своей учетной записью на панели навигации появиться строка с именем учетно записи,<br>
нажав на которую произойдет выход из учетной записи.<br>
___

2. Страница регистрации предлагает завести свою учетную запись:<br>

![Image of registration](https://github.com/IvanPavlovets/job4j_cinema/blob/master/images/registration.png)<br>
___

3. Вкладка Афиша:<br>

![Image of index](https://github.com/IvanPavlovets/job4j_cinema/blob/master/images/hall.png)<br>
На этой странице показаны все сеансы системы:<br>
 - название фильма.<br> 
 - формат фильма.<br> 
 - размер зала.<br> 
 - цена билета.<br> 
___

4. Вкладка Кинотеатр - страница выбора фильма:<br>

![Image of hall](https://github.com/IvanPavlovets/job4j_cinema/blob/master/images/afisha.png)<br>
На странице можно сделать выбор фильма с последующем формирование данных билета<br> 
___

5. Вкладка Кинотеатр - страница выбора ряда и места в конозале<br>

![Image of selectSeat](https://github.com/IvanPavlovets/job4j_cinema/blob/master/images/seats.png)<br>
На странице осуществляться выбор ряда и места<br> 
___

6. Вкладка Кинотеатр - страница с итоговой информацией<br>

![Image of selectSeat](https://github.com/IvanPavlovets/job4j_cinema/blob/master/images/payment.png)<br>
На странице осуществляться подтверждение выбранных ранее данных и происходит покупка билета<br> 
___

7. Вкладка Личный кабинет - итогавая страница перехода после покупки<br>

![Image of personalInfo](https://github.com/IvanPavlovets/job4j_cinema/blob/master/images/personalInfo.png)<br>
После покупки белета осуществляеться переход на вкладку личного кабинета:<br>
 - личные данные пользователя.<br> 
 - информация о купленых билетах.<br> 
Также возможно **редактирование** личнных данных пользователя
___
