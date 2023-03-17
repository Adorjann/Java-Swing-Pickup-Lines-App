package data;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import model.PickupLine;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PickupLineRepository {

    private static List<PickupLine> allLines = new ArrayList<PickupLine>();

    static{
        loadData();
    }

    public static Collection<PickupLine> getAll(){
        return allLines;
    }

    private static void loadData(){

        File file = new File("src\\data\\pickupLines.json");

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = null;
        try {
            rootNode = mapper.readTree(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        JsonNode stringsNode = rootNode.get("lines");
        if (stringsNode != null && stringsNode.isArray()) {
            for (JsonNode node : stringsNode) {
                String str = node.asText();
                allLines.add(new PickupLine(str));
            }
        }
    }


}
