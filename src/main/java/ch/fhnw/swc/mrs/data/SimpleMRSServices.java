package ch.fhnw.swc.mrs.data;

import java.io.InputStreamReader;
import java.io.Reader;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import ch.fhnw.swc.mrs.model.MRSServices;
import ch.fhnw.swc.mrs.model.Movie;
import ch.fhnw.swc.mrs.model.PriceCategory;
import ch.fhnw.swc.mrs.model.Rental;
import ch.fhnw.swc.mrs.model.User;

/**
 * A simple implementation of the MRS Services.
 */
public class SimpleMRSServices implements MRSServices {

  private List<Movie> movieList = new ArrayList<>();
  private List<User> userList = new ArrayList<>();
  private List<Rental> rentalList = new ArrayList<>();

  private int movieId = 0;
  private int userId = 0;
  private int rentalId = 0;

  @Override
  public Movie createMovie(Movie m) {
    if (!movieList.contains(m)) {
      m.setId(movieId++);
      movieList.add(m);
      return m;
    }
    return null;
  }

  @Override
  public List<Movie> getAllMovies() {
    return Collections.unmodifiableList(movieList);
  }

  @Override
  public List<Movie> getAllMovies(boolean rented) {
    List<Movie> result = new ArrayList<>();
    for (Movie m : movieList) {
      if (rented == m.isRented()) {
        result.add(m);
      }
    }
    return result;
  }

  @Override
  public Movie getMovieById(long id) {
    for (Movie m : movieList) {
      if (m.getId() == id) {
        return m;
      }
    }
    return null;
  }

  @Override
  public boolean updateMovie(Movie movie) {
    return true;
  }

  @Override
  public boolean deleteMovie(Movie m) {
    return movieList.remove(m);
  }

  @Override
  public List<User> getAllUsers() {
    return Collections.unmodifiableList(userList);
  }

  @Override
  public User getUserById(long id) {
    for (User u : userList) {
      if (u.getId() == id) {
        return u;
      }
    }
    return null;
  }

  @Override
  public User getUserByName(String name) {
    for (User u : userList) {
      if (u.getName().equals(name)) {
        return u;
      }
    }
    return null;
  }

  @Override
  public User createUser(User u) {
    if (!userList.contains(u)) {
      u.setId(userId++);
      userList.add(u);
      return u;
    }
    return null;
  }

  @Override
  public boolean updateUser(User u) {
    return true;
  }

  @Override
  public boolean deleteUser(User u) {
    return userList.remove(u);
  }

  @Override
  public List<Rental> getAllRentals() {
    return Collections.unmodifiableList(rentalList);
  }

  @Override
  public boolean createRental(User u, Movie m, LocalDate d) {
    if (u != null && m != null && !m.isRented() && !d.isAfter(LocalDate.now())) {
      Rental r = new Rental(u, m, d);
      r.setId(rentalId++);
      return rentalList.add(r);
    }
    return false;
  }

  @Override
  public boolean returnRental(Rental r) {
    r.getMovie().setRented(false);
    return r.getUser().getRentals().remove(r);
  }

  /**
   * Initialize the "server component".
   */
  public void init() {
    readMovies();
    readUsers();
    readRentals();
    movieId = movieList.size() + 1;
    userId = userList.size() + 1;
    rentalId = rentalList.size() + 1;
  }

  private void readMovies() {
    try (Reader in = new InputStreamReader(getClass().getResourceAsStream("/data/movies.csv"))) {
      Iterable<CSVRecord> movies = CSVFormat.EXCEL.withFirstRecordAsHeader().withHeader(MovieHeaders.class)
          .withDelimiter(';').parse(in);
      for (CSVRecord m : movies) {
        int id = Integer.parseInt(m.get(MovieHeaders.ID));
        String title = m.get(MovieHeaders.Title);
        LocalDate releaseDate = LocalDate.parse(m.get(MovieHeaders.ReleaseDate));
        PriceCategory pc = PriceCategory.getPriceCategoryFromId(m.get(MovieHeaders.PriceCategory));
        boolean isRented = Boolean.parseBoolean(m.get(MovieHeaders.isRented));
//        int ageRating = Integer.parseInt(m.get(MovieHeaders.AgeRating));
        Movie movie = new Movie(title, releaseDate, pc);
        movie.setId(id);
        movie.setRented(isRented);
        movieList.add(movie);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void readUsers() {
    try (Reader in = new InputStreamReader(getClass().getResourceAsStream("/data/users.csv"))) {
      Iterable<CSVRecord> users = CSVFormat.EXCEL.withFirstRecordAsHeader().withHeader(UserHeaders.class)
          .withDelimiter(';').parse(in);
      for (CSVRecord u : users) {
        int id = Integer.parseInt(u.get(UserHeaders.ID));
        String surname = u.get(UserHeaders.Surname);
        String firstname = u.get(UserHeaders.FirstName);
//        LocalDate birthdate = LocalDate.parse(u.get(UserHeaders.Birthdate));
        User user = new User(surname, firstname);
        user.setId(id);
        userList.add(user);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private void readRentals() {
    try (Reader in = new InputStreamReader(getClass().getResourceAsStream("/data/rentals.csv"))) {
      Iterable<CSVRecord> rentals = CSVFormat.EXCEL.withFirstRecordAsHeader().withHeader(RentalHeaders.class)
          .withDelimiter(';').parse(in);
      for (CSVRecord r : rentals) {
        int id = Integer.parseInt(r.get(RentalHeaders.ID));
        LocalDate rentaldate = LocalDate.parse(r.get(RentalHeaders.RentalDate));
        int userId = Integer.parseInt(r.get(RentalHeaders.UserID));
        int movieId = Integer.parseInt(r.get(RentalHeaders.MovieID));
        User u = getUserById(userId);
        Movie m = getMovieById(movieId);
        Rental rental = new Rental(u, m, rentaldate);
        rental.setId(id);
        rentalList.add(rental);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  enum MovieHeaders {
    ID, Title, ReleaseDate, PriceCategory, AgeRating, isRented
  }

  enum UserHeaders {
    ID, Surname, FirstName, Birthdate
  }

  enum RentalHeaders {
    ID, RentalDate, UserID, MovieID
  }

}
