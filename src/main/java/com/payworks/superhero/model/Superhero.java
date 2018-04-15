package com.payworks.superhero.model;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.payworks.superhero.helpers.JsonFunctions;

/**
 * <h1> Superhero </h1>
 * 
 * <p>
 * Since version 0.0.2:
 * allies can be empty/null
 * 
 * @author Norman Moeschter-SChenck
 * @version 0.0.2
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
	private Date firstAppearance;
	
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
	// format JAVA Date/mongodb ISODate to 'yyyy-MM-dd' (2018-04-15)
	@JsonFormat(pattern="yyyy-MM-dd") 
	public Date getFirstAppearance() {
		return firstAppearance;
	}
	public void setFirstAppearance(Date firstAppearance) {
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
		} else if (firstAppearance.compareTo(other.firstAppearance) != 0)
			return false;
				
		return true;
	}
	
	@Override
	public String toString() {	
		return JsonFunctions.getJsonFromObject(this);
	}
	
	
}
