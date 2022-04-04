package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.json.JSONObject;
import uefs.ComumBase.enums.Method;

public class RecycleBin implements Consumer {

    private static final List<Integer> ALL_IDS = new ArrayList<>();
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final String id;
    private final Map<String, JSONObject> jsonMap;

    public RecycleBin(final DataInputStream inputStream, final DataOutputStream outputStream, final Map<String, JSONObject> jsonMap) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        ALL_IDS.sort(Integer::compareTo);
        id = Integer.toString(ALL_IDS.get(ALL_IDS.size() - 1) + 1);
        this.jsonMap = jsonMap;
    }

    public RecycleBin(final String id, final DataInputStream inputStream, final DataOutputStream outputStream, final Map<String, JSONObject> jsonMap) {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.id = id;
        this.jsonMap = jsonMap;
    }

    /**
     * Método responsável por buscar dados da lideira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void get() throws IOException {
        if (jsonMap.containsKey(id)) {
            outputStream.flush();
            outputStream.writeUTF(jsonMap.get(id).toString());
        } else {
            final JSONObject newUser = new JSONObject();
            newUser.put("METHOD", Method.toString(Method.get));
            newUser.put("DEVICE", "RECYCLE_BIN");
            newUser.put("ID", id);
        }
    }

    /**
     * Método responsável por criar dados da lixeira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void post() throws IOException {
        if (jsonMap.containsKey(id));
    }

    /**
     * Método responsável por atualizar dados da lixeira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void put() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    /**
     * Método responsável por apagar dados da lideira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void delete() throws IOException {
        throw new UnsupportedOperationException("Not supported yet.");
    }

}
