# Diplom 3
Проект для задания 3: веб-приложение  
Тесты для веб-приложения [Stellar Burgers](https://stellarburgers.nomoreparties.site/).  
Элементы UI описаны с помощью Page Object.  
Тесты реализованы для браузеров Google Chrome и Yandex. 

## Документация API
[Документация API](https://code.s3.yandex.net/qa-automation-engineer/java/cheatsheets/paid-track/diplom/api-documentation.pdf)

## Технологии
- java 11
- JUnit 4.13.2
- allure-junit4 2.15.0
- maven 3.8.1
- rest-assured 5.4.0
- webdrivermanager 5.8.0
- allure-rest-assured 2.15.0
- allure-maven 2.10.0
- javafaker 0.15


## Конфигурация
Настройки запуска в файле config.properties
Для запуска в Yandex браузере:
```properties
browser=yandex
```

Для запуска в Google chrome:
```properties
browser=chrome
```

## Запуск
```shell
mvn clean test
```

## Сформировать отчет Allure
Для построения отчета использовать команду
```shell
mvn allure:serve
```