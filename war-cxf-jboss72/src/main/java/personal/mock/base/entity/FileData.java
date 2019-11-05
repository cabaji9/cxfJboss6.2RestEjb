package personal.mock.base.entity;

import java.util.Arrays;

/**
 * Created by Hyun Woo Son on 5/10/18
 **/
public class FileData {

    private String fileName;
    private String contentType;
    private byte[] file;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getContentType() {
        return contentType;
    }

    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    public byte[] getFile() {
        return file;
    }

    public void setFile(byte[] file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "FileData{" +
                "fileName='" + fileName + '\'' +
                ", contentType='" + contentType + '\'' +
                ", file=" + Arrays.toString(file) +
                '}';
    }
}
