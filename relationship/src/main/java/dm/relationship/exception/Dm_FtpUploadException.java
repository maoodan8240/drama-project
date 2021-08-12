package dm.relationship.exception;

public class Dm_FtpUploadException extends DmBaseException {
    public Dm_FtpUploadException(Throwable t) {
        super(t);
    }

    public Dm_FtpUploadException(String msg) {
        super(msg);
    }

    public Dm_FtpUploadException(String msg, Throwable t) {
        super(msg, t);
    }
}
