package algonquin.cst2335.kuma0181;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class ChatMessage {



    @ColumnInfo(name="message")
    protected String message;

    @ColumnInfo(name="TimeSent")
    protected String timeSent;

    @ColumnInfo(name="IsSentButton")
    protected boolean isSentButton;

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    public int id;

    public ChatMessage(){}

    public ChatMessage(String m, String t, boolean sent) {
        message = m;
        timeSent = t;
        isSentButton = sent;
    }
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTimeSent() {
        return timeSent;
    }

    public void setTimeSent(String timeSent) {
        this.timeSent = timeSent;
    }

    public boolean isSentButton() {
        return isSentButton;
    }

    public void setSentButton(boolean sentButton) {
        isSentButton = sentButton;
    }
}
