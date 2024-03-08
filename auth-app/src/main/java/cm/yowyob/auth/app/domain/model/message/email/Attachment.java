package cm.yowyob.auth.app.domain.model.message.email;


import lombok.AllArgsConstructor;

@AllArgsConstructor
public class Attachment {
    public byte[] attachment;
    public String mime;
    public String name;
}
