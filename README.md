"# javaSchoolFinal" 
Должны быть установлены: Maven 3.6.3, Java 17, PostgreSQL 14
1. создать базу данных на postgresql с именем news
username: postgres
password: 5558
ИЛИ
перейти в javaSchoolFinal-main\src\main\resources\application.properties
изменить username и password на свои значения
2. выполнить mvn spring-boot:run (через cmd из папки javaSchoolFinal-main)
3. открыть в браузере http://localhost:8080/
Для заполнения базы тестовыми данными скопировать schema.sql, в базе данных(например, через pgAdmin): таблица article, CREATE Script, вставить скопированный текст
