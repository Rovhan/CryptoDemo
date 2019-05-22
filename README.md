# CryptoDemo
Assessment for PharmaPartners

## Logs

Als de applicatie draaiend is, kun je deze vinden van alle http requests op /actuator/httptrace

## Design

De applicatie is gemaakt als een onion architecture. Hieronder benoem ik de lagen van binnen naar buiten.

#### Domain
Hierin staan alle domeinobjecten. In dit geval alleen crypto. 
#### Repositories
Hierin staan alle repositories. Er is een pagingandsorting repository voor crypto. Hier waren geen tests voor nodig gezien ik geen extra functionaliteiten heb toegevoegd. 
#### Services
Gezien er alleen CRUD acties zijn heb ik deze weggelaten voor nu. 
#### Controllers
Hier ook maar één controller nodig. De CryptoController maakt gebruik van de crypto repository via injection. Deze wordt geïnjecteerd via de constructor om goed te kunnen testen. 

## Tests

Er zijn twee test classes. 1 om de controller te testen en 1 om de builder van Crypto te testen. De controller wordt getest doormiddel van mockMVC

## Gegevens
Door de Dataloader is de database gevuld bij het opstarten van de applicatie. 



