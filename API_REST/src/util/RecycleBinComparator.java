package util;

import java.util.Comparator;
import java.util.Map.Entry;
import org.json.JSONObject;
import static util.Constants.IS_PRIORITY;
import static util.Constants.*;

public class RecycleBinComparator implements Comparator<Entry<String, JSONObject>> {

    @Override
    public int compare(final Entry<String, JSONObject> entryOne, final Entry<String, JSONObject> entryTwo) {
        final JSONObject recycleBinOne;
        final JSONObject recycleBinTwo;
        final double recycleBinOneUsage;
        final double recycleBinTwoUsage;
        recycleBinOne = entryOne.getValue();
        recycleBinTwo = entryTwo.getValue();
        recycleBinOneUsage = Double.parseDouble(recycleBinOne.getString(USAGE));
        recycleBinTwoUsage = Double.parseDouble(recycleBinTwo.getString(USAGE));
        return entryOne.getKey().equals(entryTwo.getKey())
                ? EQUAL
                : (recycleBinOneUsage > 0 && recycleBinTwoUsage == 0)
                        ? BIGGER
                        : (recycleBinOneUsage == 0 && recycleBinTwoUsage > 0)
                                ? SMALLER
                                : compareConsideringEverything(entryOne, entryTwo);
    }

    private int compareConsideringEverything(final Entry<String, JSONObject> entryOne, final Entry<String, JSONObject> entryTwo) {
        final JSONObject recycleBinOne;
        final JSONObject recycleBinTwo;
        final boolean recycleBinOneIsPriority;
        final boolean recycleBinTwoIsPriority;
        final double recycleBinOneUsage;
        final double recycleBinTwoUsage;
        recycleBinOne = entryOne.getValue();
        recycleBinTwo = entryTwo.getValue();
        recycleBinOneIsPriority = recycleBinOne.getString(IS_PRIORITY).equals("TRUE");
        recycleBinTwoIsPriority = recycleBinTwo.getString(IS_PRIORITY).equals("TRUE");
        recycleBinOneUsage = Double.parseDouble(recycleBinOne.getString(USAGE));
        recycleBinTwoUsage = Double.parseDouble(recycleBinTwo.getString(USAGE));
        switch (Double.compare(recycleBinOneUsage, recycleBinTwoUsage)) {
            case BIGGER: //EM TERMOS DE USO A LIXEIRA 2 É MAIOR QUE LIXEIRA 1
                return recycleBinTwoIsPriority && !recycleBinOneIsPriority
                        ? SMALLER
                        : BIGGER;
            case SMALLER: //EM TERMOS DE USO A LIXEIRA 1 É MAIOR QUE LIXEIRA 2
                return recycleBinOneIsPriority && !recycleBinTwoIsPriority
                        ? BIGGER
                        : SMALLER;
            case EQUAL: //EM TERMOS DE USO A LIXEIRA 1 E 2 SÃO IGUAIS
                return recycleBinOneIsPriority && recycleBinTwoIsPriority || !recycleBinOneIsPriority && !recycleBinTwoIsPriority
                        ? entryOne.getKey().compareTo(entryTwo.getKey())
                        : recycleBinOneIsPriority
                                ? BIGGER
                                : SMALLER;
            default:
                throw new RuntimeException();
        }
    }

}
