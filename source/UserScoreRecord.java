
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Saves player's time on a level to their profile.
 *
 * @author Tom
 * @author Stephen
 */
public class UserScoreRecord {
    private String user;
    private String score;

    /**
     * UserScoreRecord constructor.
     * @param user username of profile
     * @param score score from level
     */
    public UserScoreRecord(final String user, final String score) {
        this.user = user;
        this.score = msToTime(Long.parseLong(score));
    }

    /**
     * Converts level play time from milliseconds to more easily read format.
     * @param score time spent playing level
     * @return time in preferred format
     */
    private String msToTime(final Long score) {
        Instant instant = Instant.ofEpochMilli(score);
        ZonedDateTime zdt = ZonedDateTime.ofInstant(instant,
                ZoneOffset.UTC);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "mm:ss:SSS");
        String output = formatter.format(zdt);
        return output;
    }

    /**
     * Getter for score.
     * @return level score
     */
    public String getScore() {
        return this.score;
    }

    /**
     * Getter for profile username.
     * @return profile username
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Setter for profile username.
     * @param user profile username
     */
    public void setUser(final String user) {
        this.user = user;
    }

    /**
     * Setter for level score.
     * @param score level score
     */
    public void setScore(final String score) {
        this.score = score;
    }

    /**
     * Generic toString.
     * @return preferred toString format
     */
    @Override
    public String toString() {
        return getUser() + " " + getScore();
    }
}
