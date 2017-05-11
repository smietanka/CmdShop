# CmdShop
Minecraft plugin with sms shop.

# Informacje
Jest to plugin SMS Shop w grze minecraft z możliwością wybrania czy chcemy, aby lista ofert pojawiała się na chacie czy w specjalnym inventory. Plugin jest rozszerzalny bardzo łatwo pod każdą usługę (oczywiście wcześniej muszę zaimplementować z tego powodu, że nie mam dostępu do wszystkich usługodawców SMS kodów). Aktualnie jest DotPay (mógłbym Platności online, ProfitSms oraz HomePay).
Wszystko wybierane w configu. Wystarczy wrzucić plugin do folderu, odpalić serwer, zresetować, skonfigurować oraz dodać oferty i po włączeniu wszystko działa.

# Zdjęcia

![konfiguracja](http://wrzuc.se/images/582b6b680de0e.png) <br />
Plik konfiguracyjny z możliwością wyboru listy ofert, usługodawcy, nazwy pliku logów lub pliku z usługami.

Plik konfiguracyjny ofert w SMS Shopie. Możliwość wyboru materiału, który widać na zdjęciu poniżej. Ceny, numeru, nazwy, opisu, komendy gdzie nazwa użytkownika jest wpisywana jako {PLAYER} oraz wiadomość zwrotna dla użytkownika po zakupionej usłudze. Każda usługa musi mieć wszystkie pola, jednak nie musi mieć w niej danych. Widać na przykładzie drugiej oferty "svip" gdzie nie ma opisu oferty, a w ofercie "vip" jest już opis wpisany i widnieje tak jak widać na zdjęciu poniżej.<br />
![produkty](http://wrzuc.se/images/582b71f0ecfb8.png)

Po wpisaniu komendy /shop<br />
![shop](http://wrzuc.se/images/582b7143515db.png)

Po wpisaniu komendy /shop list.<br />
![shop list](http://wrzuc.se/images/582b718c1b93c.png)

Po wpisaniu komemdy /shop <numer sługi lub nazwa usługi>, np. /shop 1 jest równoznaczne z /shop vip<br />
![shopNumer](http://wrzuc.se/images/582b73827d258.png)

Po wpisaniu komendy /shop <numer lub nazwa usługi> <kod zwrotny sms> 
Gdy kod jest zły<br />
![zly](http://wrzuc.se/images/582b73d581ae3.png)

Gdy kod jest dobry<br />
![dobry](http://wrzuc.se/images/582b748a7ac0f.png)

Plugin wymaga Minecraft 1.10
