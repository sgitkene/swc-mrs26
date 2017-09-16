package ch.fhnw.swc.mrs.model;

import java.time.LocalDate;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * Represents a movie.
 * 
 */
public class Movie {

  private final IntegerProperty id = new SimpleIntegerProperty(0);
  private final BooleanProperty rented = new SimpleBooleanProperty(false);

  private final StringProperty title = new SimpleStringProperty("Untitled") {
    @Override public void set(String aTitle) {
      if (aTitle == null || aTitle.trim().isEmpty()) {
        throw new IllegalArgumentException("Title must not be null nor emtpy");
      }
      super.set(aTitle);
    }
  };
  
  private final ObjectProperty<LocalDate> releaseDate = new SimpleObjectProperty<LocalDate>() {
    @Override public void set(LocalDate aReleaseDate) {
      if (aReleaseDate == null) {
        throw new IllegalArgumentException("Release date must not be null");
      }
      super.set(aReleaseDate);
    }
  };
  
  private final IntegerProperty ageRating = new SimpleIntegerProperty(0) {
    @Override public void set(int anAgeRating) {
      super.set(anAgeRating);      
    }
  };

  /** the rental cost of the movie. */
  private ObjectProperty<PriceCategory> priceCategory = new SimpleObjectProperty<PriceCategory>() {
    @Override public void set(PriceCategory aCategory) {
      if (aCategory == null) {
        throw new IllegalArgumentException("price category must not be null");
      }
      super.set(aCategory);
    }
  };

  /** Ctor only for testing needed. */
  protected Movie() {
    this("Untitled", LocalDate.now(), RegularPriceCategory.getInstance());
  }

  /**
   * @param aTitle none.
   * @param aReleaseDate none.
   * @param aPriceCategory none.
   */
  public Movie(String aTitle, LocalDate aReleaseDate, PriceCategory aPriceCategory) {
    title.set(aTitle);
    releaseDate.set(aReleaseDate);
    priceCategory.set(aPriceCategory);
    ageRating.set(0);
  }

  /**
   * @return unique identification number of this Movie.
   */
  public Integer getId() {
    return id.get();
  }

  /**
   * @param anId set an unique identification number for this Movie.
   */
  public void setId(Integer anId) {
    this.id.set(anId);
  }

  /**  @return Java FX property for unique identification number. */
  public IntegerProperty idProperty() {
    return id;
  }

  /**
   * @return The title of this Movie.
   */
  public String getTitle() {
    return title.get();
  }

  /**
   * @param aTitle set the title of this Movie.
   */
  public void setTitle(String aTitle) {
    title.set(aTitle);
  }

  /** @return Java FX property for title. */
  public StringProperty titleProperty() {
    return title;
  }

  /**
   * @return whether this Movie is rented to a User.
   */
  public boolean isRented() {
    return rented.get();
  }

  /**
   * @param isRented set the rented status.
   */
  public void setRented(boolean isRented) {
    rented.set(isRented);
  }

  /** @return Java FX property for rented status. */
  public BooleanProperty rentedProperty() {
    return rented;
  }

  /**
   * @return the date this Movie was released.
   */
  public LocalDate getReleaseDate() {
    return releaseDate.get();
  }

  /**
   * @param aReleaseDate set the date this Movie was released.
   */
  public void setReleaseDate(LocalDate aReleaseDate) {
    releaseDate.set(aReleaseDate);
  }

  /** @return Java FX property for release date. */
  public ObjectProperty<LocalDate> releaseDateProperty() {
    return releaseDate;
  }

   
  /** @return Java FX property for age rating. */
  public IntegerProperty ageRatingProperty() {
    return ageRating;
  }
  

  /**
   * @return PriceCategory of this Movie.
   */
  public PriceCategory getPriceCategory() {
    return priceCategory.get();
  }

  /**
   * @param aPriceCategory set PriceCategory for this Movie.
   */
  public void setPriceCategory(PriceCategory aPriceCategory) {
    this.priceCategory.set(aPriceCategory);
  }

  /** @return Java FX property for PriceCategory. */
  public ObjectProperty<PriceCategory> priceCategoryProperty() {
    return priceCategory;
  }

  /**
   * @see java.lang.Object#hashCode()
   * @return none.
   */
  @Override
  public int hashCode() {
    final int prime = 31;
    int result = prime + getId();
    result = prime * result + ((getReleaseDate() == null) ? 0 : getReleaseDate().hashCode());
    result = prime * result + ((getTitle() == null) ? 0 : getTitle().hashCode());
    return result;
  }

  /**
   * @see java.lang.Object#equals(java.lang.Object)
   * @param obj none.
   * @return none.
   */
  @Override
  public boolean equals(Object obj) {
      if (this == obj) {
          return true;
      }

      if ((obj == null) || !(obj instanceof Movie)) {
          return false;
      }

      // declare other object to avoid casts in the following
      final Movie other = (Movie) obj;
      if (getId() != other.getId()) {
          return false;
      }

      if (getReleaseDate() == null) {
        if (other.getReleaseDate() != null) {
            return false;
        }
      } else if (!getReleaseDate().equals(other.getReleaseDate())) {
        return false;
      }

      if (getTitle() == null) {
        if (other.getTitle() != null) {
            return false;
        }
      } else if (!getTitle().equals(other.getTitle())) {
        return false;
      }

      return true;
 
  }
  
 }
