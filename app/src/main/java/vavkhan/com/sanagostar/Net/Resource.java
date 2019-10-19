package vavkhan.com.sanagostar.Net;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import static vavkhan.com.sanagostar.Net.Status.ERROR;
import static vavkhan.com.sanagostar.Net.Status.SUCCESS;

/**
 * This class use as Responce for request to server
 * @param <T>
 */
public class Resource<T> {
    @NonNull
    public final Status status;
    @Nullable
    public final T data;
    @Nullable
    public final String message;

    private Resource(@NonNull Status status, @Nullable T data, @Nullable String message) {
        this.status = status;
        this.data = data;
        this.message = message;
    }

    /**
     * Set Success as Responce
     * @param data    Data model returning from server
     * @param <T>     Model type
     * @return Resource<T>
     */
    public static <T> Resource<T> success(@NonNull T data) {
        return new Resource<>(SUCCESS, data, null);
    }

    /**
     * Set An Error as Responce
     * @param msg    Error Message
     * @param data   Data Model of exist
     * @param <T>    Model type
     * @return Resource<T>
     */
    public static <T> Resource<T> error(String msg, @Nullable T data) {
        return new Resource<>(ERROR, data, msg);
    }

    /*public static <T> Resource<T> loading(@Nullable T data) {
        return new Resource<>(LOADING, data, null);
    }*/
}
