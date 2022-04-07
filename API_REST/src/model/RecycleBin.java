package model;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.ClientConsumer;
import static uefs.ComumBase.interfaces.Status.*;

public class RecycleBin implements ClientConsumer {

    private static final class Constant {

        private static final IdGenerator ID_GENERATOR = new IdGenerator();
        private static final String UNDETERMINED = "UNDETERMINED";
        private static final String IS_BLOCKED = "IS_BLOCKED";
        private static final String STATUS = "STATUS";
        private static final String USAGE = "USAGE";
        private static final String ID = "ID";

    }
    private final String id;
    private final DataInputStream request;
    private final DataOutputStream response;
    private final Map<String, JSONObject> jsonMap;

    public RecycleBin(final String id, final DataInputStream inputStream, final DataOutputStream outputStream, final Map<String, JSONObject> jsonMap) throws InterruptedException {
        this.id = (Constant.UNDETERMINED.equals(id) || id == null) ? Constant.ID_GENERATOR.getStringId() : id;
        this.request = inputStream;
        this.response = outputStream;
        this.jsonMap = jsonMap;
    }

    private String successfulRequest() throws IOException {
        final JSONObject dataUser = new JSONObject(jsonMap.get(id).toMap());
        dataUser.put(Constant.STATUS, OK);
        response.flush();
        return dataUser.toString();
    }

    private String unsuccessfulRequest() throws IOException {
        final JSONObject newDataUser = new JSONObject();
        newDataUser.put(Constant.STATUS, NOT_FOUND);
        newDataUser.put(Constant.ID, id);
        response.flush();
        return newDataUser.toString();
    }

    /**
     * Método responsável por buscar dados da lideira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void get() throws IOException {
        final boolean userFound = jsonMap.containsKey(id);
        response.writeUTF(userFound ? successfulRequest() : unsuccessfulRequest());
    }

    /**
     * Método responsável por atualizar dados da lixeira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void put() throws IOException {
        final boolean userFound = jsonMap.containsKey(id);
        if (userFound) {
            jsonMap.put(id, new JSONObject(request.readUTF()));
            final JSONObject dataUser = new JSONObject(jsonMap.get(id).toMap());
            dataUser.put(Constant.STATUS, OK);
            response.flush();
            response.writeUTF(dataUser.toString());
        } else {
            final JSONObject newDataUser = new JSONObject();
            newDataUser.put(Constant.STATUS, NOT_FOUND);
            newDataUser.put(Constant.ID, id);
        }
    }

    /**
     * Método responsável por apagar dados da lideira.
     *
     * @throws IOException Refere-se a algum possível erro de entrada/saída.
     */
    @Override
    public void delete() throws IOException {
        if (jsonMap.containsKey(id)) {
            final JSONObject dataUser = new JSONObject(jsonMap.get(id).toMap());
            dataUser.put(Constant.STATUS, OK);
            response.flush();
            response.writeUTF(dataUser.toString());
        } else {
            final JSONObject newDataUser = new JSONObject();
            newDataUser.put(Constant.STATUS, NOT_FOUND);
            newDataUser.put(Constant.ID, id);
        }
    }

    @Override
    public void post() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
