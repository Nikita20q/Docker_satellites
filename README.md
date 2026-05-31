## Запуск тестов

Клонируем репозиторий тестов: git clone git@github.com:Nikita20q/AutoTests.git
Переходим в склонированную папку: cd AutoTests
Запуск тестов: ./gradlew clean test
Генерация отчёта: allure serve build/allure-results