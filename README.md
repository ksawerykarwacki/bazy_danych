Oracle podniesiony na porcie 1521 i z sidem xe. jezeli inny zmień w src/man/resources/application.yaml (linia 5)

Do uruchomienia potrzebny przygotowany pusty schemat aplikacji w bazie, z uzytkownika systemowego zawolac: 

`create user app identified by app;`

`grant dba to app;`

po wszystkm zawolac z intellij run z gradle'a lub z konsoli wpisac `./gradlew clean run`

Przy pierwszym uruchomieniu powinien zainstalowac sie schemat bazy z nasza appka

