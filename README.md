# Workshop-12

*Kommentar*

**Workshop 12** senare delar förutsätter en fil med nästlade if-satser, det är såklart helt beroende på er faktiska kod och kan ändras. 
Gör något motsvarande.

## Kör observer-exemplet

Kompilera och kör från projektets rot:

```powershell
javac src\*.java
java -cp src Main
```

`Main` publicerar en orderhändelse som `AuditLogObserver` tar emot och skriver ut.
