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
        switch (Double.compare(recycleBinTwoUsage, recycleBinOneUsage)) {
            case BIGGER: //LIXEIRA 2 É MAIOR QUE LIXEIRA 1
                return recycleBinTwoIsPriority
                        ? BIGGER
                        : isNotZero(recycleBinOneUsage)
                        ? recycleBinOneIsPriority
                                ? SMALLER
                                : BIGGER
                        : BIGGER;
            case SMALLER: //LIXEIRA 1 É MAIOR QUE LIXEIRA 2
                return recycleBinOneIsPriority
                        ? SMALLER
                        : isNotZero(recycleBinTwoUsage)
                        ? recycleBinTwoIsPriority
                                ? BIGGER
                                : SMALLER
                        : SMALLER;
            case EQUAL: //LIXEIRA 1 E 2 SÃO IGUAIS
                return recycleBinOneIsPriority && recycleBinTwoIsPriority || !recycleBinOneIsPriority && !recycleBinTwoIsPriority
                        ? entryTwo.getKey().compareTo(entryOne.getKey())
                        : recycleBinOneIsPriority
                                ? SMALLER
                                : BIGGER;
            default:
                throw new RuntimeException();
        }
    }

    private static boolean isNotZero(final double value) {
        return value != 0.00;
    }

}
