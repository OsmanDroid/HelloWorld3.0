package osmandroid.venturesity.helloworld3o;

public class ChatModel {

    String msgText;
    String msgUser;

    public ChatModel()
    {
    }

    public ChatModel(String msgText, String msgUser) {
        this.msgText = msgText;
        this.msgUser = msgUser;
    }

    public String getMsgText() {
        return msgText;
    }

    public void setMsgText(String msgText) {
        this.msgText = msgText;
    }

    public String getMsgUser() {
        return msgUser;
    }

    public void setMsgUser(String msgUser) {
        this.msgUser = msgUser;
    }
}
