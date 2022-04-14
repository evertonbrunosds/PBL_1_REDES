package model;

import java.util.List;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.Queue;
import org.json.JSONException;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.ClientConsumer;
import static util.Constants.*;

public class GarbageTruck implements ClientConsumer {

    private final JSONObject request;
    private final DataOutputStream response;
    private final JSONObject dataGarbageTruck;
    private final Map<String, JSONObject> dataRecycleBinMap;
    private final String recycleBinId;

    public GarbageTruck(final JSONObject request, final DataOutputStream response, final JSONObject dataGarbageTruck, final Map<String, JSONObject> dataRecycleBinMap) {
        String tmpId = null;
        try {
            tmpId = request.getString(ID);
        } catch (final JSONException ex) {
            tmpId = "";
        } finally {
            this.recycleBinId = tmpId;
        }
        this.request = request;
        this.response = response;
        this.dataGarbageTruck = dataGarbageTruck;
        this.dataRecycleBinMap = dataRecycleBinMap;
    }

    private boolean notContainsDevice() {
        return request.toMap().containsKey(DEVICE);
    }

    private boolean notContainsID() {
        return request.toMap().containsKey(ID);
    }

    private boolean notContainsUsage() {
        return request.toMap().containsKey(USAGE);
    }

    private boolean userFound() {
        return dataRecycleBinMap.containsKey(ID);
    }

    private String getRequest() {
        final JSONObject msg = new JSONObject();
        final Queue<Entry<String, JSONObject>> entryQueue;
        entryQueue = new PriorityQueue<>((entryOne, entryTwo) -> {
            final JSONObject recycleBinOne;
            final JSONObject recycleBinTwo;
            final boolean recycleBinOneIsPriority;
            final boolean recycleBinTwoIsPriority;
            final double recycleBinOneUsage;
            final double recycleBinTwoUsage;
            final int isBooleanPriority;
            final int isUsagePriority;
            recycleBinOne = entryOne.getValue();
            recycleBinTwo = entryTwo.getValue();
            recycleBinOneIsPriority = recycleBinOne.getString(IS_PRIORITY).equals("TRUE");
            recycleBinTwoIsPriority = recycleBinTwo.getString(IS_PRIORITY).equals("TRUE");
            recycleBinOneUsage = Double.parseDouble(recycleBinOne.getString(USAGE));
            recycleBinTwoUsage = Double.parseDouble(recycleBinTwo.getString(USAGE));
            isBooleanPriority = Boolean.compare(recycleBinOneIsPriority, recycleBinTwoIsPriority);
            isUsagePriority = Double.compare(recycleBinOneUsage, recycleTwoTwoUsage);
            if (isBooleanPriority > 0) { //LIXEIRA 1 É PRIORIDADE E LIXEIRA 2 NÃO
                
            } else if (isBooleanPriority < 0) { //LIXEIRA 2 É PRIORIDADE E LIXEIRA 1 NÃO

            } else { //LIXEIRA 1 E 2 SÃO IGUAIS EM TERMOS DE PRIORIDADE

            }
            return 0;
        });
        for (final Entry<String, JSONObject> entry : dataRecycleBinMap.entrySet()) {

        }
        msg.put(ALL_IDS, allRecycleBinsId.replaceFirst(".$", ""));

        return msg.toString();
    }

    @Override
    public void post() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void get() throws IOException {

    }

    @Override
    public void put() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void delete() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
