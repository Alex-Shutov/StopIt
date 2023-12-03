## Описание
Это декстопное приложение для блокирования NSFW контента на компьютере
посредством классификации скриншотов экрана компьютера.

## Проект
Папка *src* содержит 2 пакета
java.com.example содержит основной код приложения
 - controllers - содержит логику взаимодействия с fxml
 -  services - содержит бизнес логику 
 -  utils - содержит утилиты для работы с winapi и подобными библиотеками

Пакет *resources* содержит следующую структуру папок
 - images - картинки, использующиеся в приложении
 - screens - файлы, содержащие разметку экрана
 - scripts - скрипты, которые запускаются в сервисах
 - styles/css - файлы css.
Можно настроить создание scss и перевод их в css, настраиватся в IntellijIDEA

## Для запуска необходимо: 
- [Python 3.7](https://www.python.org/downloads/release/python-370/) или выше
- JDK
Перед запуском нужно установить пакет NudeNet командой:  
`pip install -U git+https://github.com/platelminto/NudeNet`  
Также в Windows должен быть установлен [Maven](https://maven.apache.org/download.cgi)

## Как запустить ?
1. `cd ./JavaFXApp`  
2. `mvn install`
3. `mvn clean javafx:run`