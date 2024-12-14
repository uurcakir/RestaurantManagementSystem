package Models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTime {

	private LocalDateTime myDateObj;
	private DateTimeFormatter myFormatObj;

	// Constructor
	public DateTime() {
		this.myDateObj = LocalDateTime.now(); // Güncel tarihi alıyoruz
		this.myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"); // Formatı belirliyoruz
	}

	// DateTime objesini döndüren metod
	public String getFormattedDate() {
		return myDateObj.format(myFormatObj); // Formatlanmış tarihi döndürür
	}
}
