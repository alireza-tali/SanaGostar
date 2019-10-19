package vavkhan.com.sanagostar.Net;

import java.util.List;

import androidx.annotation.Nullable;

public abstract class RestApiListListener<T>  {
    /**
     * Implement on Request Error
     *
     * @param message Message For Error
     * @param code    Code of Error If Exist
     */
    public abstract void Error(String message, @Nullable String code);

    /**
     * Implement On Request Success
     *
     * @param message Success Message
     */
    public abstract void Success(List<T> message);
}
