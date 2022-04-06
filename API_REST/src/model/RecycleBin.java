package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import org.json.JSONObject;

public class RecycleBin implements Consumer {

    private static final List<Integer> ALL_IDS = new ArrayList<>();
    private static final Semaphore SEMAPHORE = new Semaphore(1);
    private final DataInputStream inputStream;
    private final DataOutputStream outputStream;
    private final String id;
    private final Map<String, JSONObject> jsonMap;

    public RecycleBin(final String id, final DataInputStream inputStream, final DataOutputStream outputStream, final Map<String, JSONObject> jsonMap) throws InterruptedException {
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.id = "UNDETERMINED".equals(id) ? gerateNewId() : id;
        this.jsonMap = jsonMap;
    }

    private static String gerateNewId() throws InterruptedException {
        try {
            SEMAPHORE.acquire();
            ALL_IDS.sort(Integer::compareTo);
            final int lastUsableIndex = ALL_IDS.size() - 1;
            final int highestValue = ALL_IDS.get(lastUsableIndex);
            final int newHighestValue = highestValue + 1;
            ALL_IDS.add(newHighestValue);
            return Integer.toString(newHighestValue);
        } finally {
            SEMAPHORE.release();
        }
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
            newUser.put("STATUS", "404");
            newUser.put("ID", id);
        }
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
