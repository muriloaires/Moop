package mobi.moop.model.rotas.reponse;

import java.util.List;

/**
 * Created by murilo aires on 26/07/2017.
 */

public class GenericListResponse<T> {
    private List<T> data;

    public List<T> getData() {
        return data;
    }
}
