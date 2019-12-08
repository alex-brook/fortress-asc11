
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Saves player's time on a level to their profile
 * Javadoc comments added by Stephen
 *
 * @author
 */
public class UserScoreRecord {
    private String user;
    private String score;
    public UserScoreRecord(String user, String score){
        this.user = user;
        this.score = msToTime(Long.parseLong(score));
    }

    /**
     *
     * @param score
     * @return
     */
    private String msToTime(Long score){
        Instant instant = Instant.ofEpochMilli (score);
        ZonedDateTime zdt = ZonedDateTime.ofInstant ( instant , ZoneOffset.UTC );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "mm:ss:SSS" );
        String output = formatter.format(zdt);
        return output;
    }

    /**
     *
     * @return
     */
    public String getScore(){
        return this.score;
    }

    /**
     *
     * @return
     */
    public String getUser(){
        return this.user;
    }

    /**
     *
     * @param user
     */
    public void setUser(String user){
        this.user = user;
    }

    /**
     *
     * @param score
     */
    public void setScore(String score){
        this.score = score;
    }

    /**
     *
     * @return
     */
    public String toString(){
        return getUser() + " " + getScore();
    }
}
