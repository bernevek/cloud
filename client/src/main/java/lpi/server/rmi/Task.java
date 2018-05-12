package lpi.server.rmi;

import java.io.Serializable;

public interface Task<T> extends Serializable {
    T execute();
}
