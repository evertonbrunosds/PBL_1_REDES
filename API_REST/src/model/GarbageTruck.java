package model;

import util.RecycleBinComparator;
import util.MyPriorityQueue;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Map;
import java.util.Map.Entry;
import org.json.JSONException;
import org.json.JSONObject;
import uefs.ComumBase.interfaces.ClientConsumer;
import static uefs.ComumBase.interfaces.Status.*;
import static uefs.ComumBase.interfaces.Status.BAD_REQUEST;
import static uefs.ComumBase.interfaces.Status.FOUND;
import static util.Constants.*;

public class GarbageTruck implements ClientConsumer {

    private final JSONObject request;
    private final DataOutputStream response;
    private final Map<String, JSONObject> recycleBinDataMap;
    private final JSONObject garbageTruckDataJSON;
    private final String recycleBinId;

    public GarbageTruck(
            final JSONObject request,
            final DataOutputStream response,
            final Map<String, JSONObject> recycleBinDataMap,
            final JSONObject garbageTruckDataJSON
    ) {
        String tmpId = null;
        try {
            tmpId = request.getString(ID);
        } catch (final JSONException ex) {
            tmpId = "";
        } finally {
            this.recycleBinId = tmpId;
        }
        this.garbageTruckDataJSON = garbageTruckDataJSON;
        this.request = request;
        this.response = response;
        this.recycleBinDataMap = recycleBinDataMap;
    }

    private boolean notContainsDevice() {
        return !request.toMap().containsKey(DEVICE);
    }

    private boolean notContainsID() {
        return !request.toMap().containsKey(ID);
    }

    private boolean notContainsUsage() {
        return !request.toMap().containsKey(USAGE);
    }

    private boolean notContainsLocation() {
        return !request.toMap().containsKey(LOCATION);
    }

    private boolean notContainsCorrespondingLocation() {
        if (userNotFound()) {
            return true;
        }
        final String requestLocation = request.getString(LOCATION);
        final String recycleBinLocation = recycleBinDataMap.get(recycleBinId).getString(LOCATION);
        return !requestLocation.equals(recycleBinLocation);
    }

    private boolean userFound() {
        return recycleBinDataMap.containsKey(recycleBinId);
    }

    private boolean userNotFound() {
        return !userFound();
    }

    private String unsuccessfulRequest(final String status) throws IOException {
        final JSONObject msg = new JSONObject();
        msg.put(STATUS, status);
        msg.put(ALL_IDS, getAllIds());
        response.flush();
        return msg.toString();
    }

    private String getRequest() {
        final Map<String, Object> dataUser = recycleBinDataMap.get(recycleBinId).toMap();
        final JSONObject msg = new JSONObject();
        msg.put(STATUS, FOUND);
        msg.put(USAGE, dataUser.get(USAGE));
        msg.put(IS_PRIORITY, dataUser.get(IS_PRIORITY));
        msg.put(LOCATION, dataUser.get(LOCATION));
        msg.put(IS_BLOCKED, dataUser.get(IS_BLOCKED));
        msg.put(ALL_IDS, getAllIds());
        return msg.toString();
    }

    private String putRequest() throws IOException {
        System.out.println("GARBAGE_TRUCK: ".concat(request.toString()));
        switch (request.getString(DEVICE)) {
            case "RECYCLE_BIN":
                final JSONObject dataUser = recycleBinDataMap.get(recycleBinId);
                dataUser.put(CLEAR, request.getString(CLEAR));
                return getRequest();
            case "GARBAGE_TRUCK":
                garbageTruckDataJSON.put(USAGE, request.getString(USAGE));
                garbageTruckDataJSON.put(LOCATION, request.getString(LOCATION));
                final JSONObject msg = new JSONObject();
                msg.put(STATUS, FOUND);
                return msg.toString();
            default:
                return unsuccessfulRequest(BAD_REQUEST);
        }
    }

    private String getAllIds() {
        final MyPriorityQueue<Entry<String, JSONObject>> entryQueue;
        entryQueue = new MyPriorityQueue<>(new RecycleBinComparator());
        recycleBinDataMap.entrySet().forEach(entry -> {
            if (entry.getValue().getString(LOCATION).equals(request.getString(LOCATION))) {
                if (entry.getValue().getString(IS_BLOCKED).equals("FALSE")) {
                    if (Double.parseDouble(entry.getValue().getString(USAGE)) > 0) {
                        entryQueue.add(entry);
                    }
                }
            }
        });
        String allRecycleBinsId = "";
        for (final Entry<String, JSONObject> entry : entryQueue) {
            allRecycleBinsId += entry.getKey().concat(";");
        }
        return allRecycleBinsId.replaceFirst(".$", "");
    }

    @Override
    public void post() throws IOException {
        response.writeUTF(
                notContainsID() || notContainsLocation() || notContainsDevice()
                        ? unsuccessfulRequest(BAD_REQUEST)
                        : notContainsCorrespondingLocation()
                                ? unsuccessfulRequest(NOT_FOUND)
                                : putRequest()
        );
    }

    @Override
    public void get() throws IOException {
        response.writeUTF(notContainsID() || notContainsLocation()
                ? unsuccessfulRequest(BAD_REQUEST)
                : notContainsCorrespondingLocation()
                        ? unsuccessfulRequest(NOT_FOUND)
                        : getRequest()
        );
    }

    @Override
    public void put() throws IOException {
        response.writeUTF(
                notContainsLocation() || notContainsDevice() || notContainsUsage()
                        ? unsuccessfulRequest(BAD_REQUEST)
                        : putRequest()
        );
    }

    @Override
    public void delete() throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
