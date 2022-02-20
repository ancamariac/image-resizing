# Image Resizing using Pixel Replication Method

This is a project developed for the Java Web Applications subject. The input image is a 24-bit BMP image and the processed photo is resized corresponding to a scale factor.

# Tech Stack

- Eclipse Mars 2
- Java 8
- Threads

# Cerinte de implementare

1. Imaginea sursa este BMP (fisier) – 24bit BMP – RGB
2. Pentru procesare se folosesc doar algoritmi si/ sau secvente de cod low-level (nu se
accepta utilizare de metode de procesare altele decat cele scrise in tema)
3. Include in totalitate conceptele POO – incapsulare, mostenire, polimorphism,
abstractizare
4. Codul sursa respecta absolut toate “Coding standards”. Codul sursa este comentat
5. Operatii de lucru cu fisiere
6. Operatii de intrare de la tastatura si prin parametri liniei de comanda pentru asignarea
fisierelor de intrare, parametri / setarile / optiunile de executie si pentru asignarea
fisierelor de iesire
7. Aplicatia trebuie sa fie multimodulara (impartirea in clase cu ierarhii – chiar cu cost in
timp de procesare). Cel putin 3 niveluri de mostenire
8. Include varargs
9. Include Interface (cu o clasa care o implementeaza)
10.Include Clase Abstracte si metode abstracte si clase concrete care extind clasele
abstracte
11.Include tratarea exceptiilor
12.Aplicatia contine 2 pachete: Pachetul 1 sa contina aplicatia de test, pachetul 2 sa
contina restul claselor
13.Aplicatia este multithreading cu urmatoarele cerinte:

    a. un nou thread este alocat citirii din fisier a imaginii sursa – Producer Thread.
    Intra in Not Runnable dupa citirea a fiecarui sfert (1/4) de informatie
    
    b. un nou thread (Consumer Thread) este alocat consumului informatiei furnizate
    de Producer Thread. Se utilizeaza “multithread communication” (notify).
    
    c. Se insereaza output la consola si sleep (1000) pentru a evidentia etapele
    comunicarii.

    d. Se folosesc elementele de sincronizare pentru protectia la o eventuala
    interferenta cu alte posibile threaduri

    e. Dupa terminarea consumult intregii informatii de imagine sursa, se incepe
    procesarea
    
# Etapele de executie ale aplicatiei

- citire informatii de identificare fisier sursa (fisiere sursa) si citire informatii de
identificare fisier destinatie
- citire fisier sursa
- procesare imagine
- scriere fisier destinatie
- inregistrare timp de executie fiecare etapa
- afisare rezultate timp de procesare fiecare etapa

