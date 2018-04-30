package com.payworks.superhero.model;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.payworks.superhero.helpers.JsonFunctions;
import com.payworks.superhero.validation.ValidDate;

/**
 * <h1> Superhero </h1>
 * 
 * <p>
 * Since version 0.0.2:
 * allies can be empty/null
 * <p>
 * Since version 0.0.3:
 * compare {@code firstAppearance} by values of year, month and day only
 * <p>
 * Since version 0.0.4:
 * added {@code @ValidDate} annotation to validate empty value in JSON string as incorrect Java.Date
 * <p>
 * Since version 0.0.5:
 * replaced {@code Date} by {@code LocalDate} in order to only allow valid months (1..12) and days
 * 
 * @author Norman Moeschter-SChenck
 * @version 0.0.5
 * @since 2018-04-14
 *
 */
@Document(collection = "superheros")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Superhero {
	@Id
	private String id;
	@NotBlank
	private String name;
	@NotBlank
	private String pseudonym;
	@NotBlank
	private String publisher;
	@NotNull
	private List<String> powers;
	private List<String> allies;
	@ValidDate
	// format JAVA Date/mongodb ISODate to 'yyyy-MM-dd' (2018-04-15)	
	@JsonFormat(pattern="yyyy-MM-dd")
	private LocalDate firstAppearance;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPseudonym() {
		return pseudonym;
	}
	public void setPseudonym(String pseudonym) {
		this.pseudonym = pseudonym;
	}
	
	public String getPublisher() {
		return publisher;
	}
	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}
	public List<String> getPowers() {
		return powers;
	}
	public void setPowers(List<String> powers) {
		this.powers = powers;
	}
	public List<String> getAllies() {
		return allies;
	}
	public void setAllies(List<String> allies) {
		this.allies = allies;
	}	
	public LocalDate getFirstAppearance() {
		return firstAppearance;
	}
	public void setFirstAppearance(LocalDate firstAppearance) {
		this.firstAppearance = firstAppearance;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		result = prime * result + ((name == null) ? 0 : name.hashCode());
		result = prime * result + ((publisher == null) ? 0 : publisher.hashCode());
		result = prime * result + ((pseudonym == null) ? 0 : pseudonym.hashCode());
		result = prime * result + ((powers == null) ? 0 : powers.hashCode());
		result = prime * result + ((allies == null) ? 0 : allies.hashCode());
		result = prime * result + ((firstAppearance == null) ? 0 : firstAppearance.hashCode());
		return result;
	}	
	
	@Override
	public boolean equals(Object obj) {
		if (obj == this) return true;
		if (obj == null) return false;
		Superhero other = (Superhero) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		if (publisher == null) {
			if (other.publisher != null)
				return false;
		} else if (!publisher.equals(other.publisher))
			return false;
		if (pseudonym == null) {
			if (other.pseudonym != null)
				return false;
		} else if (!pseudonym.equals(other.pseudonym))
			return false;
		if (powers == null) {
			if (other.powers != null)
				return false;
		} else if (!CollectionUtils.isEqualCollection(powers, other.powers))
			return false;
		if (allies == null) {
			if (other.allies != null)
				return false;
		} else if (!CollectionUtils.isEqualCollection(allies, other.allies))
			return false;
		
		if (firstAppearance == null) {
			if (other.firstAppearance != null)
				return false;
		} else { 
			Calendar calendar = new GregorianCalendar();
			Date date = Date.from(this.getFirstAppearance().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			calendar.setTime(date);		
			int thisYear = calendar.get(Calendar.YEAR);
			int thisMonth = calendar.get(Calendar.MONTH);
			int thisDay = calendar.get(Calendar.DAY_OF_MONTH);
			date = Date.from(other.getFirstAppearance().atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
			calendar.setTime(date);
			int otherYear = calendar.get(Calendar.YEAR);
			int otherMonth = calendar.get(Calendar.MONTH);
			int otherDay = calendar.get(Calendar.DAY_OF_MONTH);
			
			if ( thisYear != otherYear || thisMonth != otherMonth || thisDay != otherDay )
				return false;
		}
				
		return true;
	}
	
	@Override
	public String toString() {	
		return JsonFunctions.getJsonFromObject(this);
	}
	
	
}
