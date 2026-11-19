# Workshop 12: Observer Pattern & Refactoring to Patterns

## Övergripande Mål & Utmaning
I denna workshop fokuserar vi på hur man kopplar loss systemkomponenter med hjälp av **Observer Pattern** samt hur man genomför kontrollerad refaktorering (*Refactor to Patterns*) i projektkoden (BorrowBook). Du kommer att koppla ihop händelsehantering med loggningsramverket från Workshop 11 samt refaktorera villkorslogik till rena strategimönster.

### Efter workshopen ska du kunna:
* Tillämpa **Observer Pattern** för att frikoppla händelsepublicering från händelselyssnare.
* Bygga ett händelsedrivet audit- och loggningssystem som automatiskt reagerar på domänhändelser.
* Identifiera kopplad villkorslogik i projektkod och refaktorera den i korta, testade steg mot **Strategy Pattern**.
* Säkra refaktoreringen med täckande JUnit 5-tester som garanterar noll regressionsfel.

---

## 1. Teoretisk Sammanfattning

### A. Observer Pattern (Pub/Sub)
Observer-mönstret definierar ett en-till-många-beroende mellan objekt. När ett objekt (Subject/Publisher) ändrar tillstånd notifieras alla dess registrerade observatörer (Observers/Listeners) automatiskt.
* **När det används:** När en handling i systemet (t.ex. en bokning skapas) ska utlösa flera fristående reaktioner (t.ex. skriv audit-logg, uppdatera statistik, skicka e-post) utan att bokningsmotorn behöver känna till dessa klasser.

### B. Refactor to Patterns
Att refaktorera mot designmönster handlar inte om att tvinga in mönster överallt från början. Det handlar om att känna igen kodlukter (t.ex. växande `switch`-satser, djupa nästlingar eller hårdkopplingar) och i korta steg städa koden mot ett mönster.
* **Princip:** Bygg först ett grönt JUnit 5-säkerhetsnät. Refaktorera därefter ett litet steg i taget och kör testerna kontinuerligt.

---

## 2. Förmiddag: Bygga Observer-baserad Auditloggning

### Övning 1A: Skapa Händelsemodell och Observer-gränssnitt
1. Skapa `enum BookingEventType` med värdena `CREATED`, `CANCELLED`, `RETURNED`, `OVERDUE`.
2. Skapa en `record BookingEvent(String bookingId, String memberId, String resourceId, BookingEventType type, LocalDateTime timestamp)`.
3. Skapa gränssnittet `@FunctionalInterface public interface BookingEventListener` med metoden:
   `void onBookingEvent(BookingEvent event);`

### Övning 1B: Skapa BookingEventPublisher
1. Skapa klassen `BookingEventPublisher`.
2. Håll en intern lista `List<BookingEventListener> listeners = new ArrayList<>()`.
3. Tillhandahåll metoder:
   * `public void registerListener(BookingEventListener listener)`
   * `public void publishEvent(BookingEvent event)`
4. Säkerställ att `publishEvent` loopar igenom alla lyssnare och anropar `onBookingEvent`.

### Övning 1C: Koppla ihop Observer med Loggningsramverket från Workshop 11
1. Skapa klassen `AuditLogObserver` som implementerar `BookingEventListener`.
2. `AuditLogObserver` ska ta emot en `LogAppender` (t.ex. `FileAppender` skapad via `LogAppenderFactory`).
3. När `onBookingEvent` anropas ska observatören formatera händelsen och skicka den till sin appender.

---

## 3. Eftermiddag: Refaktorering av BorrowBook (Refactor to Strategy) i en experimentel branch

### Övning 2A: Identifiera Kodlukt i Avgifterna
I projektkoden finns en klass som beräknar förseningsavgifter med nästlade `if/else`-satser baserat på resurskategori eller liknande.
1. Identifiera kodlukten (*Long Method* / *Switch Statements*).
2. Skriv en JUnit 5-testsvit som verifierar nuvarande avgiftsberäkningar för alla kategorier.

### Övning 2B: Refaktorera till Strategy Pattern
1. Skapa gränssnittet `FeeCalculationStrategy` med metoden:
   `double calculateFee(int overdueDays);`
2. Skapa konkreta strategier: `StandardEquipmentFeeStrategy`, `HeavyEquipmentFeeStrategy`, `SpecialResourceFeeStrategy`.
3. Refaktorera avgiftsservicen så att den använder en `Map<String, FeeCalculationStrategy>` för att slå upp rätt strategi i stället för `if/else`-kedjor.
4. Kör JUnit 5-testerna och verifiera att alla tester förblir gröna.

---

## 4. Reflektionsfrågor för Egenkontroll
1. Vilka fördelar ger **Observer Pattern** när du vill lägga till en ny händelsereaktion (t.ex. SMS-notifiering) i framtiden?
2. Hur hjälpte JUnit 5-testsviten dig under refaktoreringen till **Strategy Pattern**?
3. Varför är det viktigt att en enskild observatör som kraschar inte stoppar notifieringen till övriga observatörer?
