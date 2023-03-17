import service.PickupLineService;
import view.PickupLineListView;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            // create the browser
            PickupLineListView browser = new PickupLineListView(PickupLineService.getAllLines());
            browser.display();
        });
    }
}

