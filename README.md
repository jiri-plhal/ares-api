# ARES API

Tato aplikace zobrazuje vybrané informace o společnostech z obchodního rejstříku. Aplikace běží na portu 8000 a adresa homepage aplikace je http://localhost:8000/.

Tato aplikace je napsaná v jazyce JAVA. Aplikace je postavena Spring Frameworku (s využitím Spring Boot) a využívá MVC architekturu.
Projekt je vytvořen s pomocí Apache Maven (struktura, řízení externích knihoven a frameworků). Frontend aplikace je zobrazen pomocí knihovny Thymeleaf.

Na homepage je potřeba zadat IČO firmy, o které chceme zobrazit informace. Aplikace komunikuje s API ARES (administrativní registr ekonomických subjektů) a získává všechny informace o subjektu ve formátu XML. Z těchto mnoha informací aplikace vybere některé požadované základní údaje o firmě (využití JAVA DOM parseru) a tyto informace zobrazí uživateli.

V aplikace zobrazují základní informace o firmě. Mimo jiné i statutární orgány společnosti. Aktuální verze aplikace dokáže zobrazit jako statutární orgány společnosti pouze fyzické osoby (české právní zákony umožňují, aby u některých společností byl statutárním orgánem i právnická osoba). Právnické osoby jako statutární orgány nejsou zobrazeny vůbec.
