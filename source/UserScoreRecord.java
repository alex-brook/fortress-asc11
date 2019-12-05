
import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class UserScoreRecord {
    private String user;
    private String score;
    public UserScoreRecord(String user, String score){
        this.user = user;
        this.score = msToTime(Long.parseLong(score));
    }

    private String msToTime(Long score){
        Instant instant = Instant.ofEpochMilli (score);
        ZonedDateTime zdt = ZonedDateTime.ofInstant ( instant , ZoneOffset.UTC );

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern ( "mm:ss:SSS" );
        String output = formatter.format(zdt);
        return output;
    }

    public String getScore(){
        return this.score;
    }

    public String getUser(){
        return this.user;
    }
    public void setUser(String user){
        this.user = user;
    }
    public void setScore(String score){
        this.score = score;
    }

    public String toString(){
        return getUser() + " " + getScore();
    }
}
