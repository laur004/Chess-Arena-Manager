# Chess-Arena-Manager
**Chess Arena Manager** este o aplicatie care gestioneaza turnee de șah.

## Metode disponibile

### 1. **newTournament(String tournamentName, String organizerFirstName, String organizerLastName)**
   - Creează un nou turneu cu numele specificat și organizatorul.

### 2. **removeTournament(int i)**
   - Șterge un turneu existent la indexul specificat.

### 3. **showTournaments()**
   - Afișează toate turneele disponibile.

### 4. **showTournamentPlayers()**
   - Afișează lista de jucători înregistrați într-un turneu selectat.

### 5. **addPlayer()**
   - Adaugă un jucător într-un turneu existent. Jucătorul poate fi adăugat cu sau fără un ID FIDE.

### 6. **removePlayer()**
   - Elimină un jucător dintr-un turneu, pe baza ID-ului FIDE al acestuia.

### 7. **startTournament()**
   - Începe un turneu și generează sistemul de împerechere(momentan doar Entities.Round-Robin).

### 8. **showRounds()**
   - Afișează toate partidele fiecărei runde dintr-un turneu.

### 9. **setPlayersPoints()**
   - Permite setarea punctelor pentru toți jucătorii dintr-un turneu.

### 10. **showRanking()**
   - Afișează clasamentul turneului pe baza punctelor și ratingului jucătorilor.

### 11. **setTournamentArbiter()**
   - Permite atribuirea unui arbitru unui turneu, specificând numele și prenumele acestuia.

### 12. **showTournamentArbiter()**
   - Afișează arbitru atribuit unui turneu, dacă există.


## Sisteme de imperechere disponibile
### -Entities.Round-Robin

