package com.leveluplove.leveluplove.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity-Klasse zur Speicherung von Profilbildern
 * Repräsentiert die Datenbanktabelle "profile_images"
 */
@Entity
@Table(name = "profile_images") // Definition des Tabellennamens in der Datenbank
@Data // Lombok-Annotation für automatische Getter/Setter/toString/hashCode/equals
@NoArgsConstructor // Lombok-Annotation für einen leeren Konstruktor
@AllArgsConstructor // Lombok-Annotation für einen Konstruktor mit allen Feldern
public class ProfileImage {

    // Konstanten für Validierungen und Begrenzungen
    
    // Maximale Anzahl von Bildern pro Benutzerprofil
    public static final int MAX_IMAGES_PER_USER = 10;

    // Maximale Dateigröße in Bytes (400KB)
    public static final long MAX_FILE_SIZE = 400 * 1024;

    @Id // Primärschlüssel dieser Entity
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Automatische ID-Generierung (Auto-Increment)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY) // Mehrere Bilder können zu einem Profil gehören
    @JoinColumn(name = "user_profile_id") // Fremdschlüsselspalte in der Datenbank
    // LAZY: Profilreferenz wird erst bei Bedarf geladen (Performance-Optimierung)
    private UserProfile userProfile; // Referenz auf das zugehörige Benutzerprofil

    private String fileName; // Original-Dateiname des Bildes
    
    private String fileType; // MIME-Typ des Bildes (z.B. image/jpeg)
    
    private Long fileSize; // Dateigröße in Bytes
    
    // Anmerkung: filePath wurde entfernt, da alle Bilddaten in PostgreSQL gespeichert werden
    // private String filePath;
    
    @Lob // Annotation für große Objekte (Large Objects)
    @Column(columnDefinition = "BYTEA") // PostgreSQL-spezifischer Typ für Binärdaten
    private byte[] imageData; // Direkte Speicherung der Bilddaten in der Datenbank
    
    private boolean isProfilePicture = false; // Kennzeichnet das Hauptprofilbild (Standard: false)
    
    // Hochladezeitpunkt für Sortierung und Anzeige
    @Column(name = "upload_date")
    private java.time.LocalDateTime uploadDate; // Zeitpunkt des Uploads
    
    /**
     * Konstruktor für Dateien, die extern gespeichert werden (nur Pfad, keine Binärdaten)
     * 
     * @param userProfile Das zugehörige Benutzerprofil
     * @param fileName Original-Dateiname
     * @param fileType MIME-Typ der Datei
     * @param fileSize Größe der Datei in Bytes
     * @param filePath Pfad zur Datei im Dateisystem
     */
    public ProfileImage(UserProfile userProfile, String fileName, String fileType, Long fileSize, String filePath) {
        this.userProfile = userProfile;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        // this.filePath = filePath; // Aktuell nicht verwendet, da Bilder in der DB gespeichert werden
        this.uploadDate = java.time.LocalDateTime.now(); // Aktuelles Datum und Uhrzeit setzen
    }
    
    /**
     * Konstruktor für Dateien, die direkt in der Datenbank gespeichert werden
     * 
     * @param userProfile Das zugehörige Benutzerprofil
     * @param fileName Original-Dateiname
     * @param fileType MIME-Typ der Datei
     * @param fileSize Größe der Datei in Bytes
     * @param imageData Binärdaten des Bildes als Byte-Array
     */
    public ProfileImage(UserProfile userProfile, String fileName, String fileType, Long fileSize, byte[] imageData) {
        this.userProfile = userProfile;
        this.fileName = fileName;
        this.fileType = fileType;
        this.fileSize = fileSize;
        this.imageData = imageData;
        this.uploadDate = java.time.LocalDateTime.now(); // Aktuelles Datum und Uhrzeit setzen
    }

    /**
     * Methode zum korrekten Setzen des Profilbild-Flags
     * 
     * @param isProfilePicture true, wenn dieses Bild als Profilbild verwendet werden soll
     */
    public void setProfilePicture(boolean isProfilePicture) {
        this.isProfilePicture = isProfilePicture;
    }
}
