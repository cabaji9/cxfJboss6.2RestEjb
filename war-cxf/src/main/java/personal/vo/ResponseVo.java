package personal.vo;

/**
 * Created by Hyun Woo Son on 10/19/17.
 */
public class ResponseVo {

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }


    @Override
    public String toString() {
        return "ResponseVo{" +
                "errorMessage='" + errorMessage + '\'' +
                '}';
    }
}
