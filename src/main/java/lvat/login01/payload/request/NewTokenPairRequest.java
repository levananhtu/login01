package lvat.login01.payload.request;

public class NewTokenPairRequest {
    private String refreshToken;

    public NewTokenPairRequest() {
    }

    public NewTokenPairRequest(String refreshToken) {
        this.refreshToken = refreshToken;
    }


    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

}
