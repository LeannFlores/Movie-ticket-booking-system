package oopMTBS;

import java.io.Serializable;

public class Movie implements Serializable {
    private static final long serialVersionUID = 1L;

	public static final String movie = null;

    private String title;
    private String genre;
    private String duration;
    private String cinema;
    private String showDate;
    private String showtime;
    private int seatsAvailable;
    private double price;

    public Movie(String title, String genre, String duration, String cinema, String showDate, String showtime, int seatsAvailable, double price) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.cinema = cinema;
        this.showDate = showDate;
        this.showtime = showtime;
        this.seatsAvailable = seatsAvailable;
        this.price = price;
    }

    // Getter and setter methods for each property

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getCinema() {
        return cinema;
    }

    public void setCinema(String cinema) {
        this.cinema = cinema;
    }

    public String getShowDate() {
        return showDate;
    }

    public void setShowDate(String showDate) {
        this.showDate = showDate;
    }

    public String getShowtime() {
        return showtime;
    }

    public void setShowtime(String showtime) {
        this.showtime = showtime;
    }

    public int getSeatsAvailable() {
        return seatsAvailable;
    }

    public void setSeatsAvailable(int seatsAvailable) {
        this.seatsAvailable = seatsAvailable;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return "Movie{" +
                "title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                ", duration='" + duration + '\'' +
                ", cinema='" + cinema + '\'' +
                ", showDate='" + showDate + '\'' +
                ", showtime='" + showtime + '\'' +
                ", seatsAvailable=" + seatsAvailable +
                ", price=" + price +
                '}';
    }
}
