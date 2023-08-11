# Задача:

> Есть большой xml файл WP_PL на 100_000 неповторяющихся, но и не упорядоченных элементов. 
>                               
>       <DOCNUM>0000017115154750</DOCNUM>(уникален) и 
>       <MHDHB>30</MHDHB>(случайное число до 100). 
> Нужно распарсить этот файл как можно быстрее и сохранить в БД postgres в двух таблицах связанных один к одному по ключу(полю) RCVPRN. 
> 
> **Первая** — таблица все поля из EDI_DC40 
> 
> **Вторая** — из E1WPA01 
>
> Связанные значения один к одному
>
>                                <RCVPRN>0000004821</RCVPRN>
>
>                                <FILIALE>0000004821</FILIALE>
> Построить запрос где найти все документы DOCNUM в которых значение поля MHDHB < 50

# Решение
Для парсинга файла будем использовать высокопроизводительную реализацию `Stax XML API`  [aalto-xml](https://github.com/FasterXML/aalto-xml) предоставляющую возможность неблокирующего (асинхронного) парсинга xml файлов

В условии задания сказано, что в файле хранится 100_000 записей. Попробуем оценить размер файла.
Примем средний размер одной строчки таблицы хранящейся в xml файле за ≈740 символов. Размер одного символа при хранении в кодировке UTF-8 от 1 до 4 байт.
Приблизительный размер файла будет равен 740 * 2 Byte * 100000 = 148000000 Byte ≈ 141.143798828125 MB
Такой размер обычно не является критичным для считывания файла в память целиком за раз.

Были написаны несколько реализаций чтения файла
* Считывание файла в память целиком `WpplFullFileReader`
* Поэтапное считывание файла `WpplBufferFileReader`
* Поэтапное считывание файла и многопоточная обработка записей `WppParallelReader`

Каждая из реализаций оптимальна для своего набора требований

## Валидация

Был создан файл схемы [scheme.xsd](scheme.xsd) для валидации **предоставленного** входного файла и класс валидатора [WpplFileValidator.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fwppl%2Fvalidator%2FWpplFileValidator.java).  

Для валидации `WP_PL.xml` файла может быть использован метод isValidXmlFile(String filePathName, String schemaDocPath) класса [WpplService.java](src%2Fmain%2Fjava%2Fcom%2Fexample%2Fwppl%2Fservice%2FWpplService.java)

## Тесты 
Для проверки работоспособности были написаны тесты.   
Тесты требуют подключение PostgeSQL DB. Параметры подключений должны быть прописаны в [application.yaml](src%2Ftest%2Fresources%2Fapplication.yaml)

Был добавлен [BigFileGenerator.java](src%2Ftest%2Fjava%2Fcom%2Fexample%2Fgenerator%2FBigFileGenerator.java) позволяющий сгенерировать тестовый входной файл на любое количество записей.  
Для файлов размером 10000 записей и 100000 были проведены замеры скорости работы (Чтение, Парсинг).  
Для каждой ячейки было взято лучшее время из нескольких запусков.  

| Размер файла   | BufferFileReader | FullFileReader | ParallelReader (2 потока) | ParallelReader (4 потока) | ParallelReader (8 потоков) |
|----------------|:----------------:|---------------:|--------------------------:|--------------------------:|---------------------------:|
| 1 запись       |     3 millis     |       1 millis |                  1 millis |                  2 millis |                   3 millis |
| 10000 записей  |    452 millis    |     303 millis |                194 millis |                221 millis |                 244 millis |
| 100000 записей |   3285 millis    |    3066 millis |               2340 millis |               1389 millis |                1727 millis |


